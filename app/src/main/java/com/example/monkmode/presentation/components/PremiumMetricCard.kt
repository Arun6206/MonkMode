package com.example.monkmode.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun PremiumMetricCard(

    title: String,

    value: String,

    subtitle: String,

    icon: ImageVector,

    iconColor: Color,

    modifier: Modifier = Modifier

) {

    Card(

        modifier = modifier,

        shape = RoundedCornerShape(24.dp),

        elevation = CardDefaults.cardElevation(
            defaultElevation = 8.dp
        ),

        colors = CardDefaults.cardColors(
            containerColor = Color.Transparent
        )

    ) {

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .height(170.dp)
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            Color(0xFF1B1B2F),
                            Color(0xFF23233B)
                        )
                    )
                )
                .padding(18.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {

            Box(

                modifier = Modifier
                    .size(48.dp)
                    .background(
                        iconColor.copy(alpha = 0.18f),
                        CircleShape
                    ),

                contentAlignment = Alignment.Center

            ) {

                Icon(

                    imageVector = icon,

                    contentDescription = null,

                    tint = iconColor

                )

            }

            Spacer(
                modifier = Modifier.height(18.dp)
            )

            Text(

                text = title,

                color = Color(0xFFB5B5C3),

                fontSize = 14.sp

            )

            Spacer(
                modifier = Modifier.height(6.dp)
            )

            Text(

                text = value,

                color = Color.White,

                fontWeight = FontWeight.Bold,

                fontSize = 28.sp

            )

            Spacer(
                modifier = Modifier.height(4.dp)
            )

            Text(

                text = subtitle,

                color = Color.Gray,

                style = MaterialTheme.typography.bodySmall

            )

        }

    }

}