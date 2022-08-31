package com.example.careium.frontend.authentication.activities

import android.annotation.SuppressLint
import android.app.Activity
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.careium.R
import com.example.careium.backend.authentication.AuthenticationInterface
import com.example.careium.databinding.ActivityAuthenticationBinding

class AuthenticationActivity : AppCompatActivity(), AuthenticationInterface {

    private lateinit var option: String
    companion object {
        @SuppressLint("StaticFieldLeak")
        lateinit var instance: Activity
        @SuppressLint("StaticFieldLeak")
        private lateinit var binding: ActivityAuthenticationBinding

        fun setAuthToolbarTitle(title: String) {
            binding.authToolbar.toolbarTitle.text =
                (instance as AppCompatActivity).getString(R.string.auth_toolbar_title, title)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAuthenticationBinding.inflate(layoutInflater)
        setContentView(binding.root)
        instance = this

        // back button click destroys the current activity
        binding.authToolbar.backImgButton.setOnClickListener {
            finish()
        }

        // weather to move on Login or Register Fragments
        authOptions()
    }

}
