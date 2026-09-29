package com.example.monkmode.presentation.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.CheckCircle
import androidx.compose.material.icons.rounded.LocalFireDepartment
import androidx.compose.material.icons.rounded.Schedule
import androidx.compose.material.icons.rounded.TrackChanges
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.monkmode.ui.theme.AppColors
import java.util.Calendar

@Composable
fun HeroBanner(
    userName: String,
    completedHabits: Int,
    totalHabits: Int,
    focusMinutes: Int = 0,
    streakCount: Int = 0
) {
    val hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY)
    val greeting = when {
        hour < 12 -> "Good Morning,"
        hour < 17 -> "Good Afternoon,"
        else -> "Good Evening,"
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp)
    ) {
        // Greeting Section
        Text(
            text = greeting,
            color = AppColors.TextSecondary,
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium
        )
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = userName.split(" ").firstOrNull() ?: userName,
                color = Color.White,
                fontSize = 32.sp,
                fontWeight = FontWeight.Black
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(text = "👋", fontSize = 28.sp)
        }
        
        Spacer(modifier = Modifier.height(32.dp))

        // Today's Progress Card
        ProgressCard(completedHabits, totalHabits)

        Spacer(modifier = Modifier.height(24.dp))

        // Metrics Row
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            MetricBox(
                icon = Icons.Rounded.LocalFireDepartment,
                value = "$streakCount",
                label = "Day Streak",
                iconColor = AppColors.IgniteOrange,
                modifier = Modifier.weight(1f)
            )
            MetricBox(
                icon = Icons.Rounded.Schedule,
                value = "${focusMinutes / 60}h ${focusMinutes % 60}m",
                label = "Focus Time",
                iconColor = Color(0xFF8B5CF6),
                modifier = Modifier.weight(1.2f)
            )
            MetricBox(
                icon = Icons.Rounded.CheckCircle,
                value = "$totalHabits",
                label = "Habits Today",
                iconColor = AppColors.CyberEmerald,
                modifier = Modifier.weight(1f)
            )
        }
    }
}

@Composable
fun ProgressCard(completed: Int, total: Int) {
    val progress = if (total == 0) 0f else completed.toFloat() / total
    val animatedProgress by animateFloatAsState(
        targetValue = progress,
        animationSpec = tween(durationMillis = 800),
        label = "HeroProgress"
    )

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = AppColors.CardBg),
        border = androidx.compose.foundation.BorderStroke(1.dp, AppColors.Border)
    ) {
        Column(
            modifier = Modifier.padding(20.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(36.dp)
                            .clip(RoundedCornerShape(10.dp))
                            .background(AppColors.RoyalViolet.copy(alpha = 0.15f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Rounded.TrackChanges,
                            contentDescription = null,
                            tint = AppColors.RoyalViolet,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                    Spacer(modifier = Modifier.width(12.dp))
                    Text(
                        text = "Today's Progress",
                        color = Color.White,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
                
                Row(verticalAlignment = Alignment.Bottom) {
                    Text(
                        text = "$completed ",
                        color = Color.White,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Black
                    )
                    Text(
                        text = "/ $total",
                        color = AppColors.TextSecondary,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(bottom = 2.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(6.dp))
            Text(
                text = "habits completed",
                color = AppColors.TextSecondary,
                fontSize = 11.sp,
                modifier = Modifier.align(Alignment.End)
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Progress Bar
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(10.dp)
                    .clip(RoundedCornerShape(5.dp))
                    .background(Color.White.copy(alpha = 0.05f))
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth(animatedProgress)
                        .fillMaxHeight()
                        .clip(RoundedCornerShape(5.dp))
                        .background(
                            Brush.horizontalGradient(
                                listOf(AppColors.VioletGradientStart, AppColors.VioletGradientEnd)
                            )
                        )
                )
            }
            
            Spacer(modifier = Modifier.height(12.dp))
            
            Text(
                text = "${(progress * 100).toInt()}%",
                color = AppColors.TextSecondary,
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.align(Alignment.End)
            )
        }
    }
}

@Composable
fun MetricBox(
    icon: ImageVector,
    value: String,
    label: String,
    iconColor: Color,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.height(110.dp),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = AppColors.CardBg),
        border = androidx.compose.foundation.BorderStroke(1.dp, AppColors.Border)
    ) {
        Column(
            modifier = Modifier.fillMaxSize().padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = iconColor,
                modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = value,
                color = Color.White,
                fontSize = 18.sp,
                fontWeight = FontWeight.Black
            )
            Text(
                text = label,
                color = AppColors.TextSecondary,
                fontSize = 10.sp,
                fontWeight = FontWeight.Medium
            )
        }
    }
}
