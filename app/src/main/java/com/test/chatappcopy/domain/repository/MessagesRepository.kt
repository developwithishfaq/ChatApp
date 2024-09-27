package com.test.chatappcopy.domain.repository

import com.test.chatappcopy.data.model.chat.ChatMessage
import com.test.chatappcopy.data.network.NetworkResponse
import kotlinx.coroutines.flow.Flow

interface MessagesRepository {

    suspend fun sendMessage(chatId:String,msg: ChatMessage) : NetworkResponse<Boolean>
    fun getAllChatsLiveById(chatId: String): Flow<NetworkResponse<List<ChatMessage>>>
}