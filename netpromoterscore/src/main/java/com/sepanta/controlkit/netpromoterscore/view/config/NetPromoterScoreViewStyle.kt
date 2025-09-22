package com.sepanta.controlkit.netpromoterscore.view.config

import com.sepanta.controlkit.netpromoterscore.view.ui.NetPromoterScoreViewPopover1
import com.sepanta.controlkit.netpromoterscore.view.ui.NetPromoterScoreViewPopover2


enum class NetPromoterScoreViewStyle {
    Popover1,
    Popover2;
    companion object {
        fun checkViewStyle(style: NetPromoterScoreViewStyle): NetPromoterScoreViewContract {
            return when (style) {
                Popover1 -> NetPromoterScoreViewPopover1()
                Popover2 -> NetPromoterScoreViewPopover2()
            }

        }

    }

}
