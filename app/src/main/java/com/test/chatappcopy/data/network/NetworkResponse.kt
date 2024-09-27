package com.test.chatappcopy.data.network

sealed class NetworkResponse<out T> {
    data object Idle : NetworkResponse<Nothing>()
    data object Loading : NetworkResponse<Nothing>()
    data class Success<out T>(val data: T) : NetworkResponse<T>()
    data class Failure(val message: String, val throwable: Throwable? = null) : NetworkResponse<Nothing>()
}
