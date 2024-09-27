package com.test.chatappcopy.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.test.chatappcopy.data.model.UserModel
import com.test.chatappcopy.data.network.NetworkResponse
import com.test.chatappcopy.domain.repository.UsersRepository
import com.test.chatappcopy.domain.usecases.CreateChatBox
import com.test.chatappcopy.domain.usecases.GetAllChats
import com.test.chatappcopy.domain.usecases.LogoutUseCase
import com.test.chatappcopy.presentation.home.events.HomeScreenEvents
import com.test.chatappcopy.presentation.home.events.OnTimeEventHomeScreen
import com.test.chatappcopy.presentation.home.state.HomeScreenState
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class HomeScreenViewModel(
    private val logoutUseCase: LogoutUseCase,
    private val usersRepository: UsersRepository,
    private val createChatBox: CreateChatBox,
    private val getAllChats: GetAllChats
) : ViewModel() {

    private val _state = MutableStateFlow(HomeScreenState())
    val state = _state.asStateFlow()

    private val _oneTimeEvents = Channel<OnTimeEventHomeScreen>()
    val oneTimeEvents = _oneTimeEvents.receiveAsFlow()

    init {
        viewModelScope.apply {
            launch {
                getAllChats.invoke().collectLatest { chats ->
                    _state.update {
                        it.copy(
                            chatsList = chats
                        )
                    }
                }
            }
            launch {
                usersRepository.getAllUsers().collectLatest { data ->
                    _state.update {
                        it.copy(
                            usersList = data
                        )
                    }
                }
            }
        }
    }

    fun onEvent(event: HomeScreenEvents) {
        when (event) {
            HomeScreenEvents.Logout -> {
                logoutUseCase.invoke()
            }

            HomeScreenEvents.OnAddClick -> {
                _state.update {
                    it.copy(
                        showBottomSheet = true
                    )
                }
            }

            HomeScreenEvents.OnBottomSheetDismiss -> {
                _state.update {
                    it.copy(
                        showBottomSheet = false
                    )
                }
            }

            is HomeScreenEvents.CreateChatClick -> {
                createChat(event.userModel)
            }
        }
    }

    private fun createChat(userModel: UserModel) {
        viewModelScope.launch {
            _state.update {
                it.copy(
                    showLoadingDialog = true,
                    showBottomSheet = false
                )
            }
            val chatCreatedResponse = createChatBox.invoke(userModel.id, userModel.name)
            _state.update {
                it.copy(
                    showLoadingDialog = false
                )
            }
            if (chatCreatedResponse is NetworkResponse.Success) {
                _oneTimeEvents.send(OnTimeEventHomeScreen.MoveToChatsScreen(chatCreatedResponse.data))
            } else {
                _oneTimeEvents.send(OnTimeEventHomeScreen.Toast("Unable To Create Chat"))
            }

        }
    }

}