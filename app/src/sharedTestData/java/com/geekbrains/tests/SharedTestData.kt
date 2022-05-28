package com.geekbrains.tests

import android.view.View
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.matcher.ViewMatchers
import org.hamcrest.Matcher

internal const val SEARCH_EDIT_TEXT = "searchEditText"
internal const val SEARCH_BUTTON = "searchButton"
internal const val TOTAL_COUNT_TEXT_VIEW = "totalCountTextView"
internal const val TO_DETAILS_ACTIVITY_BUTTON = "toDetailsActivityButton"
internal const val INCREMENT_BUTTON = "incrementButton"
internal const val DECREMENT_BUTTON = "decrementButton"

internal const val TEST_QUERY = "dggv"
internal const val NUMBER_OF_RESULTS_FAKE = "Number of results: 20"
internal const val NUMBER_OF_RESULTS_REAL = "Number of results: 5"
internal const val NUMBER_OF_RESULTS_ZERO = "Number of results: 0"
internal const val NUMBER_OF_RESULTS_PLUS_ONE = "Number of results: 1"
internal const val NUMBER_OF_RESULTS_MINUS_ONE = "Number of results: -1"

internal const val TEST_NUMBER = 42
internal const val DEFAULT_VAL = 0
internal const val TOTAL_COUNT = 0
internal const val NUMBER_OF_INVOCATIONS_ONE = 1
internal const val MOCK_VALUE = 101

internal const val SEARCH_ERROR_MESSAGE = "Search results or total count are null"

internal const val TIMEOUT = 8000L
internal const val DELAY_TIME = 3000L

internal fun delay(delay: Long): ViewAction {
    return object : ViewAction {
        override fun getConstraints(): Matcher<View> = ViewMatchers.isRoot()

        override fun getDescription(): String = "wait for $2 seconds"

        override fun perform(uiController: UiController, view: View?) {
            uiController.loopMainThreadForAtLeast(delay)
        }
    }
}