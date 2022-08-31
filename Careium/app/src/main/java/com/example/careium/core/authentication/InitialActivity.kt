package com.example.careium.core.authentication

import android.content.Intent
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.example.careium.backend.authentication.AuthenticationInterface
import com.example.careium.core.database.authentication.SharedPreferences
import com.example.careium.frontend.authentication.activities.AuthenticationActivity
import com.example.careium.frontend.authentication.activities.SplashActivity
import com.example.careium.frontend.home.activities.MainActivity

/**
 * This is the Authentication Activity which implements the Login and Register Actions.
 *
 * @author Kareem Sherif
 * @see AuthenticationInterface
 * @see SplashActivity
 */
class InitialActivity : AppCompatActivity(){

    /**
     * This method opens the **AuthenticationActivity** as an action for the Login Button Click.
     *
     * @author Kareem Sherif
     * @see AuthenticationActivity
     */
    fun authLoginAction() {
        val intent = Intent(SplashActivity.instance, AuthenticationActivity::class.java)
        intent.putExtra("AuthOption", "login")
        SplashActivity.instance.startActivity(intent)
    }

    /**
     * This method opens the **AuthenticationActivity** as an action for the Register Button Click.
     *
     * @author Kareem Sherif
     * @see AuthenticationActivity
     */
    fun authRegisterAction() {
        val intent = Intent(SplashActivity.instance, AuthenticationActivity::class.java)
        intent.putExtra("AuthOption", "register")
        SplashActivity.instance.startActivity(intent)
    }

    /**
     * This method calls **hasAccount** method to check whether the user login data
     * is cashed on the current device, if true, the **MainActivity** is opened.
     *
     * @return **true** if the user login data is cashed on the device **false** otherwise.
     * @author Kareem Sherif
     * @see hasAccount
     * @see openMainActivity
     */
    fun authenticateUser(): Boolean {
        if (hasAccount()) {
            openMainActivity()
            return true
        }
        return false
    }

    /**
     * Helper function to ensure the existence of the entered user data.
     *
     * @return **true** if the user login data is cashed on the device **false** otherwise.
     * @author Kareem Saeed
     */
    private fun hasAccount(): Boolean {
        val preference = SharedPreferences(SplashActivity.instance)
        return preference.isSPHasValue()
    }

    /**
     * Helper function to open the MainActivity once the user is authenticated.
     *
     * @author Kareem Saeed
     * @see MainActivity
     */
    private fun openMainActivity() {
        Handler(Looper.getMainLooper()).postDelayed({
            val intent = Intent(SplashActivity.instance, MainActivity::class.java)
            SplashActivity.instance.startActivity(intent)
            SplashActivity.instance.finish()
        }, 2000)
    }
}