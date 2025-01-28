package me.ibrahim.bookpedia.kmp.book.presentation.book_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import me.ibrahim.bookpedia.kmp.book.domain.Book
import me.ibrahim.bookpedia.kmp.book.domain.BookRepository
import me.ibrahim.bookpedia.kmp.core.domain.onError
import me.ibrahim.bookpedia.kmp.core.domain.onSuccess
import me.ibrahim.bookpedia.kmp.core.presentation.toUiText

class BookListViewModel(
    private val bookRepository: BookRepository
) : ViewModel() {

    private val cachedBooks = mutableListOf<Book>()
    private var searchJob: Job? = null
    private var favoriteBooksJob: Job? = null

    private val _state = MutableStateFlow(BookListState())
    val state = _state
        .onStart {
            if (cachedBooks.isEmpty()) {
                observeSearchQuery()
            }
            observeFavoriteBooks()
        }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000L),
            _state.value
        )

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

    private fun observeFavoriteBooks() {
        favoriteBooksJob?.cancel()
        favoriteBooksJob = bookRepository.getFavoriteBooks()
            .onEach { favoriteBooks ->
                _state.update {
                    it.copy(favoriteBooks = favoriteBooks)
                }
            }
            .launchIn(viewModelScope)
    }

    @OptIn(FlowPreview::class)
    private fun observeSearchQuery() {
        state.map { it.searchQuery }
            .distinctUntilChanged()
            .debounce(500L)
            .onEach { query ->

                when {
                    query.isBlank() -> {
                        _state.update {
                            it.copy(
                                errorMessage = null,
                                searchResults = cachedBooks
                            )
                        }
                    }

                    query.length >= 2 -> {
                        searchJob?.cancel()
                        searchJob = searchBooks(query)
                    }
                }

            }
            .launchIn(viewModelScope)
    }

    private fun searchBooks(query: String) = viewModelScope.launch {
        _state.update { it.copy(isLoading = true) }
        bookRepository.searchBooks(query = query)
            .onSuccess { books ->
                cachedBooks.apply {
                    clear()
                    addAll(books)
                }
                _state.update {
                    it.copy(
                        searchResults = books,
                        isLoading = false,
                        errorMessage = null
                    )
                }
            }
            .onError { error ->
                _state.update {
                    it.copy(
                        isLoading = false,
                        searchResults = emptyList(),
                        errorMessage = error.toUiText()
                    )
                }
            }
    }
}