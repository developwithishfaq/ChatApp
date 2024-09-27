package com.test.chatappcopy.domain.usecases

import com.test.chatappcopy.data.network.NetworkResponse
import com.test.chatappcopy.domain.repository.AuthRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext

class SignUpUseCase(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(
        name: String,
        email: String,
        password: String,
    ): NetworkResponse<Boolean> {
        return withContext(Dispatchers.IO) {
            val authResult = authRepository.createUserAuth(email, password)
            if (authResult is NetworkResponse.Success) {
                val user = authResult.data
                val dbJob = async {
                    authRepository.createUserInDb(
                        uid = user.uid,
                        name = name,
                        email = email,
                        password = password
                    )
                }
                val displayNameJob = async {
                    authRepository.setDisplayName(name, user.uid)
                }
                val resultOne = dbJob.await()
                val resultTwo = displayNameJob.await()
                NetworkResponse.Success(resultOne is NetworkResponse.Success && resultTwo is NetworkResponse.Success)
            } else {
                NetworkResponse.Failure("Auth Not Success")
            }
        }

    }

}