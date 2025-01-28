package me.ibrahim.bookpedia.kmp.book.data.repository

import androidx.sqlite.SQLiteException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import me.ibrahim.bookpedia.kmp.book.data.database.FavoriteBookDao
import me.ibrahim.bookpedia.kmp.book.data.mappers.toBook
import me.ibrahim.bookpedia.kmp.book.data.mappers.toBookEntity
import me.ibrahim.bookpedia.kmp.book.data.network.RemoteBookDataSource
import me.ibrahim.bookpedia.kmp.book.domain.Book
import me.ibrahim.bookpedia.kmp.book.domain.BookRepository
import me.ibrahim.bookpedia.kmp.core.domain.DataError
import me.ibrahim.bookpedia.kmp.core.domain.EmptyResult
import me.ibrahim.bookpedia.kmp.core.domain.Result
import me.ibrahim.bookpedia.kmp.core.domain.map

class DefaultBookRepository(
    private val remoteBookDataSource: RemoteBookDataSource,
    private val favoriteBookDao: FavoriteBookDao
) : BookRepository {
    override suspend fun searchBooks(
        query: String,
        resultLimit: Int?
    ): Result<List<Book>, DataError.Remote> {
        return remoteBookDataSource
            .searchBooks(query, resultLimit)
            .map { dto -> dto.results.map { it.toBook() } }
    }

    override suspend fun fetchBookDescription(bookId: String): Result<String, DataError.Remote> {
        val localResult = favoriteBookDao.getFavoriteBook(bookId)

        return if (localResult != null) {
            Result.Success(localResult.description ?: "")
        } else
            remoteBookDataSource
                .fetchBookDescription(bookId = bookId)
                .map { it.description ?: "" }
    }

    override suspend fun deleteFromFavorite(bookId: String) {
        favoriteBookDao.deleteFavoriteBook(bookId = bookId)
    }

    override suspend fun markAsFavorite(book: Book): EmptyResult<DataError.Local> {
        return try {
            favoriteBookDao.upsert(book.toBookEntity())
            Result.Success(Unit)
        } catch (e: SQLiteException) {
            Result.Error(DataError.Local.DISK_FULL)
        } catch (e: Exception) {
            Result.Error(DataError.Local.UNKNOWN)
        }
    }

    override fun getFavoriteBooks(): Flow<List<Book>> {
        return favoriteBookDao.getFavoriteBooks()
            .map { bookEntities -> bookEntities.map { it.toBook() } }
    }

    override fun isBookFavorite(bookId: String): Flow<Boolean> {
        return favoriteBookDao.getFavoriteBooks()
            .map { bookEntities -> bookEntities.any { it.id == bookId } }
    }
}