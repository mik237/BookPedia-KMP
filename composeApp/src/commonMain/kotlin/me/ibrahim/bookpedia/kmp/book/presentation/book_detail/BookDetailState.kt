package me.ibrahim.bookpedia.kmp.book.presentation.book_detail

import me.ibrahim.bookpedia.kmp.book.domain.Book

data class BookDetailState(
    val isLoading: Boolean = true,
    val selectedBook: Book? = null
)
