package com.test.chatappcopy.presentation.auth

import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.test.chatappcopy.R
import com.test.chatappcopy.core.composables.LargeTextView
import com.test.chatappcopy.core.composables.LoadingDialog
import com.test.chatappcopy.core.composables.MainButton
import com.test.chatappcopy.core.composables.MediumTextView
import com.test.chatappcopy.core.composables.NormalTextFiled
import com.test.chatappcopy.core.composables.SmallTextView
import com.test.chatappcopy.core.composables.VerticalSpace
import com.test.chatappcopy.core.composables.getColor
import com.test.chatappcopy.data.network.NetworkResponse
import com.test.chatappcopy.presentation.LocalNavController
import com.test.chatappcopy.presentation.auth.event.AuthEvents
import com.test.chatappcopy.presentation.auth.event.AuthOneTimeEvents
import com.test.chatappcopy.presentation.auth.state.AuthType
import com.test.chatappcopy.presentation.navigation.Routes
import org.koin.androidx.compose.koinViewModel

@Composable
fun AuthenticationScreen(
    viewModel: AuthViewModel = koinViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val oneTimeEvents by viewModel.oneTimeEvents.collectAsStateWithLifecycle(AuthOneTimeEvents.Idle)

    val context = LocalContext.current

    val navController = LocalNavController.current

    if (oneTimeEvents is AuthOneTimeEvents.LoginSuccess) {
        Toast.makeText(context, "Login Success", Toast.LENGTH_SHORT).show()
    } else if (oneTimeEvents is AuthOneTimeEvents.SignUpSuccess) {
        Toast.makeText(context, "Signup Success", Toast.LENGTH_SHORT).show()
    } else if (oneTimeEvents is AuthOneTimeEvents.Failure) {
        Toast.makeText(
            context,
            "Login Failed, reason=${(oneTimeEvents as AuthOneTimeEvents.Failure).msg}",
            Toast.LENGTH_SHORT
        ).show()
    } else if (oneTimeEvents is AuthOneTimeEvents.MoveToNextScreen) {
        navController.navigate(Routes.HomeScreen.name)
    }

    if (
        state.loginResponse is NetworkResponse.Loading ||
        state.signUpResponse is NetworkResponse.Loading
    ) {
        LoadingDialog()
    }

    Scaffold(
        bottomBar = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 30.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                val texts = when (state.authType) {
                    AuthType.Signup -> {
                        Pair(
                            "Already have an account?",
                            "Login"
                        )
                    }

                    AuthType.SignIn -> {
                        Pair(
                            "New To We Chat?",
                            "SignUp"
                        )
                    }
                }
                SmallTextView(text = texts.first)
                VerticalSpace(6)
                MediumTextView(text = texts.second,
                    modifier = Modifier
                        .clickable {
                            viewModel.onEvent(AuthEvents.OnAuthSwitch)
                        })
            }
        },
        topBar = {
//            NormalTopBar()
        },
        containerColor = R.color.white.getColor()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
                .padding(horizontal = 10.dp, vertical = 20.dp)
        ) {
            VerticalSpace(40)
            LargeTextView(text = "We Chat")
            VerticalSpace(45)
            Column(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                AnimatedVisibility(visible = state.authType == AuthType.Signup) {
                    Column {
                        NormalTextFiled(
                            value = state.name,
                            hint = "Name",
                            onValueChange = {
                                viewModel.onEvent(AuthEvents.NameChanged(it))
                            })
                        VerticalSpace()
                    }
                }
                NormalTextFiled(value = state.email,
                    hint = "Email", onValueChange = {
                        viewModel.onEvent(AuthEvents.EmailChanged(it))
                    })
                VerticalSpace()
                NormalTextFiled(value = state.password,
                    hint = "Password", onValueChange = {
                        viewModel.onEvent(AuthEvents.PasswordChanged(it))
                    })
            }
            VerticalSpace()
            MainButton(
                text = if (state.authType == AuthType.SignIn) {
                    "Login"
                } else {
                    "SignUp"
                },
                modifier = Modifier
                    .align(Alignment.End),
                onClick = {
                    viewModel.onEvent(AuthEvents.OnAuthBtnClick)
                }
            )
        }
    }


}