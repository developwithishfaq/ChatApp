package com.test.chatappcopy.presentation.auth.event

sealed class AuthEvents {

    data class NameChanged(val text: String) : AuthEvents()
    data class EmailChanged(val text: String) : AuthEvents()
    data class PasswordChanged(val text: String) : AuthEvents()
    data object OnAuthBtnClick : AuthEvents()
    data object OnAuthSwitch : AuthEvents()

}