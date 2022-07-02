package com.example.careium.core.models

data class FoodCalories(private var foodName: String, private var foodCalories: Int) {

    fun getFoodName(): String {
        return this.foodName
    }

    fun getFoodCalories(): Int {
        return this.foodCalories
    }
}