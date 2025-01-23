package me.ibrahim.bookpedia.kmp.book.domain

import me.ibrahim.bookpedia.kmp.core.domain.DataError
import me.ibrahim.bookpedia.kmp.core.domain.Result

interface BookRepository {
    suspend fun searchBooks(
        query: String,
        resultLimit: Int? = null
    ): Result<List<Book>, DataError.Remote>
}