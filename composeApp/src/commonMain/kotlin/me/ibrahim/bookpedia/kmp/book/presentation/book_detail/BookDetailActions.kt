package me.ibrahim.bookpedia.kmp.book.presentation.book_detail

import me.ibrahim.bookpedia.kmp.book.domain.Book

sealed interface BookDetailActions {
    data object OnBackClick : BookDetailActions
    data object OnFavoriteClick : BookDetailActions
    data class OnSelectedBookChange(val book: Book?) : BookDetailActions
}