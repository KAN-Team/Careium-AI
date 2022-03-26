package com.example.careium.frontend.authentication.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import com.example.careium.R
import com.example.careium.databinding.FragmentLoginBinding
import com.example.careium.frontend.authentication.activities.viewModel

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
        //your logic here

    }


}