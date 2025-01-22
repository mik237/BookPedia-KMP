package me.ibrahim.bookpedia.kmp.book.presentation.book_list

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import bookpedia_kmp.composeapp.generated.resources.Res
import bookpedia_kmp.composeapp.generated.resources.no_favorite_books
import bookpedia_kmp.composeapp.generated.resources.no_search_results
import me.ibrahim.bookpedia.kmp.book.domain.Book
import me.ibrahim.bookpedia.kmp.book.presentation.book_list.components.BookList
import me.ibrahim.bookpedia.kmp.book.presentation.book_list.components.BookSearchBar
import me.ibrahim.bookpedia.kmp.book.presentation.book_list.components.BookTabs
import me.ibrahim.bookpedia.kmp.theme.DarkBlue
import me.ibrahim.bookpedia.kmp.theme.DesertWhite
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun BookListScreenRoot(
    viewModel: BookListViewModel = koinViewModel<BookListViewModel>(),
    onBookClick: (Book) -> Unit
) {

    val state by viewModel.state.collectAsStateWithLifecycle()
    BookListScreen(state = state, onAction = { action ->
        when (action) {
            is BookListActions.OnBookClick -> onBookClick(action.book)
            else -> viewModel.onAction(action)
        }
    })
}


@Composable
fun BookListScreen(
    state: BookListState,
    onAction: (BookListActions) -> Unit
) {

    val keyboardController = LocalSoftwareKeyboardController.current
    val pagerState = rememberPagerState { 2 }

    val searchResultListState = rememberLazyListState()
    val favoriteBooksListState = rememberLazyListState()

    LaunchedEffect(state.searchResults) {
        searchResultListState.animateScrollToItem(0)
    }

    LaunchedEffect(state.selectedIndex) {
        pagerState.animateScrollToPage(state.selectedIndex)
    }

    LaunchedEffect(pagerState.currentPage) {
        onAction(BookListActions.OnTabSelected(pagerState.currentPage))
    }

    Column(
        modifier = Modifier.fillMaxSize()
            .background(DarkBlue)
            .statusBarsPadding(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        BookSearchBar(
            searchQuery = state.searchQuery,
            onSearchQueryChange = { onAction(BookListActions.OnSearchQueryChange(it)) },
            onImeSearch = { keyboardController?.hide() },
            modifier = Modifier
        )
        Surface(
            modifier = Modifier
                .weight(1f)
                .widthIn(max = 700.dp)
                .fillMaxSize(),
            color = DesertWhite,
            shape = RoundedCornerShape(topStart = 30.dp, topEnd = 30.dp)
        ) {

            Column {
                BookTabs(
                    state = state,
                    onAction = onAction
                )

                HorizontalPager(
                    state = pagerState,
                    modifier = Modifier
                        .widthIn(max = 700.dp)
                        .fillMaxWidth()
                        .weight(1f)
                ) { page: Int ->
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f),
                        contentAlignment = Alignment.TopCenter
                    ) {
                        when (page) {
                            0 -> {
                                if (state.isLoading) {
                                    CircularProgressIndicator()
                                } else {
                                    when {
                                        state.errorMessage != null -> {
                                            Text(
                                                state.errorMessage.asString(),
                                                textAlign = TextAlign.Center,
                                                style = MaterialTheme.typography.headlineSmall,
                                                color = MaterialTheme.colorScheme.error
                                            )
                                        }

                                        state.searchResults.isEmpty() -> {
                                            Text(
                                                stringResource(Res.string.no_search_results),
                                                textAlign = TextAlign.Center,
                                                style = MaterialTheme.typography.headlineSmall,
                                                color = MaterialTheme.colorScheme.error
                                            )
                                        }

                                        else -> {
                                            BookList(
                                                books = state.searchResults,
                                                onBookClick = {
                                                    onAction(BookListActions.OnBookClick(it))
                                                },
                                                modifier = Modifier.fillMaxSize(),
                                                scrollState = searchResultListState
                                            )
                                        }
                                    }
                                }
                            }

                            1 -> {
                                if (state.favoriteBooks.isEmpty()) {
                                    Text(
                                        stringResource(Res.string.no_favorite_books),
                                        textAlign = TextAlign.Center,
                                        style = MaterialTheme.typography.headlineSmall
                                    )
                                } else {
                                    BookList(
                                        books = state.favoriteBooks,
                                        onBookClick = {
                                            onAction(BookListActions.OnBookClick(it))
                                        },
                                        modifier = Modifier.fillMaxSize(),
                                        scrollState = favoriteBooksListState
                                    )
                                }
                            }
                        }
                    }
                }


            }
        }

    }
}