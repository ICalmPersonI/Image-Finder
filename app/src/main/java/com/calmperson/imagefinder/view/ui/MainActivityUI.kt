package com.calmperson.imagefinder.view.ui

import android.content.res.Configuration
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.calmperson.imagefinder.model.Image
import com.calmperson.imagefinder.model.networkapi.GoogleSearchApiSearchQuery

import com.calmperson.imagefinder.view.ui.theme.ImageFinderTheme

@Composable
fun ImageFinder(
    doSearch: (String) -> Unit = { },
    images: LiveData<MutableList<Image>> = MutableLiveData(mutableStateListOf()),
    isThereInternetConnection: MutableLiveData<Boolean> = MutableLiveData(false),
    onScrollEnd: () -> Unit = { },
    onImageClick: (Image) -> Unit = { },
    onDropMenuItemClick: (GoogleSearchApiSearchQuery.Query) -> Unit = { }
) {
    Column {
        SearchField(
            modifier = Modifier.weight(0.15f),
            doSearch = doSearch
        )
        SearchTools(
            modifier = Modifier.weight(0.1f).fillMaxWidth(),
            onDropMenuItemClick = onDropMenuItemClick
        )
        ImageGrid(
            modifier = Modifier.weight(1f),
            images = images,
            isThereInternetConnection = isThereInternetConnection,
            onScrollEnd = onScrollEnd,
            onClick = onImageClick
        )
    }
}

@Preview(showBackground = true, heightDp = 100)
@Composable
fun SearchFieldPreview() {
    ImageFinderTheme {
        SearchField(doSearch = { })
    }
}

@Preview(showBackground = true, heightDp = 480, widthDp = 300)
@Composable
fun PreviewCompact() {
    ImageFinderTheme {
        ImageFinder()
    }
}

@Preview(showBackground = true, heightDp = 900, widthDp = 600)
@Composable
fun PreviewMedium() {
    ImageFinderTheme {
        ImageFinder()
    }
}

@Preview(showBackground = true, widthDp = 840, heightDp = 900)
@Composable
fun PreviewExpanded() {
    ImageFinderTheme {
        ImageFinder()
    }
}

@Preview(
    showBackground = true,
    widthDp = 840,
    heightDp = 900,
    uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun PreviewDesktopNight() {
    ImageFinderTheme {
        ImageFinder()
    }
}