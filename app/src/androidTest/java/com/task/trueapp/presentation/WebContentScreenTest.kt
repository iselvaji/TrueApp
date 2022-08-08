package com.task.trueapp.presentation

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import com.ramcosta.composedestinations.DestinationsNavHost
import com.task.trueapp.R
import com.task.trueapp.di.AppModule
import com.task.trueapp.presentation.webcontent.NavGraphs
import com.task.trueapp.ui.theme.TrueAppTheme
import com.task.trueapp.util.TestUtil.disableNetwork
import com.task.trueapp.util.TestUtil.enableNetwork
import com.task.trueapp.util.TestUtil.waitUntilExist
import com.task.trueapp.utils.Constants
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import org.junit.Before
import org.junit.FixMethodOrder
import org.junit.Rule
import org.junit.Test
import org.junit.runners.MethodSorters


/**
 * Web content screen test contains the instrumentation UI test cases
 *
 */

@HiltAndroidTest
@UninstallModules(AppModule::class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
class WebContentScreenTest {

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeRule = createAndroidComposeRule<MainActivity>()

    @Before
    fun setUp() {
        hiltRule.inject()
        composeRule.setContent {
            TrueAppTheme {
                DestinationsNavHost(navGraph = NavGraphs.root)
            }
        }
    }

    @Test
    fun test1_isNthCharDisplayed() {

        composeRule.onNodeWithText(composeRule.activity.getString(R.string.fetch)).apply {
            assertIsDisplayed()
            performClick()
        }

        val progressBar = composeRule.onNode(hasTestTag(Constants.TEST_TAG_PROGRESS.plus(0)), useUnmergedTree = true)
        progressBar.assertExists()

        val nthCharText = composeRule.activity.getString(R.string.nth_Char, Constants.CHAR_POSITION_TO_FIND)

        composeRule.waitUntilExist(hasText(nthCharText))

        composeRule.onNodeWithText(nthCharText).assertIsDisplayed()

    }

    @Test
    fun test2_areNthCharsDisplayed() {

        composeRule.onNodeWithText(composeRule.activity.getString(R.string.fetch)).apply {
            assertIsDisplayed()
            performClick()
        }

        val progressMessage = composeRule.onNode(hasTestTag(Constants.TEST_TAG_LOADING.plus(1)), useUnmergedTree = true)
        progressMessage.assertExists()

        val nthCharsText = composeRule.activity.getString(R.string.every_nthChar, Constants.CHAR_POSITION_TO_FIND)

        composeRule.waitUntilExist(hasText(nthCharsText))

        composeRule.onNodeWithText(nthCharsText).assertIsDisplayed()
    }

    @Test
    fun test3_wordsCountDisplayed() {

        composeRule.onNodeWithText(composeRule.activity.getString(R.string.fetch)).apply {
            assertIsDisplayed()
            performClick()
        }

        val progressBar = composeRule.onNode(hasTestTag(Constants.TEST_TAG_PROGRESS.plus(0)), useUnmergedTree = true)
        progressBar.assertExists()

        val wordCount = composeRule.activity.getString(R.string.words_count, Constants.CHAR_POSITION_TO_FIND)

        composeRule.waitUntilExist(hasText(wordCount))

        composeRule.onNodeWithText(wordCount).assertIsDisplayed()
    }

    @Test
    fun test4_isNetworkErrorVisible() {

        disableNetwork()

        composeRule.onNodeWithText(composeRule.activity.getString(R.string.fetch)).apply {
            assertIsDisplayed()
            performClick()
        }

        val message = composeRule.activity.getString(R.string.err_connectivity)
        composeRule.waitUntilExist(hasText(message), count = 3)

        enableNetwork()
    }

    @Test
    fun test5_isSettingsAlertDisplayed() {

        composeRule.onNodeWithContentDescription(composeRule.activity.getString(R.string.settings)).apply {
            assertIsDisplayed()
            performClick()
        }

        val alertMsg = composeRule.activity.getString(R.string.dialog_title)
        composeRule.onNodeWithText(alertMsg).assertIsDisplayed()
    }

}