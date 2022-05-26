package com.example.careium.frontend.factory

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class DishNameViewModel: ViewModel() {
    val mutableDishName = MutableLiveData<String>()
}