package com.example.monkmode.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun PremiumFAB(
    modifier: Modifier = Modifier,
    onClick:()->Unit

){

    Row(

        modifier = modifier
            .clip(RoundedCornerShape(50.dp))
            .background(

                brush = Brush.horizontalGradient(

                    colors = listOf(

                        Color(0xFF7C4DFF),

                        Color(0xFF5B3FD6)

                    )

                )

            )
            .clickable {

                onClick()

            }
            .padding(
                horizontal = 22.dp,
                vertical = 14.dp
            ),

        verticalAlignment = Alignment.CenterVertically

    ){

        Icon(

            imageVector = Icons.Default.Add,

            contentDescription = null,

            tint = Color.White

        )

        Spacer(
            modifier = Modifier.width(8.dp)
        )

        Text(

            text = "Add Habit",

            color = Color.White,

            fontWeight = FontWeight.Bold,

            fontSize = 16.sp

        )

    }

}