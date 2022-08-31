package com.example.careium.core.authentication

import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProviders
import com.example.careium.R
import com.example.careium.backend.authentication.AuthenticationInterface
import com.example.careium.core.models.User
import com.example.careium.frontend.authentication.activities.AuthenticationActivity
import com.example.careium.frontend.authentication.fragments.LoginFragment
import com.example.careium.frontend.authentication.fragments.RegisterFragment
import com.example.careium.frontend.factory.AuthTitleViewModel

/**
 * This is the Core Authenticator which implements the Login and Register Transfer Actions.
 *
 * @author Kareem Sherif
 * @see AuthenticationInterface
 * @see AuthenticationActivity
 */
class Authenticator : AppCompatActivity(), AuthenticationInterface {

    private val authCompatActivity = AuthenticationActivity.instance as AppCompatActivity
    companion object {
        val user = User.getInstance() // singleton object
        val titleViewModel = ViewModelProviders
            .of(AuthenticationActivity.instance as FragmentActivity)
            .get(AuthTitleViewModel::class.java)
    }

    /**
     * It gets the **AuthenticationActivity** Intent String Extra to fire the action of loading
     * the specified fragment from the known option (Login/Register) also, fires the **AuthTitle**
     * Observer to update the Toolbar Title text whenever changed using Mutable Live Data.
     *
     * @author Kareem Sherif
     * @see AuthenticationActivity
     */
    fun authenticate() {
        val option = AuthenticationActivity.instance.intent
            .getStringExtra("AuthOption").toString()
        User.reset(user)

        when (option) {
            "login" -> loadFragment(LoginFragment.newInstance())
            "register" -> loadFragment(RegisterFragment.newInstance())
            else -> Toast.makeText(AuthenticationActivity.instance,
                authCompatActivity.getString(R.string.something_wrong), Toast.LENGTH_SHORT).show()
        }

        // observe AuthTitle Changes
        titleViewModel.mutableAuthTitleLD.observe(authCompatActivity) { fragmentTitle ->
            updateAuthToolbarTitle(fragmentTitle)
        }
    }

    /**
     * Loads a given **Fragment** through the **SupportFragmentManager** into the **auth_frame**
     * included in **activity_authentication.xml** layout
     *
     * @author Kareem Sherif
     * @see AuthenticationActivity
     */
    private fun loadFragment(fragment: Fragment) {
        authCompatActivity.supportFragmentManager.beginTransaction()
            .replace(R.id.auth_frame, fragment)
            .commit()
    }

}