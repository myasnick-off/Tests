package com.geekbrains.tests

import androidx.core.os.bundleOf
import androidx.fragment.app.testing.FragmentScenario
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.lifecycle.Lifecycle
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.geekbrains.tests.view.details.DetailsFragment
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class DetailsFragmentEspressoTest {

    private lateinit var scenario: FragmentScenario<DetailsFragment>

    @Before
    fun setup() {
        scenario = launchFragmentInContainer()
    }

    @Test
    fun fragment_TestBundle() {
        val fragmentArgs = bundleOf(TOTAL_COUNT_EXTRA to COUNT_FAKE)
        val scenario = launchFragmentInContainer<DetailsFragment>(fragmentArgs)
        scenario.moveToState(Lifecycle.State.RESUMED)
        val assertion = matches(withText(NUMBER_OF_RESULTS_FAKE))
        onView(withId(R.id.totalCountTextView)).check(assertion)
    }

    @Test
    fun fragment_TestSetCountMethod() {
        scenario.onFragment { fragment ->
            fragment.setCount(COUNT_FAKE)
        }
        val assertion = matches(withText(NUMBER_OF_RESULTS_FAKE))
        onView(withId(R.id.totalCountTextView)).check(assertion)
    }

    @Test
    fun fragment_TestIncrementButton() {
        onView(withId(R.id.incrementButton)).perform(click())
        onView(withId(R.id.totalCountTextView)).check(matches(withText(NUMBER_OF_RESULTS_PLUS_ONE)))
    }

    companion object {
        private const val TOTAL_COUNT_EXTRA = "total_count_extra"
        private const val COUNT_FAKE = 20
    }
}