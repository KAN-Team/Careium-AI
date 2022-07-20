package com.example.careium.frontend.authentication.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Toast
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.ViewModelProviders
import com.example.careium.R
import com.example.careium.core.authentication.Authenticator
import com.example.careium.core.database.authentication.AuthViewModel
import com.example.careium.core.database.authentication.InternetConnection
import com.example.careium.core.database.authentication.Register
import com.example.careium.core.database.authentication.SharedPreferences
import com.example.careium.core.database.realtime.UserData
import com.example.careium.databinding.LayoutErrorCustomViewBinding
import com.example.careium.databinding.FragmentUserGoalBinding
import com.example.careium.frontend.authentication.activities.SplashActivity
import com.example.careium.frontend.factory.ErrorAlertDialog
import com.example.careium.frontend.factory.FutureGoal
import com.example.careium.frontend.home.activities.MainActivity

class UserGoalFragment : Fragment(R.layout.fragment_user_goal) {
    private lateinit var binding: FragmentUserGoalBinding
    private lateinit var authViewModel: AuthViewModel
    private val user = Authenticator.user

    companion object {
        fun newInstance() = UserGoalFragment().apply {}
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentUserGoalBinding.bind(view)
        authViewModel = ViewModelProviders.of(this).get(AuthViewModel::class.java)
        observeAuthCallBackChange()

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
                desiredWeight.isEmpty() -> alert(
                    getString(R.string.error_title),
                    getString(R.string.error_weight_message)
                )
                futureGoal == null -> alert(
                    getString(R.string.error_title),
                    getString(R.string.error_future_goal_message)
                )
                else -> {
                    saveUserData(desiredWeight.toFloat(), futureGoal)
                    createNewAccount()
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

    private fun createNewAccount() {
        if (InternetConnection.isConnected(this.requireContext())) {
            val register = Register(user.email, user.password)
            register.createNewAccount(authViewModel)
        } else
            Toast.makeText(activity, getString(R.string.no_internet_connection), Toast.LENGTH_SHORT)
                .show()
    }

    private fun observeAuthCallBackChange() {
        authViewModel.mutableIsAuthComplete.observe(viewLifecycleOwner) { isLogged ->
            if (isLogged) {
                showProgress()
                saveDataOnDatabase()
                commitEmailOnSharedPreference()
                openMainActivity()
            } else
                Toast.makeText(
                    activity,
                    getString(R.string.already_has_account),
                    Toast.LENGTH_SHORT
                ).show()
        }
    }

    private fun saveDataOnDatabase() {
        val userData = UserData()
        userData.saveUserData(user)
    }

    private fun commitEmailOnSharedPreference() {
        val preference = this.context?.let { SharedPreferences(it) }
        preference?.write(user.email)
    }

    private fun alert(title: String, message: String) {
        val view: LayoutErrorCustomViewBinding = binding.goalErrorView
        ErrorAlertDialog.alert(view, title, message)
    }

    private fun showProgress() {
        binding.goalProgress.visibility = View.VISIBLE
    }

    private fun openMainActivity() {
        Toast.makeText(activity, getString(R.string.confirmation_register_msg), Toast.LENGTH_SHORT)
            .show()
        startActivity(Intent(activity, MainActivity::class.java))
        SplashActivity.instance.finish()
        activity?.finish()
    }

    private fun saveUserData(desiredWeight: Float, futureGoal: FutureGoal) {
        user.desiredWeight = desiredWeight
        user.futureGoal = futureGoal
    }

    private fun updateUserDataUI() {
        if (user.desiredWeight != 0f && user.futureGoal != null) {
            binding.goalDesiredWeight.setText(user.desiredWeight.toString())
            when (user.futureGoal) {
                FutureGoal.LoseWeight -> binding.goalLoseWeight.isChecked = true
                FutureGoal.GainWeight -> binding.goalGainWeight.isChecked = true
                FutureGoal.FitnessTracker -> binding.goalFitnessTracker.isChecked = true
                FutureGoal.PatientTreatment -> binding.goalPatientTreatment.isChecked = true
                else -> {}
            }
        }
    }

}