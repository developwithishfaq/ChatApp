package com.test.chatappcopy.domain.usecases

import com.google.firebase.auth.FirebaseAuth
import com.test.chatappcopy.data.model.chat.ChatModel
import com.test.chatappcopy.data.network.NetworkResponse
import com.test.chatappcopy.domain.repository.ChatsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetAllChats(
    private val chatsRepository: ChatsRepository
) {

    operator fun invoke(): Flow<NetworkResponse<List<ChatModel>>> {
        return chatsRepository.getAllChatsLive().map {
            if (it is NetworkResponse.Success) {
                val newData = it.data.map { chatModel ->
                    if (chatModel.type == 0) {
                        val members = chatModel.member.filter {
                            it.memberId != FirebaseAuth.getInstance().currentUser?.uid
                        }
                        chatModel.copy(
                            title = members.getOrNull(0)?.memberName ?: "Unknown"
                        )
                    } else {
                        chatModel
                    }
                }
                NetworkResponse.Success(newData)
            } else {
                it
            }
        }
    }
}