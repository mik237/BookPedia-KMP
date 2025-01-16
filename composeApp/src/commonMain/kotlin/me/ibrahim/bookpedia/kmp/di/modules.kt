package me.ibrahim.bookpedia.kmp.di

import me.ibrahim.bookpedia.kmp.book.presentation.book_list.BookListViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val sharedModule = module {
    viewModelOf(::BookListViewModel)
}