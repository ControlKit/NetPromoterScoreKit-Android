package com.sepanta.controlkit.netpromoterscore.view.config

import androidx.annotation.DrawableRes
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.CardColors
import androidx.compose.material3.TextFieldColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.sepanta.controlkit.netpromoterscore.R
import com.sepanta.controlkit.netpromoterscore.utils.FontSizeRange

data class NetPromoterScoreViewConfig(
    var viewStyle: NetPromoterScoreViewStyle = NetPromoterScoreViewStyle.Popover1,
    var selectedColors: List<ButtonColors>? = null,
    var unSelectedColor: ButtonColors? = null,
    var totalScore: Int = 5,

    var closeImageDrawble: Int? = null,
    var updateImageDrawble: Int? = null,
    var updateImageColor: Color? = null,
    var closeImageColor: Color? = null,
    var closeImageLayoutModifier: Modifier? = null,
    var updateImageLayoutModifier: Modifier? = null,
    var popupViewLayoutModifier: Modifier? = null,
    var popupViewBackGroundColor: Color? = null,
    var popupViewCornerRadius: RoundedCornerShape? = null,
    var headerTitle: String = "Rate the service",
    var headerTitleLayoutModifier: Modifier? = null,
    var descriptionTitle: String = "Your feedback will  help us get better!",
    var lowScoreTitle: String = "NOT AT ALL LIKELY",
    var highScoreTitle: String = "EXTREME LIKELY",
    var questionTitle: String = "How much would you recommend our company to your friends and family?",
    var descriptionTitleColor: Color? = null,
    var lowScoreTitleColor: Color? = null,
    var highScoreTitleColor: Color? = null,
    var questionTitleColor: Color? = null,
    var descriptionTitleLayoutModifier: Modifier? = null,
    var questionTitleLayoutModifier: Modifier? = null,
    var buttonsLayoutModifier: Modifier? = null,
    var headerTitleFontSizeRange: FontSizeRange? = null,
    var headerTitleTextStyle: TextStyle? = null,

    var scoreButtonLayoutModifier: Modifier? = null,
    var scoreButtonTitleView: @Composable ((String) -> Unit)? = null,

    var notchIconLayoutModifier: Modifier? = null,
    var notchIcon: Int = R.drawable.icon,
    var notchIconLogo: @Composable (() -> Unit)? = null,
    var notchBackgroundColor: Color = Color(0xFF730300),
    var notchViewLayoutModifier: Modifier? = null,
    var notchHeight: Dp = 115.dp,
    var notchIconSize: Dp = 42.dp,
    var notchRadius: Dp = 36.dp,
    var notchCornerRadius: Dp = 16.dp,

    var ratingStarSize: Dp = 40.dp,
    var ratingEmpotyColor: Color = Color.Gray,
    var ratingFullColor: Color = Color.Yellow,

    var degreeLevelWidgetFirstcolor: Color? = null,
    var degreeLevelWidgetSecendColor: Color? = null,
    var degreeLevelWidgetThirdColor: Color? = null,

    var lineTitleColor: Color? = null,
    var submitButtonTitle: String? = null,
    var cancelButtonTitle: String? = null,
    var updateButtonTitleColor: Color? = null,
    var submitButtonColor: Color? = null,
    var cancelButtonColor: Color? = null,
    var cancelButtonCornerColor: Color? = null,
    var submitButtonCornerRadius: RoundedCornerShape? = null,
    var cancelButtonCornerRadius: RoundedCornerShape? = null,
    var updateButtonBorderColor: Color? = null,
    var submitButtonLayoutModifier: Modifier? = null,
    var cancelButtonLayoutModifier: Modifier? = null,
    var versionTitle: String = "Up to 12.349 version Apr 2024.",
    var versionTitleColor: Color? = null,
    var versionTitleLayoutModifier: Modifier? = null,
    var imageView: @Composable (() -> Unit)? = null,
    var closeButtonView: @Composable (() -> Unit)? = null,
    var lineView: @Composable (() -> Unit)? = null,


    var setScoreLayoutModifier: Modifier? = null,

    var descriptionTextFieldLayoutModifier: Modifier? = null,
    var descriptionTextFieldTitle: String = "Describe what motivated your review (optional)",
    var descriptionTextFieldTitleColor: Color? = null,
    var descriptionTextFieldTextStyle: TextStyle? = null,
    var descriptionTextFieldShape: Shape? = null,
    var descriptionTextFieldColors: TextFieldColors? = null,
    var descriptionTextFieldPlaceholderText: String = "Please write your complaint in 500 characters.",
    var descriptionTextFieldPlaceholderView: @Composable (() -> Unit)? = null,
    var descriptionTextFieldSupportingText: String = "Complete this field",
    var descriptionTextFieldModifier: Modifier? = null,
    var descriptionTextFieldSupportingTextView: @Composable ((String) -> Unit)? = null,
    var descriptionTextFieldTitleView:  @Composable ((String) -> Unit)? = null,

    var headerTitleView: @Composable ((String) -> Unit)? = null,

    var descriptionTitleView: @Composable ((String) -> Unit)? = null,
    var lowScoreTitleView: @Composable ((String) -> Unit)? = null,
    var highScoreTitleView: @Composable ((String) -> Unit)? = null,
    var questionTitleView: @Composable ((String) -> Unit)? = null,
    var versionTitleView: @Composable (() -> Unit)? = null,
    var submitButtonView: (@Composable (libraryOnClick: () -> Unit) -> Unit)? = null,
    var cancelButtonView: (@Composable (libraryOnClick: () -> Unit) -> Unit)? = null,
)
