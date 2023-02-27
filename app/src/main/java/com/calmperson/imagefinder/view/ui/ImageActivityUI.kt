package com.calmperson.imagefinder.view.ui

import android.content.res.Configuration
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.MutableLiveData
import com.calmperson.imagefinder.R
import com.calmperson.imagefinder.TestTag
import com.calmperson.imagefinder.view.ui.theme.ImageFinderTheme


@Composable
fun ImageView(
    image: MutableLiveData<Bitmap> = MutableLiveData(
        BitmapFactory.decodeResource(LocalContext.current.resources, R.drawable.test)
    ),
    imageTitle: String = "Some title",
    imageContextLink: String = "Some ink",
    imageFormat: String = "image/png",
    imageByteSize: Int = 1000,
    imageHeight: Int = 100,
    imageWidth: Int = 100,
    saveImage: () -> Unit = { },
    shareImage: () -> Unit = { }
) {
    val img = image.observeAsState()
    var tapPoint by remember { mutableStateOf(Offset.Zero)}
    var expandedDropdownMenu by remember { mutableStateOf(false) }
    var isImageInfoDialogOpen by remember { mutableStateOf(false) }

    Box(modifier = Modifier.fillMaxSize()) {
        img.value?.let {
            ZoomableImage(
                modifier = Modifier
                    .align(Alignment.Center)
                    .testTag(TestTag.IMAGE),
                bitmap = it.asImageBitmap(),
                onLongPress = { offset ->
                    tapPoint = offset
                    expandedDropdownMenu = true
                }
            )
            Box(
                modifier = Modifier.absoluteOffset(tapPoint.x.dp, tapPoint.y.dp)
            ) {
                DropdownMenu(
                    modifier = Modifier,
                    expanded = expandedDropdownMenu,
                    onDismissRequest = { expandedDropdownMenu = !expandedDropdownMenu }
                ) {
                    DropdownMenuItem(onClick = shareImage) {
                        Text(stringResource(R.string.share))
                    }
                    DropdownMenuItem(onClick = saveImage) {
                        Text(stringResource(R.string.save))
                    }
                    DropdownMenuItem(onClick = { isImageInfoDialogOpen = true }) {
                        Text(stringResource(R.string.about_image))
                    }
                }
            }
            if (isImageInfoDialogOpen) {
                ImageInfoDialog(
                    isDialogOpen = isImageInfoDialogOpen,
                    onDismissRequest = { isImageInfoDialogOpen = false },
                    imageTitle = imageTitle,
                    imageContextLink = imageContextLink,
                    imageByteSize = imageByteSize,
                    imageFormat = imageFormat,
                    imageHeight = imageHeight,
                    imageWidth = imageWidth
                )
            }
        } ?: run {
            Box(
                Modifier
                    .align(Alignment.Center)
                    .padding(10.dp)
                    .clip(RoundedCornerShape(30.dp))
                    .background(createImageLoadingAnimation())
                    .fillMaxSize()
            )
        }
    }
}

@Composable
fun ZoomableImage(
    modifier: Modifier = Modifier,
    bitmap: ImageBitmap,
    onLongPress: (Offset) -> Unit
) {
    var scale by remember { mutableStateOf(1f) }
    Box(
        modifier = modifier
            .fillMaxSize()
            .pointerInput(Unit) {
                detectTapGestures(
                    onTap = { scale = 1f },
                    onLongPress = onLongPress
                )
            }
            .pointerInput(Unit) {
                detectTransformGestures { _, _, zoom, _ ->
                    scale *= zoom
                }
            }
    ) {
        Image(
            modifier = Modifier
                .align(Alignment.Center)
                .padding(10.dp)
                .graphicsLayer(
                    scaleX = maxOf(.5f, minOf(3f, scale)),
                    scaleY = maxOf(.5f, minOf(3f, scale)),
                )
            ,
            bitmap = bitmap,
            contentDescription = null
        )
    }
}

@Composable
fun ImageInfoDialog(
    isDialogOpen: Boolean,
    onDismissRequest: () -> Unit,
    imageTitle: String,
    imageContextLink: String,
    imageByteSize: Int,
    imageFormat: String,
    imageHeight: Int,
    imageWidth: Int
) {
    if (isDialogOpen) {
        AlertDialog(
            modifier = Modifier.testTag(TestTag.ABOUT_IMAGE),
            onDismissRequest = onDismissRequest,
            title = {
                Text(text = stringResource(R.string.information_about_image), fontSize = 24.sp)
            },
            text = {
                Column {
                    AlertDialogItem(text = stringResource(R.string.title, imageTitle))
                    AlertDialogItem(text = stringResource(R.string.context_link, imageContextLink))
                    AlertDialogItem(text = stringResource(R.string.byte_size, imageByteSize))
                    AlertDialogItem(text = stringResource(R.string.image_format, imageFormat))
                    AlertDialogItem(text = stringResource(R.string.height, imageHeight))
                    AlertDialogItem(text = stringResource(R.string.width, imageWidth))
                }
            },
            buttons = {}
        )
    }
}

@Composable
fun AlertDialogItem(modifier: Modifier = Modifier, text: String) {
    Text(modifier = modifier.testTag(TestTag.ALERT_DIALOG_ITEM), text = text, fontSize = 16.sp)
}

@Preview(showBackground = true, heightDp = 480, widthDp = 300)
@Composable
fun ImageViewPreviewCompact() {
    ImageFinderTheme {
        ImageView()
    }
}

@Preview(showBackground = true, heightDp = 900, widthDp = 600)
@Composable
fun ImageViewPreviewMedium() {
    ImageFinderTheme {
        ImageView()
    }
}

@Preview(showBackground = true, widthDp = 840, heightDp = 900)
@Composable
fun ImageViewPreviewExpanded() {
    ImageFinderTheme {
        ImageView()
    }
}

@Preview(
    showBackground = true,
    widthDp = 840,
    heightDp = 900,
    uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun ImageViewPreviewDesktopNight() {
    ImageFinderTheme {
        ImageView()
    }
}
