package com.example.mvvmsampleapp.ui.auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.lifecycleScope
import com.example.mvvmsampleapp.R
import com.example.mvvmsampleapp.data.db.entities.User
import com.example.mvvmsampleapp.databinding.ActivityLoginBinding
import com.example.mvvmsampleapp.databinding.ActivitySignupBinding
import com.example.mvvmsampleapp.ui.home.HomeActivity
import com.example.mvvmsampleapp.util.*
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.coroutines.launch
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance

class SignupActivity : AppCompatActivity(),KodeinAware {
    override val kodein by kodein()
    private val factory by instance<AuthViewModelFactory>()

    private lateinit var binding: ActivitySignupBinding
    private lateinit var viewmodel:AuthViewModel

 //   private val factory:AuthViewModelFactory by instance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
         binding =DataBindingUtil.setContentView(this, R.layout.activity_signup)
         viewmodel = ViewModelProvider(this, factory).get(AuthViewModel::class.java)
        viewmodel.getLoggedInUser().observe(this, Observer { user ->
            if (user != null) {
                Intent(this, HomeActivity::class.java).also {
                    it.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(it)
                }

            }
        })

     binding.buttonSignUp.setOnClickListener {

         userSignUp()
     }
     binding.textViewLogin.setOnClickListener {
         startActivity(Intent(this,LoginActivity::class.java))

     }
    }
    private fun userSignUp(){
        val email = binding.editTextEmail.text.toString().trim()
        val password = binding.editTextPassword.text.toString().trim()
        val name = binding.editTextName.text.toString().trim()
        val password1 = binding.editTextPasswordConfirm.text.toString().trim()
        //@todo validation

        lifecycleScope.launch {
            try {
                val authResponse=viewmodel.userSignUp(name,email,password)
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
