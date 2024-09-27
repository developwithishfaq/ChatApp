package com.test.chatappcopy.data.model

data class UserModel(
    val name: String = "",
    val id: String = "",
    val email: String = "",
    val lastTimeStamp: Long = System.currentTimeMillis(),
    val privateUser: Boolean = false,
    val typingTo: String = "",
    val intoChat: String = ""
) {
}
