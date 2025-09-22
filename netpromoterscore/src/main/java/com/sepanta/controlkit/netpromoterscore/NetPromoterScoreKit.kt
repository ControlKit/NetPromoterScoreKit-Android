package com.sepanta.controlkit.netpromoterscore

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import com.sepanta.controlkit.netpromoterscore.config.NetPromoterScoreServiceConfig
import com.sepanta.controlkit.netpromoterscore.service.ApiService
import com.sepanta.controlkit.netpromoterscore.service.NetPromoterScoreApi
import com.sepanta.controlkit.netpromoterscore.service.RetrofitClientInstance
import com.sepanta.controlkit.netpromoterscore.utils.UniqueUserIdProvider
import com.sepanta.controlkit.netpromoterscore.view.config.NetPromoterScoreViewStyle
import com.sepanta.controlkit.netpromoterscore.view.ui.viewModel.NetPromoterScoreViewModel
import com.sepanta.controlkit.netpromoterscore.view.ui.viewModel.ViewModelFactory
import com.sepanta.controlkit.netpromoterscore.view.ui.viewModel.state.ViewModelState
import kotlin.jvm.java

class NetPromoterScoreKit(
    private var config: NetPromoterScoreServiceConfig,
    context: Context

) {

    private var _viewModel: NetPromoterScoreViewModel? = null

    init {
        setupViewModel(context)
    }

    private fun setupViewModel(context: Context) {
        val retrofit = RetrofitClientInstance.getRetrofitInstance(
            config.timeOut,
            config.maxRetry,
            config.timeRetryThreadSleep
        ) ?: return

        val api = NetPromoterScoreApi(retrofit.create(ApiService::class.java))
        _viewModel = ViewModelFactory(api).create(NetPromoterScoreViewModel::class.java)
        if (config.deviceId == null) {
            config.deviceId = UniqueUserIdProvider.getOrCreateUserId(context)
            _viewModel?.setConfig(config)

        } else {
            _viewModel?.setConfig(config)

        }
    }


    @Composable
    internal fun ConfigureComposable(
        onDismiss: (() -> Unit)? = null,
        onState: ((ViewModelState) -> Unit)? = null
    ) {
        val state = _viewModel?.state?.collectAsState()?.value

        LaunchedEffect(Unit) {
            _viewModel?.cancelButtonEvent?.collect {
                onDismiss?.invoke()
            }
        }

        when (state) {
            ViewModelState.Initial -> {
                NetPromoterScoreViewStyle.checkViewStyle(config.viewConfig.viewStyle)
                    .ShowView(config = config.viewConfig, _viewModel!!)
                onState?.invoke(ViewModelState.Initial)
            }

            ViewModelState.NoContent -> onState?.invoke(ViewModelState.NoContent)

            is ViewModelState.Success -> onState?.invoke(ViewModelState.Success)

            is ViewModelState.Error -> onState?.invoke(ViewModelState.Error(state.data))

            else -> Unit
        }
    }

    fun showView() {
        _viewModel?.showDialog()
    }
}

@Composable
fun netPromoterScoreKitHost(
    config: NetPromoterScoreServiceConfig,
    onDismiss: (() -> Unit)? = null,
    onState: ((ViewModelState) -> Unit)? = null
): NetPromoterScoreKit {
    val context = LocalContext.current

    val kit = remember { NetPromoterScoreKit(config, context = context) }
    kit.ConfigureComposable(onState = onState, onDismiss = onDismiss)
    return kit
}





