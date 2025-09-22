package com.sepanta.controlkit.netpromoterscore.view.ui.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sepanta.controlkit.netpromoterscore.BuildConfig
import com.sepanta.controlkit.netpromoterscore.config.NetPromoterScoreServiceConfig
import com.sepanta.controlkit.netpromoterscore.service.NetPromoterScoreApi
import com.sepanta.controlkit.netpromoterscore.service.apierror.NetworkResult
import com.sepanta.controlkit.netpromoterscore.service.apierror.model.ErrorValidation
import com.sepanta.controlkit.netpromoterscore.view.ui.viewModel.state.ViewModelState
import com.sepanta.errorhandler.ApiError
import com.sepanta.errorhandler.ErrorEntityRegistry
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class NetPromoterScoreViewModel(
    private val api: NetPromoterScoreApi,
) : ViewModel() {
    private val url = BuildConfig.API_URL

    fun setupErrorEntities() {
        ErrorEntityRegistry.register(ErrorValidation::class.java)
    }

    private var config: NetPromoterScoreServiceConfig? = null

    fun setConfig(config: NetPromoterScoreServiceConfig) {
        this.config = config
        setupErrorEntities()
    }

    private val _mutableState = MutableStateFlow<ViewModelState>(ViewModelState.Initial)
    val state: StateFlow<ViewModelState> = _mutableState.asStateFlow()


    private val _showDescriptionTextFieldError = MutableStateFlow(false)
    val showDescriptionTextFieldError = _showDescriptionTextFieldError.asStateFlow()
    private var descriptionText = ""

    fun updateDescription(text: String) {
        descriptionText = text
        _showDescriptionTextFieldError.value = false
    }

    private var score = 1

    fun setScore(score: Int) {
        this.score = score
    }

    fun setViewAction() {
        if (config == null) return
        viewModelScope.launch {
            api.sendViewAction(
                "$url/view",
                config!!.appId,
                config!!.version,
                config!!.deviceId ?: "",
                BuildConfig.LIB_VERSION_NAME,
                name = config!!.name,

                )
        }
    }

    fun sendForm() {
        if (config == null) return
        viewModelScope.launch {
            val data = api.sendSubmitAction(
                "$url/submit",
                config!!.appId,
                config!!.version,
                config!!.deviceId ?: "",
                BuildConfig.LIB_VERSION_NAME,
                name = config!!.name,
                score = score,
                comment = descriptionText,
            )

            when (data) {
                is NetworkResult.Success -> {
                    val response = data.value
                    if (response == null) {
                        _mutableState.value = ViewModelState.NoContent
                    } else {
                        _mutableState.value = ViewModelState.Success
                    }
                }

                is NetworkResult.Error -> {
                    when (data.error) {
                        is ApiError<*> -> {
                            val entity = data.error.errorEntity
                            if (entity is ErrorValidation) {
                                entity.errors?.email?.forEach { println("Email error: $it") }
                                entity.errors?.subject?.forEach { println("Subject error: $it") }
                                entity.errors?.message?.forEach { println("Message error: $it") }
                            }
                        }
                    }
                    _mutableState.value = ViewModelState.Error(data.error)
                }
            }
        }

    }

    private val _openDialog = MutableStateFlow(false)

    val openDialog: StateFlow<Boolean> = _openDialog.asStateFlow()

    fun showDialog() {
        _openDialog.value = true
        setViewAction()
    }

    fun dismissDialog() {
        _openDialog.value = false
        triggerForceUpdate()
    }


    fun send() {
        sendForm()
        _openDialog.value = false
    }


    private val _cancelButtonEvent = Channel<Unit>(Channel.BUFFERED)
    val cancelButtonEvent = _cancelButtonEvent.receiveAsFlow()

    fun triggerForceUpdate() {
        viewModelScope.launch {
            _cancelButtonEvent.send(Unit)
        }
    }

}
