package com.calmperson.imagefinder.view.activity

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import com.calmperson.imagefinder.model.Image
import com.calmperson.imagefinder.model.networkapi.GoogleSearchApiSearchQuery
import com.calmperson.imagefinder.view.ui.ImageFinder
import com.calmperson.imagefinder.view.ui.theme.ImageFinderTheme
import com.calmperson.imagefinder.viewmodel.MainActivityViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel by viewModels<MainActivityViewModel>()

    private val doSearch: (String) -> Unit = {
        with(viewModel) {
            pageState.value!!.query = it
            pageState.value!!.pageNumber = 1
            images.value!!.clear()
            loadPage()
        }
    }

    private val onImageClick: (Image) -> Unit = { img ->
        val intent = Intent(this, ImageActivity::class.java)
        intent.putExtra("image", img).also { startActivity(it) }
    }

    private val onScrollEnd: () -> Unit = {
        viewModel.loadNextPage()
    }

    private val onDropMenuItemClick: (GoogleSearchApiSearchQuery.Query) -> Unit = { query ->
        val pageState = viewModel.pageState.value!!
        when (query) {
            is GoogleSearchApiSearchQuery.Period -> pageState.period = query
            is GoogleSearchApiSearchQuery.ImageSize -> pageState.imageSize = query
            is GoogleSearchApiSearchQuery.ImageType -> pageState.imageType = query
            is GoogleSearchApiSearchQuery.DominantColor -> pageState.dominantColor = query
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ImageFinderTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    ImageFinder(
                        images = viewModel.images,
                        isThereInternetConnection = viewModel.isThereInternetConnection,
                        doSearch = doSearch,
                        onScrollEnd = onScrollEnd,
                        onImageClick = onImageClick,
                        onDropMenuItemClick = onDropMenuItemClick
                    )
                }
            }
        }
    }
}
