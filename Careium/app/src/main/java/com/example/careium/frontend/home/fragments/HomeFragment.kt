package com.example.careium.frontend.home.fragments

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.lifecycle.ViewModelProviders
import com.example.careium.R
import com.example.careium.databinding.FragmentHomeBinding
import com.example.careium.frontend.factory.DishNameViewModel
import com.example.careium.frontend.factory.NutritionViewModel
import com.example.careium.frontend.home.activities.dishNameViewModel
import com.example.careium.frontend.home.activities.nutritionViewModel
import java.text.SimpleDateFormat
import java.util.*

class HomeFragment : Fragment(R.layout.fragment_home) {

    private lateinit var binding: FragmentHomeBinding

    // Date View Day
    private var daysFrom: Int = 0

    // Main Components
    private var progressVal: Int = 0
    private var caloriesTarget: Int = 3000
    private var caloriesVal: Int = 0
    private var carbsTarget: Float = 100f
    private var carbsVal: Float = 0f      // Suppose to be Zero but it's kept for visualization
    private var fatsTarget: Float = 100f
    private var fatsVal: Float = 0f        // Suppose to be Zero but it's kept for visualization
    private var proteinsTrgt: Float = 100f
    private var proteinsVal: Float = 0f    // Suppose to be Zero but it's kept for visualization

    companion object {
        @JvmStatic
        fun newInstance() =
            HomeFragment().apply {}
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentHomeBinding.bind(view)
        // Observe The Nutrition Values and Class Name
        observeNutrition()
        observeClassification()

        // setting target values for each component
        hookTargets()

        // initializing date section
        hookDateSection()

        // initializing progress section
        hookProgressSection()

        // initializing components values
        hookComponentsSection()
    }

    private fun hookTargets() {
        // Setting COMPONENTS progress max value
        binding.progressHomeCircular.max = caloriesTarget
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

    private fun hookDateSection() {
        //binding.textCalendarDate.text = getDate(0) // display the date of the current day

        binding.imageButtonPrevDate.setOnClickListener {
            daysFrom++
            binding.textCalendarDate.text = getDate(daysFrom)

            if (binding.imageButtonNextDate.visibility == View.INVISIBLE)
                binding.imageButtonNextDate.visibility = View.VISIBLE
        }

        binding.imageButtonNextDate.setOnClickListener {
            if (daysFrom > 0)
                daysFrom--
            binding.textCalendarDate.text = getDate(daysFrom)

            if (daysFrom == 0 && binding.imageButtonNextDate.visibility == View.VISIBLE)
                binding.imageButtonNextDate.visibility = View.INVISIBLE
        }
    }

    private fun getDate(from: Int): String {
        val cal: Calendar = Calendar.getInstance()
        cal.add(Calendar.DATE, -from)
        val sdf = SimpleDateFormat("EEEE MMM dd, yyyy", Locale.UK) // e.g. (Friday Mar 04, 2022)
        return sdf.format(cal.time)
    }

    private fun hookProgressSection() {
        updateProgress()

        binding.progressHomeCircular.setOnClickListener {
            if (progressVal > 100) {
                progressVal -= 100
                caloriesVal += 100
                caloriesTarget -= 100
                updateProgress()
            } else {
                progressVal = 0
                //caloriesVal = 0
                caloriesTarget = 3000
                updateProgress()
            }
        }
    }

    private fun updateProgress() {
        binding.progressHomeCircular.progress = progressVal
        binding.texTProgressHome.text = "$progressVal"
        updateComponentsSection()
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

        updateComponentsSection()
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

    private fun observeNutrition() {
        nutritionViewModel = ViewModelProviders.of(this).get(NutritionViewModel::class.java)
        nutritionViewModel.mutableNutrition.observe(viewLifecycleOwner) { nutritionList ->
            progressVal = nutritionList[0].toInt()
            caloriesVal = nutritionList[0].toInt()
            carbsVal = nutritionList[3]
            fatsVal = nutritionList[2]
            proteinsVal = nutritionList[4]
            updateProgress()
        }
    }

    private fun observeClassification() {
        dishNameViewModel = ViewModelProviders.of(this).get(DishNameViewModel::class.java)
        dishNameViewModel.mutableDishName.observe(viewLifecycleOwner) { className ->
            binding.textCalendarDate.text = className
        // will be changed later to class name text view not textCalendarDate

        }
    }

}