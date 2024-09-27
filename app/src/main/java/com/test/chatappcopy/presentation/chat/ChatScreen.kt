package com.test.chatappcopy.presentation.chat

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.test.chatappcopy.R
import com.test.chatappcopy.core.composables.MediumTextView
import com.test.chatappcopy.core.composables.NormalTextFiled
import com.test.chatappcopy.core.composables.NormalTopBar
import com.test.chatappcopy.core.composables.SmallTextView
import com.test.chatappcopy.core.composables.VerticalSpace
import com.test.chatappcopy.core.composables.getColor
import com.test.chatappcopy.data.model.chat.ChatModel
import com.test.chatappcopy.data.network.NetworkResponse
import com.test.chatappcopy.domain.model.MessageUiModel
import com.test.chatappcopy.presentation.chat.events.ChatEvent
import org.koin.androidx.compose.koinViewModel

@Composable
fun ChatScreen(
    viewModel: ChatViewModel = koinViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    when (state.chatModel) {
        is NetworkResponse.Failure -> {

        }

        NetworkResponse.Idle -> {

        }

        NetworkResponse.Loading -> {

        }

        is NetworkResponse.Success -> {
            Scaffold(
                modifier = Modifier
                    .fillMaxSize(),
                topBar = {
                    NormalTopBar(
                        text = (state.chatModel as NetworkResponse.Success<ChatModel?>).data?.getActualTitle()
                            ?: ""
                    )
                },
                bottomBar = {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(R.color.almostWhite.getColor(), RoundedCornerShape(10.dp))
                            .padding(horizontal = 5.dp, vertical = 4.dp)
                    ) {
                        NormalTextFiled(
                            modifier = Modifier
                                .fillMaxWidth()
                                .weight(1f)
                                .padding(end = 10.dp),
                            value = state.msgToSend, onValueChange = {
                                viewModel.onEvent(ChatEvent.OnMsgCHanged(it))
                            })
                        IconButton(
                            onClick = {
                                viewModel.onEvent(ChatEvent.SendMsg)
                            }) {
                            Image(Icons.Default.Send, contentDescription = null)

                        }
                    }
                },
                containerColor = R.color.white.getColor()
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(it)
                ) {
                    when (state.msgsList) {
                        is NetworkResponse.Failure -> {

                        }

                        NetworkResponse.Idle -> {

                        }

                        NetworkResponse.Loading -> {

                        }

                        is NetworkResponse.Success -> {
                            val list = (state.msgsList as NetworkResponse.Success<List<MessageUiModel>>).data.sortedBy {
                                it.sentAt
                            }
                            LazyColumn(
                            ) {
                                items(list,) { model ->
                                    MessageItem(model)
                                }
                            }

                        }
                    }
                }
            }
        }
    }
}

@Composable
fun MessageItem(model: MessageUiModel) {
    Column(
        modifier = Modifier
            .padding(horizontal = 8.dp, vertical = 8.dp)
    ) {
        SmallTextView(text = model.name)
        VerticalSpace(2)
        Column(
            modifier = Modifier
                .padding(horizontal = 6.dp)
                .background(R.color.mainColor.getColor(), RoundedCornerShape(12.dp))
                .padding(horizontal = 12.dp, vertical = 4.dp)
        ) {
            MediumTextView(
                text = model.msg,
                modifier = Modifier
                    .defaultMinSize(100.dp),
                color = R.color.white
            )
        }
    }
}
