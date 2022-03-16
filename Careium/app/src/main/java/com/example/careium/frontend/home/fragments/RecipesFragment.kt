package com.example.careium.frontend.home.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.careium.R
import com.example.careium.databinding.FragmentRecipesBinding

class RecipesFragment : Fragment(R.layout.fragment_recipes) {

    private lateinit var binding: FragmentRecipesBinding

    companion object {
        @JvmStatic
        fun newInstance() =
            RecipesFragment().apply {}
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentRecipesBinding.bind(view)

    }

}