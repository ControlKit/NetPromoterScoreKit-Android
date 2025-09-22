package com.sepanta.controlkit.netpromoterscore.view.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.captionBar
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetValue
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.sepanta.controlkit.netpromoterscore.theme.Black10
import com.sepanta.controlkit.netpromoterscore.theme.Gray40
import com.sepanta.controlkit.netpromoterscore.theme.Typography
import com.sepanta.controlkit.netpromoterscore.theme.White100
import com.sepanta.controlkit.netpromoterscore.view.config.NetPromoterScoreViewConfig
import com.sepanta.controlkit.netpromoterscore.view.config.NetPromoterScoreViewContract
import com.sepanta.controlkit.netpromoterscore.view.ui.viewModel.NetPromoterScoreViewModel
import com.sepanta.controlkit.netpromoterscore.view.ui.widgets.NotchWidget
import com.sepanta.controlkit.netpromoterscore.view.ui.widgets.StarRatingBar

class NetPromoterScoreViewPopover2 : NetPromoterScoreViewContract {

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    override fun ShowView(
        config: NetPromoterScoreViewConfig,
        viewModel: NetPromoterScoreViewModel,
    ) {

        val openDialog = viewModel.openDialog.collectAsState()
        var rating by remember { mutableFloatStateOf(1f) }

        val sheetState =
            rememberModalBottomSheetState(
                skipPartiallyExpanded = true,
                confirmValueChange = { newValue ->
                    newValue != SheetValue.Hidden
                })

        if (!openDialog.value) return

        ModalBottomSheet(
            onDismissRequest = { /*viewModel.dismissDialog()*/ },
            sheetState = sheetState,
            contentWindowInsets = { WindowInsets.captionBar },
            dragHandle = null,

            shape = config.popupViewCornerRadius ?: RoundedCornerShape(
                topStart = 15.dp,
                topEnd = 15.dp
            ),
            containerColor = White100,

            ) {
            NotchWidget(config = config) {
                Column(
                    modifier = config.popupViewLayoutModifier ?: Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Bottom
                ) {

                    QuestionTitle(config = config, modifier = Modifier)
                    StarRatingBar(
                        rating = rating,
                        empotyColor = config.ratingEmpotyColor,
                        fullColor = config.ratingFullColor,
                        starSize = config.ratingStarSize,
                        maxRating = config.totalScore,
                        onRatingChanged = {
                            rating = it
                            viewModel.setScore(rating.toInt())

                        },
                        modifier = Modifier.weight(1f)
                    )
                    Buttons(config, viewModel, Modifier.weight(1f))
                }
            }


        }


    }


    @Composable
    private fun QuestionTitle(
        config: NetPromoterScoreViewConfig, modifier: Modifier,

        ) {
        Box(
            modifier = config.questionTitleLayoutModifier ?: modifier
                .padding(
                    top = 35.dp, end = 15.dp, start = 15.dp
                )
                .height(40.dp)
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

    @Composable
    private fun Buttons(
        config: NetPromoterScoreViewConfig,
        viewModel: NetPromoterScoreViewModel,
        modifier: Modifier,

        ) {
        Box(
            modifier = config.buttonsLayoutModifier ?: modifier.padding(
                start = 14.dp, end = 14.dp
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
            shape = config.submitButtonCornerRadius ?: RoundedCornerShape(
                topEnd = 20.dp,
                topStart = 20.dp
            ),
            colors = ButtonDefaults.buttonColors(
                containerColor = config.submitButtonColor ?: Gray40
            ),
            modifier = Modifier
                .height(36.dp)
                .width(250.dp),
            elevation = ButtonDefaults.buttonElevation(defaultElevation = 0.dp)
        ) {
            Text(
                text = config.submitButtonTitle ?: ("Done"),
                style = Typography.titleMedium,
                color = Color.Black

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
            shape = config.cancelButtonCornerRadius ?: RoundedCornerShape(
                bottomEnd = 20.dp,
                bottomStart = 20.dp
            ),
            colors = ButtonDefaults.buttonColors(
                containerColor = config.cancelButtonColor ?: Color.Transparent
            ),
            border = BorderStroke(1.dp, Gray40),
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