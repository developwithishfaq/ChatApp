package com.test.chatappcopy.domain.model

data class ChatModel(
    val chatId: String,
    val desc: String,
    val chatType: String,
    val adminId: String,
    val members: List<ChatMember>,
    val messages: List<ChatMessage>
)

data class ChatMessage(
    val message: String,
    val readBy: List<String> = listOf(),
    val messageId: String,
    val senderId: String,
    val deliveredTo: List<String> = listOf()
)

data class ChatMember(
    val memberId: String,
    val name: String,
    val status: String
)