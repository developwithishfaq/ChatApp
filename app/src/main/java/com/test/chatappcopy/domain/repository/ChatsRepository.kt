package com.test.chatappcopy.domain.repository

import com.test.chatappcopy.data.model.chat.ChatModel
import com.test.chatappcopy.data.network.NetworkResponse
import kotlinx.coroutines.flow.Flow

interface ChatsRepository {
    suspend fun getAllChats(): NetworkResponse<List<ChatModel>>
    suspend fun createNewChatWith(
        otherUserId: String,
        otherUserName: String
    ): NetworkResponse<String>

    fun getAllChatsLive() : Flow<NetworkResponse<List<ChatModel>>>
    suspend fun getChatByChatId(id: String) : NetworkResponse<ChatModel?>

}