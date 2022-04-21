package com.example.careium.core.database.authentication

import com.google.firebase.auth.FirebaseAuth

abstract class Auth : Thread() {
    protected var authInstance: FirebaseAuth = FirebaseAuth.getInstance()
    protected lateinit var authViewModel: AuthViewModel

    abstract override fun run()

}