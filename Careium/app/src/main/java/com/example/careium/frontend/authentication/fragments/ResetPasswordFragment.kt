package com.example.careium.frontend.authentication.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Toast
import androidx.fragment.app.FragmentTransaction
import com.example.careium.R
import com.example.careium.databinding.ErrorCustomViewBinding
import com.example.careium.databinding.FragmentResetPasswordBinding
import com.example.careium.frontend.factory.ErrorAlertDialog

class ResetPasswordFragment : Fragment(R.layout.fragment_reset_password) {
    lateinit var binding: FragmentResetPasswordBinding

    companion object {
        fun newInstance() = ResetPasswordFragment().apply {}
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentResetPasswordBinding.bind(view)

        handleClickButton()
    }


    private fun handleClickButton() {
        binding.sendBtn.setOnClickListener {
            val email = binding.resetPasswordEmail.text?.trim().toString()

            when{
                email.isEmpty() -> alert(getString(R.string.error_title), getString(R.string.error_message))
                else -> {
                    sendResendEmail()
                    Toast.makeText(activity, getString(R.string.confirmation_reset_msg), Toast.LENGTH_SHORT).show()
                    returnToLogin()
                }
            }

        }

        binding.backIcon.setOnClickListener {
            returnToLogin()
        }

    }

    private fun sendResendEmail(){
        // TODO: Send an Email to reset User Password
    }

    private fun alert(title: String, message: String) {
        val view: ErrorCustomViewBinding = binding.resetErrorView
        ErrorAlertDialog.alert(view, title, message)
    }

    private fun returnToLogin(){
        activity?.supportFragmentManager?.beginTransaction()
            ?.replace(R.id.auth_frame, LoginFragment.newInstance())
            ?.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
            ?.commit()
    }

}