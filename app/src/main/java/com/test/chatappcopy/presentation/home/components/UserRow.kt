package com.test.chatappcopy.presentation.home.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.test.chatappcopy.R
import com.test.chatappcopy.core.composables.HorizontalSpace
import com.test.chatappcopy.core.composables.SmallTextView
import com.test.chatappcopy.core.composables.getColor
import com.test.chatappcopy.data.model.UserModel

@Composable
fun UserRow(userModel: UserModel, onAddClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 10.dp, horizontal = 5.dp),
        colors = CardDefaults.cardColors(
            contentColor = R.color.black.getColor(),
            containerColor = R.color.white.getColor()
        ),
        elevation = CardDefaults.cardElevation(0.dp),
        shape = RoundedCornerShape(5.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(R.color.white.getColor())
                .padding(vertical = 10.dp, horizontal = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                modifier = Modifier
                    .weight(1f),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(60.dp)
                        .background(R.color.almostWhite.getColor(), RoundedCornerShape(20.dp)),
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        Icons.Default.Person,
                        contentDescription = null,
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(15.dp)
                    )
                }
                HorizontalSpace()
                SmallTextView(
                    text = userModel.name,
                    fontSize = 14
                )
            }
            Image(
                Icons.Default.Add, contentDescription = null,
                modifier = Modifier
                    .padding(horizontal = 10.dp)
                    .size(22.dp)
                    .clickable {
                        onAddClick.invoke()
                    }
            )
        }
    }
}