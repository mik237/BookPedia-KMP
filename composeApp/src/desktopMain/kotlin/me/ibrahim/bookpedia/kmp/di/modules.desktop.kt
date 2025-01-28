package me.ibrahim.bookpedia.kmp.di

import io.ktor.client.engine.okhttp.OkHttp
import me.ibrahim.bookpedia.kmp.book.data.database.DatabaseFactory
import org.koin.core.module.Module
import org.koin.dsl.module

actual val platformModule: Module
    get() = module {
        single { OkHttp.create() }
        single { DatabaseFactory() }
    }