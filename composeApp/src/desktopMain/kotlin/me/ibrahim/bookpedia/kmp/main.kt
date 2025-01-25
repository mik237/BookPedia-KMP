package me.ibrahim.bookpedia.kmp

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import me.ibrahim.bookpedia.kmp.app.App
import me.ibrahim.bookpedia.kmp.di.initKoin

fun main() {
    initKoin()
    application {
        Window(
            onCloseRequest = ::exitApplication,
            title = "BookPedia-KMP",
        ) {
            App()
        }
    }
}