package com.test.chatappcopy.domain.repository

import com.test.chatappcopy.data.model.UserModel
import com.test.chatappcopy.data.network.NetworkResponse
import kotlinx.coroutines.flow.Flow

interface UsersRepository {
    fun getAllUsers(): Flow<NetworkResponse<List<UserModel>>>
    suspend fun getUserDetailById(userId: String): NetworkResponse<UserModel?>
}