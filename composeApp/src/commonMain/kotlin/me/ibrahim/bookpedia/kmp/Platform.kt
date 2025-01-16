package me.ibrahim.bookpedia.kmp

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform