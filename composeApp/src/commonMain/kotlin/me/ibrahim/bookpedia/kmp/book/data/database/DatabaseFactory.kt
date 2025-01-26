package me.ibrahim.bookpedia.kmp.book.data.database

import androidx.room.Room
import androidx.room.RoomDatabase

expect class DatabaseFactory {
    fun create(): RoomDatabase.Builder<FavoriteBookDatabase>
}