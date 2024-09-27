package com.test.chatappcopy.presentation.home

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.test.chatappcopy.R
import com.test.chatappcopy.core.composables.LoadingDialog
import com.test.chatappcopy.core.composables.MediumTextView
import com.test.chatappcopy.core.composables.SmallTextView
import com.test.chatappcopy.core.composables.getColor
import com.test.chatappcopy.data.model.UserModel
import com.test.chatappcopy.data.model.chat.ChatModel
import com.test.chatappcopy.data.network.NetworkResponse
import com.test.chatappcopy.presentation.LocalNavController
import com.test.chatappcopy.presentation.home.components.ChatsRow
import com.test.chatappcopy.presentation.home.components.UserRow
import com.test.chatappcopy.presentation.home.events.HomeScreenEvents
import com.test.chatappcopy.presentation.home.events.OnTimeEventHomeScreen
import com.test.chatappcopy.presentation.navigation.Routes
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    viewModel: HomeScreenViewModel = koinViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val oneTimeEvents by viewModel.oneTimeEvents.collectAsStateWithLifecycle(OnTimeEventHomeScreen.Idle)
    val context = LocalContext.current

    LaunchedEffect(oneTimeEvents) {
        when (oneTimeEvents) {
            OnTimeEventHomeScreen.Idle -> {

            }

            is OnTimeEventHomeScreen.MoveToChatsScreen -> {
                Toast.makeText(
                    context,
                    "Chat Created",
                    Toast.LENGTH_SHORT
                ).show()
            }

            is OnTimeEventHomeScreen.Toast -> {
                Toast.makeText(
                    context,
                    (oneTimeEvents as OnTimeEventHomeScreen.Toast).msg,
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    if (state.showLoadingDialog) {
        LoadingDialog()
    }
    if (state.showBottomSheet) {
        ModalBottomSheet(
            onDismissRequest = {
                viewModel.onEvent(HomeScreenEvents.OnBottomSheetDismiss)
            },
            sheetState = rememberModalBottomSheetState()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.5f)
            ) {
                when (state.usersList) {
                    is NetworkResponse.Failure -> {
                        Box(
                            modifier = Modifier
                                .fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            SmallTextView(
                                text = (state.usersList as NetworkResponse.Failure).message,
                                color = R.color.red
                            )
                        }
                    }

                    NetworkResponse.Idle -> {

                    }

                    NetworkResponse.Loading -> {
                        Box(
                            modifier = Modifier
                                .fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator(color = R.color.mainColor.getColor())
                        }
                    }

                    is NetworkResponse.Success -> {
                        val usersList =
                            (state.usersList as NetworkResponse.Success<List<UserModel>>).data
                        if (usersList.isNotEmpty()) {
                            LazyColumn {
                                items((state.usersList as NetworkResponse.Success<List<UserModel>>).data) { userModel ->
                                    UserRow(userModel) {
                                        viewModel.onEvent(HomeScreenEvents.CreateChatClick(userModel))
                                    }
                                }
                            }
                        } else {
                            Box(
                                modifier = Modifier
                                    .fillMaxSize(),
                                contentAlignment = Alignment.Center
                            ) {
                                SmallTextView(text = "No Users Found")
                            }
                        }
                    }
                }
            }
        }
    }
    Scaffold(
        topBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(R.color.almostWhite.getColor())
                    .padding(horizontal = 8.dp, vertical = 12.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                MediumTextView(text = "We Chat")
                Image(
                    painterResource(id = R.drawable.ic_logout),
                    contentDescription = null,
                    modifier = Modifier
                        .size(22.dp)
                        .clickable {

                        }
                )
            }
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    viewModel.onEvent(HomeScreenEvents.OnAddClick)
                },
                containerColor = R.color.mainColor.getColor(),
                contentColor = R.color.white.getColor()
            ) {
                Image(
                    Icons.Default.Add,
                    contentDescription = null,
                    modifier = Modifier.size(22.dp),
                    colorFilter = ColorFilter.tint(R.color.white.getColor())
                )
            }
        },
        containerColor = R.color.white.getColor()
    ) {
        Column(
            modifier = Modifier
                .padding(it)
                .fillMaxSize()
        ) {
            when (state.chatsList) {
                is NetworkResponse.Failure -> {

                }

                NetworkResponse.Idle -> {

                }

                NetworkResponse.Loading -> {
                    Box(
                        modifier = Modifier
                            .fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator(
                            color = R.color.mainColor.getColor()
                        )
                    }
                }

                is NetworkResponse.Success -> {
                    val navController = LocalNavController.current
                    LazyColumn() {
                        items((state.chatsList as NetworkResponse.Success<List<ChatModel>>).data) { model ->
                            ChatsRow(model) {
                                navController.navigate(
                                    Routes.ChatScreen.name + "/${model.id}"
                                )
                            }
                        }
                    }
                }
            }
        }
    }

}

