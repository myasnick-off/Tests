package com.geekbrains.tests.view.search

import android.view.View
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.geekbrains.tests.*
import com.geekbrains.tests.DELAY_TIME
import com.geekbrains.tests.TEST_QUERY
import com.geekbrains.tests.delay
import org.hamcrest.Matcher
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MainActivityRecyclerViewTest {

    private lateinit var scenario: ActivityScenario<MainActivity>

    @Before
    fun setup() {
        scenario = ActivityScenario.launch(MainActivity::class.java)
    }

    @Test
    fun activitySearch_ScrollTo() {
        if (BuildConfig.TYPE == MainActivity.FAKE) {
            loadList()
            onView(withId(R.id.recyclerView))
                .perform(
                    RecyclerViewActions.scrollTo<SearchResultAdapter.SearchResultViewHolder>(
                        hasDescendant(withText(FAKE_ITEM_NAME_42))
                    )
                )
        }
    }

    @Test
    fun activitySearch_PerformClickAtPosition() {
        loadList()
        if (BuildConfig.TYPE != MainActivity.FAKE) {
            onView(isRoot()).perform(delay(DELAY_TIME))
        }
        onView(withId(R.id.recyclerView))
            .perform(
                RecyclerViewActions.actionOnItemAtPosition<SearchResultAdapter.SearchResultViewHolder>(
                    POSITION_0,
                    click()
                )
            )
    }

    @Test
    fun activitySearch_PerformClickOnItem() {
        if (BuildConfig.TYPE == MainActivity.FAKE) {
            loadList()
            onView(withId(R.id.recyclerView)).perform(
                    RecyclerViewActions.scrollTo<SearchResultAdapter.SearchResultViewHolder>(
                        hasDescendant(withText(FAKE_ITEM_NAME_50))
                    )
                )
            onView(withId(R.id.recyclerView)).perform(
                    RecyclerViewActions.actionOnItem<SearchResultAdapter.SearchResultViewHolder>(
                        hasDescendant(withText(FAKE_ITEM_NAME_42)),
                        click()
                    )
                )
        }
    }

    @Test
    fun activitySearch_PerformClickOnRealItem() {
        if (BuildConfig.TYPE != MainActivity.FAKE) {
            loadList()
            onView(isRoot()).perform(delay(DELAY_TIME))
            onView(withId(R.id.recyclerView)).perform(
                RecyclerViewActions.actionOnItem<SearchResultAdapter.SearchResultViewHolder>(
                    hasDescendant(withText(REAL_ITEM_NAME)),
                    click()
                )
            )
        }
    }

    @Test
    fun activitySearch_PerformCustomClick() {
        if (BuildConfig.TYPE == MainActivity.FAKE) {
            loadList()
            onView(withId(R.id.recyclerView))
                .perform(
                    RecyclerViewActions.actionOnItemAtPosition<SearchResultAdapter.SearchResultViewHolder>(
                        POSITION_0,
                        tapOnItemWithId(R.id.checkBox)
                    )
                )
        }
    }

    @After
    fun close() {
        scenario.close()
    }

    private fun loadList() {
        onView(withId(R.id.searchEditText)).perform(click())
        onView(withId(R.id.searchEditText)).perform(replaceText(TEST_QUERY), closeSoftKeyboard())
        onView(withId(R.id.searchEditText)).perform(pressImeActionButton())
    }

    private fun tapOnItemWithId(id: Int) = object : ViewAction {
        override fun getDescription(): String {
            return "Нажимаем на view с указанным id"
        }

        override fun getConstraints(): Matcher<View>? {
            return null
        }

        override fun perform(uiController: UiController, view: View) {
            val v = view.findViewById(id) as View
            v.performClick()
        }
    }

    companion object {
        private const val FAKE_ITEM_NAME_42 = "FullName: 42"
        private const val FAKE_ITEM_NAME_50 = "FullName: 50"
        private const val REAL_ITEM_NAME = "Qymaen/schedule"
        private const val POSITION_0 = 0
    }
}