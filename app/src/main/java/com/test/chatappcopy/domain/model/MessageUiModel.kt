package com.test.chatappcopy.domain.model

data class MessageUiModel(
    val name: String,
    val sentBy: String,
    val msg: String,
    val sentAt: Long,
    val readBy: List<String>
)
