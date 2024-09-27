package com.test.chatappcopy.domain.repository

import com.google.firebase.auth.FirebaseUser
import com.test.chatappcopy.data.network.NetworkResponse

interface AuthRepository {
    suspend fun loginUser(email: String, password: String): NetworkResponse<FirebaseUser>
    suspend fun createUserAuth(email: String, password: String): NetworkResponse<FirebaseUser>
    suspend fun createUserInDb(
        uid: String,
        name: String,
        email: String,
        password: String
    ): NetworkResponse<Boolean>
    fun logOut()
    suspend fun setDisplayName(name: String, uid: String): NetworkResponse<Boolean>
}