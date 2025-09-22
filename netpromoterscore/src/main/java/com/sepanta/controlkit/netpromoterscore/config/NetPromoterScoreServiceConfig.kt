package com.sepanta.controlkit.netpromoterscore.config

import com.sepanta.controlkit.netpromoterscore.view.config.NetPromoterScoreViewConfig


data class NetPromoterScoreServiceConfig(
    var viewConfig: NetPromoterScoreViewConfig = NetPromoterScoreViewConfig(),
    var version: String = "0.0.0",
    var appId: String,
    var deviceId: String? = null,
    var name: String,
    var timeOut: Long = 5000L,
    var timeRetryThreadSleep: Long = 1000L,
    var maxRetry: Int = 5,
)