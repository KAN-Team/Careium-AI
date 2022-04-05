package com.example.careium.core.models

import com.example.careium.frontend.factory.FutureGoal
import com.example.careium.frontend.factory.Gender

class User {
    var name: String = ""
    var email: String = ""
    var password: String = ""
    var height: Float = 0f
    var weight: Float = 0f
    var age: Int = 0
    var gender: Gender? = null
    var desiredWeight: Float = 0f
    var futureGoal: FutureGoal? = null


    companion object {
        private var instance: User? = null
        fun getInstance(): User {
            if (instance == null) return User()
            return instance as User
        }
    }

    fun reset(){
        instance?.name = ""
        instance?.email = ""
        instance?.password = ""
        instance?.height = 0f
        instance?.weight = 0f
        instance?.age = 0
        instance?.gender = null
        instance?.desiredWeight = 0f
        instance?.futureGoal = null
    }

}