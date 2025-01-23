package me.ibrahim.bookpedia.kmp.book.data.repository

import me.ibrahim.bookpedia.kmp.book.data.mappers.toBook
import me.ibrahim.bookpedia.kmp.book.data.network.RemoteBookDataSource
import me.ibrahim.bookpedia.kmp.book.domain.Book
import me.ibrahim.bookpedia.kmp.book.domain.BookRepository
import me.ibrahim.bookpedia.kmp.core.domain.DataError
import me.ibrahim.bookpedia.kmp.core.domain.Result
import me.ibrahim.bookpedia.kmp.core.domain.map

class DefaultBookRepository(
    private val remoteBookDataSource: RemoteBookDataSource
) : BookRepository {
    override suspend fun searchBooks(
        query: String,
        resultLimit: Int?
    ): Result<List<Book>, DataError.Remote> {
        return remoteBookDataSource
            .searchBooks(query, resultLimit)
            .map { dto -> dto.results.map { it.toBook() } }
    }
}