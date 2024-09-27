package com.test.chatappcopy.presentation.auth.state

import com.google.firebase.auth.FirebaseUser
import com.test.chatappcopy.data.network.NetworkResponse

enum class AuthType {
    Signup,
    SignIn
}

data class AuthState(
    val authType: AuthType = AuthType.SignIn,
    val email: String = "",
    val password: String = "",
    val name: String = "",
    val loginResponse: NetworkResponse<FirebaseUser> = NetworkResponse.Idle,
    val signUpResponse: NetworkResponse<Boolean> = NetworkResponse.Idle
)
