package com.example.careium.core.database.authentication

class ResetPassword(private var email: String) : Auth() {
    override fun run() {
        this.authInstance.sendPasswordResetEmail(this.email).addOnCompleteListener { task ->
            //set the view model with the callback from database authentication
            this.authViewModel.mutableIsAuthComplete.value = task.isSuccessful

        }
    }

    fun resetPassword(authViewModel: AuthViewModel) {
        this.authViewModel = authViewModel
        this.start()
    }
}