package com.example.mviapplication

sealed class MainViewState {
    // idle still never do any thing
    object Idle : MainViewState()

    //results // number
    data class Number(val number:Int):MainViewState()

    //Error
    data class Error(val error: String):MainViewState();
}