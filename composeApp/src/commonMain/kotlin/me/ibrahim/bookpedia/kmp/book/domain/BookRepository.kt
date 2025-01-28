package me.ibrahim.bookpedia.kmp.book.domain

import kotlinx.coroutines.flow.Flow
import me.ibrahim.bookpedia.kmp.core.domain.DataError
import me.ibrahim.bookpedia.kmp.core.domain.EmptyResult
import me.ibrahim.bookpedia.kmp.core.domain.Result

interface BookRepository {
    suspend fun searchBooks(
        query: String,
        resultLimit: Int? = null
    ): Result<List<Book>, DataError.Remote>

    suspend fun fetchBookDescription(bookId: String): Result<String, DataError.Remote>

    fun getFavoriteBooks(): Flow<List<Book>>
    fun isBookFavorite(bookId: String): Flow<Boolean>
    suspend fun deleteFromFavorite(bookId: String)
    suspend fun markAsFavorite(book: Book): EmptyResult<DataError.Local>
}