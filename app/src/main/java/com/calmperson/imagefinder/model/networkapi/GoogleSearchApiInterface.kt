package com.calmperson.imagefinder.model.networkapi

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface GoogleSearchApiInterface {

    @GET("v1")
    fun getImages(
        @Query("key") apiKey: String = "Your key", // write your key here
        @Query("cx") pseID: String = "Your cx", // write your cx here
        @Query("searchType") searchType: String = "image",
        @Query("start") firstItemOnPage: Int,
        @Query("dateRestrict") period: String?,
        @Query("imgDominantColor") dominantColor: String?,
        @Query("imgSize") imageSize: String?,
        @Query("imgType") imageType: String?,
        @Query("q") query: String
    ): Call<GoogleSearchApiResponseBody>

}