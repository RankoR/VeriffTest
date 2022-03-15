package com.example.core_ui.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

/**
 * Base view model
 */
abstract class BaseViewModel : ViewModel() {

    private val _showErrorMessage = Channel<String>(capacity = 1)

    /**
     * Error messages flow
     */
    internal val showErrorMessage = _showErrorMessage.receiveAsFlow()

    private val _showLoading = MutableStateFlow(false)

    /**
     * Loading state flow
     */
    internal val showLoading = _showLoading.asStateFlow()

    open fun showErrorMessage(message: String) {
        viewModelScope.launch(Dispatchers.Main) {
            _showErrorMessage.send(message)
        }
    }

    open fun showErrorMessage(throwable: Throwable) {
        showErrorMessage(throwable.message.orEmpty())
    }

    protected open fun setIsLoading(isLoading: Boolean) {
        viewModelScope.launch(Dispatchers.Main) {
            _showLoading.emit(isLoading)
        }
    }
}
