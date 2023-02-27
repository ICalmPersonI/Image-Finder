package com.calmperson.imagefinder.model

import com.calmperson.imagefinder.model.networkapi.GoogleSearchApiSearchQuery.DominantColor
import com.calmperson.imagefinder.model.networkapi.GoogleSearchApiSearchQuery.ImageSize
import com.calmperson.imagefinder.model.networkapi.GoogleSearchApiSearchQuery.ImageType
import com.calmperson.imagefinder.model.networkapi.GoogleSearchApiSearchQuery.Period

data class PageState(
    var pageNumber: Int = 1,
    var query: String = "",
    var period: Period = Period.ANY_TIME,
    var dominantColor: DominantColor = DominantColor.ANY_COLOR,
    var imageSize: ImageSize = ImageSize.ANY_SIZE,
    var imageType: ImageType = ImageType.ANY_TYPE

)
