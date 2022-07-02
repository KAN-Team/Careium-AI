package com.example.careium.core.database.realtime

import android.util.Log
import com.example.careium.frontend.factory.FoodDatesViewModel
import com.example.careium.frontend.factory.FoodTotalNutritionViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class FoodNutrition {
    private val userID: String? = FirebaseAuth.getInstance().currentUser?.uid
    private val root = FirebaseDatabase.getInstance().reference

    fun getFoodDateNodes(foodViewModel: FoodDatesViewModel) {
        try {

            val datesString: ArrayList<String> = ArrayList()
            val node = this.root.child("users").child(this.userID!!).child("food_data")

            val valueEventListener: ValueEventListener = object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    if (dataSnapshot.exists()) {
                        for (snapshot in dataSnapshot.children)
                            datesString.add(snapshot.key.toString())
                    } else
                        Log.d("database", "food date not exist!!!")

                    foodViewModel.mutableFoodDates.value = datesString
                }

                override fun onCancelled(databaseError: DatabaseError) {}
            }
            node.addListenerForSingleValueEvent(valueEventListener)
        } catch (e: Exception) {
            Log.d("database", "There are exception in food dates DB!!!")
        }
    }

    fun getTotalWeekNutrition(
        validDates: ArrayList<String>,
        foodNutritionViewModel: FoodTotalNutritionViewModel
    ) {
        try {
            val totalNutrition: ArrayList<Float> =
                floatArrayOf(0f, 0f, 0f, 0f, 0f).toCollection(ArrayList())
            var size = validDates.count()

            for (validDate: String in validDates) { //iterate foreach meal (date string in DB)

                val node = this.root.child("users").child(this.userID!!).child("food_data")
                    .child(validDate)
                val valueEventListener: ValueEventListener = object : ValueEventListener {
                    override fun onDataChange(dataSnapshot: DataSnapshot) {
                        if (dataSnapshot.exists()) {
                            for (snapshot in dataSnapshot.children) {
                                when (snapshot.key) {
                                    "food_calories" -> totalNutrition[0] += snapshot.value.toString()
                                        .toFloat()
                                    "food_mass" -> totalNutrition[1] += snapshot.value.toString()
                                        .toFloat()
                                    "food_fats" -> totalNutrition[2] += snapshot.value.toString()
                                        .toFloat()
                                    "food_carbs" -> totalNutrition[3] += snapshot.value.toString()
                                        .toFloat()
                                    "food_proteins" -> totalNutrition[4] += snapshot.value.toString()
                                        .toFloat()
                                }
                            }
                            size--
                            if (size == 0)
                                foodNutritionViewModel.mutableFoodTotalNutrition.value =
                                    totalNutrition
                        } else
                            Log.d("database", "food date not exist!!!")
                    }

                    override fun onCancelled(databaseError: DatabaseError) {}
                }
                node.addListenerForSingleValueEvent(valueEventListener)

            }
            if (validDates.isEmpty())
                foodNutritionViewModel.mutableFoodTotalNutrition.value = totalNutrition
        } catch (e: Exception) {
            Log.d("database", "There are exception in food nutrition DB")
        }

    }

}