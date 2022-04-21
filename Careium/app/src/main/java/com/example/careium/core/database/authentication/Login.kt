package com.example.careium.core.database.authentication

class Login(private var email: String, private var password: String) : Auth() {

    override fun run() {
        this.authInstance.signInWithEmailAndPassword(this.email, this.password)
            .addOnCompleteListener { task ->
                //set the view model with the callback from database authentication
                this.authViewModel.mutableIsAuthComplete.value = task.isSuccessful

            }
    }

    fun isUserLoggedIn(authViewModel: AuthViewModel) {
        this.authViewModel = authViewModel
        this.start()
    }

}