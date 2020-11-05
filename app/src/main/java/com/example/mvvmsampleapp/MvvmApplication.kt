package com.example.mvvmsampleapp

import android.app.Application
import com.example.mvvmsampleapp.data.db.AppDatabase
import com.example.mvvmsampleapp.data.network.MyApi
import com.example.mvvmsampleapp.data.network.NetworkConnectionInterceptor
import com.example.mvvmsampleapp.data.network.responses.QuotesResponse
import com.example.mvvmsampleapp.data.preferences.PrefernceProvider
import com.example.mvvmsampleapp.data.repository.QuoteRepository
import com.example.mvvmsampleapp.data.repository.UserRepository
import com.example.mvvmsampleapp.ui.auth.AuthViewModel
import com.example.mvvmsampleapp.ui.auth.AuthViewModelFactory
import com.example.mvvmsampleapp.ui.profile.ProfileViewModelFactory
import com.example.mvvmsampleapp.ui.quotes.QuotesViewModelFactory
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.androidXModule
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider
import org.kodein.di.generic.singleton

class MvvmApplication : Application(), KodeinAware {
    override val kodein = Kodein.lazy {
        import(androidXModule(this@MvvmApplication))
        bind() from singleton { NetworkConnectionInterceptor(instance()) }
        bind() from singleton { MyApi(instance()) }
        bind() from singleton { AppDatabase(instance()) }
        bind() from singleton { PrefernceProvider(instance()) }

        bind() from singleton { UserRepository(instance(),instance()) }
        bind() from singleton { QuoteRepository(instance(),instance(),instance()) }
        bind() from provider { AuthViewModelFactory(instance()) }
        bind() from provider { ProfileViewModelFactory(instance()) }
        bind() from provider { QuotesViewModelFactory(instance()) }

    }
}