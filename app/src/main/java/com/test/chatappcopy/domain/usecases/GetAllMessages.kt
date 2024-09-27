package com.test.chatappcopy.domain.usecases

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.test.chatappcopy.data.model.chat.ChatModel
import com.test.chatappcopy.data.network.NetworkResponse
import com.test.chatappcopy.domain.model.MessageUiModel
import com.test.chatappcopy.domain.repository.MessagesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlin.math.log

class GetAllMessages(
    private val msgRepository: MessagesRepository
) {

    operator fun invoke(chatModel: ChatModel): Flow<NetworkResponse<List<MessageUiModel>>> {
        Log.d("cvv", "GetAllMessages invoke: $chatModel")
        return msgRepository.getAllChatsLiveById(chatModel.id).map { response ->
            when (response) {
                is NetworkResponse.Success -> {
                    val data = response.data.map { msgModel ->
                        if (msgModel.senderId != FirebaseAuth.getInstance().uid) {
                            FirebaseFirestore.getInstance()
                                .collection("Chat")
                                .document(chatModel.id)
                                .collection("Messages")
                                .document(msgModel.id)
                                .update("readBy", FieldValue.arrayUnion(FirebaseAuth.getInstance().uid))
                        }
                        MessageUiModel(
                            name = chatModel.member.firstOrNull {
                                it.memberId == msgModel.senderId
                            }?.memberName ?: "Unknown",
                            sentAt = msgModel.sentAt,
                            sentBy = msgModel.senderId,
                            readBy = msgModel.readBy,
                            msg = msgModel.msg
                        )
                    }
                    Log.d("cvv", "Get All msg Usecase:${data.size} ")

                    NetworkResponse.Success(data)
                }

                is NetworkResponse.Failure -> {
                    Log.d("cvv", "Get All msg Usecase: Failure ")
                    NetworkResponse.Failure(response.message)
                }

                NetworkResponse.Idle -> {
                    Log.d("cvv", "Get All msg Usecase: Idle")
                    NetworkResponse.Idle
                }

                NetworkResponse.Loading -> {
                    Log.d("cvv", "Get All msg Usecase: Loading")
                    NetworkResponse.Loading
                }
            }
        }
    }
}