package com.geekbrains.tests.view.search


import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.geekbrains.tests.DELAY_TIME
import com.geekbrains.tests.R
import com.geekbrains.tests.TEST_QUERY
import com.geekbrains.tests.delay
import org.hamcrest.Matchers.allOf
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@LargeTest
@RunWith(AndroidJUnit4::class)
class AppTestFromRecorder {

    @Rule
    @JvmField
    var mActivityTestRule = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun appTestFromRecorder() {
        onView(allOf(withId(R.id.searchEditText), isDisplayed())).apply {
            perform(replaceText(TEST_QUERY), closeSoftKeyboard())
        }

        onView(allOf(withId(R.id.searchButton), isDisplayed())).apply {
            perform(click())
        }
        onView(isRoot()).perform(delay(DELAY_TIME))

        onView(allOf(withId(R.id.toDetailsActivityButton), isDisplayed())).apply {
            perform(click())
        }

        onView(allOf(withId(R.id.incrementButton), isDisplayed())).apply {
            perform(click())
        }

        val textView = onView(allOf(withId(R.id.totalCountTextView), isDisplayed()))
        textView.check(matches(withText(NUMBER_OF_RESULTS_6)))
    }

    companion object {
        internal const val NUMBER_OF_RESULTS_6 = "Number of results: 6"
    }
}
