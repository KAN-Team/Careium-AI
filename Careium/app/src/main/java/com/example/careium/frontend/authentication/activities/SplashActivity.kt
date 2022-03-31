package com.example.careium.frontend.authentication.activities

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.careium.databinding.ActivitySplashBinding
import com.example.careium.frontend.home.activities.MainActivity

@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySplashBinding
    private val x: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)


        // Handle Here Authentication Condition to start main activity
        if (x == 1) {
            binding.loginBtn.visibility = View.GONE
            binding.registerBtn.visibility = View.GONE
            Handler().postDelayed({
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            }, 100)
        }


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
}