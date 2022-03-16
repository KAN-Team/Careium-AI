package com.example.careium.frontend.home.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.TextView
import com.example.careium.R
import com.example.careium.databinding.FragmentProfileBinding

class ProfileFragment : Fragment(R.layout.fragment_profile) {

    private lateinit var binding: FragmentProfileBinding

    companion object {
        @JvmStatic
        fun newInstance() =
            ProfileFragment().apply {}
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentProfileBinding.bind(view)

        val editProfileText: TextView = binding.profileEditButton
        editProfileText.setOnClickListener {
            //TODO: activate editing user profile by enable editing EditViews in profile fragment layout

        }

    }

}