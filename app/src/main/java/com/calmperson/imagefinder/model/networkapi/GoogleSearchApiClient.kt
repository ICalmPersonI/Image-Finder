package com.calmperson.imagefinder.model.networkapi

import okhttp3.HttpUrl
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object GoogleSearchApiClient {

    val instance: GoogleSearchApiInterface

    init {
        val url = HttpUrl.Builder()
            .scheme("https")
            .host("www.googleapis.com")
            .addPathSegment("customsearch")
            .addPathSegments("")
            .build()

        val client = OkHttpClient.Builder().build()

        instance = Retrofit.Builder()
            .baseUrl(url)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
            .create(GoogleSearchApiInterface::class.java)
    }
}