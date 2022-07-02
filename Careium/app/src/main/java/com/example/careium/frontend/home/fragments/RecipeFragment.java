package com.example.careium.frontend.home.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.careium.R;
import com.example.careium.core.adapters.RecipesAdapter;
import com.example.careium.databinding.FragmentRecipeBinding;


public class RecipeFragment extends Fragment {

    public RecipeFragment() {
        // Required empty public constructor
    }

    public static RecipeFragment newInstance() {
        return new RecipeFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_recipe, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        com.example.careium.databinding.FragmentRecipeBinding binding = FragmentRecipeBinding.bind(view);

        String[] imageText = {"Burger" , "Pan Cake","Chocolate Cake", "white sauce pasta"};
        int[] images = {R.drawable.burger , R.drawable.pan_cake , R.drawable.chocolate_cake , R.drawable.white_sauce_pasta};
        RecipesAdapter recipesAdapter = new RecipesAdapter(getContext(),imageText,images);
        binding.recipesGridview.setAdapter(recipesAdapter);
    }
}