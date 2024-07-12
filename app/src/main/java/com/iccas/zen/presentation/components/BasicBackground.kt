package com.iccas.zen.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.iccas.zen.R

@Composable
fun BasicBackground(
    content: @Composable () -> Unit
) {
    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = R.drawable.basic_background),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(vertical = 40.dp, horizontal = 25.dp)
        ) {
            content()
        }
    }
}

@Composable
fun BasicBackgroundWithLogo(
    content: @Composable () -> Unit
) {
    BasicBackground {
        Row() {
            Image(
                painter = painterResource(id = R.drawable.zen_logo),
                contentDescription = null,
                modifier = Modifier.width(65.dp),
                contentScale = ContentScale.Crop
            )
        }
        content()
    }
}
