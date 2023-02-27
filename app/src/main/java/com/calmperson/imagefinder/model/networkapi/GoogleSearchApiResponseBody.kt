package com.calmperson.imagefinder.model.networkapi

import com.google.gson.annotations.SerializedName

class GoogleSearchApiResponseBody {

    @SerializedName("items")
    var items: List<Item>? = null

    class Item {

        @SerializedName("title")
        var title: String? = null

        @SerializedName("link")
        var link: String? = null

        @SerializedName("fileFormat")
        var fileFormat: String? = null

        @SerializedName("image")
        var imageInfo: ImageInfo? = null

        class ImageInfo {

            @SerializedName("contextLink")
            var contextLink: String? = null

            @SerializedName("byteSize")
            var byteSize: Int? = null

            @SerializedName("height")
            var height: Int? = null

            @SerializedName("width")
            var width: Int? = null

        }
    }

}
