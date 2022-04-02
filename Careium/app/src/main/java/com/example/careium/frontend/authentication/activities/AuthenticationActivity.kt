package com.example.careium.frontend.authentication.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.*
import com.example.careium.R
import com.example.careium.databinding.ActivityAuthenticationBinding
import com.example.careium.frontend.authentication.fragments.LoginFragment
import com.example.careium.frontend.authentication.fragments.RegisterFragment
import com.example.careium.frontend.factory.AuthViewModel

lateinit var viewModel: AuthViewModel

class AuthenticationActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAuthenticationBinding
    private lateinit var option: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAuthenticationBinding.inflate(layoutInflater)
        setContentView(binding.root)
        option = intent.getStringExtra("AuthOption").toString()


        //observe ant change in Fragment Title
        observeAuthTitleChange()
        handleBackButton()


        if (option == "login")
            loadFragment(LoginFragment.newInstance(getString(R.string.login)), "Auth")
        else if (option == "register")
            loadFragment(RegisterFragment.newInstance(getString(R.string.register)), "Auth")

    }

    private fun loadFragment(fragment: Fragment, fragmentTAG: String) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.auth_frame, fragment, fragmentTAG)
            //.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
            .commit()
    }

    private fun observeAuthTitleChange() {
        viewModel = ViewModelProviders.of(this).get(AuthViewModel::class.java)
        viewModel.mutableAuthTitleLD.observe(this) { fragmentTitle ->
            binding.authToolbar.toolbarTitle.text =
                getString(R.string.auth_toolbar_title, fragmentTitle)
        }
    }


    private fun handleBackButton() {
        binding.authToolbar.backImgButton.setOnClickListener {
            val myFragment = supportFragmentManager.findFragmentByTag("Auth")
            if (myFragment != null && myFragment.isVisible) {
                finish()
            }
        }
    }

}
