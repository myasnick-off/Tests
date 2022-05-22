package com.geekbrains.tests.automator

import android.widget.TextView
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SdkSuppress
import androidx.test.platform.app.InstrumentationRegistry.getInstrumentation
import androidx.test.uiautomator.UiDevice
import androidx.test.uiautomator.UiScrollable
import androidx.test.uiautomator.UiSelector
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@SdkSuppress(minSdkVersion = 18)
class OpenOtherAppsTest {

    private val uiDevice: UiDevice = UiDevice.getInstance(getInstrumentation())

    @Test
    fun test_OpenSettings() {
        uiDevice.pressHome()
        uiDevice.swipe(START_X, START_Y, END_X, END_Y, STEPS)
        val appViews = UiScrollable(UiSelector().scrollable(false))
        val settingsApp = appViews.getChildByText(UiSelector().className(TextView::class.java.name), SETTINGS_TEXT)
        settingsApp.clickAndWaitForNewWindow(TIMEOUT)
        val settingsValidation = uiDevice.findObject(UiSelector().packageName(SETTINGS_NAME))
        Assert.assertTrue(settingsValidation.exists())
    }

    companion object {
        private const val SETTINGS_TEXT = "Settings"
        private const val SETTINGS_NAME = "com.android.settings"

        private const val START_X = 500
        private const val START_Y = 1500
        private const val END_X = 500
        private const val END_Y = 0
        private const val STEPS = 5
        private const val TIMEOUT = 8000L
    }
}