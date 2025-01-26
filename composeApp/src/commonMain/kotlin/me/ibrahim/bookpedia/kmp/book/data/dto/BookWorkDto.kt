package me.ibrahim.bookpedia.kmp.book.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable(with = BookWorkDtoSerializer::class)
data class BookWorkDto(
    @SerialName("description") val description: String? = "",
)