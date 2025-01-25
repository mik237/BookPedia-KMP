package me.ibrahim.bookpedia.kmp.book.presentation.book_list

import me.ibrahim.bookpedia.kmp.book.domain.Book
import me.ibrahim.bookpedia.kmp.core.presentation.UiText

data class BookListState(
    val searchQuery: String = "Kotlin",
    val searchResults: List<Book> = emptyList(),
    val favoriteBooks: List<Book> = emptyList(),
    val isLoading: Boolean = true,
    val selectedIndex: Int = 0,
    val errorMessage: UiText? = null
)
