package me.ibrahim.bookpedia.kmp.book.presentation.book_detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import me.ibrahim.bookpedia.kmp.app.Route
import me.ibrahim.bookpedia.kmp.book.domain.BookRepository
import me.ibrahim.bookpedia.kmp.core.domain.onSuccess

class BookDetailViewModel(
    private val bookRepository: BookRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val bookId = savedStateHandle.toRoute<Route.BookDetail>().id

    private val _state = MutableStateFlow(BookDetailState())
    val state = _state
        .onStart {
            fetchBookDescription()
        }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(),
            _state.value
        )

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

    private fun fetchBookDescription() {
        viewModelScope.launch {
            bookRepository.fetchBookDescription(bookId = bookId)
                .onSuccess { description ->
                    _state.update {
                        it.copy(
                            selectedBook = it.selectedBook?.copy(
                                description = description
                            ),
                            isLoading = false
                        )
                    }
                }
        }
    }
}