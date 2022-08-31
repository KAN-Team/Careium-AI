package com.example.careium.frontend.authentication.activities

import android.annotation.SuppressLint
import android.app.Activity
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.daimajia.androidanimations.library.Techniques
import com.daimajia.androidanimations.library.YoYo
import com.example.careium.backend.authentication.AuthenticationInterface
import com.example.careium.databinding.ActivitySplashBinding

@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity(), AuthenticationInterface {
    private lateinit var binding: ActivitySplashBinding

    companion object {
        @SuppressLint("StaticFieldLeak")
        lateinit var instance: Activity
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)
        instance = this

        buildAnimation()
        handleClickButtons()
        authenticateUser()
    }

    private fun buildAnimation() {
        YoYo.with(Techniques.FlipInX)
            .duration(2000)
            .repeat(0)
            .playOn(binding.welcomeLabel)

        YoYo.with(Techniques.ZoomIn)
            .duration(2000)
            .repeat(0)
            .playOn(binding.appNameLabel)
    }

    private fun handleClickButtons() {
        binding.loginBtn.setOnClickListener {
            authLogin()
        }

        binding.registerBtn.setOnClickListener {
            authRegister()
        }
    }

    override fun buttonsVisibilityGone() {
        binding.loginBtn.visibility = View.GONE
        binding.registerBtn.visibility = View.GONE
    }

}