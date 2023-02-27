package com.calmperson.imagefinder.view.ui

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.calmperson.imagefinder.R
import com.calmperson.imagefinder.TestTag
import com.calmperson.imagefinder.model.Image

@Composable
fun ImageGrid(
    modifier: Modifier = Modifier,
    images: LiveData<MutableList<Image>>,
    isThereInternetConnection: MutableLiveData<Boolean>,
    onScrollEnd: () -> Unit,
    onClick: (Image) -> Unit
) {
    val state = rememberLazyGridState()
    val imageRotation = state.getRotationYWhileScrolling()
    val imagesState = images.observeAsState()
    val isThereInternetConnectionState = isThereInternetConnection.observeAsState()

    val endReached = remember {
        derivedStateOf {
            state.isScrolledToEnd(images.value!!.size)
        }
    }
    if (endReached.value) {
        LaunchedEffect(Unit) {
            onScrollEnd.invoke()
        }
    }
    if (isThereInternetConnectionState.value!!) {
        LazyHorizontalGrid(
            modifier = modifier.fillMaxSize(),
            rows = GridCells.Fixed(3),
            verticalArrangement = Arrangement.Center,
            state = state
        ) {
            items(imagesState.value!!) { image ->
                Box(
                    Modifier
                        .padding(5.dp)
                        .width(250.dp)
                        .graphicsLayer {
                            rotationY = imageRotation.value
                        }
                ) {

                    val painter = rememberAsyncImagePainter(
                        model = ImageRequest.Builder(LocalContext.current)
                            .data(image.drawableId ?: image.link)
                            .size(250, 250)
                            .build()
                    )

                    if (painter.state is AsyncImagePainter.State.Success) {
                        IconButton(
                            modifier = Modifier.testTag(TestTag.IMAGE),
                            onClick = { onClick.invoke(image) }
                        ) {
                            Image(
                                modifier = Modifier
                                    .padding(10.dp)
                                    .clip(RoundedCornerShape(100.dp))
                                    .align(Alignment.Center)
                                    .fillMaxSize(),
                                painter = painter,
                                contentScale = ContentScale.Crop,
                                contentDescription = null
                            )
                        }
                    } else {
                        Box(modifier = Modifier
                            .padding(10.dp)
                            .clip(RoundedCornerShape(100.dp))
                            .background(createImageLoadingAnimation())
                            .fillMaxSize()
                        )
                    }
                }
            }
        }
    } else {
        Box(modifier = modifier.fillMaxSize()) {
            Text(
                modifier = Modifier
                    .padding(10.dp)
                    .align(Alignment.Center),
                text = stringResource(R.string.internet_connection_issue),
                fontSize = 34.sp
            )
        }
    }
}

@Composable
private fun LazyGridState.getRotationYWhileScrolling(): State<Float> {
    val isScrollInProgress by remember {
        derivedStateOf {
            isScrollInProgress
        }
    }
    return animateFloatAsState(
        targetValue = if (isScrollInProgress) { if (isScrollingLeft()) -30f else 30f } else 0f,
        animationSpec = tween(500, 0, FastOutSlowInEasing),
    )
}

@Composable
private fun LazyGridState.isScrollingLeft(): Boolean {
    var previousIndex by remember(this) { mutableStateOf(firstVisibleItemIndex) }
    var previousScrollOffset by remember(this) { mutableStateOf(firstVisibleItemScrollOffset) }
    return remember(this) {
        derivedStateOf {
            if (previousIndex != firstVisibleItemIndex) {
                previousIndex > firstVisibleItemIndex
            } else {
                previousScrollOffset >= firstVisibleItemScrollOffset
            }.also {
                previousIndex = firstVisibleItemIndex
                previousScrollOffset = firstVisibleItemScrollOffset
            }
        }
    }.value
}

private fun LazyGridState.isScrolledToEnd(total: Int): Boolean {
    return layoutInfo.visibleItemsInfo.lastOrNull()?.index == total - 1
}