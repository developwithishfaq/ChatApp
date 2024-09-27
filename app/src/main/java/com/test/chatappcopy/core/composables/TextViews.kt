package com.test.chatappcopy.core.composables

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import com.test.chatappcopy.R


@Composable
fun MediumTextView(
    text: String,
    fontSize: Int = 16,
    color: Int = R.color.black,
    modifier: Modifier = Modifier,
    fontWeight: FontWeight? = null,
    textAlign: TextAlign = TextAlign.Center
) {
    Text(
        modifier = modifier,
        text = text,
        fontSize = fontSize.sp,
        color = color.getColor(),
        fontWeight = fontWeight,
        textAlign = textAlign
    )
}

@Composable
fun MediumTextViewSemiBold(
    text: String,
    fontSize: Int = 16,
    color: Int = R.color.black,
    fontWeight: FontWeight? = FontWeight.SemiBold,
    textAlign: TextAlign = TextAlign.Center,
    modifier: Modifier = Modifier,
) {
    Text(
        modifier = modifier,
        text = text,
        fontSize = fontSize.sp,
        color = color.getColor(),
        fontWeight = fontWeight,
        textAlign = textAlign
    )
}

@Composable
fun SmallTextView(
    text: String,
    fontSize: Int = 12,
    color: Int = R.color.black,
    modifier: Modifier = Modifier,
    fontWeight: FontWeight? = null,
    textAlign: TextAlign = TextAlign.Center
) {
    Text(
        modifier = modifier,
        text = text,
        fontSize = fontSize.sp,
        color = color.getColor(),
        fontWeight = fontWeight,
        textAlign = textAlign
    )
}

@Composable
fun LargeTextView(
    text: String,
    fontSize: Int = 24,
    color: Int = R.color.black,
    modifier: Modifier = Modifier,
    fontWeight: FontWeight? = FontWeight.Bold,
    textAlign: TextAlign = TextAlign.Center
) {
    Text(
        modifier = modifier,
        text = text,
        fontSize = fontSize.sp,
        color = color.getColor(),
        fontWeight = fontWeight,
        textAlign = textAlign
    )
}