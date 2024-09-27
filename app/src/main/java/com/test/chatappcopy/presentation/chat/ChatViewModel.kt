package com.test.chatappcopy.presentation.chat

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.test.chatappcopy.data.model.chat.ChatMessage
import com.test.chatappcopy.data.model.chat.ChatModel
import com.test.chatappcopy.data.network.NetworkResponse
import com.test.chatappcopy.domain.repository.ChatsRepository
import com.test.chatappcopy.domain.repository.MessagesRepository
import com.test.chatappcopy.domain.usecases.GetAllMessages
import com.test.chatappcopy.presentation.chat.events.ChatEvent
import com.test.chatappcopy.presentation.chat.state.ChatsState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.UUID

class ChatViewModel(
    private val savedStateHandle: SavedStateHandle,
    private val chatsRepository: ChatsRepository,
    private val getAllMessages: GetAllMessages,
    private val msgRepository: MessagesRepository
) : ViewModel() {
    private val _state = MutableStateFlow(ChatsState())
    val state = _state.asStateFlow()

    init {
        val chatId = savedStateHandle.get<String?>("chatId")
        if (chatId != null) {
            viewModelScope.launch {
                val chatModel = chatsRepository.getChatByChatId(chatId)
                _state.update {
                    it.copy(
                        chatModel = chatModel,
                        chatId = chatId
                    )
                }
                if (chatModel is NetworkResponse.Success) {
                    if (chatModel.data != null) {
                        listenToMsgs(chatModel.data)
                    }
                }
            }
        }
    }

    fun onEvent(event: ChatEvent) {
        when (event) {
            is ChatEvent.OnMsgCHanged -> {
                _state.update {
                    it.copy(
                        msgToSend = event.text
                    )
                }
            }

            ChatEvent.SendMsg -> {
                viewModelScope.launch {
                    msgRepository.sendMessage(
                        chatId = state.value.chatId,
                        msg = ChatMessage(
                            id = UUID.randomUUID().toString(),
                            msg = state.value.msgToSend,
                            senderId = FirebaseAuth.getInstance().uid ?: "",
                            sentAt = System.currentTimeMillis(),
                            deliveredTo = listOf(),
                            readBy = listOf()
                        )
                    )
                    _state.update {
                        it.copy(
                            msgToSend = ""
                        )
                    }
                }
            }
        }
    }

    private fun listenToMsgs(model: ChatModel) {
        Log.d("cvv", "listenToMsgs: Called ")
        viewModelScope.launch {
            getAllMessages(chatModel = model).collectLatest { msgs ->
                Log.d("cvv", "listenToMsgs Msgs Size:$msgs ")
                _state.update {
                    it.copy(
                        msgsList = msgs
                    )
                }
            }
        }
    }

}