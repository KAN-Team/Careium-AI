package com.example.careium.frontend.home.fragments

import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.careium.R
import com.example.careium.core.adapters.CalenderAdapter
import com.example.careium.databinding.FragmentCalenderBinding


class CalenderFragment : Fragment(R.layout.fragment_calender) {
    private lateinit var binding: FragmentCalenderBinding

    companion object {
        @JvmStatic
        fun newInstance() =
            CalenderFragment().apply {}
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentCalenderBinding.bind(view)
        val list: ArrayList<Int> = ArrayList()
        val calender = CalenderInformation()

        var start: Int
        var end = 1
        for (i in calender.month downTo calender.startMonth) {
            start = if (i == calender.month) {
                calender.dayNum
            } else {
                calender.getNumberOfDaysInMonth(2022, i)
            }
            if (i == calender.startMonth) {
                end = calender.startDay
            }
            for (j in start downTo end) {
                println("Day : $j Month :$i")
                list.add(j)
            }
        }

        val calenderAdapter = CalenderAdapter(this.context, list)
        binding.recyclerViewCalender.layoutManager = LinearLayoutManager(this.context)
        binding.recyclerViewCalender.adapter = calenderAdapter
    }
}