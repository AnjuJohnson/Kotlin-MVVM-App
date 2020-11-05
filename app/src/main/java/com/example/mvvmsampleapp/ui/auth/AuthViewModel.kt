package com.example.mvvmsampleapp.ui.auth

import android.content.Intent
import android.view.View
import androidx.lifecycle.ViewModel
import com.example.mvvmsampleapp.data.db.entities.User
import com.example.mvvmsampleapp.data.repository.UserRepository
import com.example.mvvmsampleapp.util.ApiException
import com.example.mvvmsampleapp.util.Coroutines
import com.example.mvvmsampleapp.util.NoInternetException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

//inject constructor
class AuthViewModel(
    private val repository: UserRepository
) : ViewModel() {

    fun getLoggedInUser() = repository.getUser()
//we can use withcontext dispatecher.io for network calls.so that network calls will be in io dipatcher not in main dispatcher
    suspend fun userLogin(
        email: String,
        password: String
    ) = withContext(Dispatchers.IO){repository.userLogin(email, password)}

    suspend fun saveLoggedInUser(user: User) = repository.saveUser(user)

    suspend fun userSignUp(
        name:String,
        email: String,
        password: String)=
    withContext(Dispatchers.IO){repository.userSignUp(name,email, password)}
}