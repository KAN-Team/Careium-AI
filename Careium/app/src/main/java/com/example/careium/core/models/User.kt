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

        fun reset(_instance: User) {
            _instance.name = ""
            _instance.email = ""
            _instance.password = ""
            _instance.height = 0f
            _instance.weight = 0f
            _instance.age = 0
            _instance.gender = null
            _instance.desiredWeight = 0f
            _instance.futureGoal = null
        }
    }

}