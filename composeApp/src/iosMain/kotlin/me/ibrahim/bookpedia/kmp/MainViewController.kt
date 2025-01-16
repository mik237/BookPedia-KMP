package me.ibrahim.bookpedia.kmp

import androidx.compose.ui.window.ComposeUIViewController
import me.ibrahim.bookpedia.kmp.di.initKoin

fun MainViewController() = ComposeUIViewController(
    configure = { initKoin() }
) { App() }