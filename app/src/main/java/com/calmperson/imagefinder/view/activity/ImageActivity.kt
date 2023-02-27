package com.calmperson.imagefinder.view.activity

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.material.MaterialTheme
import androidx.core.app.ActivityCompat
import androidx.lifecycle.MutableLiveData
import com.calmperson.imagefinder.model.Image
import com.calmperson.imagefinder.view.ui.ImageView
import com.calmperson.imagefinder.viewmodel.ImageActivityViewModel


class ImageActivity : ComponentActivity() {

    companion object {
        private val TAG: String = this::class.java.simpleName
        private val PERMISSIONS = arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE)
        private const val REQ_CODE_PERMISSION = 130
    }

    private val permReqLauncher: (String, String) -> ActivityResultLauncher<Array<String>> =
        { title, format ->
            registerForActivityResult(
                ActivityResultContracts.RequestMultiplePermissions()
            ) { permissions ->
                val granted = permissions.entries.all {
                    it.value
                }
                if (granted) {
                    viewModel.saveImage(this, title, format)
                    Log.i(TAG, "Permissions $PERMISSIONS has been granted")
                }
            }
        }

    private val viewModel by viewModels<ImageActivityViewModel>()

    override fun onCreate(savedInstantState: Bundle?) {
        super.onCreate(savedInstantState)
        val image: Image? = if (Build.VERSION.SDK_INT == Build.VERSION_CODES.TIRAMISU) {
            intent.getSerializableExtra("image", Image::class.java)
        } else {
            intent.getSerializableExtra("image") as Image
        }
        image?.let {
            if (it.drawableId == null) {
                viewModel.loadImage(image.link, this)
            }

            val format = image.format.split('/').last()

            val bitmap = it.drawableId?.let { id ->
                MutableLiveData(BitmapFactory.decodeResource(this.resources, id))
            } ?: viewModel.image

            setContent {
                MaterialTheme {
                    ImageView(
                        image = bitmap,
                        saveImage = saveImage(image.title, format),
                        shareImage = shareImage(image.link),
                        imageTitle = image.title,
                        imageContextLink = image.contextLink,
                        imageByteSize = image.byteSize,
                        imageFormat = image.format,
                        imageHeight = image.height,
                        imageWidth = image.weigh
                    )
                }
            }
        } ?: run {
            Log.e(TAG, "Intent extra is null")
            finish()
        }

    }


    private fun shareImage(imageUrl: String): () -> Unit = {
        val intent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, imageUrl)
            type = "text/plain"
        }
        startActivity(Intent.createChooser(intent, null))
    }

    private fun saveImage(title: String, format: String): () -> Unit = {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            viewModel.saveImage(this, title, format)
        } else {
            if (hasPermissions(this, PERMISSIONS)) {
                viewModel.saveImage(this, title, format)
            } else {
                Log.i(TAG, "Request permission $PERMISSIONS")
                permReqLauncher.invoke(title, format).launch(PERMISSIONS)
            }
        }
    }

    private fun hasPermissions(context: Context, permissions: Array<String>): Boolean {
        Log.i(TAG, "$permissions has been checked")
        return permissions.all {
            ActivityCompat.checkSelfPermission(context, it) == PackageManager.PERMISSION_GRANTED
        }
    }

}