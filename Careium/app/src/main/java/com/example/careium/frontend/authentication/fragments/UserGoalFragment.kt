package com.example.careium.frontend.authentication.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Toast
import androidx.fragment.app.FragmentTransaction
import com.example.careium.R
import com.example.careium.databinding.ErrorCustomViewBinding
import com.example.careium.databinding.FragmentUserGoalBinding
import com.example.careium.frontend.authentication.activities.SplashActivity
import com.example.careium.frontend.authentication.activities.user
import com.example.careium.frontend.factory.ErrorAlertDialog
import com.example.careium.frontend.factory.FutureGoal
import com.example.careium.frontend.home.activities.MainActivity

class UserGoalFragment : Fragment(R.layout.fragment_user_goal) {
    lateinit var binding:FragmentUserGoalBinding

    companion object {
        fun newInstance() = UserGoalFragment().apply {}
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentUserGoalBinding.bind(view)

        updateUserDataUI()
        handleClickButtons()
    }


    private fun handleClickButtons() {
        binding.finalRegister.setOnClickListener {
            val desiredWeight = binding.goalDesiredWeight.text.toString()
            var futureGoal: FutureGoal? = null
            when {
                binding.goalLoseWeight.isChecked -> futureGoal = FutureGoal.LoseWeight
                binding.goalGainWeight.isChecked -> futureGoal = FutureGoal.GainWeight
                binding.goalFitnessTracker.isChecked -> futureGoal = FutureGoal.FitnessTracker
                binding.goalPatientTreatment.isChecked -> futureGoal = FutureGoal.PatientTreatment
            }


            when {
                desiredWeight.isEmpty() -> alert(getString(R.string.error_title), getString(R.string.error_weight_message))
                futureGoal == null -> alert(getString(R.string.error_title), getString(R.string.error_future_goal_message))
                else -> {
                    saveUserData(desiredWeight.toFloat(), futureGoal)
                    saveDataOnDatabase()
                    Toast.makeText(activity, getString(R.string.confirmation_register_msg), Toast.LENGTH_SHORT).show()
                    openMainActivity()
                }
            }

        }


        binding.backIcon.setOnClickListener {
            activity?.supportFragmentManager?.beginTransaction()
                ?.replace(R.id.auth_frame, UserInfoFragment.newInstance())
                ?.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                ?.commit()
        }

    }

    private fun saveDataOnDatabase() {
        // TODO Save User Object on Database
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

    private fun saveUserData(desiredWeight:Float,futureGoal: FutureGoal){
        user.desiredWeight = desiredWeight
        user.futureGoal = futureGoal
    }

    private fun updateUserDataUI() {
        if (user.desiredWeight!=0f && user.futureGoal != null){
            binding.goalDesiredWeight.setText(user.desiredWeight.toString())
            when (user.futureGoal) {
                FutureGoal.LoseWeight -> binding.goalLoseWeight.isChecked = true
                FutureGoal.GainWeight -> binding.goalGainWeight.isChecked = true
                FutureGoal.FitnessTracker -> binding.goalFitnessTracker.isChecked = true
                FutureGoal.PatientTreatment -> binding.goalPatientTreatment.isChecked = true
            }
        }
    }

}