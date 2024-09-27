package com.test.chatappcopy.core.composables

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.test.chatappcopy.R

@Composable
fun MainButton(
    text: String,
    onClick: () -> Unit,
    bgColor: Int = R.color.mainColor,
    textColor: Int = R.color.white,
    contentColor: Int = R.color.white,
    modifier: Modifier = Modifier
) {
    Button(
        onClick = onClick,
        modifier = modifier,
        colors = ButtonDefaults
            .buttonColors(
                containerColor = bgColor.getColor(),
                contentColor = contentColor.getColor(),
                disabledContainerColor = bgColor.getColor(),
                disabledContentColor = contentColor.getColor()
            )
    ) {
        MediumTextView(text = text, color = textColor)
    }
}