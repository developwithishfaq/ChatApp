package com.test.chatappcopy.data.model.chat

import com.google.firebase.auth.FirebaseAuth

data class ChatModel(
    val id: String = "",
    val desc: String = "",
    val title: String = "",
    val adminId: String = "",
    val creationTime: Long = System.currentTimeMillis(),
    val member: List<ChatMembers> = listOf(),
    val type: Int = 0
) {
    fun getActualTitle(): String {
        return if (type == 0) {
            val model = member.firstOrNull {
                it.memberId != FirebaseAuth.getInstance().uid
            }
            model?.memberName ?: "Unknown"
        } else {
            title
        }
    }
}

data class ChatMembers(
    val memberId: String = "",
    val memberName: String = "",
    val status: String = "Active"
)

data class ChatMessage(
    val id: String = "",
    val msg: String = "",
    val senderId: String = "",
    val sentAt: Long =0,
    val deliveredTo: List<String> = listOf(),
    val readBy: List<String> = listOf()
)

