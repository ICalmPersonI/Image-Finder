package com.calmperson.imagefinder.model.networkapi

import com.calmperson.imagefinder.model.Image
import com.calmperson.imagefinder.model.PageState


class GoogleSearchApiRepositoryImpl : GoogleSearchApiRepository {

    override fun getPage(
        pageState: PageState,
        successCallback: (List<Image>) -> Unit,
        failureCallBack: (Throwable) -> Unit
    ) {
        val controller = GoogleSearchApiController(successCallback, failureCallBack)
        with(pageState) {
            GoogleSearchApiClient.instance
                .getImages(
                    firstItemOnPage = pageNumber * 10 - 9,
                    period = period.value,
                    dominantColor = dominantColor.value,
                    imageSize = imageSize.value,
                    imageType = imageType.value,
                    query = query

                ).enqueue(controller)
        }
    }
}