package com.test.chatappcopy.core.composables

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp


@Composable
fun HorizontalSpace(space: Int = 10) {
    Spacer(modifier = Modifier.width(space.dp))
}

@Composable
fun VerticalSpace(space: Int = 10) {
    Spacer(modifier = Modifier.height(space.dp))
}

@Composable
fun Int.getColor() = colorResource(id = this)