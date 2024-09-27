package com.test.chatappcopy.data.repository

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.test.chatappcopy.data.model.chat.ChatMessage
import com.test.chatappcopy.data.network.NetworkResponse
import com.test.chatappcopy.domain.repository.MessagesRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume

class MessagesRepositoryImpl : MessagesRepository {
    @OptIn(ExperimentalCoroutinesApi::class)
    override suspend fun sendMessage(chatId: String, msg: ChatMessage): NetworkResponse<Boolean> {
        return suspendCancellableCoroutine { cor ->
            FirebaseFirestore.getInstance()
                .collection("Chat")
                .document(chatId)
                .collection("Messages")
                .document(msg.id)
                .set(msg)
                .addOnCompleteListener {
                    if (it.isSuccessful) {
                        cor.resume(NetworkResponse.Success(true))
                        cor.cancel()
                    } else {
                        cor.resume(NetworkResponse.Failure(it.exception?.message ?: ""))
                        cor.cancel()
                    }
                }
        }
    }

    override fun getAllChatsLiveById(chatId: String): Flow<NetworkResponse<List<ChatMessage>>> =
        callbackFlow {
            val query = FirebaseFirestore.getInstance()
                .collection("Chat")
                .document(chatId)
                .collection("Messages")

            val listener = query.addSnapshotListener { value, error ->
                if (error != null) {
                    NetworkResponse.Failure(error.message ?: "")
                } else if (value != null) {
                    val msgs = value.documents.mapNotNull {
                        it.toObject(ChatMessage::class.java)
                    }
                    trySend(NetworkResponse.Success(msgs))
                } else {
                    trySend(NetworkResponse.Failure("Something went wrong"))
                }
            }
            awaitClose {
                Log.e("cvv", "getAllChatsLiveById: await close")
                listener.remove()
            }
        }
}