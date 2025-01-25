package me.ibrahim.bookpedia.kmp.book.presentation.book_detail

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class BookDetailViewModel : ViewModel() {

    private val _state = MutableStateFlow(BookDetailState())
    val state = _state.asStateFlow()

    fun onAction(action: BookDetailActions) {
        when (action) {
            BookDetailActions.OnFavoriteClick -> {}
            is BookDetailActions.OnSelectedBookChange -> {
                _state.update {
                    it.copy(selectedBook = action.book)
                }
            }

            else -> Unit
        }
    }
}