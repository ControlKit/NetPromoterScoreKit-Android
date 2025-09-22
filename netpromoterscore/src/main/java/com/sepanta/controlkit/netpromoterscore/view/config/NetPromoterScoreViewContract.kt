package com.sepanta.controlkit.netpromoterscore.view.config

import androidx.compose.runtime.Composable
import com.sepanta.controlkit.netpromoterscore.view.ui.viewModel.NetPromoterScoreViewModel

interface NetPromoterScoreViewContract {
    @Composable
    fun ShowView(
        config: NetPromoterScoreViewConfig,
        viewModel: NetPromoterScoreViewModel,

    )
}