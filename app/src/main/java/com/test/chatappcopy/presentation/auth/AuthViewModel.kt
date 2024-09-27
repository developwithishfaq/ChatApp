package com.test.chatappcopy.presentation.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.test.chatappcopy.core.constants.isOurCredentialsOk
import com.test.chatappcopy.core.constants.isValidText
import com.test.chatappcopy.data.network.NetworkResponse
import com.test.chatappcopy.domain.usecases.IsUserLoggedIn
import com.test.chatappcopy.domain.usecases.LoginUseCase
import com.test.chatappcopy.domain.usecases.SignUpUseCase
import com.test.chatappcopy.presentation.auth.event.AuthEvents
import com.test.chatappcopy.presentation.auth.event.AuthOneTimeEvents
import com.test.chatappcopy.presentation.auth.state.AuthState
import com.test.chatappcopy.presentation.auth.state.AuthType
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class AuthViewModel(
    private val loginUseCase: LoginUseCase,
    private val signUpUseCase: SignUpUseCase,
    private val isUserLoggedIn: IsUserLoggedIn
) : ViewModel() {

    private val _state = MutableStateFlow(AuthState())
    val state = _state.asStateFlow()

    private val _oneTimeEvents = Channel<AuthOneTimeEvents>()
    val oneTimeEvents = _oneTimeEvents.receiveAsFlow()


    init {
        moveToNextScreen()
    }


    fun onEvent(event: AuthEvents) {
        when (event) {
            is AuthEvents.EmailChanged -> {
                _state.update {
                    it.copy(
                        email = event.text
                    )
                }
            }

            is AuthEvents.NameChanged -> {
                _state.update {
                    it.copy(
                        name = event.text
                    )
                }
            }

            AuthEvents.OnAuthBtnClick -> {
                when (state.value.authType) {
                    AuthType.Signup -> {
                        createNewUser()
                    }

                    AuthType.SignIn -> {
                        signInUser()
                    }
                }
            }

            AuthEvents.OnAuthSwitch -> {
                val newAuthType = if (state.value.authType == AuthType.Signup) {
                    AuthType.SignIn
                } else {
                    AuthType.Signup
                }
                _state.update {
                    it.copy(
                        authType = newAuthType
                    )
                }
            }

            is AuthEvents.PasswordChanged -> {
                _state.update {
                    it.copy(
                        password = event.text
                    )
                }
            }
        }
    }

    private fun signInUser() {
        if (isOurCredentialsOk(state.value.email, state.value.password)) {
            viewModelScope.launch {
                _state.update {
                    it.copy(
                        loginResponse = NetworkResponse.Loading
                    )
                }
                val response = loginUseCase(state.value.email, state.value.password)
                if (response is NetworkResponse.Success) {
                    _oneTimeEvents.send(AuthOneTimeEvents.LoginSuccess("Login Done"))
                    moveToNextScreen()
                } else if (response is NetworkResponse.Failure) {
                    _oneTimeEvents.send(AuthOneTimeEvents.Failure(response.message))
                }
                _state.update {
                    it.copy(
                        loginResponse = response
                    )
                }
            }

        }
    }

    private fun moveToNextScreen() {
        if (isUserLoggedIn.invoke()) {
            viewModelScope.launch {
                _oneTimeEvents.send(AuthOneTimeEvents.MoveToNextScreen)
            }
        }
    }

    private fun createNewUser() {
        if (isOurCredentialsOk(
                state.value.email,
                state.value.password
            ) && state.value.name.isValidText()
        ) {
            viewModelScope.launch {
                _state.update {
                    it.copy(
                        signUpResponse = NetworkResponse.Loading
                    )
                }
                val response =
                    signUpUseCase.invoke(state.value.name, state.value.email, state.value.password)

                if (response is NetworkResponse.Success) {
                    _oneTimeEvents.send(AuthOneTimeEvents.SignUpSuccess("Signup Success"))
                } else if (response is NetworkResponse.Failure) {
                    _oneTimeEvents.send(AuthOneTimeEvents.Failure(response.message))
                }
                _state.update {
                    it.copy(
                        signUpResponse = response
                    )
                }
            }
        }
    }
}