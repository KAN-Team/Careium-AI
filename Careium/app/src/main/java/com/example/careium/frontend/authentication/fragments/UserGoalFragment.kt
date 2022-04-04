package com.example.careium.frontend.authentication.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.fragment.app.FragmentTransaction
import com.example.careium.R
import com.example.careium.databinding.ErrorCustomViewBinding
import com.example.careium.databinding.FragmentUserGoalBinding
import com.example.careium.frontend.authentication.activities.SplashActivity
import com.example.careium.frontend.factory.ErrorAlertDialog
import com.example.careium.frontend.home.activities.MainActivity

class UserGoalFragment : Fragment(R.layout.fragment_user_goal) {
    lateinit var binding:FragmentUserGoalBinding
    val LOSEWEIGHT = 0
    val GAINWEIGHT = 1
    val FITNESSTRACKER = 2
    val PATIENTTREATMENT = 3

    companion object {
        fun newInstance() = UserGoalFragment().apply {}
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentUserGoalBinding.bind(view)

        handleClickButtons()
    }


    private fun handleClickButtons() {
        binding.finalRegister.setOnClickListener {
            val desiredWeight = binding.goalDesiredWeight.text.toString()
            var futureGoal = -1
            when {
                binding.goalLoseWeight.isChecked -> futureGoal = LOSEWEIGHT
                binding.goalGainWeight.isChecked -> futureGoal = GAINWEIGHT
                binding.goalFitnessTracker.isChecked -> futureGoal = FITNESSTRACKER
                binding.goalPatientTreatment.isChecked -> futureGoal = PATIENTTREATMENT
            }

            when {
                desiredWeight.isEmpty() -> alert(getString(R.string.error_title), getString(R.string.error_weight_message))
                futureGoal == -1 -> alert(getString(R.string.error_title), getString(R.string.error_future_goal_message))
                else -> {
                    // TODO: store the user data in the object
                    openMainActivity()
                }
            }

        }

        binding.backIcon.setOnClickListener {
            activity?.supportFragmentManager?.beginTransaction()
                ?.replace(R.id.user_goal_frame, UserInfoFragment.newInstance())
                ?.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                ?.commit()
        }

    }


    private fun alert(title: String, message: String) {
        val view: ErrorCustomViewBinding = binding.goalErrorView
        ErrorAlertDialog.alert(view, title, message)
    }

    private fun openMainActivity() {
        startActivity(Intent(activity, MainActivity::class.java))
        SplashActivity._this.finish()
        activity?.finish()
    }



}