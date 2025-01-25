package me.ibrahim.bookpedia.kmp.di

import io.ktor.client.engine.js.Js
import org.koin.core.module.Module
import org.koin.dsl.module

actual val platformModule: Module
    get() = module { single { Js } }