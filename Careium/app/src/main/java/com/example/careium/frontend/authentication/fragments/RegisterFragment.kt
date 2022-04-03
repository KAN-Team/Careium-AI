package com.example.careium.frontend.authentication.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.careium.R
import com.example.careium.databinding.FragmentRegisterBinding
import com.example.careium.frontend.authentication.activities.viewModel

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

        binding.nextInfoIcon.setOnClickListener {
            val name = binding.infoName.text.toString()
            val email = binding.infoEmail.text?.trim().toString()
            val password = binding.infoPassword.text.toString()
            val conf_pass = binding.infoConPassword.text.toString()

            if (name.isEmpty() || email.isEmpty() || password.isEmpty() || conf_pass.isEmpty())
                alert("Error", "Please Fill The empty cell")
            else {
                if (password == conf_pass) {
                    alert("Success", "Congratulations")
                } else
                    alert("Error", "Confirmation Password must be same as password")
            }

        }


        binding.infoBackIcon.setOnClickListener {
            activity?.finish()
        }

    }


    private fun alert(title: String, message: String) {
        binding.infoErrorView.layoutOverlay.visibility = View.VISIBLE
        binding.infoErrorView.errorContainer.visibility = View.VISIBLE
        binding.infoErrorView.errorInfoTitle.text = title
        binding.infoErrorView.errorInfoMessage.text = message
        binding.infoErrorView.errorInfoBtn.setOnClickListener {
            binding.infoErrorView.errorContainer.visibility = View.GONE
            binding.infoErrorView.layoutOverlay.visibility = View.GONE
        }
    }

}