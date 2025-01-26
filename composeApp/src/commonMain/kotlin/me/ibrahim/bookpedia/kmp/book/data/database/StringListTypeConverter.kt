package me.ibrahim.bookpedia.kmp.book.data.database

import androidx.room.TypeConverter
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

object StringListTypeConverter {

    @TypeConverter
    fun listToString(list: List<String>): String {
        return Json.encodeToString(list)
    }

    @TypeConverter
    fun stringToList(value: String): List<String> {
        return Json.decodeFromString(value)
    }
}