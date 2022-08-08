package com.task.trueapp.util

import androidx.compose.ui.test.SemanticsMatcher
import androidx.compose.ui.test.junit4.ComposeContentTestRule
import androidx.test.platform.app.InstrumentationRegistry

/**
 * Test util contains the functions which will be used in UI testing
 */

object TestUtil {

    /**
     * Wait until the provided UI element/widget hide from the screen within max timeout duration
     *
     * @param matcher
     * @param timeoutMillis
     */
    fun ComposeContentTestRule.waitUntilExist(
        matcher: SemanticsMatcher,
        timeoutMillis: Long = 10_000L,
        count:Int = 1
    ) {
        return this.waitUntilNodeCount(matcher, count, timeoutMillis)
    }


    /**
     * Wait until the provided UI element/widget hide from the screen within max timeout duration
     *
     * @param matcher
     * @param timeoutMillis
     */
    fun ComposeContentTestRule.waitUntilDoesNotExist(
        matcher: SemanticsMatcher,
        timeoutMillis: Long = 10_000L,
        count:Int = 0
    ) {
        return this.waitUntilNodeCount(matcher, count, timeoutMillis)
    }

    /**
     * Wait until UI node count matches the given count within max timeout duration
     *
     * @param matcher
     * @param count
     * @param timeoutMillis
     */
    private fun ComposeContentTestRule.waitUntilNodeCount(
        matcher: SemanticsMatcher,
        count: Int,
        timeoutMillis: Long = 10_000L
    ) {
        this.waitUntil(timeoutMillis) {
            this.onAllNodes(matcher).fetchSemanticsNodes().size == count
        }
    }

    /**
     * Disable network using instrumentation command
     *
     */
    fun disableNetwork() {
        InstrumentationRegistry.getInstrumentation().uiAutomation.executeShellCommand("svc wifi disable")
        InstrumentationRegistry.getInstrumentation().uiAutomation.executeShellCommand("svc data disable")
    }

    /**
     * Enable network using instrumentation command
     *
     */
    fun enableNetwork() {
        InstrumentationRegistry.getInstrumentation().uiAutomation.executeShellCommand("svc wifi enable")
        InstrumentationRegistry.getInstrumentation().uiAutomation.executeShellCommand("svc data enable")
    }
}