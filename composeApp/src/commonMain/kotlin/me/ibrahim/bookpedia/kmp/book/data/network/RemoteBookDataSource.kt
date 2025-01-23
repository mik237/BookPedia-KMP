package me.ibrahim.bookpedia.kmp.book.data.network

import me.ibrahim.bookpedia.kmp.book.data.dto.SearchResponseDto
import me.ibrahim.bookpedia.kmp.core.domain.DataError
import me.ibrahim.bookpedia.kmp.core.domain.Result

interface RemoteBookDataSource {

    suspend fun searchBooks(
        query: String,
        resultLimit: Int? = null
    ): Result<SearchResponseDto, DataError.Remote>

}