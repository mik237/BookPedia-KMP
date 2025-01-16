package me.ibrahim.bookpedia.kmp.book.presentation.book_list

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import me.ibrahim.bookpedia.kmp.book.domain.Book

@Composable
fun BookListScreenRoot(
    viewModel: BookListViewModel = viewModel<BookListViewModel>(),
    onBookClick: (Book) -> Unit
) {

    val state by viewModel.state.collectAsStateWithLifecycle()
    BookListScreen(state =  state , onAction = { action ->
        when (action) {
            is BookListActions.OnBookClick -> onBookClick(action.book)
            else -> viewModel.onAction(action)
        }
    })
}


@Composable
fun BookListScreen(
    state:  BookListState,
    onAction: (BookListActions) -> Unit
) {
}