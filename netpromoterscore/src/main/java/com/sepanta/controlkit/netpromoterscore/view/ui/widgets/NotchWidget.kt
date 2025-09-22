package com.sepanta.controlkit.netpromoterscore.view.ui.widgets


import androidx.annotation.DrawableRes
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.RoundRect
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathOperation
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.ui.res.painterResource
import com.sepanta.controlkit.netpromoterscore.R
import com.sepanta.controlkit.netpromoterscore.view.config.NetPromoterScoreViewConfig
import kotlin.apply

@Composable
fun NotchWidget(
    topButtonText: String = "Logo",
    topButtonCornerRadius: Dp = 15.dp,

    config: NetPromoterScoreViewConfig,
    content: @Composable ColumnScope.() -> Unit,

    ) {
    Column(
        modifier = config.notchViewLayoutModifier?:Modifier
            .fillMaxHeight(0.6f)
            .fillMaxWidth()
            .background(Color.White),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(config.notchHeight)
        ) {
            Canvas(modifier = Modifier.matchParentSize()) {
                val radius = config.notchRadius.toPx()
                val cRadius = config.notchCornerRadius.toPx()
                val centerX = size.width / 2
                val bottomY = size.height

                val rectPath = Path().apply {
                    addRoundRect(
                        RoundRect(
                            rect = Rect(0f, 0f, size.width, size.height),
                            topLeft = CornerRadius(cRadius),
                            topRight = CornerRadius(cRadius),
                            bottomRight = CornerRadius(0f),
                            bottomLeft = CornerRadius(0f)
                        )
                    )
                }

                val circlePath = Path().apply {
                    addOval(
                        Rect(
                            centerX - radius, bottomY - radius, centerX + radius, bottomY + radius
                        )
                    )
                }

                val notchPath = Path().apply {
                    op(rectPath, circlePath, PathOperation.Difference)
                }

                drawPath(
                    path = notchPath, color = config.notchBackgroundColor
                )
            }
            config.notchIconLogo ?: Button(
                onClick = {},
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .padding(top = 16.dp),
                shape = RoundedCornerShape(topButtonCornerRadius)
            ) {
                Text(topButtonText)
            }

            Icon(
                painter = painterResource(id = config.notchIcon),
                contentDescription = "Notch Icon",
                tint = Color.Unspecified,
                modifier = config.notchIconLayoutModifier?.align(Alignment.BottomCenter)
                    ?: Modifier
                        .align(Alignment.BottomCenter)
                        .offset(y = config.notchRadius / 2)
                        .size(config.notchIconSize)
            )
        }

        content()
        Spacer(modifier = Modifier.height(25.dp))
    }
}