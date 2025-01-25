package me.ibrahim.bookpedia.kmp.book.presentation.book_detail

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import bookpedia_kmp.composeapp.generated.resources.Res
import bookpedia_kmp.composeapp.generated.resources.go_back
import coil3.compose.rememberAsyncImagePainter
import me.ibrahim.bookpedia.kmp.theme.DarkBlue
import me.ibrahim.bookpedia.kmp.theme.DesertWhite
import org.jetbrains.compose.resources.stringResource


@Composable
fun BookDetailScreenRoot(
    viewModel: BookDetailViewModel,
    onBackClick: () -> Unit
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    BookDetailScreen(getState = { state }, onAction = { action ->
        when (action) {
            BookDetailActions.OnBackClick -> onBackClick()
            else -> viewModel.onAction(action)
        }

    })
}

@Composable
fun BookDetailScreen(getState: () -> BookDetailState, onAction: (BookDetailActions) -> Unit) {

    val state by rememberUpdatedState(newValue = getState.invoke())

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .widthIn(max = 700.dp)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(0.3f)
                    .fillMaxWidth()
                    .background(DarkBlue)
            ) {

                var imageLoadResult by remember { mutableStateOf<Result<Painter>?>(null) }
                val painter = rememberAsyncImagePainter(
                    model = state.selectedBook?.imageUrl,
                    onSuccess = {
                        val size = it.painter.intrinsicSize
                        imageLoadResult = if (size.width > 1 && size.height > 1) {
                            Result.success(it.painter)
                        } else {
                            Result.failure(Exception("Invalid Image Dimensions"))
                        }
                    },
                    onError = {
                        it.result.throwable.printStackTrace()
                        imageLoadResult = Result.failure(it.result.throwable)
                    })

                imageLoadResult?.let { result ->
                    if (result.isSuccess) {
                        Image(
                            contentScale = ContentScale.Crop,
                            painter = painter,
                            contentDescription = "",
                            modifier = Modifier.fillMaxSize()
                                .blur(15.dp)
                        )
                    }
                }

                IconButton(
                    onClick = { onAction(BookDetailActions.OnBackClick) },
                    modifier = Modifier
                        .align(Alignment.TopStart)
                        .padding(horizontal = 16.dp)
                        .statusBarsPadding()
                        .background(brush = Brush.radialGradient(listOf(Color.White.copy(alpha = 50f), Color.Transparent)))
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = stringResource(Res.string.go_back),
                        tint = Color.Black
                    )
                }
            }
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(0.7f)
                    .background(DesertWhite)
            ) {
                Text(
                    text = "${state.selectedBook?.authors}",
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        }
    }
}