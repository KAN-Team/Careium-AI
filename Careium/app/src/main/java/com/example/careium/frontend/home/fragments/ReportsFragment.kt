package com.example.careium.frontend.home.fragments

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import com.example.careium.R
import com.example.careium.databinding.FragmentReportsBinding
import java.text.SimpleDateFormat
import java.util.*

class ReportsFragment : Fragment(R.layout.fragment_reports) {
    private lateinit var binding: FragmentReportsBinding

    private var today: Int = 0
    private var monthCounter: Int = 0
    private var caloriesTarget: Int = 3000
    private var caloriesVal: Int = 100
    private var carbsTarget: Float = 100f
    private var carbsVal: Float = 50f      // Suppose to be Zero but it's kept for visualization
    private var fatsTarget: Float = 100f
    private var fatsVal: Float = 30f        // Suppose to be Zero but it's kept for visualization
    private var proteinsTrgt: Float = 100f
    private var proteinsVal: Float = 40f


    companion object {
        fun newInstance() = ReportsFragment().apply {}
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentReportsBinding.bind(view)

        today = getCurrentDay()
        hookComponentsSection()
        updateComponentsSection()
        hookTargets()
        updateDate()
        listenDate()

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
        binding.layoutCalsItem.progressCmpnt.progress = caloriesVal
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
        binding.layoutProteinsItem.progressCmpnt.max = proteinsTrgt.toInt()

        // Setting COMPONENTS target values to the views
        binding.layoutCalsItem.textCmpntTarget.text = getString(R.string.cal_val, caloriesTarget)
        binding.layoutCarbsItem.textCmpntTarget.text = getString(R.string.gram_val, carbsTarget)
        binding.layoutFatsItem.textCmpntTarget.text = getString(R.string.gram_val, fatsTarget)
        binding.layoutProteinsItem.textCmpntTarget.text = getString(R.string.gram_val, proteinsTrgt)

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

    private fun listenDate() {
        binding.imageButtonPrevDate.setOnClickListener {
            binding.imageButtonNextDate.visibility = View.VISIBLE
            if (today - 8 <= 1) {
                monthCounter++
                today = 30
            } else today -= 8

            updateDate()
        }

        binding.imageButtonNextDate.setOnClickListener {
            if (today + 8 >= 30) {
                monthCounter--
                today = 8
            } else today += 8
            if (today >= getCurrentDay() && monthCounter == 0)
                binding.imageButtonNextDate.visibility = View.INVISIBLE

            updateDate()
        }

    }

}