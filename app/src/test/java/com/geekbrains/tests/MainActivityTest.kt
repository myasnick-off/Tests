package com.geekbrains.tests

import android.content.Context
import android.os.Build
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.lifecycle.Lifecycle
import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.geekbrains.tests.view.search.MainActivity
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.annotation.Config

@RunWith(AndroidJUnit4::class)
@Config(sdk = [Build.VERSION_CODES.O_MR1])
class MainActivityTest {

    private lateinit var scenario: ActivityScenario<MainActivity>
    private lateinit var context: Context

    @Before
    fun setup() {
        scenario = ActivityScenario.launch(MainActivity::class.java)
        context = ApplicationProvider.getApplicationContext()
    }

    @Test
    fun activity_NotNull() {
        scenario.onActivity { activity ->
            assertNotNull(activity)
        }
    }

    @Test
    fun activity_IsResumed() {
        assertEquals(Lifecycle.State.RESUMED, scenario.state)
    }

    @Test
    fun activitySearchEditText_NotNull() {
        scenario.onActivity { activity ->
            val searchEditText = activity.findViewById<TextView>(R.id.searchEditText)
            assertNotNull(searchEditText)
        }
    }

    @Test
    fun activitySearchEditText_IsVisible() {
        scenario.onActivity { activity ->
            val searchEditText = activity.findViewById<TextView>(R.id.searchEditText)
            assertEquals(View.VISIBLE, searchEditText.visibility)
        }
    }

    @Test
    fun activityToDetailsActivityButton_NotNull() {
        scenario.onActivity { activity ->
            val toDetailsActivityButton = activity.findViewById<Button>(R.id.toDetailsActivityButton)
            assertNotNull(toDetailsActivityButton)
        }
    }

    @Test
    fun activityToDetailsActivityButton_IsVisible() {
        scenario.onActivity { activity ->
            val toDetailsActivityButton = activity.findViewById<Button>(R.id.toDetailsActivityButton)
            assertEquals(View.VISIBLE, toDetailsActivityButton.visibility)
        }
    }

    @Test
    fun activity_SearchEditText_setText() {
        scenario.onActivity { activity ->
            val searchEditText = activity.findViewById<TextView>(R.id.searchEditText)
            searchEditText.setText("some text", TextView.BufferType.EDITABLE)
            assertNotNull(searchEditText.text)
            assertEquals("some text", searchEditText.text.toString())
        }
    }

    @After
    fun close() {
        scenario.close()
    }
}