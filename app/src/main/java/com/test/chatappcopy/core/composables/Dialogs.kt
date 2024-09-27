package com.test.chatappcopy.core.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.test.chatappcopy.R

@Composable
fun LoadingDialog(text: String = "Please wait") {
    Dialog(
        onDismissRequest = {
        },
        properties = DialogProperties(
            usePlatformDefaultWidth = false,
            dismissOnClickOutside = false,
            dismissOnBackPress = false
        )
    ) {
        Box(
            modifier = Modifier
                .height(100.dp)
                .width(
                    120.dp
                )
                .background(R.color.white.getColor()),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator(color = R.color.mainColor.getColor())
        }

    }
}