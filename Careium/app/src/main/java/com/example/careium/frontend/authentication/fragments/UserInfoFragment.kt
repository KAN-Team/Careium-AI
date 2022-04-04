package com.example.careium.frontend.authentication.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.example.careium.R
import com.example.careium.databinding.ErrorCustomViewBinding
import com.example.careium.databinding.FragmentUserInfoBinding
import com.example.careium.frontend.factory.ErrorAlertDialog

class UserInfoFragment : Fragment(R.layout.fragment_user_info) {
    private lateinit var binding: FragmentUserInfoBinding
    private val MALE = 0
    private val FEMALE = 1

    companion object {
        fun newInstance() = UserInfoFragment().apply {}
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentUserInfoBinding.bind(view)

        handleClickButtons()
    }

    private fun handleClickButtons() {
        binding.infoTransition.nextIcon.setOnClickListener {
            val height = binding.infoHeight.text.toString()
            val weight = binding.infoWeight.text.toString()
            val age = binding.infoAge.text.toString()
            var gender = -1
            when {
                binding.infoMale.isChecked -> gender = MALE
                binding.infoFemale.isChecked -> gender = FEMALE
            }


            if (height.isEmpty() || weight.isEmpty() || age.isEmpty())
                alert(getString(R.string.error_title), getString(R.string.error_message))
            else if (gender == -1)
                alert(getString(R.string.error_title), getString(R.string.error_gender_message))
             else {
                // TODO: store the user data in the object
                openGoalScreen()
            }

        }

        binding.infoTransition.backIcon.setOnClickListener {
            activity?.supportFragmentManager?.beginTransaction()
                ?.replace(R.id.user_info_frame, RegisterFragment.newInstance(getString(R.string.register)))
                ?.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                ?.commit()
        }

    }


    private fun alert(title: String, message: String) {
        val view: ErrorCustomViewBinding = binding.infoErrorView
        ErrorAlertDialog.alert(view, title, message)
    }

    private fun openGoalScreen() {
        activity?.supportFragmentManager?.beginTransaction()
            ?.replace(R.id.user_info_frame, UserGoalFragment.newInstance())
            ?.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
            ?.commit()
    }

}