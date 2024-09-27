package com.test.chatappcopy.data.repository

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.test.chatappcopy.data.model.chat.ChatMembers
import com.test.chatappcopy.data.model.chat.ChatModel
import com.test.chatappcopy.data.network.NetworkResponse
import com.test.chatappcopy.domain.repository.ChatsRepository
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.tasks.await
import java.util.UUID
import kotlin.coroutines.resume

class ChatsRepositoryImpl : ChatsRepository {
    override suspend fun getAllChats(): NetworkResponse<List<ChatModel>> {
        val results = FirebaseFirestore.getInstance()
            .collection("Chat")
            .get()
            .await()
        val chatsList = results.documents.mapNotNull {
            it.toObject(ChatModel::class.java)
        }
        return NetworkResponse.Success(chatsList)
    }

    override suspend fun createNewChatWith(
        otherUserId: String,
        otherUserName: String
    ): NetworkResponse<String> {
        val chatId = UUID.randomUUID().toString()
        val currentUser = FirebaseAuth.getInstance().currentUser
        val chatModel = ChatModel(
            id = chatId,
            desc = "",
            title = "",
            adminId = currentUser?.uid ?: "",
            creationTime = System.currentTimeMillis(),
            member = listOf(
                ChatMembers(
                    memberId = currentUser?.uid ?: "",
                    memberName = currentUser?.displayName ?: ""
                ),
                ChatMembers(
                    memberId = otherUserId,
                    memberName = otherUserName
                )
            ),
            type = 0,
        )
        return suspendCancellableCoroutine<NetworkResponse<String>> { cor ->
            FirebaseFirestore.getInstance().collection("Chat").document(chatId)
                .set(chatModel)
                .addOnCompleteListener {
                    if (it.isSuccessful) {
                        cor.resume(NetworkResponse.Success(chatId))
                        cor.cancel()
                    } else {
                        cor.resume(NetworkResponse.Failure(it.exception?.message ?: ""))
                        cor.cancel()
                    }
                }
        }
    }

    override fun getAllChatsLive(): Flow<NetworkResponse<List<ChatModel>>> = callbackFlow {
        val query = FirebaseFirestore.getInstance()
            .collection("Chat")
        val listener = query.addSnapshotListener { value, error ->
            if (value != null && error == null) {
                val chatsList = value.documents.mapNotNull {
                    it.toObject(ChatModel::class.java)
                }
                trySend(NetworkResponse.Success(chatsList))
            } else if (error != null) {
                trySend(NetworkResponse.Failure(error.message ?: ""))
            }
        }
        awaitClose {
            listener.remove()
        }
    }

    override suspend fun getChatByChatId(id: String): NetworkResponse<ChatModel?> {
        val data = FirebaseFirestore.getInstance().collection("Chat")
            .document(id)
            .get().await()
        val model = data.toObject(ChatModel::class.java)
        return NetworkResponse.Success(model)
    }

}