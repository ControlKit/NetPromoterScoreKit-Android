package com.sepanta.controlkit.netpromoterscore.view.ui

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.sepanta.controlkit.netpromoterscore.theme.Black10
import com.sepanta.controlkit.netpromoterscore.theme.Black30
import com.sepanta.controlkit.netpromoterscore.theme.Black60
import com.sepanta.controlkit.netpromoterscore.theme.Blue80
import com.sepanta.controlkit.netpromoterscore.theme.Orange100
import com.sepanta.controlkit.netpromoterscore.theme.Orange20
import com.sepanta.controlkit.netpromoterscore.theme.Orange40
import com.sepanta.controlkit.netpromoterscore.theme.Orange60
import com.sepanta.controlkit.netpromoterscore.theme.Orange80
import com.sepanta.controlkit.netpromoterscore.theme.Typography
import com.sepanta.controlkit.netpromoterscore.theme.White100
import com.sepanta.controlkit.netpromoterscore.R
import com.sepanta.controlkit.netpromoterscore.utils.AutoResizeText
import com.sepanta.controlkit.netpromoterscore.utils.FontSizeRange
import com.sepanta.controlkit.netpromoterscore.view.config.NetPromoterScoreViewConfig
import com.sepanta.controlkit.netpromoterscore.view.config.NetPromoterScoreViewContract
import com.sepanta.controlkit.netpromoterscore.view.ui.viewModel.NetPromoterScoreViewModel
import kotlin.math.atan2
import kotlin.math.roundToInt
import kotlin.math.sqrt

class NetPromoterScoreViewPopover1 : NetPromoterScoreViewContract {

    @Composable
    override fun ShowView(
        config: NetPromoterScoreViewConfig,
        viewModel: NetPromoterScoreViewModel,
    ) {

        val openDialog = viewModel.openDialog.collectAsState()
        val degree = remember { mutableFloatStateOf(-90f) }

        val unSelectedColor: ButtonColors =
            ButtonDefaults.buttonColors(containerColor = Color.Transparent)
        val selectedColors: List<ButtonColors> = listOf(
            ButtonDefaults.buttonColors(containerColor = Orange20),
            ButtonDefaults.buttonColors(containerColor = Orange20),
            ButtonDefaults.buttonColors(containerColor = Orange40),
            ButtonDefaults.buttonColors(containerColor = Orange60),
            ButtonDefaults.buttonColors(containerColor = Orange60),
            ButtonDefaults.buttonColors(containerColor = Orange60),
            ButtonDefaults.buttonColors(containerColor = Orange80),
            ButtonDefaults.buttonColors(containerColor = Orange100),
            ButtonDefaults.buttonColors(containerColor = Orange100),
            ButtonDefaults.buttonColors(containerColor = Orange100)
        )

        if (!openDialog.value) return
        Dialog(
            onDismissRequest = { viewModel.dismissDialog() }, properties = DialogProperties(
                dismissOnClickOutside = true,
                dismissOnBackPress = false,
                usePlatformDefaultWidth = false
            )
        ) {
            Surface(
                modifier = config.popupViewLayoutModifier ?: Modifier
                    .fillMaxHeight(0.95f)
                    .fillMaxWidth(0.94f),
                shape = config.popupViewCornerRadius ?: RoundedCornerShape(15.dp),
                color = config.popupViewBackGroundColor ?: White100,

                ) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Top
                ) {
                    HeaderTitle(config, Modifier.weight(10f))
                    DescriptionTitle(config, Modifier.weight(8f))
                    DegreeLevelWidget(degree, Modifier.weight(15f), config)
                    QuestionTitle(config, Modifier.weight(8f))
                    SetScoreLayout(
                        config.totalScore,
                        config,
                        unSelectedColor,
                        selectedColors,
                        degree,
                        Modifier.weight(15f),
                        viewModel
                    )
                    DescriptionTextFieldLayout(config, viewModel, Modifier.weight(20f))
                    Buttons(config, viewModel, Modifier.weight(20f))
                }
            }


        }

    }

    @Composable
    private fun HeaderTitle(
        config: NetPromoterScoreViewConfig, modifier: Modifier,
    ) {
        Box(
            modifier = config.headerTitleLayoutModifier ?: modifier
                .padding(
                    top = 35.dp, end = 15.dp, start = 15.dp
                )
                .fillMaxWidth(), contentAlignment = Alignment.TopCenter

        ) {

            config.headerTitleView?.let { textView ->
                textView(config.headerTitle)
            } ?: AutoResizeText(
                text = config.headerTitle,
                maxLines = 1,
                fontSizeRange = config.headerTitleFontSizeRange ?: FontSizeRange(
                    min = 14.sp,
                    max = 18.sp,
                ),
                overflow = TextOverflow.Visible,
                style = config.headerTitleTextStyle ?: Typography.bodyLarge,
                textAlign = config.headerTitleTextStyle?.textAlign ?: TextAlign.Center,
                fontWeight = config.headerTitleTextStyle?.fontWeight ?: FontWeight.Bold,
                color = config.headerTitleTextStyle?.color ?: Typography.bodyLarge.color
            )
        }


    }

    @Composable
    private fun DescriptionTitle(
        config: NetPromoterScoreViewConfig, modifier: Modifier,

        ) {
        Box(
            modifier = config.descriptionTitleLayoutModifier ?: modifier
                .padding(
                    top = 15.dp, end = 15.dp, start = 15.dp
                )
                .fillMaxWidth(), contentAlignment = Alignment.TopCenter

        ) {
            config.descriptionTitleView?.let { textView ->
                textView(config.descriptionTitle)
            } ?: Text(
                text = config.descriptionTitle,
                style = Typography.bodyMedium,
                color = config.descriptionTitleColor ?: Typography.bodyMedium.color
            )
        }


    }

    @Composable
    private fun QuestionTitle(
        config: NetPromoterScoreViewConfig, modifier: Modifier,

        ) {
        Box(
            modifier = config.questionTitleLayoutModifier ?: modifier
                .padding(
                    top = 20.dp, end = 15.dp, start = 15.dp
                )
                .fillMaxWidth(), contentAlignment = Alignment.TopCenter

        ) {
            config.questionTitleView?.let { textView ->
                textView(config.questionTitle)
            } ?: Text(
                text = config.questionTitle,
                style = Typography.titleMedium,
                color = config.questionTitleColor ?: Typography.titleMedium.color

            )
        }

    }


    fun mapRangeDegreeToInt(x: Float, max: Int = 10): Int {
        val xMin = -90.0
        val xMax = 90.0
        val yMin = 1
        val yMax = max

        val mapped = yMin + (x - xMin) * (yMax - yMin) / (xMax - xMin)
        return mapped.roundToInt()
    }

    fun mapRangeIntToDegree(input: Int, max: Int = 10): Float {
        val inMin = 1
        val inMax = max
        val outMin = -90f
        val outMax = 90f
        return outMin + (input - inMin) * (outMax - outMin) / (inMax - inMin)
    }

    @Composable
    private fun SetScoreLayout(
        ratingSize: Int,
        config: NetPromoterScoreViewConfig,
        unSelectedColor: ButtonColors,
        selectedColors: List<ButtonColors>,
        degree: MutableState<Float>,
        modifier: Modifier,
        viewModel: NetPromoterScoreViewModel,
    ) {
        val selectedIndex = remember { mutableIntStateOf(-1) }
        LaunchedEffect(degree.value) {
            selectedIndex.intValue = mapRangeDegreeToInt(degree.value, ratingSize)
        }
        LaunchedEffect(selectedIndex.intValue) {
            viewModel.setScore(selectedIndex.intValue)
        }
        Box(
            modifier = config.setScoreLayoutModifier ?: modifier
                .padding(top = 28.dp)
                .fillMaxWidth()
                .height(55.dp), contentAlignment = Alignment.TopCenter
        ) {

            Row(
                Modifier
                    .padding(top = 28.dp)
                    .fillMaxHeight()
                    .wrapContentWidth()
                    .align(Alignment.Center)
            ) {
                for (i in 1..ratingSize) {
                    SetScoreButton(
                        i,
                        selectedIndex,
                        config.unSelectedColor ?: unSelectedColor,
                        config.selectedColors ?: selectedColors,
                        onClick = { index ->
                            degree.value = mapRangeIntToDegree(index, ratingSize)
                        },
                                config=config,

                    )
                }
            }


            config.lowScoreTitleView?.let { textView ->
                textView(config.lowScoreTitle)
            } ?: Text(
                modifier = Modifier
                    .padding(start = 20.dp)
                    .align(Alignment.TopStart),
                text = config.lowScoreTitle,
                style = Typography.titleSmall,
                color = config.lowScoreTitleColor ?: Typography.titleSmall.color

            )
            config.highScoreTitleView?.let { textView ->
                textView((config.highScoreTitle))
            } ?: Text(
                modifier = Modifier
                    .padding(end = 20.dp)
                    .align(Alignment.TopEnd),
                text = config.highScoreTitle,
                style = Typography.titleSmall,
                color = config.highScoreTitleColor ?: Typography.titleSmall.color

            )

        }


    }


    @Composable
    private fun SetScoreButton(
        index: Int,
        selectedIndex: MutableState<Int>,
        unSelectedColor: ButtonColors,
        selectedColors: List<ButtonColors>,
        onClick: (Int) -> Unit,
        config: NetPromoterScoreViewConfig,
    ) {
        val selectedColor: ButtonColors = if (index < selectedColors.size) {
            selectedColors[index]
        } else {
            ButtonDefaults.buttonColors(containerColor = Color.Transparent)
        }

        Button(
            modifier = config.scoreButtonLayoutModifier ?: Modifier
                .padding(end = 1.dp)
                .height(32.dp)
                .width(28.dp),
            contentPadding = PaddingValues(),

            shape = RoundedCornerShape(10.dp),
            colors = if (selectedIndex.value >= index) selectedColor
            else unSelectedColor,
            border = BorderStroke(
                1.dp, Black10
            ),
            onClick = {
                selectedIndex.value = index
                onClick.invoke(index)
            }) {

            config.scoreButtonTitleView?.let { textView ->
                textView(index.toString())
            } ?: Text(
                text = index.toString(),
                style = Typography.titleSmall,
                modifier = Modifier.wrapContentSize()
            )

        }


    }

    @Composable
    fun DegreeLevelWidget(
        degree: MutableState<Float>,
        modifier: Modifier,
        config: NetPromoterScoreViewConfig,
    ) {
        val animatedRotation by animateFloatAsState(
            targetValue = degree.value, label = "rotation", animationSpec = tween(100)
        )

        val stroke = 12.dp
        val maxWidth = 180.dp

        val density = LocalDensity.current
        val strokePx = with(density) { stroke.toPx() }
        val diameterPx = with(density) { (maxWidth / 2).toPx() }
        val radiusPx = diameterPx / 2f
        val centerYOffset = with(density) { (maxWidth / 8).toPx() }
        val hitSlop = with(density) { 10.dp.toPx() }

        Box(
            modifier = modifier.fillMaxWidth(), contentAlignment = Alignment.TopCenter
        ) {
            Column(
                modifier = Modifier
                    .padding(top = 25.dp, bottom = 15.dp)
                    .size(height = 60.dp, width = 105.dp)
            ) {
                Box(modifier = Modifier.wrapContentSize()) {

                    Canvas(
                        modifier = Modifier
                            .size(height = 60.dp, width = 105.dp)
                            .pointerInput(Unit) {

                                detectTapGestures { p ->
                                    updateDegreeFromTouch(
                                        p = p,
                                        size = Size(size.width.toFloat(), size.height.toFloat()),
                                        degree = degree,
                                        radiusPx = radiusPx,
                                        strokePx = strokePx,
                                        centerYOffset = centerYOffset,
                                        hitSlop = hitSlop
                                    )
                                }
                            }
                            .pointerInput(Unit) {
                                detectDragGestures { change, _ ->
                                    updateDegreeFromTouch(
                                        p = change.position,
                                        size = Size(size.width.toFloat(), size.height.toFloat()),
                                        degree = degree,
                                        radiusPx = radiusPx,
                                        strokePx = strokePx,
                                        centerYOffset = centerYOffset,
                                        hitSlop = hitSlop
                                    )
                                }
                            }) {
                        val offsetx = this.center.x - (maxWidth / 4).toPx()
                        val offsety = this.center.y - (maxWidth / 8).toPx()

                        drawArc(
                            size = Size((maxWidth / 2).toPx(), (maxWidth / 2).toPx()),
                            color = config.degreeLevelWidgetFirstcolor ?: Color(0xFFEA580C),
                            startAngle = 0f,
                            sweepAngle = -60f,
                            topLeft = Offset(offsetx, offsety),
                            useCenter = false,
                            style = Stroke(stroke.toPx(), cap = StrokeCap.Butt)
                        )
                        drawArc(
                            size = Size((maxWidth / 2).toPx(), (maxWidth / 2).toPx()),
                            color = config.degreeLevelWidgetSecendColor ?: Color(0xFFFB923C),
                            startAngle = -60f,
                            sweepAngle = -60f,
                            topLeft = Offset(offsetx, offsety),
                            useCenter = false,
                            style = Stroke(stroke.toPx(), cap = StrokeCap.Butt)
                        )
                        drawArc(
                            size = Size((maxWidth / 2).toPx(), (maxWidth / 2).toPx()),
                            color = config.degreeLevelWidgetThirdColor ?: Color(0xFFFED7AA),
                            startAngle = -120f,
                            sweepAngle = -60f,
                            topLeft = Offset(offsetx, offsety),
                            useCenter = false,
                            style = Stroke(stroke.toPx(), cap = StrokeCap.Butt)
                        )
                    }

                    Icon(
                        modifier = Modifier
                            .height(45.dp)
                            .width(6.dp)
                            .align(Alignment.Center)
                            .graphicsLayer {
                                transformOrigin = TransformOrigin(0.5f, 1f)
                                rotationZ = animatedRotation
                            },
                        painter = painterResource(id = R.drawable.pointer),
                        contentDescription = null,
                        tint = Color.Unspecified
                    )
                }
            }
        }
    }

    private fun updateDegreeFromTouch(
        p: Offset,
        size: Size,
        degree: MutableState<Float>,
        radiusPx: Float,
        strokePx: Float,
        centerYOffset: Float,
        hitSlop: Float,
    ) {
        val canvasCenter = Offset(size.width / 2f, size.height / 2f)
        val circleCenter = Offset(canvasCenter.x, canvasCenter.y + centerYOffset)

        val dx = p.x - circleCenter.x
        val dy = p.y - circleCenter.y
        val r = sqrt(dx * dx + dy * dy)

        val inRing = r in (radiusPx - strokePx / 2f - hitSlop)..(radiusPx + strokePx / 2f + hitSlop)
        val onTopHalf = p.y <= circleCenter.y

        if (inRing && onTopHalf) {
            val theta =
                Math.toDegrees(atan2((circleCenter.y - p.y), (p.x - circleCenter.x)).toDouble())
                    .toFloat()
            val rotation = 90f - theta
            degree.value = rotation.coerceIn(-90f, 90f)
        }
    }


    @Composable
    private fun DescriptionTextFieldLayout(
        config: NetPromoterScoreViewConfig,
        viewModel: NetPromoterScoreViewModel,
        modifier: Modifier,
    ) {
        Box(
            modifier = config.descriptionTextFieldLayoutModifier ?: modifier
                .padding(
                    top = 25.dp, start = 14.dp, end = 14.dp
                )
                .fillMaxWidth()
                .wrapContentHeight(), contentAlignment = Alignment.TopStart
        ) {
            DescriptionTextFieldTitle(config)
            DescriptionTextField(config, viewModel)

        }
    }

    @Composable
    private fun DescriptionTextFieldTitle(
        config: NetPromoterScoreViewConfig,
    ) {
        config.descriptionTextFieldTitleView?.let { textView ->
            textView(config.descriptionTextFieldTitle)
        } ?: Text(
            modifier = Modifier
                .wrapContentSize()
                .padding(end = 10.dp),
            text = config.descriptionTextFieldTitle,
            style = Typography.titleMedium,
            color = config.descriptionTextFieldTitleColor ?: Typography.titleMedium.color

        )


    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    private fun DescriptionTextField(
        config: NetPromoterScoreViewConfig,
        viewModel: NetPromoterScoreViewModel,
    ) {
        val showError by viewModel.showDescriptionTextFieldError.collectAsState()

        var text by rememberSaveable { mutableStateOf("") }
        OutlinedTextField(
            modifier = config.descriptionTextFieldModifier ?: Modifier
                .padding(top = 25.dp)
                .defaultMinSize(minHeight = 100.dp),
            value = text,
            singleLine = false,
            placeholder = {
                config.descriptionTextFieldPlaceholderView ?: Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = config.descriptionTextFieldPlaceholderText,
                    color = Black60,
                    style = Typography.titleSmall
                )
            },
            isError = showError,
            supportingText = {
                if (showError) {
                    config.descriptionTextFieldSupportingTextView?.let { textView ->
                        textView(config.descriptionTextFieldSupportingText)
                    } ?: Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = config.descriptionTextFieldSupportingText,
                        color = MaterialTheme.colorScheme.error
                    )
                }

            },
            textStyle = config.descriptionTextFieldTextStyle ?: Typography.titleSmall,
            shape = config.descriptionTextFieldShape ?: RoundedCornerShape(20.dp),
            colors = config.descriptionTextFieldColors ?: TextFieldDefaults.colors(
                focusedContainerColor = Color.Transparent, focusedIndicatorColor = Black30
            ),
            onValueChange = {
                text = it
                viewModel.updateDescription(text)

            },
        )
    }

    @Composable
    private fun Buttons(
        config: NetPromoterScoreViewConfig,
        viewModel: NetPromoterScoreViewModel,
        modifier: Modifier,

        ) {
        Box(
            modifier = config.buttonsLayoutModifier ?: modifier.padding(
                top = 26.dp, start = 14.dp, end = 14.dp, bottom = 34.dp
            ), contentAlignment = Alignment.TopCenter
        ) {
            ButtonSend(config, viewModel, modifier)
            ButtonCancel(config, modifier, viewModel)
        }
    }


    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    private fun ButtonSend(
        config: NetPromoterScoreViewConfig,
        viewModel: NetPromoterScoreViewModel,
        modifier: Modifier,
    ) {

        val onClickAction: () -> Unit = {
            viewModel.send()

        }
        config.submitButtonView?.let { button ->
            button(onClickAction)
        } ?: Button(
            onClick = onClickAction,
            shape = config.submitButtonCornerRadius ?: RoundedCornerShape(10.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = config.submitButtonColor ?: Blue80
            ),
            modifier = Modifier
                .height(36.dp)
                .width(250.dp),
            elevation = ButtonDefaults.buttonElevation(defaultElevation = 5.dp)
        ) {
            Text(
                text = config.submitButtonTitle ?: ("Done"),
                style = Typography.titleMedium,
                color = White100

            )
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    private fun ButtonCancel(
        config: NetPromoterScoreViewConfig,
        modifier: Modifier,
        viewModel: NetPromoterScoreViewModel,

        ) {
        val onClickAction: () -> Unit = {
            viewModel.dismissDialog()
        }
        config.cancelButtonView?.let { button ->
            button(onClickAction)
        } ?: Button(
            onClick = onClickAction,
            shape = config.cancelButtonCornerRadius ?: RoundedCornerShape(10.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = config.cancelButtonColor ?: Color.Transparent
            ),
            border = BorderStroke(1.dp, Blue80),
            modifier = Modifier
                .padding(top = 41.dp)
                .height(36.dp)
                .width(250.dp),
            elevation = ButtonDefaults.buttonElevation(defaultElevation = 0.dp)
        ) {
            Text(
                text = config.cancelButtonTitle ?: ("Not Now"),
                style = Typography.titleMedium,
                color = Black10
            )
        }


    }

}