package com.example.careium.frontend.factory

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class AuthViewModel: ViewModel() {
    val mutableAuthTitleLD = MutableLiveData<String>()

}