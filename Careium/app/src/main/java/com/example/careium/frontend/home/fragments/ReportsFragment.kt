package com.example.careium.frontend.home.fragments

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.example.careium.R
import com.example.careium.core.database.authentication.InternetConnection
import com.example.careium.core.database.realtime.FoodNutrition
import com.example.careium.core.database.realtime.UserData
import com.example.careium.databinding.FragmentReportsBinding
import com.example.careium.frontend.factory.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList
import kotlin.math.abs


class ReportsFragment : Fragment(R.layout.fragment_reports) {
    private lateinit var binding: FragmentReportsBinding
    private lateinit var foodDatesViewModel: FoodDatesViewModel
    private lateinit var foodTotalNutritionViewModel: FoodTotalNutritionViewModel
    private lateinit var userDataViewModel: UserDataViewModel

    private var today: Int = 0
    private var monthCounter: Int = 0
    private var caloriesTarget: Int = 5000
    private var caloriesVal: Float = 0f
    private var carbsTarget: Float = 700f
    private var carbsVal: Float = 00f
    private var fatsTarget: Float = 700f
    private var fatsVal: Float = 0f
    private var proteinsTarget: Float = 700f
    private var proteinsVal: Float = 0f


    companion object {
        fun newInstance() = ReportsFragment().apply {}
        var hasReport: Boolean = true
        var numberOfExistenceMeals = 0
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentReportsBinding.bind(view)

        // Database callback listeners
        foodDatesViewModel = ViewModelProviders.of(this).get(FoodDatesViewModel::class.java)
        observeFoodDatesCallBackChange()
        foodTotalNutritionViewModel =
            ViewModelProviders.of(this).get(FoodTotalNutritionViewModel::class.java)
        observeFoodNutritionCallBackChange()
        userDataViewModel = ViewModelProviders.of(this).get(UserDataViewModel::class.java)
        observeUserDataCallBackChange()

        // set ui components
        today = getCurrentDay()
        binding.waitContainer.visibility = View.VISIBLE
        hookComponentsSection()
        updateComponentsSection()
        hookTargets()
        updateDate()
        listenDateChange()
        getFoodDateNodesFromDatabase()
    }

    private fun hookComponentsSection() {
        // Hooking CALORIES
        binding.layoutCalsItem.textCmpntLabel.text = getString(R.string.calories)
        // Hooking CARBS
        binding.layoutCarbsItem.textCmpntLabel.text = getString(R.string.carbs)
        // Hooking FATS
        binding.layoutFatsItem.textCmpntLabel.text = getString(R.string.fats)
        // Hooking PROTEINS
        binding.layoutProteinsItem.textCmpntLabel.text = getString(R.string.proteins)
    }

    private fun updateComponentsSection() {
        // CALORIES
        binding.layoutCalsItem.textCmpntValue.text = "$caloriesVal"
        binding.layoutCalsItem.progressCmpnt.progress = caloriesVal.toInt()
        // CARBS
        binding.layoutCarbsItem.textCmpntValue.text = "$carbsVal"
        binding.layoutCarbsItem.progressCmpnt.progress = carbsVal.toInt()
        // FATS
        binding.layoutFatsItem.textCmpntValue.text = "$fatsVal"
        binding.layoutFatsItem.progressCmpnt.progress = fatsVal.toInt()
        // PROTEINS
        binding.layoutProteinsItem.textCmpntValue.text = "$proteinsVal"
        binding.layoutProteinsItem.progressCmpnt.progress = proteinsVal.toInt()
    }

    private fun hookTargets() {
        // Setting COMPONENTS progress max value
        binding.layoutCalsItem.progressCmpnt.max = caloriesTarget
        binding.layoutCarbsItem.progressCmpnt.max = carbsTarget.toInt()
        binding.layoutFatsItem.progressCmpnt.max = fatsTarget.toInt()
        binding.layoutProteinsItem.progressCmpnt.max = proteinsTarget.toInt()

        // Setting COMPONENTS target values to the views
        binding.layoutCalsItem.textCmpntTarget.text = getString(R.string.cal_val, caloriesTarget)
        binding.layoutCarbsItem.textCmpntTarget.text = getString(R.string.gram_val, carbsTarget)
        binding.layoutFatsItem.textCmpntTarget.text = getString(R.string.gram_val, fatsTarget)
        binding.layoutProteinsItem.textCmpntTarget.text =
            getString(R.string.gram_val, proteinsTarget)

        // Setting COMPONENTS progress bar colors
        binding.layoutCalsItem.progressCmpnt.setIndicatorColor(Color.parseColor("#F29C2B"))     // gold
        binding.layoutCarbsItem.progressCmpnt.setIndicatorColor(Color.parseColor("#1F640A"))    // green
        binding.layoutFatsItem.progressCmpnt.setIndicatorColor(Color.parseColor("#E8222D"))     // crimson
        binding.layoutProteinsItem.progressCmpnt.setIndicatorColor(Color.parseColor("#2E94B9")) // Indigo
    }

    @SuppressLint("SetTextI18n")
    private fun updateDate() {
        var lastWeekDay = 1
        if (today - 7 > 1) lastWeekDay = today - 7

        binding.textCalendarDate.text =
            lastWeekDay.toString() + "-" + today.toString() + " " + getCurrentDate(monthCounter * 30)
    }

    private fun getCurrentDate(from: Int): String {
        val cal: Calendar = Calendar.getInstance()
        cal.add(Calendar.DATE, -from)
        val sdf = SimpleDateFormat("MMM yyyy", Locale.UK)
        // e.g. (May 2022)
        return sdf.format(cal.time).toString()
    }

    private fun getCurrentDay(): Int {
        val cal: Calendar = Calendar.getInstance()
        val sdf = SimpleDateFormat("dd", Locale.UK)
        // e.g. (15)
        return sdf.format(cal.time).toInt()
    }

    private fun listenDateChange() {
        binding.imageButtonPrevDate.setOnClickListener {
            binding.waitContainer.visibility = View.VISIBLE
            binding.imageButtonNextDate.visibility = View.VISIBLE
            if (today - 8 <= 1) {
                monthCounter++
                today = 30
            } else today -= 8

            updateDate()
            getFoodDateNodesFromDatabase()
        }

        binding.imageButtonNextDate.setOnClickListener {
            binding.waitContainer.visibility = View.VISIBLE
            if (today + 8 >= 30) {
                monthCounter--
                today = 8
            } else today += 8
            if (today >= getCurrentDay() && monthCounter == 0) {
                binding.imageButtonNextDate.visibility = View.INVISIBLE
                today = getCurrentDay()
            }
            updateDate()
            getFoodDateNodesFromDatabase()
        }

    }

    private fun getFoodDateNodesFromDatabase() {
        if (InternetConnection.isConnected(this.requireContext())) {
            val foodDate = FoodNutrition()
            foodDate.getFoodDateNodes(foodDatesViewModel)
        } else {
            hasReport = false
            Toast.makeText(activity, getString(R.string.no_internet_connection), Toast.LENGTH_SHORT)
                .show()
            createReport(floatArrayOf().toCollection(ArrayList()))
        }
    }

    private fun observeFoodDatesCallBackChange() {
        foodDatesViewModel.mutableFoodDates.observe(viewLifecycleOwner) { foodDates ->
            if (foodDates.isNotEmpty())
                hasReport = true
            else {
                hasReport = false
                Toast.makeText(
                    activity,
                    getString(R.string.no_meals_in_database),
                    Toast.LENGTH_LONG
                ).show()
            }
            parseFoodDateString(foodDates)
        }
    }

    private fun parseFoodDateString(datesStrArr: ArrayList<String>) {
        val existenceFoodDates: ArrayList<String> = ArrayList()

        for (date: String in datesStrArr) {
            val dateStr = date.split(",")[0]
            val day: Int = dateStr.split(" ")[0].toInt()
            val month: String = dateStr.split(" ")[1]
            val year: String = dateStr.split(" ")[2]
            val monthYear = "$month $year"
            if (day >= today - 7 && day <= today && getCurrentDate(monthCounter * 30) == monthYear)
                existenceFoodDates.add(date)
        }

        numberOfExistenceMeals = existenceFoodDates.count()
        if (existenceFoodDates.isNotEmpty())
            hasReport = true
        else {
            if (hasReport) {
                hasReport = false
                Toast.makeText(
                    activity,
                    getString(R.string.no_meals_in_database_week),
                    Toast.LENGTH_LONG
                ).show()
            }
        }
        getTotalWeekNutritionFromDB(existenceFoodDates)
    }

    private fun getTotalWeekNutritionFromDB(validDates: ArrayList<String>) {
        val foodNutrition = FoodNutrition()
        foodNutrition.getTotalWeekNutrition(validDates, foodTotalNutritionViewModel)
    }

    private fun observeFoodNutritionCallBackChange() {
        foodTotalNutritionViewModel.mutableFoodTotalNutrition.observe(viewLifecycleOwner)
        { foodTotalNutrition -> createReport(foodTotalNutrition) }
    }

    private fun createReport(foodTotalNutrition: ArrayList<Float>) {
        if (hasReport) {
            binding.reportCardContainer.visibility = View.VISIBLE
            caloriesVal = foodTotalNutrition[0] //[1] -> mass
            fatsVal = foodTotalNutrition[2]
            carbsVal = foodTotalNutrition[3]
            proteinsVal = foodTotalNutrition[4]

            val userData = UserData()
            userData.getUserData(userDataViewModel)
        } else {
            caloriesVal = 0f; fatsVal = 0f; carbsVal = 0f; proteinsVal = 0f
            binding.waitContainer.visibility = View.GONE
            binding.reportCardContainer.visibility = View.INVISIBLE
        }
        updateComponentsSection()

    }

    @SuppressLint("SetTextI18n")
    private fun observeUserDataCallBackChange() {
        userDataViewModel.mutableUserData.observe(viewLifecycleOwner) { user ->
            if (user != null) {
                binding.waitContainer.visibility = View.GONE
                // calculate basal metabolic rate (BMR)
                var bmr = (10 * user.weight) + (6.25 * user.height) - (5 * user.age)
                if (user.gender == Gender.Male) bmr += 5
                else bmr -= 161

                binding.layoutCalsItem.progressCmpnt.max = (bmr * 7).toInt()
                binding.layoutCalsItem.textCmpntTarget.text =
                    getString(R.string.cal_val, (bmr * 7).toInt())

                bmr *= numberOfExistenceMeals // to calc the required calories in all days in week

                // set report messages
                if (caloriesVal < bmr) { // lose weight
                    if (user.futureGoal == FutureGoal.GainWeight) {
                        binding.reportMessage.text = getString(R.string.report_negative_lose_weight)
                        binding.reportNextDaysMessage.text =
                            getString((R.string.report_careful_message))
                    } else {
                        binding.reportMessage.text = getString(R.string.report_positive_lose_weight)
                        binding.reportNextDaysMessage.text =
                            getString((R.string.report_keep_message))
                    }
                    binding.reportWeightTitle.text = getString(R.string.report_lose_weight_title)

                } else { // Gain weight
                    if (user.futureGoal == FutureGoal.GainWeight) {
                        binding.reportMessage.text = getString(R.string.report_positive_lose_weight)
                        binding.reportNextDaysMessage.text =
                            getString((R.string.report_keep_message))
                    } else {
                        binding.reportMessage.text =
                            getString(R.string.report_negative_gained_weight)
                        binding.reportNextDaysMessage.text =
                            getString((R.string.report_careful_message))
                    }
                    binding.reportWeightTitle.text = getString(R.string.report_gain_weight_title)
                }

                val remainingCalories = abs(caloriesVal - bmr)
                val rounded =
                    String.format("%.1f", remainingCalories / 2500)// 2500 calories for every KG
                binding.reportWeight.text = "$rounded KG"
            }

        }
    }

}
