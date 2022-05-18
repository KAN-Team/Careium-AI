package com.example.careium.frontend.factory

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class FoodDatesViewModel : ViewModel(){
    val mutableFoodDates = MutableLiveData<ArrayList<String>>()
}

class FoodTotalNutritionViewModel : ViewModel(){
    val mutableFoodTotalNutrition = MutableLiveData<ArrayList<Float>>()
}
