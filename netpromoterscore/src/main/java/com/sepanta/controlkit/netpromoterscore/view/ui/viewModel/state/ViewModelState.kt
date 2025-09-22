package com.sepanta.controlkit.netpromoterscore.view.ui.viewModel.state

import com.sepanta.controlkit.netpromoterscore.service.errorHandler.ApiError


sealed class ViewModelState {
    object Initial : ViewModelState()
    object NoContent : ViewModelState()
    object Success : ViewModelState()
    data class Error(val data: ApiError<*>?) : ViewModelState()


}

