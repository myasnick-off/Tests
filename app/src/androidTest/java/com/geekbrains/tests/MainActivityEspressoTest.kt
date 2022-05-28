package com.geekbrains.tests

import android.view.View
import androidx.lifecycle.Lifecycle
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.geekbrains.tests.view.search.MainActivity
import com.geekbrains.tests.view.search.MainActivity.Companion.FAKE
import org.hamcrest.Matcher
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MainActivityEspressoTest {

    private lateinit var scenario: ActivityScenario<MainActivity>

    @Before
    fun setup() {
        scenario = ActivityScenario.launch(MainActivity::class.java)
    }

    @Test
    fun activity_NotNull() {
        scenario.onActivity { mainActivity ->
            assertNotNull(mainActivity)
        }
    }

    @Test
    fun activity_IsResumed() {
        assertEquals(Lifecycle.State.RESUMED, scenario.state)
    }

    @Test
    fun activity_EditText_IsCompletelyDisplayed() {
        onView(withId(R.id.searchEditText)).check(matches(isCompletelyDisplayed()))
    }

    @Test
    fun activity_EditText_HasHint() {
        val assertion = matches(withHint(R.string.search_hint))
        onView(withId(R.id.searchEditText)).check(assertion)
    }

    @Test
    fun activity_ToDetailsActivityButton_IsCompletelyDisplayed() {
        onView(withId(R.id.toDetailsActivityButton)).check(matches(isCompletelyDisplayed()))
    }

    @Test
    fun activity_ToDetailsActivityButton_HasText() {
        val assertion = matches(withText(R.string.to_details))
        onView(withId(R.id.toDetailsActivityButton)).check(assertion)
    }

    @Test
    fun activity_TotalCountTextView_IsNotDisplayed() {
        onView(withId(R.id.totalCountTextView)).check(matches(withEffectiveVisibility(Visibility.INVISIBLE)))
    }

    @Test
    fun activity_ProgressBar_IsNotDisplayed() {
        onView(withId(R.id.progressBar)).check(matches(withEffectiveVisibility(Visibility.GONE)))
    }

    @Test
    fun activity_ProgressBar_IsDisplayed() {
        onView(withId(R.id.searchEditText)).perform(click())
        onView(withId(R.id.searchEditText)).perform(replaceText(TEST_QUERY), closeSoftKeyboard())
        onView(withId(R.id.searchEditText)).perform(pressImeActionButton())
        onView(withId(R.id.progressBar)).check(matches(withEffectiveVisibility(Visibility.VISIBLE)))
    }

    @Test
    fun activity_TotalCountTextView_IsDisplayed() {
        onView(withId(R.id.searchEditText)).perform(click())
        onView(withId(R.id.searchEditText)).perform(replaceText(TEST_QUERY), closeSoftKeyboard())
        onView(withId(R.id.searchEditText)).perform(pressImeActionButton())
        onView(isRoot()).perform(delay())
        onView(withId(R.id.totalCountTextView)).check(matches(withEffectiveVisibility(Visibility.VISIBLE)))
    }

    @Test
    fun activity_Search_IsWorking() {
        onView(withId(R.id.searchEditText)).perform(click())
        onView(withId(R.id.searchEditText)).perform(replaceText(TEST_QUERY), closeSoftKeyboard())
        onView(withId(R.id.searchEditText)).perform(pressImeActionButton())
        onView(isRoot()).perform(delay())
        if (BuildConfig.FLAVOR == FAKE) {
            onView(withId(R.id.totalCountTextView)).check(matches(withText(NUMBER_OF_RESULTS_FAKE)))
        } else {
            onView(withId(R.id.totalCountTextView)).check(matches(withText(NUMBER_OF_RESULTS_REAL)))
        }
    }

    private fun delay(): ViewAction {
        return object : ViewAction {
            override fun getConstraints(): Matcher<View> = isRoot()

            override fun getDescription(): String = "wait for $2 seconds"

            override fun perform(uiController: UiController, view: View?) {
                uiController.loopMainThreadForAtLeast(3000)
            }
        }
    }

    @After
    fun close() {
        scenario.close()
    }
}