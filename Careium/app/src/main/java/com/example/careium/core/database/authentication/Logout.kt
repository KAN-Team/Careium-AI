package com.example.careium.core.database.authentication

import com.google.firebase.auth.FirebaseAuth

class Logout {
    private val instance: FirebaseAuth = FirebaseAuth.getInstance()

    fun signOut() {
        this.instance.signOut()
    }

}
