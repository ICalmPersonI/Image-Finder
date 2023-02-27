package com.calmperson.imagefinder

import com.calmperson.imagefinder.model.Image
import com.calmperson.imagefinder.model.PageState
import com.calmperson.imagefinder.model.networkapi.GoogleSearchApiRepositoryImpl
import com.calmperson.imagefinder.model.networkapi.GoogleSearchApiSearchQuery
import org.junit.Assert
import org.junit.Test

class GoogleSearchApiRepositoryTest {

    private val pageState = listOf(
        PageState(query = "cat"),
        PageState(query = "dog", pageNumber = 3),
        PageState(query = "flowers", period = GoogleSearchApiSearchQuery.Period.PAST_MONTH),
        PageState(query = "apple", dominantColor = GoogleSearchApiSearchQuery.DominantColor.BLACK),
        PageState(query = "56+////gfjfgj+\"\"\"\"fdfsvcv\"\"\"")
    )

    private val repository = GoogleSearchApiRepositoryImpl()


    @Test
    fun getPage_firstPageState_tenImages() {
        getPage_pageState_teImages(0, 10)
    }

    @Test
    fun getPage_secondPageState_tenImages() {
        getPage_pageState_teImages(1, 10)
    }

    @Test
    fun getPage_thirdPageState_tenImages() {
        getPage_pageState_teImages(2, 10)
    }

    @Test
    fun getPage_fourthPageState_tenImages() {
        getPage_pageState_teImages(3, 10)
    }

    @Test
    fun getPage_fifthPageState_zeroImages() {
        getPage_pageState_teImages(4, 0)
    }

    private fun getPage_pageState_teImages(id: Int, expectedImageCount: Int) {
        var actualImageCount = 0
        val success: (List<Image>) -> Unit = {
            actualImageCount = it.size
        }
        val failure: (Throwable) -> Unit = {
            Assert.assertFalse(true)
        }
        repository.getPage(pageState[id], success, failure)
        Thread.sleep(1000)
        Assert.assertEquals(expectedImageCount, actualImageCount)
    }

}