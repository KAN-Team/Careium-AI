package com.example.careium.frontend.authentication.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.example.careium.R
import com.example.careium.databinding.ErrorCustomViewBinding
import com.example.careium.databinding.FragmentRegisterBinding
import com.example.careium.frontend.authentication.activities.viewModel
import com.example.careium.frontend.factory.ErrorAlertDialog

private const val ARG_PARAM1 = ""

class RegisterFragment : Fragment(R.layout.fragment_register) {
    private lateinit var binding: FragmentRegisterBinding
    private var fragmentName: String? = null

    companion object {
        @JvmStatic
        fun newInstance(_fragmentName: String) =
            RegisterFragment().apply {
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
        binding = FragmentRegisterBinding.bind(view)
        viewModel.mutableAuthTitleLD.value = fragmentName.toString()

        handleClickButtons()
    }


    private fun handleClickButtons() {
        binding.dataTransition.nextIcon.setOnClickListener {
            val name = binding.dataName.text.toString()
            val email = binding.dataEmail.text?.trim().toString()
            val password = binding.dataPassword.text.toString()
            val conf_pass = binding.dataConPassword.text.toString()


            if (name.isEmpty() || email.isEmpty() || password.isEmpty() || conf_pass.isEmpty())
                alert(getString(R.string.error_title), getString(R.string.error_message))
            else {
                if (password == conf_pass) {
                    // TODO: store the user data in the object
                    openInfoScreen()
                } else
                    alert(getString(R.string.error_title), getString(R.string.error_confirm_message))
            }

        }

        binding.dataTransition.backIcon.setOnClickListener {
            activity?.finish()
        }

    }

    private fun alert(title: String, message: String) {
        val view: ErrorCustomViewBinding = binding.dataErrorView
        ErrorAlertDialog.alert(view, title, message)
    }

    private fun openInfoScreen() {
        activity?.supportFragmentManager?.beginTransaction()
            ?.replace(R.id.user_data_frame, UserInfoFragment.newInstance())
            ?.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
            ?.commit()
    }

}