package com.example.careium.frontend.authentication.activities

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.daimajia.androidanimations.library.Techniques
import com.daimajia.androidanimations.library.YoYo
import com.example.careium.core.database.authentication.SharedPreferences
import com.example.careium.databinding.ActivitySplashBinding
import com.example.careium.frontend.home.activities.MainActivity

@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySplashBinding

    companion object {
        @SuppressLint("StaticFieldLeak")
        lateinit var this_activity: Activity
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)
        this_activity = this

        buildAnimation()
        handleClickButtons()


        if (hasAccount()) {
            binding.loginBtn.visibility = View.GONE
            binding.registerBtn.visibility = View.GONE
            Handler(Looper.getMainLooper()).postDelayed({
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            }, 2000)
        }

    }

    private fun hasAccount(): Boolean {
        val preference = SharedPreferences(this)
        return preference.isSPHasValue()
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