package com.example.careium.frontend.factory

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class NutritionViewModel : ViewModel() {
    val mutableNutrition = MutableLiveData<ArrayList<Float>>()
}