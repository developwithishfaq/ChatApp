package com.test.chatappcopy.presentation.home.events

sealed class OnTimeEventHomeScreen {
    data object Idle : OnTimeEventHomeScreen()
    data class Toast(val msg: String) : OnTimeEventHomeScreen()
    data class MoveToChatsScreen(val chatId: String) : OnTimeEventHomeScreen()
}