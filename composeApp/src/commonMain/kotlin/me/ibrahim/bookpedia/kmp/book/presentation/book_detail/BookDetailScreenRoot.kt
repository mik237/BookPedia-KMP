package me.ibrahim.bookpedia.kmp.book.presentation.book_detail

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import bookpedia_kmp.composeapp.generated.resources.Res
import bookpedia_kmp.composeapp.generated.resources.description_unavailable
import bookpedia_kmp.composeapp.generated.resources.languages
import bookpedia_kmp.composeapp.generated.resources.pages
import bookpedia_kmp.composeapp.generated.resources.rating
import bookpedia_kmp.composeapp.generated.resources.synopsis
import me.ibrahim.bookpedia.kmp.book.presentation.book_detail.components.BlurredImageBackground
import me.ibrahim.bookpedia.kmp.book.presentation.book_detail.components.BookChip
import me.ibrahim.bookpedia.kmp.book.presentation.book_detail.components.TitledContent
import me.ibrahim.bookpedia.kmp.theme.SandYellow
import org.jetbrains.compose.resources.stringResource
import kotlin.math.round


@Composable
fun BookDetailScreenRoot(
    viewModel: BookDetailViewModel, onBackClick: () -> Unit
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    BookDetailScreen(getState = { state }, onAction = { action ->
        when (action) {
            BookDetailActions.OnBackClick -> onBackClick()
            else -> viewModel.onAction(action)
        }

    })
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun BookDetailScreen(getState: () -> BookDetailState, onAction: (BookDetailActions) -> Unit) {
    val state by rememberUpdatedState(newValue = getState.invoke())

    BlurredImageBackground(state = state, onAction = onAction) {
        state.selectedBook?.let { book ->
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top,
                modifier = Modifier
                    .padding(start = 16.dp, end = 16.dp, top = 16.dp, bottom = 20.dp)
                    .verticalScroll(rememberScrollState())
            ) {
                Text(
                    text = book.title,
                    style = MaterialTheme.typography.headlineSmall,
                    textAlign = TextAlign.Center
                )

                Text(
                    text = book.authors.joinToString(),
                    style = MaterialTheme.typography.titleMedium,
                    textAlign = TextAlign.Center
                )


                Row(
                    modifier = Modifier
                        .padding(vertical = 8.dp),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    book.averageRating?.let { rating ->
                        TitledContent(
                            title = stringResource(Res.string.rating),
                        ) {
                            BookChip {
                                Row {
                                    Text(text = "${round(rating * 10) / 10.0}")
                                    Icon(
                                        Icons.Default.Star, contentDescription = null,
                                        modifier = Modifier.size(20.dp),
                                        tint = SandYellow
                                    )
                                }
                            }
                        }
                    }
                    TitledContent(
                        title = stringResource(Res.string.pages),
                    ) {
                        BookChip {
                            Text(text = "${book.numPages}")
                        }
                    }

                }

                if (book.languages.isNotEmpty()) {
                    TitledContent(
                        title = stringResource(Res.string.languages),
                        modifier = Modifier
                            .padding(vertical = 8.dp)
                    ) {
                        FlowRow(
                            horizontalArrangement = Arrangement.Center,
                            modifier = Modifier
                                .wrapContentSize()
                        ) {
                            book.languages.forEach { language ->
                                BookChip(
                                    size = ChipSize.SMALL,
                                    modifier = Modifier.padding(2.dp)
                                ) {
                                    Text(
                                        text = language.uppercase(),
                                        style = MaterialTheme.typography.bodyMedium
                                    )
                                }
                            }
                        }
                    }
                }

                Text(
                    text = stringResource(Res.string.synopsis),
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier
                        .align(Alignment.Start)
                        .fillMaxWidth()
                        .padding(
                            top = 24.dp,
                            bottom = 8.dp
                        )
                )

                if (state.isLoading) {
                    CircularProgressIndicator()
                } else {
                    Text(
                        text = if (book.description.isNullOrBlank()) {
                            stringResource(Res.string.description_unavailable)
                        } else book.description
                    )
                }
            }
        }
    }
}