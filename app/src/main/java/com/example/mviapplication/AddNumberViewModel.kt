package com.example.mviapplication

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.launch
import java.lang.Exception

class AddNumberViewModel : ViewModel() {


    val intentChannel = Channel<MainIntent>(Channel.UNLIMITED)
    private val _viewState = MutableStateFlow<MainViewState>(MainViewState.Idle)
    val state: StateFlow<MainViewState> get() = _viewState
    private var number = 0;

    init {
        processIntent()
    }
    //process
    private fun processIntent() {
        viewModelScope.launch {
            intentChannel.consumeAsFlow().collect {
                when (it) {
                    is MainIntent.AddNumber -> reduceResult()
                }
            }
        }
    }

    //reduce
    private fun reduceResult() {
        viewModelScope.launch {
            _viewState.value =
                try {
                    MainViewState.Number(++number)
                } catch (e: Exception) {
                        MainViewState.Error(e.message!!)
                }
        }


    }
}