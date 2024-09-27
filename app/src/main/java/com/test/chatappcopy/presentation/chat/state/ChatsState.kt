package com.test.chatappcopy.presentation.chat.state

import com.test.chatappcopy.data.model.chat.ChatModel
import com.test.chatappcopy.data.network.NetworkResponse
import com.test.chatappcopy.domain.model.MessageUiModel

data class ChatsState(
    val chatId: String = "",
    val msgToSend: String = "",
    val chatModel: NetworkResponse<ChatModel?> = NetworkResponse.Idle,
    val msgsList: NetworkResponse<List<MessageUiModel>> = NetworkResponse.Idle
)
