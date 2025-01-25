package me.ibrahim.bookpedia.kmp.di

import me.ibrahim.bookpedia.kmp.book.data.network.KtorRemoteBookDataSource
import me.ibrahim.bookpedia.kmp.book.data.network.RemoteBookDataSource
import me.ibrahim.bookpedia.kmp.book.data.repository.DefaultBookRepository
import me.ibrahim.bookpedia.kmp.book.domain.BookRepository
import me.ibrahim.bookpedia.kmp.book.presentation.SelectedBookViewModel
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

    viewModelOf(::BookListViewModel)
    viewModelOf(::SelectedBookViewModel)
}

expect val platformModule: Module