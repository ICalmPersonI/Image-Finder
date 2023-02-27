package com.calmperson.imagefinder.model.networkapi

import com.calmperson.imagefinder.model.Image
import com.calmperson.imagefinder.model.PageState

interface GoogleSearchApiRepository {

    fun getPage(
        pageState: PageState,
        successCallback: (List<Image>) -> Unit,
        failureCallBack: (Throwable) -> Unit
    )
}