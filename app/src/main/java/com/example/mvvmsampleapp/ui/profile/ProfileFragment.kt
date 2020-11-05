package com.example.mvvmsampleapp.ui.profile

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.view.LayoutInflater
import androidx.fragment.app.Fragment

import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.DataBindingUtil.inflate


import com.example.mvvmsampleapp.R
import com.example.mvvmsampleapp.databinding.ProfileFragmentBinding
import org.kodein.di.android.x.kodein
import org.kodein.di.KodeinAware
import org.kodein.di.generic.instance

class ProfileFragment : Fragment(), KodeinAware {

    override val kodein by kodein()
    private val factory by instance<ProfileViewModelFactory>()

   // private val factory: ProfileViewModelFactory by instance()
    private lateinit var viewModel: ProfileViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding: ProfileFragmentBinding =
            inflate(inflater, R.layout.profile_fragment, container, false)
        viewModel = ViewModelProviders.of(this, factory).get(ProfileViewModel::class.java)
        binding.viewmodel = viewModel
        //we are binding live data to the layout so define lifecycle owner
        binding.lifecycleOwner=this
        return binding.root
    }


}
