package com.example.careium.ui.fragments

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.careium.R
import com.example.careium.databinding.FragmentRecipesBinding
import java.text.SimpleDateFormat
import java.util.*

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