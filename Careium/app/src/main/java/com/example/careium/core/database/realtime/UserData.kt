package com.example.careium.core.database.realtime

import android.util.Log
import com.example.careium.core.models.User
import com.example.careium.frontend.factory.FutureGoal
import com.example.careium.frontend.factory.Gender
import com.example.careium.frontend.factory.UserDataViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import java.lang.Exception

class UserData : Thread() {
    private lateinit var user: User
    private var authInstance: FirebaseAuth = FirebaseAuth.getInstance()

    override fun run() {
        try {
            val userID: String? = this.authInstance.currentUser?.uid
            val root = FirebaseDatabase.getInstance().reference.child("users").child(userID!!)
            root.child("name").setValue(this.user.name)
            root.child("age").setValue(this.user.age)
            root.child("gender").setValue(this.user.gender)
            root.child("email").setValue(this.user.email)
            root.child("password").setValue(this.user.password)
            root.child("height").setValue(this.user.height)
            root.child("weight").setValue(this.user.weight)
            root.child("future_goal").setValue(this.user.futureGoal)
            root.child("desired_weight").setValue(this.user.desiredWeight)

        } catch (e: Exception) {
            Log.d("database", "There are exception in storing user data")
        }

    }

    fun saveUserData(user: User) {
        this.user = user
        this.start()
    }

    fun getUserData(userDataViewModel: UserDataViewModel) {
        try {
            val user = User()
            val userID: String? = FirebaseAuth.getInstance().currentUser?.uid
            val root = FirebaseDatabase.getInstance().reference
            val node = root.child("users").child(userID!!)

            val valueEventListener: ValueEventListener = object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    if (dataSnapshot.exists()) {
                        for (snapshot in dataSnapshot.children)
                            setUserData(snapshot, user)
                    } else
                        Log.d("database", "user id does not exist")

                    userDataViewModel.mutableUserData.value = user
                }

                override fun onCancelled(databaseError: DatabaseError) {}
            }
            node.addListenerForSingleValueEvent(valueEventListener)
        } catch (e: Exception) {
            Log.d("database", "There are exception in user data DB!!!")
        }
    }

    private fun setUserData(snapshot: DataSnapshot, user: User) {
        when (snapshot.key) {
            "name" -> user.name = snapshot.value.toString()
            "age" -> user.age = snapshot.value.toString().toInt()
            "email" -> user.email = snapshot.value.toString()
            "password" -> user.password = snapshot.value.toString()
            "height" -> user.height = snapshot.value.toString().toFloat()
            "weight" -> user.weight = snapshot.value.toString().toFloat()
            "desired_weight" -> user.desiredWeight = snapshot.value.toString().toFloat()
        }

        if (snapshot.key.toString() == "gender") {
            if (snapshot.value.toString() == "Male") user.gender = Gender.Male
            else user.gender = Gender.Female
        } else if (snapshot.key.toString() == "future_goal") {
            when {
                snapshot.value.toString() == "LoseWeight" -> user.futureGoal = FutureGoal.LoseWeight
                snapshot.value.toString() == "GainWeight" -> user.futureGoal = FutureGoal.GainWeight
                snapshot.value.toString() == "FitnessTracker" -> user.futureGoal =
                    FutureGoal.FitnessTracker
                else -> user.futureGoal = FutureGoal.PatientTreatment
            }
        }
    }

}