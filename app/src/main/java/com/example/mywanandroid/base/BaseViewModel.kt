package com.example.mywanandroid.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

open class BaseViewModel : ViewModel() {
    fun launch(
        scope: CoroutineScope = viewModelScope,
        block: suspend CoroutineScope.() -> Unit
    ) {
        scope.launch {
            block()
        }
    }

    fun launchMain(
        scope: CoroutineScope = viewModelScope,
        block: suspend CoroutineScope.() -> Unit
    ) {
        scope.launch(Dispatchers.Main) {
            block()
        }
    }

    fun launchIO(scope: CoroutineScope = viewModelScope, block: suspend CoroutineScope.() -> Unit) {
        scope.launch(Dispatchers.IO) {
            block()
        }
    }

    fun launchCPU(
        scope: CoroutineScope = viewModelScope,
        block: suspend CoroutineScope.() -> Unit
    ) {
        scope.launch(Dispatchers.Default) {
            block()
        }
    }
}