package com.example.monkmode.presentation.achievements

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Lock
import androidx.compose.material.icons.rounded.Verified
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

// REMOVED the duplicate data class declaration from here to fix your compiler error!

@Composable
fun AchievementCard(
    achievement: Achievement, // This safely uses your pre-existing Achievement model
    modifier: Modifier = Modifier
) {
    // Premium dynamic motion states
    val contentAlpha by animateFloatAsState(
        targetValue = if (achievement.unlocked) 1f else 0.5f,
        animationSpec = tween(durationMillis = 400),
        label = "AchievementAlpha"
    )

    val iconScale by animateFloatAsState(
        targetValue = if (achievement.unlocked) 1.1f else 1.0f,
        animationSpec = tween(durationMillis = 500),
        label = "IconScaleMotion"
    )

    val cardBgColor by animateColorAsState(
        targetValue = if (achievement.unlocked) Color(0xFF161626) else Color(0xFF11111A),
        animationSpec = tween(durationMillis = 400),
        label = "CardBackgroundTransition"
    )

    val goldPremium = Color(0xFFFBBF24)
    val lockedMuted = Color(0xFF4B5563)
    val obsidianBorder = Color(0xFF232335)

    Card(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp)),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = cardBgColor),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .border(
                    width = 1.dp,
                    color = if (achievement.unlocked) goldPremium.copy(alpha = 0.15f) else obsidianBorder,
                    shape = RoundedCornerShape(16.dp)
                )
                .padding(all = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            // Motion Icon Ring Wrapper Container
            Box(
                modifier = Modifier
                    .size(44.dp)
                    .scale(iconScale)
                    .border(
                        width = 1.dp,
                        color = if (achievement.unlocked) goldPremium.copy(alpha = 0.3f) else lockedMuted.copy(alpha = 0.2f),
                        shape = CircleShape
                    )
                    .padding(3.dp),
                contentAlignment = Alignment.Center
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(CircleShape)
                        .background(
                            brush = Brush.radialGradient(
                                colors = if (achievement.unlocked) {
                                    listOf(goldPremium.copy(alpha = 0.25f), Color.Transparent)
                                } else {
                                    listOf(lockedMuted.copy(alpha = 0.1f), Color.Transparent)
                                }
                            )
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = if (achievement.unlocked) Icons.Rounded.Verified else Icons.Rounded.Lock,
                        contentDescription = null,
                        modifier = Modifier.size(20.dp),
                        tint = if (achievement.unlocked) goldPremium else lockedMuted
                    )
                }
            }

            Spacer(modifier = Modifier.width(16.dp))

            // Text layout framework (Adapted seamlessly to fit your exact properties)
            Column(
                modifier = Modifier
                    .weight(1f)
                    .alpha(contentAlpha)
            ) {
                Text(
                    text = achievement.title,
                    color = Color(0xFFF9FAFB),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = (-0.2).sp,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                Spacer(modifier = Modifier.height(2.dp))

                Text(
                    text = if (achievement.unlocked) achievement.description else "Requirement: ${achievement.requirement}",
                    color = Color(0xFF6B7280),
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Normal
                )
            }

            if (achievement.unlocked) {
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(6.dp))
                        .background(goldPremium.copy(alpha = 0.1f))
                        .padding(horizontal = 6.dp, vertical = 2.dp)
                ) {
                    Text(
                        text = "UNLOCKED",
                        color = goldPremium,
                        fontSize = 9.sp,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 0.5.sp
                    )
                }
            }
        }
    }
}