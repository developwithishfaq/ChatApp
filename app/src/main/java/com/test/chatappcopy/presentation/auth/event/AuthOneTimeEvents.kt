package com.test.chatappcopy.presentation.auth.event

sealed class AuthOneTimeEvents {
    data object Idle : AuthOneTimeEvents()
    data object MoveToNextScreen : AuthOneTimeEvents()
    data class LoginSuccess(val msg: String) : AuthOneTimeEvents()
    data class SignUpSuccess(val msg: String) : AuthOneTimeEvents()
    data class Failure(val msg: String) : AuthOneTimeEvents()
}