package com.example.careium.frontend.home.activities

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.*
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.activityScenarioRule
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4ClassRunner::class)
internal class MainActivityTest {

    @get:Rule
    var activityScenarioRule = activityScenarioRule<MainActivity>()

    //check if the "main activity" is visible
    @Test
    fun checkActivityVisibility() {
        onView(withId(com.example.careium.R.id.drawer_layout))
            .check(matches(isDisplayed()))
    }

    @Test
    fun checkTextVisibility() {
        //check if the "welcome label" is visible
        onView(withId(com.example.careium.R.id.welcome_label))
            .check(matches(isDisplayed()))

        //check if "login button" is visible
        onView(withId(com.example.careium.R.id.login_btn))
            .check(matches(isDisplayed()))

        //check if "register button" is visible
        onView(withId(com.example.careium.R.id.register_btn))
            .check(matches(isDisplayed()))
    }
}