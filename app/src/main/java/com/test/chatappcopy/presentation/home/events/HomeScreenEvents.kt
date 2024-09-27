package com.test.chatappcopy.presentation.home.events

import com.test.chatappcopy.data.model.UserModel

sealed class HomeScreenEvents {
    data object Logout : HomeScreenEvents()
    data object OnAddClick : HomeScreenEvents()
    data class CreateChatClick(val userModel: UserModel) : HomeScreenEvents()
    data object OnBottomSheetDismiss : HomeScreenEvents()
}