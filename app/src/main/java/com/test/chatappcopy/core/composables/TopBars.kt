package com.test.chatappcopy.core.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.test.chatappcopy.R


@Composable
fun NormalTopBar(
    text: String = "We Chat",
    modifier: Modifier = Modifier,
    onBackClick: (() -> Unit)? = null,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(R.color.almostWhite.getColor())
            .padding(vertical = 10.dp, horizontal = 6.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        onBackClick?.let {
            Image(
                Icons.Default.ArrowBack,
                contentDescription = null,
                modifier = Modifier
                    .size(22.dp)
                    .clickable {
                        onBackClick.invoke()
                    }
            )
        }
        Spacer(modifier = Modifier.width(10.dp))
        MediumTextView(text = text)
    }
}