package com.example.careium.frontend.home.fragments

import android.view.LayoutInflater
import android.view.ViewGroup
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.example.careium.R
import com.example.careium.databinding.FragmentCalenderItemBinding

class Calender_item : Fragment() {
    var binding: FragmentCalenderItemBinding? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_calender_item, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentCalenderItemBinding.bind(view)
    }

    companion object {
        fun newInstance(): Calender_item {
            return Calender_item().apply { }
        }
    }
}