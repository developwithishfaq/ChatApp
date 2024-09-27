package com.test.chatappcopy.domain.usecases

import com.google.firebase.auth.FirebaseUser
import com.test.chatappcopy.data.network.NetworkResponse
import com.test.chatappcopy.data.util.MyPrefs
import com.test.chatappcopy.domain.repository.AuthRepository

class LoginUseCase(
    private val repository: AuthRepository,
    private val prefs: MyPrefs
) {
    suspend operator fun invoke(email: String, password: String): NetworkResponse<FirebaseUser> {
        val result = repository.loginUser(email, password)
        if (result is NetworkResponse.Success) {
            prefs.isUserLoggedIn = true
        }
        return result
    }
}