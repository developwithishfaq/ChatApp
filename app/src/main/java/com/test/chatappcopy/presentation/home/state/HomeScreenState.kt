package com.test.chatappcopy.presentation.home.state

import com.test.chatappcopy.data.model.UserModel
import com.test.chatappcopy.data.model.chat.ChatModel
import com.test.chatappcopy.data.network.NetworkResponse

data class HomeScreenState(
    val showBottomSheet: Boolean = false,
    val showLoadingDialog: Boolean = false,
    val usersList: NetworkResponse<List<UserModel>> = NetworkResponse.Loading,
    val chatsList: NetworkResponse<List<ChatModel>> = NetworkResponse.Loading,
)
