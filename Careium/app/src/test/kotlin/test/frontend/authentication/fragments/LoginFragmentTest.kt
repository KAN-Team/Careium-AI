package test.frontend.authentication.fragments

import com.example.careium.frontend.authentication.fragments.LoginFragment
import org.junit.Assert.*
import org.junit.Before

import org.junit.Test

class LoginFragmentTest {

    private lateinit var loginFragmentObj: LoginFragment

    @Before
    fun beforeRun() {
        loginFragmentObj = LoginFragment()
    }

    @Test
    fun test1ValidateLoginInput() {
        val result = loginFragmentObj.isValidLoginInput(
            email = "",
            password = "123"
        );
        assertFalse(result)
    }

    @Test
    fun test2ValidateLoginInput() {
        val result = loginFragmentObj.isValidLoginInput(
            email = "",
            password = ""
        );
        assertFalse(result)
    }

    @Test
    fun test3ValidateLoginInput() {
        val result = loginFragmentObj.isValidLoginInput(
            email = "",
            password = "12"
        );
        assertFalse(result)
    }
}