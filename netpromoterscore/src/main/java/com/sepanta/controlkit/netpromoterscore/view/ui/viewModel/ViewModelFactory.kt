package com.sepanta.controlkit.netpromoterscore.view.ui.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.sepanta.controlkit.netpromoterscore.service.NetPromoterScoreApi
import kotlin.jvm.java

class ViewModelFactory(
    private val api: NetPromoterScoreApi,

    ) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(NetPromoterScoreViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return NetPromoterScoreViewModel(api) as T
        }
        throw kotlin.IllegalArgumentException("Unknown ViewModel class")
    }
}