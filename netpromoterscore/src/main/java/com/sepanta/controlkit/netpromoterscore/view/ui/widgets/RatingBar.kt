package com.sepanta.controlkit.netpromoterscore.view.ui.widgets

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.layout
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/*
 *  File: RatingBar.kt
 *
 *  Created by morteza on 9/15/25.
 */


@Composable
fun StarRatingBar(
    modifier: Modifier = Modifier,
    rating: Float,
    maxRating: Int = 5,
    starSize: Dp = 40.dp,
    empotyColor: Color = Color.Gray,
    fullColor: Color = Color.Yellow,
    onRatingChanged: (Float) -> Unit
) {
    var starWidth by remember { mutableFloatStateOf(0f) }

    Row(
        modifier = modifier.height(  40.dp)
            .pointerInput(Unit) {
                detectDragGestures { change, _ ->
                    val x = change.position.x
                    if (starWidth > 0) {
                        val rawRating = (x / starWidth).coerceIn(0f, maxRating.toFloat())
                        val snappedRating = (rawRating * 2).toInt() / 2f
                        onRatingChanged(snappedRating)
                    }
                }
            },
        verticalAlignment = Alignment.CenterVertically
    ) {
        for (i in 1..maxRating) {
            val fill = when {
                rating >= i -> 1f
                rating >= i - 0.5f -> 0.5f
                else -> 0f
            }

            val animatedFill by animateFloatAsState(
                targetValue = fill,
                animationSpec = tween(durationMillis = 100)
            )

            Box(
                modifier = Modifier
                    .size(starSize)
                    .onGloballyPositioned { coords ->
                        starWidth = coords.size.width.toFloat()
                    }
                    .clickable {
                        val newRating = if (rating >= i) i - 0.5f else i.toFloat()
                        onRatingChanged(newRating)
                    },
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Outlined.Star,
                    contentDescription = null,
                    tint = empotyColor,
                    modifier = Modifier.fillMaxSize()
                )

                if (animatedFill > 0f) {
                    Icon(
                        imageVector = Icons.Filled.Star,
                        contentDescription = null,
                        tint = fullColor,
                        modifier = Modifier
                            .fillMaxSize()
                            .layout { measurable, constraints ->
                                val placeable = measurable.measure(constraints)
                                val width = (placeable.width * animatedFill).toInt()
                                layout(placeable.width, placeable.height) {
                                    placeable.place(0, 0)
                                }
                            }
                            .clipToBounds()
                    )
                }
            }
        }
    }
}
