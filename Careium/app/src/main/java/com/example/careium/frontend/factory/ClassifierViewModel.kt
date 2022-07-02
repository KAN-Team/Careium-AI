package com.example.careium.frontend.factory

import android.graphics.drawable.BitmapDrawable
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ClassifierViewModel : ViewModel() {
    val mutableDishName = MutableLiveData<String>()
    val mutableDishImage = MutableLiveData<BitmapDrawable>()
}