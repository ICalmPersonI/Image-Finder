package com.calmperson.imagefinder.tests

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.calmperson.imagefinder.view.activity.MainActivity
import com.calmperson.imagefinder.R
import com.calmperson.imagefinder.TestTag
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import org.junit.Rule
import java.util.*

@RunWith(AndroidJUnit4::class)
@HiltAndroidTest
class ActivitiesTest {

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun imageGrid_FakeRepository() {
        with(composeRule) {
            onNodeWithTag(TestTag.DO_SEARCh).performClick()
            waitUntil(4000) {
                onAllNodesWithTag(TestTag.IMAGE)
                    .fetchSemanticsNodes(false).size > 2
            }
        }
    }

    @Test
    fun imageActivity_FakeRepository() {
        with(composeRule) {
            onNodeWithTag(TestTag.DO_SEARCh).performClick()
            waitUntil(4000) {
                onAllNodesWithTag(TestTag.IMAGE)
                    .fetchSemanticsNodes(false).size > 2
            }
            onAllNodesWithTag(TestTag.IMAGE).onFirst().performClick()
            onNodeWithTag(TestTag.IMAGE).performTouchInput { longClick() }
            onNodeWithText(activity.getString(R.string.share)).assertExists()
            onNodeWithText(activity.getString(R.string.save)).assertExists()
            onNodeWithText(activity.getString(R.string.about_image)).performClick()
            onNodeWithTag(TestTag.ABOUT_IMAGE).assertExists()
        }
    }
}