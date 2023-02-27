package com.calmperson.imagefinder.viewmodel

import android.content.ContentValues
import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import coil.ImageLoader
import coil.request.ImageRequest
import dagger.hilt.android.lifecycle.HiltViewModel
import java.io.File
import javax.inject.Inject


@HiltViewModel
class ImageActivityViewModel @Inject constructor() : ViewModel() {

    companion object {
        private val TAG = this::class.java.name
    }

    private val _image: MutableLiveData<Bitmap> = MutableLiveData<Bitmap>()
    val image: MutableLiveData<Bitmap>
        get() = _image

    fun loadImage(url: String, context: Context) {
        val loader = ImageLoader(context)
        val req = ImageRequest.Builder(context)
            .data(url)
            .target { result ->
                _image.value = (result as BitmapDrawable).bitmap
                Log.i(TAG, "Image has been loaded from $url")
            }
            .build()

        loader.enqueue(req)
    }

    fun saveImage(context: Context, title: String, format: String) {
        _image.value?.let { bitmap ->
            try {
                val fileName = "$title.$format"
                val values = ContentValues()
                values.put(MediaStore.Images.Media.DISPLAY_NAME, fileName)
                values.put(MediaStore.Images.Media.MIME_TYPE, "image/jpg")
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                    values.put(MediaStore.MediaColumns.RELATIVE_PATH, "DCIM/")
                    values.put(MediaStore.MediaColumns.IS_PENDING, 1)
                } else {
                    val directory =
                        Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM)
                    val file = File(directory, fileName)
                    values.put(MediaStore.MediaColumns.DATA, file.absolutePath)
                }
                val uri = context.contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values)
                context.contentResolver.openOutputStream(uri!!).use { output ->
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, output)
                }
            } catch (e: Exception) {
                Log.e(TAG, e.message ?: "Error while saving image")
            }
            Log.i(TAG, "Image has been saved successfully")
        }
    }
}