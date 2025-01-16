package me.ibrahim.bookpedia.kmp.book.presentation.book_list

import me.ibrahim.bookpedia.kmp.book.domain.Book

sealed interface BookListActions {
    data class OnSearchQueryChange(val query: String) : BookListActions
    data class OnBookClick(val book: Book) : BookListActions
    data class OnTabSelected(val index: Int) : BookListActions
}