package com.example.careium.frontend.authentication.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import com.example.careium.R
import com.example.careium.databinding.ErrorCustomViewBinding
import com.example.careium.databinding.FragmentLoginBinding
import com.example.careium.frontend.authentication.activities.SplashActivity
import com.example.careium.frontend.authentication.activities.viewModel
import com.example.careium.frontend.factory.ErrorAlertDialog
import com.example.careium.frontend.home.activities.MainActivity

private const val ARG_PARAM1 = ""

class LoginFragment : Fragment(R.layout.fragment_login) {
    private lateinit var binding: FragmentLoginBinding
    private var fragmentName: String? = null


    companion object {
        @JvmStatic
        fun newInstance(_fragmentName: String) =
            LoginFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, _fragmentName)
                }
            }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.let {
            fragmentName = it.getString(ARG_PARAM1)
        }
        binding = FragmentLoginBinding.bind(view)
        viewModel.mutableAuthTitleLD.value = fragmentName.toString()

        handleClickButtons()
    }


    private fun handleClickButtons() {
        binding.loginBtn.setOnClickListener {
            val email = binding.loginEmail.text?.trim().toString()
            val password = binding.loginPassword.text.toString()

            if (email.isEmpty() || password.isEmpty())
                alert(getString(R.string.error_title), getString(R.string.error_message))
            else {
                if(isLoggedIn())
                    openMainActivity()
            }
        }

        binding.gmailIcon.setOnClickListener {
            // TODO: Handle Login With Gmail Account
        }

        binding.facebookIcon.setOnClickListener {
            // TODO: Handle Login With Facebook Account
        }

        binding.forgetPassword.setOnClickListener {
            // TODO: Go to Forget Password Fragment
        }

    }

    private fun isLoggedIn():Boolean{
        // TODO: Check on Database for the user
        return true
    }

    private fun openMainActivity() {
        startActivity(Intent(activity, MainActivity::class.java))
        SplashActivity._this.finish()
        activity?.finish()
    }


    private fun alert(title: String, message: String) {
        val view: ErrorCustomViewBinding = binding.loginErrorView
        ErrorAlertDialog.alert(view, title, message)
    }

}