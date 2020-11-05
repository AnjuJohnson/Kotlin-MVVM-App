package com.example.mvvmsampleapp.ui.quotes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.mvvmsampleapp.data.repository.QuoteRepository
import com.example.mvvmsampleapp.data.repository.UserRepository

@Suppress("UNCHECKED_CAST")
class QuotesViewModelFactory(

  private val  repository: QuoteRepository

):ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {

        //here we are instanstiating ProfileViewModel class with repository as parameter
        return QuotesViewModel(repository) as T
    }
}