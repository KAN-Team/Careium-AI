package com.example.careium.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import com.example.careium.R
import com.example.careium.databinding.FragmentHomeBinding

class HomeFragment : Fragment(R.layout.fragment_home) {

    private lateinit var binding: FragmentHomeBinding
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

        // Initializing components values
        hookComponents()

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
        updateComponents()
    }

    private fun hookComponents() {
        // Hooking BUDGET
        binding.layoutBudgetItem.textComponentLabel.text = getString(R.string.budget)
        // Hooking CONSUMED
        binding.layoutConsumedItem.textComponentLabel.text = getString(R.string.consumed)
        // Hooking TEMP
        binding.layoutTempItem.textComponentLabel.text = getString(R.string.temp)
        // Hooking CARBS
        binding.layoutCarbsItem.textComponentLabel.text = getString(R.string.carbs)
        // Hooking FATS
        binding.layoutFatsItem.textComponentLabel.text = getString(R.string.fats)
        // Hooking PROTEINS
        binding.layoutProteinsItem.textComponentLabel.text = getString(R.string.proteins)

        updateComponents()
    }

    private fun updateComponents(){
        binding.layoutBudgetItem.textComponentValue.text = "$budgetVal cal"     // BUDGET
        binding.layoutConsumedItem.textComponentValue.text = "$consumedVal cal" // CONSUMED
        binding.layoutTempItem.textComponentValue.text = "$tempVal g"           // TEMP
        binding.layoutCarbsItem.textComponentValue.text = "$carbsVal g"         // CARBS
        binding.layoutFatsItem.textComponentValue.text = "$fatsVal g"           // FATS
        binding.layoutProteinsItem.textComponentValue.text = "$proteinsVal g"   // PROTEINS
    }
}