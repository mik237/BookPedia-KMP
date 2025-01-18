package me.ibrahim.bookpedia.kmp.book.presentation.book_list

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import me.ibrahim.bookpedia.kmp.book.domain.Book
import me.ibrahim.bookpedia.kmp.book.presentation.book_list.components.BookSearchBar
import me.ibrahim.bookpedia.kmp.theme.DarkBlue
import me.ibrahim.bookpedia.kmp.theme.DesertWhite
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
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    color = DesertWhite,
                    shape = RoundedCornerShape(topStart = 30.dp, topEnd = 30.dp)
                )
        ) {

        }
    }
}