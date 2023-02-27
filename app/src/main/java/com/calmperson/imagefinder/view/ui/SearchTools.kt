package com.calmperson.imagefinder.view.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.calmperson.imagefinder.R
import com.calmperson.imagefinder.model.networkapi.GoogleSearchApiSearchQuery.Query
import com.calmperson.imagefinder.model.networkapi.GoogleSearchApiSearchQuery.DominantColor
import com.calmperson.imagefinder.model.networkapi.GoogleSearchApiSearchQuery.ImageSize
import com.calmperson.imagefinder.model.networkapi.GoogleSearchApiSearchQuery.ImageType
import com.calmperson.imagefinder.model.networkapi.GoogleSearchApiSearchQuery.Period
import com.calmperson.imagefinder.view.ui.theme.ImageFinderTheme


@Composable
fun SearchTools(
    modifier: Modifier = Modifier,
    onDropMenuItemClick: (Query) -> Unit = { }
) {
    val sizes = ImageSize.values()
    val colors = DominantColor.values()
    val types = ImageType.values()
    val periods = Period.values()
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        ToolButton(
            name = stringResource(R.string.size),
            items = sizes,
            onDropMenuItemClick = onDropMenuItemClick
        )
        ToolButton(
            name = stringResource(R.string.color),
            items = colors,
            onDropMenuItemClick = onDropMenuItemClick
        )
        ToolButton(
            name = stringResource(R.string.type),
            items = types,
            onDropMenuItemClick = onDropMenuItemClick
        )
        ToolButton(
            name = stringResource(R.string.time),
            items = periods,
            onDropMenuItemClick = onDropMenuItemClick
        )
    }
}

@Composable
fun ToolButton(
    modifier: Modifier = Modifier,
    name: String,
    items: Array<out Query>,
    onDropMenuItemClick: (Query) -> Unit
) {
    val fontSize = 18.sp
    var showMenu by remember { mutableStateOf(false) }
    var text by remember { mutableStateOf(name) }
    Box(modifier) {
        Button(
            modifier = Modifier.fillMaxHeight(),
            onClick = {showMenu = !showMenu}
        ) {
            Row {
                Text(text = text, fontSize = fontSize)
                Icon(imageVector = Icons.Default.ArrowDropDown, contentDescription = null)
            }
        }
        DropdownMenu(expanded = showMenu, onDismissRequest = { showMenu = false }) {
            items.forEach {
                val title = stringResource(it.stringResourceId)
                DropdownMenuItem(
                    modifier = Modifier.testTag(name),
                    onClick = {
                        onDropMenuItemClick.invoke(it)
                        text = if (it.value == null) name else title
                        showMenu = false
                    }
                ) {
                    Text(text = title, fontSize = fontSize)
                }
            }
        }
    }
}

@Preview(showBackground = true, heightDp = 100)
@Composable
fun SearchToolsPreview() {
    ImageFinderTheme {
        SearchTools()
    }
}