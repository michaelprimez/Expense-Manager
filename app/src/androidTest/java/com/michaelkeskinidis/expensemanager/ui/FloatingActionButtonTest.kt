package com.michaelkeskinidis.expensemanager.ui

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import com.michaelkeskinidis.expensemanager.R
import com.michaelkeskinidis.expensemanager.ui.activities.MainActivity
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class FloatingActionButtonTest {

    @Rule
    @JvmField
    var activityTestRule = ActivityTestRule(MainActivity::class.java)

    @Test
    fun floatingActionButtonStartInsertTransactionFragment() {
        onView(withId(R.id.fab)).perform(click())
        onView(withId(R.id.switchExpenseOrIncome)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }
}