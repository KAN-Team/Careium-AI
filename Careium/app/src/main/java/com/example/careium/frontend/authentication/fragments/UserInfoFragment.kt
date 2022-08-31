package com.example.careium.frontend.authentication.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.example.careium.R
import com.example.careium.core.authentication.Authenticator
import com.example.careium.databinding.LayoutErrorCustomViewBinding
import com.example.careium.databinding.FragmentUserInfoBinding
import com.example.careium.frontend.factory.ErrorAlertDialog
import com.example.careium.frontend.factory.Gender

class UserInfoFragment : Fragment(R.layout.fragment_user_info) {
    private lateinit var binding: FragmentUserInfoBinding
    private val user = Authenticator.user

    companion object {
        fun newInstance() = UserInfoFragment().apply {}
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentUserInfoBinding.bind(view)

        updateUserDataUI()
        handleClickButtons()
    }

    private fun handleClickButtons() {
        binding.infoTransition.nextIcon.setOnClickListener {
            val height = binding.infoHeight.text.toString()
            val weight = binding.infoWeight.text.toString()
            val age = binding.infoAge.text.toString()
            var gender: Gender? = null
            when {
                binding.infoMale.isChecked -> gender = Gender.Male
                binding.infoFemale.isChecked -> gender = Gender.Female
            }


            if (height.isEmpty() || weight.isEmpty() || age.isEmpty())
                alert(getString(R.string.error_title), getString(R.string.error_message))
            else if (gender == null)
                alert(getString(R.string.error_title), getString(R.string.error_gender_message))
            else {
                saveUserData(height.toFloat(), weight.toFloat(), age.toInt(), gender)
                openGoalScreen()
            }

        }

        binding.infoTransition.backIcon.setOnClickListener {
            activity?.supportFragmentManager?.beginTransaction()
                ?.replace(R.id.auth_frame, RegisterFragment.newInstance())
                ?.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                ?.commit()
        }

    }


    private fun alert(title: String, message: String) {
        val view: LayoutErrorCustomViewBinding = binding.infoErrorView
        ErrorAlertDialog.alert(view, title, message)
    }

    private fun openGoalScreen() {
        activity?.supportFragmentManager?.beginTransaction()
            ?.replace(R.id.auth_frame, UserGoalFragment.newInstance())
            ?.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
            ?.commit()
    }

    private fun saveUserData(height: Float, weight: Float, age: Int, gender: Gender) {
        user.height = height
        user.weight = weight
        user.age = age
        user.gender = gender
    }

    private fun updateUserDataUI() {
        if (user.height != 0f && user.weight != 0f && user.age != 0 && user.gender != null) {
            binding.infoHeight.setText(user.height.toString())
            binding.infoWeight.setText(user.weight.toString())
            binding.infoAge.setText(user.age.toString())
            when (user.gender) {
                Gender.Male -> binding.infoMale.isChecked = true
                Gender.Female -> binding.infoFemale.isChecked = true
                else -> {}
            }
        }
    }

}