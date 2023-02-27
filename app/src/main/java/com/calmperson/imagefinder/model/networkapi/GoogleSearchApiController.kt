package com.calmperson.imagefinder.model.networkapi

import android.util.Log
import com.calmperson.imagefinder.model.Image
import retrofit2.Call
import retrofit2.Callback
import retrofit2.HttpException
import retrofit2.Response


class GoogleSearchApiController(
    private val successCallback: (List<Image>) -> Unit,
    private val failureCallBack: (Throwable) -> Unit
) : Callback<GoogleSearchApiResponseBody> {

    companion object {
        private val TAG = this::class.java.name
    }

    override fun onResponse(
        call: Call<GoogleSearchApiResponseBody>,
        response: Response<GoogleSearchApiResponseBody>
    ) {
        if (response.isSuccessful and (response.code() == 200)) {
            val images = mutableListOf<Image>()
            response.body()!!.items!!.forEach {
                try {
                    val imageInfo = it.imageInfo!!
                    val image = Image(
                        it.title!!, it.link!!, it.fileFormat!!,
                        imageInfo.contextLink!!, imageInfo.byteSize!!,
                        imageInfo.height!!, imageInfo.width!!, null
                    )
                    images.add(image)
                } catch (_: NullPointerException) { }
            }
            successCallback.invoke(images)
            Log.i(TAG, response.toString())
        } else {
            failureCallBack(HttpException(response))
            Log.e(TAG, "Response status: ${response.code()}")
        }
    }

    override fun onFailure(call: Call<GoogleSearchApiResponseBody>, t: Throwable) {
        failureCallBack(t)
        Log.e(TAG, t.message ?: "Failure request")
    }

}