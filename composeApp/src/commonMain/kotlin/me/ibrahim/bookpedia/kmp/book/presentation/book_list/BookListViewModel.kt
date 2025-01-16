package me.ibrahim.bookpedia.kmp.book.presentation.book_list

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class BookListViewModel : ViewModel() {

    private val _state = MutableStateFlow(BookListState())
    val state = _state.asStateFlow()

    fun onAction(actions: BookListActions) {
        when (actions) {
            is BookListActions.OnBookClick -> {}
            is BookListActions.OnSearchQueryChange -> {
                _state.update { it.copy(searchQuery = actions.query) }
            }

            is BookListActions.OnTabSelected -> {
                _state.update { it.copy(selectedIndex = actions.index) }
            }
        }
    }

}