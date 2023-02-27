# Image-Finder
The program receives data using the [Google Custom Search JSON API](https://developers.google.com/custom-search/v1/overview).  
You need to specify your own "cx" and "key" in the interface app/src/main/java/com/calmperson/imagefinder/model/networkapi/GoogleSearchApiInterface.kt.
```kotlin
interface GoogleSearchApiInterface {

    @GET("v1")
    fun getImages(
        @Query("key") apiKey: String = "Your key", // write your key here
        @Query("cx") pseID: String = "Your cx", // write your cx here
        ...
    ): Call<GoogleSearchApiResponseBody>

}
```

Gifts
---------------
<img src="https://github.com/ICalmPersonI/Image-Finder/blob/master/gifts/1.gif" alt="drawing" width="350"/>
<img src="https://github.com/ICalmPersonI/Image-Finder/blob/master/gifts/2.gif" alt="drawing" width="350"/>  

Tech Stack
---------------
- Minimum SDK level 21
- [Jetpack Compose](https://developer.android.com/jetpack/compose/documentation)
- [Material 2](https://m2.material.io)
- [LiveData](https://developer.android.com/topic/libraries/architecture/livedata)
- [Coil](https://coil-kt.github.io/coil)
- [Hilt](https://dagger.dev/hilt)
- [Retrofit2](https://square.github.io/retrofit)
- [OkHttp3](https://square.github.io/okhttp)
- [Google Custom Search JSON API](https://developers.google.com/custom-search/v1/overview)
