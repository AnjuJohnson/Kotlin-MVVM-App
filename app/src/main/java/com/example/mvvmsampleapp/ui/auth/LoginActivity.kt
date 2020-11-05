package com.example.mvvmsampleapp.ui.auth

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.lifecycleScope
import com.example.mvvmsampleapp.R
import com.example.mvvmsampleapp.data.db.entities.User
import com.example.mvvmsampleapp.databinding.ActivityLoginBinding
import com.example.mvvmsampleapp.ui.home.HomeActivity
import com.example.mvvmsampleapp.util.*
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.coroutines.launch
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance

class LoginActivity : AppCompatActivity(), KodeinAware {
    override val kodein by kodein()
    private lateinit var binding: ActivityLoginBinding
    private lateinit var viewmodel: AuthViewModel

    private val factory by instance<AuthViewModelFactory>()

    //   private val factory:AuthViewModelFactory by instance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        /*  //inject dependancy from out side for loose coupling of codes.

          val networkConnectionInterceptor=NetworkConnectionInterceptor(this)
          val api = MyApi(networkConnectionInterceptor)
          val db = AppDatabase(this)
          val repository = UserRepository(api, db)
  */

        //this repository is needed to instanstiate Authviewmodel coz we have to pass repository to authviewmodel to get the api and db
        //  val authViewModel=AuthViewModel(repository) we wont do this cz viewmodel is already there.
        //view model factory is needed to pass the required parameter to Authviewmodel
        //    val factory = AuthViewModelFactory(repository)


        binding = DataBindingUtil.setContentView(this, R.layout.activity_login)
        viewmodel = ViewModelProvider(this, factory).get(AuthViewModel::class.java)


        //here we are passig current activty instance in our view model.this should not be done.this is an anti pattern
//        viewModel.authListener = this

        viewmodel.getLoggedInUser().observe(this, Observer { user ->
            if (user != null) {
                Intent(this, HomeActivity::class.java).also {
                    it.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(it)
                }

            }
        })

        binding.buttonSignIn.setOnClickListener {
            loginUser()
        }
binding.textViewSignUp.setOnClickListener {
    startActivity(Intent(this,SignupActivity::class.java))

}

    }

    private fun loginUser() {
        val email = binding.editTextEmail.text.toString().trim()
        val password = binding.editTextPassword.text.toString().trim()
        //@todo validation


        //since this is activty we can use lifecyclescope for suspending fns
        lifecycleScope.launch {
            try {
                val authResponse = viewmodel.userLogin(email, password)
                if (authResponse.user != null) {
                    viewmodel.saveLoggedInUser(authResponse.user)
                } else {
binding.rootLayout.snackbar(authResponse.message.toString())
                }

            } catch (e: ApiException) {
                binding.rootLayout.snackbar(e.toString())


            } catch (e: NoInternetException) {
                binding.rootLayout.snackbar(e.toString())

            }

        }
    }
}
