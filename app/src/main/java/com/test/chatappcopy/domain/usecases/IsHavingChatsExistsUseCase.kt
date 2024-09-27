package com.test.chatappcopy.domain.usecases

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.test.chatappcopy.data.network.NetworkResponse
import com.test.chatappcopy.domain.repository.ChatsRepository

class IsHavingChatsExistsUseCase(
    private val chatsRepository: ChatsRepository
) {

    suspend operator fun invoke(otherUserId: String): NetworkResponse<String?> {
        return when (val allChatsList = chatsRepository.getAllChats()) {
            is NetworkResponse.Success -> {
                val exist = allChatsList.data.filter { model ->
                    val membersList = model.member.map { it.memberId }
                    (membersList.contains(otherUserId) &&
                            membersList.contains(FirebaseAuth.getInstance().currentUser?.uid)
                            )
                }
                Log.d("cvv", "Is Chat Exist Size: ${exist.size}")
                Log.d(
                    "cvv", "Data =${
                        allChatsList.data.map {model->
                            val members = model.member.map { it.memberId }
                            members
                        }
                    }"
                )
                if (exist.isNotEmpty()) {
                    NetworkResponse.Success(exist[0].id)
                } else {
                    NetworkResponse.Success(null)
                }
            }

            is NetworkResponse.Failure -> {
                NetworkResponse.Failure(allChatsList.message)
            }

            NetworkResponse.Idle -> {
                NetworkResponse.Failure("")
            }

            NetworkResponse.Loading -> {
                NetworkResponse.Failure("")
            }
        }
    }
}