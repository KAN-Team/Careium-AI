package com.example.careium.core.database.realtime

import android.util.Log
import com.example.careium.core.models.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import java.lang.Exception

class UserData(private var user: User) : Thread() {
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

    fun saveUserData() {
        this.start()
    }

}