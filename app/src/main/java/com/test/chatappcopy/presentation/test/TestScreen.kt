package com.test.chatappcopy.presentation.test

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.koin.androidx.compose.koinViewModel

@Composable
fun TestScreen(
    viewModel: TestViewModel = koinViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        var chatText by remember {
            mutableStateOf("")
        }
        LazyColumn(
            modifier = Modifier
                .weight(1f)
        ) {

        }
        Row {
            TextField(
                modifier = Modifier
                    .weight(1f),
                value = chatText,
                onValueChange = {
                    chatText = it
                }
            )
            Button(
                onClick = {
                    viewModel.sendMessage(chatText)
                }
            ) {
                Text(text = "Send")
            }
        }
    }

}