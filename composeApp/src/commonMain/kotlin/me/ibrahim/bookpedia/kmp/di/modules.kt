package me.ibrahim.bookpedia.kmp.di

import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import me.ibrahim.bookpedia.kmp.book.data.database.DatabaseFactory
import me.ibrahim.bookpedia.kmp.book.data.database.FavoriteBookDatabase
import me.ibrahim.bookpedia.kmp.book.data.network.KtorRemoteBookDataSource
import me.ibrahim.bookpedia.kmp.book.data.network.RemoteBookDataSource
import me.ibrahim.bookpedia.kmp.book.data.repository.DefaultBookRepository
import me.ibrahim.bookpedia.kmp.book.domain.BookRepository
import me.ibrahim.bookpedia.kmp.book.presentation.SelectedBookViewModel
import me.ibrahim.bookpedia.kmp.book.presentation.book_detail.BookDetailViewModel
import me.ibrahim.bookpedia.kmp.book.presentation.book_list.BookListViewModel
import me.ibrahim.bookpedia.kmp.core.data.HttpClientFactory
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.bind
import org.koin.dsl.module

val sharedModule = module {
    single { HttpClientFactory.create(get()) }
    singleOf(::DefaultBookRepository).bind<BookRepository>()
    singleOf(::KtorRemoteBookDataSource).bind<RemoteBookDataSource>()

    single {
        get<DatabaseFactory>()
            .create()
            .setDriver(BundledSQLiteDriver())
            .build()
    }

    single { get<FavoriteBookDatabase>().favoriteBookDao }

    viewModelOf(::BookListViewModel)
    viewModelOf(::SelectedBookViewModel)
    viewModelOf(::BookDetailViewModel)
}

expect val platformModule: Module