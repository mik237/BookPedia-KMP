package me.ibrahim.bookpedia.kmp

import androidx.compose.runtime.*
import org.jetbrains.compose.ui.tooling.preview.Preview

import me.ibrahim.bookpedia.kmp.book.presentation.book_list.BookListScreenRoot
import me.ibrahim.bookpedia.kmp.theme.AppTheme

@Composable
@Preview
fun App() {
    AppTheme {
        BookListScreenRoot(onBookClick = {})
    }
}