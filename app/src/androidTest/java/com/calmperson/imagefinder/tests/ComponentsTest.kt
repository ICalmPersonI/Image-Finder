package com.calmperson.imagefinder.tests

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.calmperson.imagefinder.ImageFinderApp
import com.calmperson.imagefinder.R
import com.calmperson.imagefinder.view.ui.SearchField
import com.calmperson.imagefinder.view.ui.SearchTools
import com.calmperson.imagefinder.model.networkapi.GoogleSearchApiSearchQuery
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ComponentsTest {

    @get:Rule(order = 1)
    val composeRule = createComposeRule()

    private val context = ApplicationProvider.getApplicationContext<ImageFinderApp>()

    @Test
    fun searchTools_ImageSize() {
        with(composeRule) {
            setContent { SearchTools() }
            onNodeWithText(context.resources.getString(R.string.size)).performClick()
            GoogleSearchApiSearchQuery.ImageSize.values().forEach {
                onNodeWithText(context.resources.getString(it.stringResourceId)).assertExists()
            }
        }
    }

    @Test
    fun searchTools_ImageType() {
        with(composeRule) {
            setContent { SearchTools() }
            onNodeWithText(context.resources.getString(R.string.type)).performClick()
            GoogleSearchApiSearchQuery.ImageType.values().forEach {
                onNodeWithText(context.resources.getString(it.stringResourceId)).assertExists()
            }
        }
    }

    @Test
    fun searchTools_DominantColor() {
        with(composeRule) {
            setContent { SearchTools() }
            onNodeWithText(context.resources.getString(R.string.color)).performClick()
            GoogleSearchApiSearchQuery.DominantColor.values().forEach {
                onNodeWithText(context.resources.getString(it.stringResourceId)).assertExists()
            }
        }
    }

    @Test
    fun searchTools_Period() {
        with(composeRule) {
            setContent { SearchTools() }
            onNodeWithText(context.resources.getString(R.string.time)).performClick()
            GoogleSearchApiSearchQuery.Period.values().forEach {
                onNodeWithText(context.resources.getString(it.stringResourceId)).assertExists()
            }
        }
    }

    @Test
    fun searchField_TextCat() {
        with(composeRule) {
            setContent { SearchField() }
            onNodeWithText(context.resources.getString(R.string.search)).performTextInput("cat")
            onNodeWithText("cat").assertExists()
        }
    }
}