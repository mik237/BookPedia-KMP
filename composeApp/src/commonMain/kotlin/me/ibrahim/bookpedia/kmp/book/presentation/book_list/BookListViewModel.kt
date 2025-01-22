package me.ibrahim.bookpedia.kmp.book.presentation.book_list

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import me.ibrahim.bookpedia.kmp.book.domain.Book

class BookListViewModel : ViewModel() {

    private val _state = MutableStateFlow(BookListState())
    val state = _state.asStateFlow()
    private val books = (1..100).map {
        Book(
            id = "id $it",
            title = "Book Number $it",
            ratingCount = 5,
            authers = listOf("Matthew Mathias", "def"),
            descriptions = "",
            imageUrl = "",
            languages = listOf("English", "Urdu"),
            firstPublishYear = "2005",
            averageRating = 4.323454,
            numPages = 100,
            numEditions = 2
        )
    }

    private val favoriteBooks = (1..4).map {
        Book(
            id = "id $it",
            title = "Book Number $it",
            ratingCount = 5,
            authers = listOf("Matthew Mathias", "def"),
            descriptions = "",
            imageUrl = "",
            languages = listOf("English", "Urdu"),
            firstPublishYear = "2005",
            averageRating = 4.323454,
            numPages = 100,
            numEditions = 2
        )
    }

    init {
        _state.value = _state.value.copy(searchResults = books)
        _state.value = _state.value.copy(favoriteBooks = favoriteBooks)
    }

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