package me.ibrahim.bookpedia.kmp.book.domain

data class Book(
    val id: String,
    val title: String,
    val imageUrl: String,
    val authers: List<String>,
    val descriptions: String,
    val languages: List<String>,
    val firstPublishYear: String?,
    val averageRating: Double?,
    val ratingCount: Int?,
    val numPages: Int,
    val numEditions: Int
)
