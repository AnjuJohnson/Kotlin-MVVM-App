package com.example.mvvmsampleapp.ui.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.mvvmsampleapp.data.repository.UserRepository

@Suppress("UNCHECKED_CAST")
class ProfileViewModelFactory(

  private val  repository: UserRepository

):ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {

        //here we are instanstiating ProfileViewModel class with repository as parameter
        return ProfileViewModel(repository) as T
    }
}