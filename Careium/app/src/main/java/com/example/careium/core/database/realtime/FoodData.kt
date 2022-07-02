package com.example.careium.core.database.realtime

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class FoodData(private val foodName: String, private val nutritionList: ArrayList<Float>) :
    Thread() {
    private var authInstance: FirebaseAuth = FirebaseAuth.getInstance()

    override fun run() {
        try {
            val userID: String? = this.authInstance.currentUser?.uid
            val root = FirebaseDatabase.getInstance().reference.child("users").child(userID!!)
            val foodDataNode = root.child("food_data").child(getCurrentDate())
            foodDataNode.child("food_name").setValue(this.foodName)
            foodDataNode.child("food_calories").setValue(this.nutritionList[0])
            foodDataNode.child("food_mass").setValue(this.nutritionList[1])
            foodDataNode.child("food_fats").setValue(this.nutritionList[2])
            foodDataNode.child("food_carbs").setValue(this.nutritionList[3])
            foodDataNode.child("food_proteins").setValue(this.nutritionList[4])

        } catch (e: Exception) {
            Log.d("database", "There are exception in storing food data")
        }

    }

    fun saveFoodData() {
        this.start()
    }

    private fun getCurrentDate(): String {
        var timeCode = "AM"
        val cal: Calendar = Calendar.getInstance()
        if (cal.get(Calendar.AM_PM) == Calendar.PM) timeCode = "PM"
        val sdf = SimpleDateFormat("dd MMM yyyy, hh:mm:ss", Locale.UK)
        // e.g. (15 May 2022, 01:51:37 AM)
        return sdf.format(cal.time).toString() + " " + timeCode
    }

}