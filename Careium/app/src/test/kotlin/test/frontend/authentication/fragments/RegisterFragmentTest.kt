package test.frontend.authentication.fragments

import com.example.careium.frontend.authentication.fragments.RegisterFragment
import org.junit.Assert.*
import org.junit.Before

import org.junit.Test

class RegisterFragmentTest {

    private lateinit var registerFragmentObj: RegisterFragment

    @Before
    fun beforeRun() {
        registerFragmentObj = RegisterFragment()
    }

    @Test
    fun test1ValidateRegisterInput() {
        val result = registerFragmentObj.isValidRegisterInput(
            name = "",
            email = "",
            password = "",
            conf_pass = ""
        );
        assertFalse(result)
    }

    @Test
    fun test2ValidateRegisterInput() {
        val result = registerFragmentObj.isValidRegisterInput(
            name = "",
            email = "",
            password = "",
            conf_pass = "2"
        );
        assertFalse(result)
    }

    @Test
    fun test3ValidateRegisterInput() {
        val result = registerFragmentObj.isValidRegisterInput(
            name = "",
            email = "",
            password = "123",
            conf_pass = ""
        );
        assertFalse(result)
    }

    @Test
    fun test4ValidateRegisterInput() {

        val result = registerFragmentObj.isValidRegisterInput(
            name = "",
            email = "",
            password = "123",
            conf_pass = "124"
        );
        assertFalse(result)
    }
}