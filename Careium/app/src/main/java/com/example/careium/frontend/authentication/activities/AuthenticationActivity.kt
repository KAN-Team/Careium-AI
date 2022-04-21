package com.example.careium.frontend.authentication.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.*
import com.example.careium.R
import com.example.careium.core.models.User
import com.example.careium.databinding.ActivityAuthenticationBinding
import com.example.careium.frontend.authentication.fragments.LoginFragment
import com.example.careium.frontend.authentication.fragments.RegisterFragment
import com.example.careium.frontend.factory.AuthTitleViewModel

lateinit var titleViewModel: AuthTitleViewModel
val user = User.getInstance()

class AuthenticationActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAuthenticationBinding
    private lateinit var option: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAuthenticationBinding.inflate(layoutInflater)
        setContentView(binding.root)
        option = intent.getStringExtra("AuthOption").toString()
        User.reset(user)

        observeAuthTitleChange()
        handleBackButton()


        if (option == "login")
            loadFragment(LoginFragment.newInstance())
        else if (option == "register")
            loadFragment(RegisterFragment.newInstance())

    }


    private fun loadFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.auth_frame, fragment)
            //.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
            .commit()
    }

    private fun observeAuthTitleChange() {
        titleViewModel = ViewModelProviders.of(this).get(AuthTitleViewModel::class.java)
        titleViewModel.mutableAuthTitleLD.observe(this) { fragmentTitle ->
            binding.authToolbar.toolbarTitle.text =
                getString(R.string.auth_toolbar_title, fragmentTitle)
        }
    }


    private fun handleBackButton() {
        binding.authToolbar.backImgButton.setOnClickListener {
            finish()
        }
    }

}
