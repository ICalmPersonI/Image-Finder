package com.calmperson.imagefinder.view.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.calmperson.imagefinder.R
import com.calmperson.imagefinder.TestTag

@Composable
fun SearchField(
    modifier: Modifier = Modifier,
    doSearch: (String) -> Unit = {}
) {
    val fontSize = 18.sp
    var query by remember { mutableStateOf("") }

    Box(modifier = modifier.fillMaxWidth()) {
        TextField(
            modifier = Modifier
                .padding(10.dp)
                .clip(RoundedCornerShape(30.dp))
                .fillMaxSize(),
            value = query,
            onValueChange = { query = it },
            placeholder = {
                Text(
                    text = stringResource(R.string.search),
                    fontSize = fontSize
                )
            },
            maxLines = 1,
            singleLine = true,
            trailingIcon = {
                IconButton(
                    modifier = Modifier
                        .testTag(TestTag.DO_SEARCh)
                        .width(50.dp)
                        .fillMaxHeight(),
                    onClick = {
                        doSearch.invoke(query)
                    }
                ) {
                    Icon(
                        modifier = Modifier
                            .width(50.dp)
                            .fillMaxHeight(),
                        imageVector = Icons.Default.Search,
                        contentDescription = null
                    )
                }
            },
            keyboardActions = KeyboardActions(
                onDone = {
                    doSearch.invoke(query)
                }
            ),
            textStyle = LocalTextStyle.current.copy(fontSize = fontSize)

        )
    }
}