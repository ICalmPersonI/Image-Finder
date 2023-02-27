package com.calmperson.imagefinder

import com.calmperson.imagefinder.model.Image
import com.calmperson.imagefinder.model.PageState
import com.calmperson.imagefinder.model.networkapi.GoogleSearchApiRepository

class FakeRepository : GoogleSearchApiRepository {
    override fun getPage(
        pageState: PageState,
        successCallback: (List<Image>) -> Unit,
        failureCallBack: (Throwable) -> Unit
    ) {
        val image = Image(
            "Sunflower",
            "",
            "image/png",
            "https://en.wikipedia.org/wiki/Common_sunflower",
            10000,
            1000,
            1333,
            R.drawable.test
        )

        successCallback.invoke(listOf(image, image, image, image))
    }
}