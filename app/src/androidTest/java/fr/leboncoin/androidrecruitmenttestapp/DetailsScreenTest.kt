package fr.leboncoin.androidrecruitmenttestapp

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.ComposeContentTestRule
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onAllNodesWithTag
import androidx.compose.ui.test.onNodeWithTag
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
class DetailsScreenTest {

    @Rule
    @JvmField
    var composeTestRule: ComposeContentTestRule = createAndroidComposeRule<HomeActivity>()


    @Test
    fun useAppContext() {
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("fr.leboncoin.androidrecruitmenttestapp", appContext.packageName)
    }

    @Test
    fun backButton_navigatesUp() {
        composeTestRule.waitUntil(timeoutMillis = 5_000) {
            composeTestRule.onAllNodesWithTag("albumCard").fetchSemanticsNodes().isNotEmpty()
        }
        val firstCard = composeTestRule.onAllNodesWithTag("albumCard")[0]
        firstCard.performClick()

        composeTestRule.onNodeWithText("Album Details").assertIsDisplayed()
        composeTestRule.onNodeWithTag("detailsBackButton").performClick()
        composeTestRule.onNodeWithText("Album Details").assertDoesNotExist()
    }

    @Test
    fun favoriteButton_statusChange() {
        composeTestRule.waitUntil(timeoutMillis = 5_000) {
            composeTestRule.onAllNodesWithTag("albumCard").fetchSemanticsNodes().isNotEmpty()
        }
        val firstCard = composeTestRule.onAllNodesWithTag("albumCard")[0]
        firstCard.performClick()

        composeTestRule.onNodeWithText("Album Details").assertIsDisplayed()

        composeTestRule.onNodeWithTag("detailsFavoriteButton_on").assertDoesNotExist()
        composeTestRule.onNodeWithTag("detailsFavoriteButton_off").assertExists()
        composeTestRule.onNodeWithTag("detailsFavoriteButton_off").performClick()
        composeTestRule.onNodeWithTag("detailsFavoriteButton_off").assertDoesNotExist()
        composeTestRule.onNodeWithTag("detailsFavoriteButton_on").assertExists()
    }
}