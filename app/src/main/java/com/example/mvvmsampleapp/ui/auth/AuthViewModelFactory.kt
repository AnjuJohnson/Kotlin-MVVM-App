package com.example.mvvmsampleapp.ui.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.mvvmsampleapp.data.repository.UserRepository

@Suppress("UNCHECKED_CAST")
class AuthViewModelFactory(

  private val  repository: UserRepository

):ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {

        //here we are instanstiating Authviewmodel class with repository as parameter
        return AuthViewModel(repository) as T
    }
}