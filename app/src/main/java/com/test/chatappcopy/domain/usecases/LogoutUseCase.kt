package com.test.chatappcopy.domain.usecases

import com.google.firebase.auth.FirebaseUser
import com.test.chatappcopy.data.network.NetworkResponse
import com.test.chatappcopy.data.util.MyPrefs
import com.test.chatappcopy.domain.repository.AuthRepository

class LogoutUseCase(
    private val repository: AuthRepository,
    private val prefs: MyPrefs
) {
    operator fun invoke() {
        prefs.isUserLoggedIn = false
        repository.logOut()
    }
}