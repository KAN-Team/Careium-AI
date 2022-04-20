package com.example.careium.core.database.authentication

import com.google.firebase.auth.FirebaseAuth

abstract class Auth : Thread() {
    protected var authInstance: FirebaseAuth = FirebaseAuth.getInstance()
    lateinit var authViewModel: AuthViewModel

    abstract override fun run()

}