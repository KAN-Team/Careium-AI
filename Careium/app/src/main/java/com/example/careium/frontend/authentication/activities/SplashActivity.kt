package com.example.careium.frontend.authentication.activities

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.daimajia.androidanimations.library.Techniques
import com.daimajia.androidanimations.library.YoYo
import com.example.careium.databinding.ActivitySplashBinding
import com.example.careium.frontend.home.activities.MainActivity

@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySplashBinding
    private val condition: Int = 0

    companion object {
        @SuppressLint("StaticFieldLeak")
        lateinit var _this: Activity
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)
        _this = this

        buildAnimation()
        handleClickButtons()

        // TODO: Handle Here Authentication Condition to start main activity
        if (condition == 1) {
            binding.loginBtn.visibility = View.GONE
            binding.registerBtn.visibility = View.GONE
            Handler().postDelayed({
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            }, 2000)
        }

    }


    private fun handleClickButtons() {
        binding.loginBtn.setOnClickListener {
            val intent = Intent(this, AuthenticationActivity::class.java)
            intent.putExtra("AuthOption", "login")
            startActivity(intent)
        }

        binding.registerBtn.setOnClickListener {
            val intent = Intent(this, AuthenticationActivity::class.java)
            intent.putExtra("AuthOption", "register")
            startActivity(intent)
        }
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
}