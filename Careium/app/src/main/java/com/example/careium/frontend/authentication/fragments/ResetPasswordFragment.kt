package com.example.careium.frontend.authentication.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Toast
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.ViewModelProviders
import com.example.careium.R
import com.example.careium.core.database.authentication.AuthViewModel
import com.example.careium.core.database.authentication.InternetConnection
import com.example.careium.core.database.authentication.ResetPassword
import com.example.careium.databinding.LayoutErrorCustomViewBinding
import com.example.careium.databinding.FragmentResetPasswordBinding
import com.example.careium.frontend.factory.ErrorAlertDialog

class ResetPasswordFragment : Fragment(R.layout.fragment_reset_password) {
    private lateinit var binding: FragmentResetPasswordBinding
    private lateinit var email: String
    private lateinit var authViewModel: AuthViewModel

    companion object {
        fun newInstance() = ResetPasswordFragment().apply {}
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentResetPasswordBinding.bind(view)
        authViewModel = ViewModelProviders.of(this).get(AuthViewModel::class.java)
        observeResetResultChange()

        handleClickButton()
    }


    private fun handleClickButton() {
        binding.sendBtn.setOnClickListener {
            email = binding.resetPasswordEmail.text?.trim().toString()

            when {
                email.isEmpty() -> alert(
                    getString(R.string.error_title),
                    getString(R.string.error_message)
                )
                else -> {
                    sendResendEmail()
                }
            }

        }

        binding.backIcon.setOnClickListener {
            returnToLogin()
        }

    }

    private fun sendResendEmail() {
        if (InternetConnection.isConnected(this.requireContext())) {
            val reset = ResetPassword(email)
            reset.resetPassword(authViewModel)
        } else
            Toast.makeText(activity, getString(R.string.no_internet_connection), Toast.LENGTH_SHORT)
                .show()
    }

    private fun observeResetResultChange() {
        authViewModel.mutableIsAuthComplete.observe(viewLifecycleOwner) { isSent ->
            if (isSent) {
                Toast.makeText(
                    activity,
                    getString(R.string.confirmation_reset_msg),
                    Toast.LENGTH_SHORT
                ).show()
                returnToLogin()
            } else
                Toast.makeText(activity, getString(R.string.failed_reset_msg), Toast.LENGTH_SHORT)
                    .show()
        }
    }

    private fun alert(title: String, message: String) {
        val view: LayoutErrorCustomViewBinding = binding.resetErrorView
        ErrorAlertDialog.alert(view, title, message)
    }

    private fun returnToLogin() {
        activity?.supportFragmentManager?.beginTransaction()
            ?.replace(R.id.auth_frame, LoginFragment.newInstance())
            ?.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
            ?.commit()
    }

}