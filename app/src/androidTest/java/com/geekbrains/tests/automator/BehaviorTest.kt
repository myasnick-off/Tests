package com.geekbrains.tests.automator

import android.content.Context
import android.content.Intent
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SdkSuppress
import androidx.test.platform.app.InstrumentationRegistry.getInstrumentation
import androidx.test.uiautomator.By
import androidx.test.uiautomator.UiDevice
import androidx.test.uiautomator.Until
import com.geekbrains.tests.*
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@SdkSuppress(minSdkVersion = 18)
class BehaviorTest {

    private val uiDevice: UiDevice = UiDevice.getInstance(getInstrumentation())
    private val context = ApplicationProvider.getApplicationContext<Context>()
    private val packageName = context.packageName

    @Before
    fun setup() {
        uiDevice.pressHome()
        val intent = context.packageManager.getLaunchIntentForPackage(packageName)
        intent!!.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
        context.startActivity(intent)
        uiDevice.wait(Until.hasObject(By.pkg(packageName).depth(0)), TIMEOUT)
    }

    @Test
    fun test_MainActivity_IsStarted() {
        val editText = uiDevice.findObject(By.res(packageName, SEARCH_EDIT_TEXT))
        Assert.assertNotNull(editText)
    }

    @Test
    fun test_WhenEmptyQuery_TotalCountTextView_IsInvisible() {
        val searchButton = uiDevice.findObject(By.res(packageName, SEARCH_BUTTON))
        searchButton.click()
        val changedText = uiDevice.wait(Until.findObject(By.res(packageName, TOTAL_COUNT_TEXT_VIEW)), TIMEOUT)
        Assert.assertNull(changedText)
    }

    @Test
    fun test_SearchIsPositive() {
        val editText = uiDevice.findObject(By.res(packageName, SEARCH_EDIT_TEXT))
        editText.text = TEST_QUERY
        val searchButton = uiDevice.findObject(By.res(packageName, SEARCH_BUTTON))
        searchButton.click()
        val changedText = uiDevice.wait(Until.findObject(By.res(packageName, TOTAL_COUNT_TEXT_VIEW)), TIMEOUT)
        Assert.assertEquals(changedText.text.toString(), NUMBER_OF_RESULTS_REAL)
    }

    @Test
    fun test_Open_Details_Screen() {
        val toDetails = uiDevice.findObject(By.res(packageName, TO_DETAILS_ACTIVITY_BUTTON))
        toDetails.click()
        val changedText = uiDevice.wait(Until.findObject(By.res(packageName, TOTAL_COUNT_TEXT_VIEW)), TIMEOUT)
        Assert.assertEquals(changedText.text, NUMBER_OF_RESULTS_ZERO)
    }

    @Test
    fun test_SearchIsPositive_OnDetailsScreen() {
        val editText = uiDevice.findObject(By.res(packageName, SEARCH_EDIT_TEXT))
        editText.text = TEST_QUERY
        val searchButton = uiDevice.findObject(By.res(packageName, SEARCH_BUTTON))
        searchButton.click()
        uiDevice.wait(Until.findObject(By.res(packageName, TOTAL_COUNT_TEXT_VIEW)), TIMEOUT)
        val toDetailsButton = uiDevice.findObject(By.res(packageName, TO_DETAILS_ACTIVITY_BUTTON))
        val changedText = uiDevice.wait(Until.findObject(By.res(packageName, TOTAL_COUNT_TEXT_VIEW)), TIMEOUT)
        Assert.assertEquals(changedText.text.toString(), NUMBER_OF_RESULTS_REAL)
        toDetailsButton.click()
    }

    @Test
    fun test_ReturnToMainActivity_OnBackPressed() {
        val toDetails = uiDevice.findObject(By.res(packageName, TO_DETAILS_ACTIVITY_BUTTON))
        toDetails.click()
        uiDevice.wait(Until.findObject(By.res(packageName, TOTAL_COUNT_TEXT_VIEW)), TIMEOUT)
        uiDevice.pressBack()
        val editText = uiDevice.wait(Until.findObject(By.res(packageName, SEARCH_EDIT_TEXT)), TIMEOUT)
        Assert.assertNotNull(editText)
    }

    @Test
    fun test_IncrementIsPositive_OnIncrementButtonClick() {
        val toDetails = uiDevice.findObject(By.res(packageName, TO_DETAILS_ACTIVITY_BUTTON))
        toDetails.click()
        val incrementButton = uiDevice.wait(Until.findObject(By.res(packageName, INCREMENT_BUTTON)), TIMEOUT)
        incrementButton.click()
        val totalCount = uiDevice.findObject(By.res(packageName, TOTAL_COUNT_TEXT_VIEW))
        Assert.assertEquals(totalCount.text.toString(), NUMBER_OF_RESULTS_PLUS_ONE)
    }

    @Test
    fun test_DecrementIsPositive_OnDecrementButtonClick() {
        val toDetails = uiDevice.findObject(By.res(packageName, TO_DETAILS_ACTIVITY_BUTTON))
        toDetails.click()
        val decrementButton = uiDevice.wait(Until.findObject(By.res(packageName, DECREMENT_BUTTON)), TIMEOUT)
        decrementButton.click()
        val totalCount = uiDevice.findObject(By.res(packageName, TOTAL_COUNT_TEXT_VIEW))
        Assert.assertEquals(totalCount.text.toString(), NUMBER_OF_RESULTS_MINUS_ONE)
    }
}