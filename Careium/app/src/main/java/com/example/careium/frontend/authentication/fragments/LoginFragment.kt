package com.example.careium.frontend.authentication.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Toast
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.ViewModelProviders
import com.example.careium.R
import com.example.careium.core.database.authentication.AuthViewModel
import com.example.careium.core.database.authentication.Login
import com.example.careium.databinding.ErrorCustomViewBinding
import com.example.careium.databinding.FragmentLoginBinding
import com.example.careium.frontend.authentication.activities.SplashActivity
import com.example.careium.frontend.authentication.activities.titleViewModel
import com.example.careium.frontend.factory.ErrorAlertDialog
import com.example.careium.frontend.home.activities.MainActivity

class LoginFragment : Fragment(R.layout.fragment_login) {
    private lateinit var binding: FragmentLoginBinding
    private lateinit var email: String
    private lateinit var password: String
    private lateinit var authViewModel: AuthViewModel

    companion object {
        fun newInstance() = LoginFragment().apply {}
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentLoginBinding.bind(view)
        titleViewModel.mutableAuthTitleLD.value = getString(R.string.login)
        authViewModel = ViewModelProviders.of(this).get(AuthViewModel::class.java)
        observeAuthCallBackChange()

        handleClickButtons()
    }


    private fun handleClickButtons() {
        binding.loginBtn.setOnClickListener {
            email = binding.loginEmail.text?.trim().toString()
            password = binding.loginPassword.text.toString()

            if (email.isEmpty() || password.isEmpty())
                alert(getString(R.string.error_title), getString(R.string.error_message))
            else {
                hasAccount()
            }
        }

        binding.gmailIcon.setOnClickListener {
            // TODO: Handle Login With Gmail Account
        }

        binding.facebookIcon.setOnClickListener {
            // TODO: Handle Login With Facebook Account
        }

        binding.forgetPassword.setOnClickListener {
            activity?.supportFragmentManager?.beginTransaction()
                ?.replace(R.id.auth_frame, ResetPasswordFragment.newInstance())
                ?.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                ?.addToBackStack(null)
                ?.commit()
        }

    }

    private fun hasAccount(){
        val login = Login(email, password)
        login.isUserLoggedIn(authViewModel)
    }

    private fun observeAuthCallBackChange(){
        authViewModel.mutableIsAuthComplete.observe(viewLifecycleOwner){ isLogged ->
            if(isLogged) {
                Toast.makeText(activity, getString(R.string.confirmation_logged_msg), Toast.LENGTH_SHORT).show()
                openMainActivity()
            }
            else
                Toast.makeText(activity, getString(R.string.failed_to_login), Toast.LENGTH_SHORT).show()
        }
    }

    private fun alert(title: String, message: String) {
        val view: ErrorCustomViewBinding = binding.loginErrorView
        ErrorAlertDialog.alert(view, title, message)
    }

    private fun openMainActivity() {
        startActivity(Intent(activity, MainActivity::class.java))
        SplashActivity._this.finish()
        activity?.finish()
    }

}