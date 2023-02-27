package com.calmperson.imagefinder.model

data class Image(
    val title: String,
    val link: String,
    val format: String,
    val contextLink: String,
    val byteSize: Int,
    val height: Int,
    val weigh: Int,
    var drawableId: Int?
) : java.io.Serializable
