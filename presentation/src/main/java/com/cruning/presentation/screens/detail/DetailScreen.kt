package com.cruning.presentation.screens.detail

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage

@Composable
fun DetailScreen(
    url: String,
    click: () -> Unit,
) {
    val scale = remember { mutableStateOf(1f) }
    val offsetX = remember { mutableStateOf(0f) }
    val offsetY = remember { mutableStateOf(0f) }
    Box(modifier = Modifier
        .padding(PaddingValues(4.dp))
        .pointerInput(Unit) {
            detectTransformGestures { _, pan, zoom, _ ->
                scale.value *= zoom
                offsetX.value += pan.x
                offsetY.value += pan.y
            }
        }) {
        AsyncImage(
            model = url,
            alignment = Alignment.Center,
            contentDescription = null,
            modifier = Modifier
                .fillMaxHeight()
                .graphicsLayer(
                    scaleX = maxOf(1f, minOf(5f, scale.value)),
                    scaleY = maxOf(1f, minOf(5f, scale.value)),
                    translationX = offsetX.value,
                    translationY = offsetY.value,
                ),
        )
        Image(
            imageVector = Icons.Default.ExitToApp,
            colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.primary),
            contentDescription = null,
            modifier = Modifier
                .align(Alignment.TopEnd)
                .width(48.dp)
                .height(48.dp)
                .background(MaterialTheme.colorScheme.surface)
                .border(2.dp, MaterialTheme.colorScheme.primary, RoundedCornerShape(16))
                .clip(RoundedCornerShape(16))
                .padding(2.dp)
                .clickable { click() }
        )
    }
}