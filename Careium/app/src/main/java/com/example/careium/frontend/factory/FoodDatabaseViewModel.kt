package com.example.careium.frontend.factory

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.careium.core.models.User

class FoodDatesViewModel : ViewModel() {
    val mutableFoodDates = MutableLiveData<ArrayList<String>>()
}

class FoodTotalNutritionViewModel : ViewModel() {
    val mutableFoodTotalNutrition = MutableLiveData<ArrayList<Float>>()
}

class UserDataViewModel : ViewModel() {
    val mutableUserData = MutableLiveData<User>()
}
