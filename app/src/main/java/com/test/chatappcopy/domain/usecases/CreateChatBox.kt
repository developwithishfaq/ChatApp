package com.test.chatappcopy.domain.usecases

import com.test.chatappcopy.data.network.NetworkResponse
import com.test.chatappcopy.domain.repository.ChatsRepository
import com.test.chatappcopy.domain.repository.UsersRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class CreateChatBox(
    private val isHavingChatsExistsUseCase: IsHavingChatsExistsUseCase,
    private val chatsRepository: ChatsRepository,
    private val userRepository: UsersRepository
) {

    suspend operator fun invoke(
        otherUserId: String,
        name: String? = null
    ): NetworkResponse<String> {
        return withContext(Dispatchers.IO) {
            val alreadyHavingChat = isHavingChatsExistsUseCase.invoke(otherUserId)
            if (alreadyHavingChat is NetworkResponse.Success) {
                val chatExist = alreadyHavingChat.data != null
                if (chatExist.not()) {
                    if (name == null) {
                        val userDetail = userRepository.getUserDetailById(otherUserId)
                        if (userDetail is NetworkResponse.Success) {
                            chatsRepository.createNewChatWith(
                                otherUserId = otherUserId,
                                otherUserName = userDetail.data?.name ?: ""
                            )
                        } else {
                            NetworkResponse.Failure("Something Went Wrong")
                        }
                    } else {
                        chatsRepository.createNewChatWith(
                            otherUserId = otherUserId,
                            otherUserName = name
                        )
                    }
                } else {
                    NetworkResponse.Success(alreadyHavingChat.data!!)
                }
            } else {
                NetworkResponse.Failure("Something Went Wrong")
            }
        }
    }
}