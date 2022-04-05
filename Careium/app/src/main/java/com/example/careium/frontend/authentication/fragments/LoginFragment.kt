package com.example.careium.frontend.authentication.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Toast
import androidx.fragment.app.FragmentTransaction
import com.example.careium.R
import com.example.careium.databinding.ErrorCustomViewBinding
import com.example.careium.databinding.FragmentLoginBinding
import com.example.careium.frontend.authentication.activities.SplashActivity
import com.example.careium.frontend.authentication.activities.viewModel
import com.example.careium.frontend.factory.ErrorAlertDialog
import com.example.careium.frontend.home.activities.MainActivity

class LoginFragment : Fragment(R.layout.fragment_login) {
    private lateinit var binding: FragmentLoginBinding

    companion object {
        fun newInstance() = LoginFragment().apply {}
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentLoginBinding.bind(view)
        viewModel.mutableAuthTitleLD.value = getString(R.string.login)

        handleClickButtons()
    }


    private fun handleClickButtons() {
        binding.loginBtn.setOnClickListener {
            val email = binding.loginEmail.text?.trim().toString()
            val password = binding.loginPassword.text.toString()

            if (email.isEmpty() || password.isEmpty())
                alert(getString(R.string.error_title), getString(R.string.error_message))
            else {
                if(hasAccount()) {
                    Toast.makeText(activity, getString(R.string.confirmation_logged_msg), Toast.LENGTH_SHORT).show()
                    openMainActivity()
                }
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

    private fun hasAccount():Boolean{
        // TODO: Check on Database for the user
        return true
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