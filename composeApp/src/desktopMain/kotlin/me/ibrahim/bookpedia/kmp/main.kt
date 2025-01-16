package me.ibrahim.bookpedia.kmp

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "BookPedia-KMP",
    ) {
        App()
    }
}