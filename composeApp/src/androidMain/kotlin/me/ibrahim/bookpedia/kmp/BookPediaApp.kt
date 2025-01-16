package me.ibrahim.bookpedia.kmp

import android.app.Application
import me.ibrahim.bookpedia.kmp.di.initKoin
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger

class BookPediaApp : Application() {
    override fun onCreate() {
        super.onCreate()
        initKoin {
            androidContext(this@BookPediaApp)
        }
    }
}