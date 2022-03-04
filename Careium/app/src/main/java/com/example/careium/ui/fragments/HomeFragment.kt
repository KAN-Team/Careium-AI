package com.example.careium.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import com.example.careium.R
import com.example.careium.databinding.FragmentHomeBinding
import java.text.SimpleDateFormat
import java.util.*

class HomeFragment : Fragment(R.layout.fragment_home) {

    private lateinit var binding: FragmentHomeBinding

    // Date View Day
    private var daysFrom: Int = 0

    // Main Components
    private var progressVal: Int = 3000
    private var budgetVal: Int = 3000
    private var consumedVal: Int = 0
    private var tempVal: Float = 123f
    private var carbsVal: Float = 50f
    private var fatsVal: Float = 100f
    private var proteinsVal: Float = 180f

    companion object {
        @JvmStatic
        fun newInstance() =
            HomeFragment().apply {}
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentHomeBinding.bind(view)

        // initializing date section
        hookDateSection()

        // initializing components values
        hookComponentsSection()

        // initializing progress section
        hookProgressSection()
    }

    private fun hookDateSection() {
        binding.textCalendarDate.text = getDate(0) // display the date of the current day

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
        // Setting CALORIES progress max value
        binding.progressHomeCircular.max = 3000
        updateProgress()

        binding.progressHomeCircular.setOnClickListener {
            if (progressVal > 0) {
                progressVal -= 100
                consumedVal += 100
                budgetVal -= 100
                updateProgress()
            } else {
                progressVal = 3000
                consumedVal = 0
                budgetVal = 3000
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
        // Hooking BUDGET
        binding.layoutBudgetItem.textCmpntLabel.text = getString(R.string.budget)
        // Hooking CONSUMED
        binding.layoutConsumedItem.textCmpntLabel.text = getString(R.string.consumed)
        // Hooking TEMP
        binding.layoutTempItem.textCmpntLabel.text = getString(R.string.temp)
        // Hooking CARBS
        binding.layoutCarbsItem.textCmpntLabel.text = getString(R.string.carbs)
        // Hooking FATS
        binding.layoutFatsItem.textCmpntLabel.text = getString(R.string.fats)
        // Hooking PROTEINS
        binding.layoutProteinsItem.textCmpntLabel.text = getString(R.string.proteins)

        updateComponentsSection()
    }

    private fun updateComponentsSection() {
        binding.layoutBudgetItem.textCmpntValue.text = getString(R.string.cal_val, budgetVal)
        binding.layoutConsumedItem.textCmpntValue.text = getString(R.string.cal_val, consumedVal)
        binding.layoutTempItem.textCmpntValue.text = getString(R.string.gram_val, tempVal)
        binding.layoutCarbsItem.textCmpntValue.text = getString(R.string.gram_val, carbsVal)
        binding.layoutFatsItem.textCmpntValue.text = getString(R.string.gram_val, fatsVal)
        binding.layoutProteinsItem.textCmpntValue.text = getString(R.string.gram_val, proteinsVal)
    }
}