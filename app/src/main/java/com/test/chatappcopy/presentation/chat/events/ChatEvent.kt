package com.test.chatappcopy.presentation.chat.events

sealed class ChatEvent {
    data class OnMsgCHanged(val text: String) : ChatEvent()
    data object SendMsg : ChatEvent()
}