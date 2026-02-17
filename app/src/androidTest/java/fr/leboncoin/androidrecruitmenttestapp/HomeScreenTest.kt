package fr.leboncoin.androidrecruitmenttestapp

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.ComposeContentTestRule
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onAllNodesWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import fr.leboncoin.androidrecruitmenttestapp.home.ui.HomeActivity
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class HomeScreenTest {
    @Rule
    @JvmField
    var composeTestRule: ComposeContentTestRule = createAndroidComposeRule<HomeActivity>()

    @Test
    fun useAppContext() {
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("fr.leboncoin.androidrecruitmenttestapp", appContext.packageName)
    }

    @Test
    fun albumCard_isDisplayed() {
        composeTestRule.waitUntil(timeoutMillis = 5_000) {
            composeTestRule.onAllNodesWithTag("albumCard").fetchSemanticsNodes().isNotEmpty()
        }

        val firstCard = composeTestRule.onAllNodesWithTag("albumCard")[0]
        firstCard.assertIsDisplayed()
    }

    @Test
    fun albumCard_navigatesUp() {
        composeTestRule.waitUntil(timeoutMillis = 5_000) {
            composeTestRule.onAllNodesWithTag("albumCard").fetchSemanticsNodes().isNotEmpty()
        }

        val firstCard = composeTestRule.onAllNodesWithTag("albumCard")[0]
        firstCard.performClick()

        composeTestRule.onNodeWithText("Album Details").assertIsDisplayed()
    }
}