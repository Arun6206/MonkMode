package com.example.monkmode.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.*
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.monkmode.data.local.HabitEntity
import com.example.monkmode.ui.theme.AppColors

@Composable
fun HabitTile(
    habit: HabitEntity,
    onCheckedChange: () -> Unit,
    onDelete: () -> Unit = {} // Added default to keep it optional
) {
    val habitIcon = getIconForHabit(habit.name)

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp, vertical = 8.dp)
            .clickable { onCheckedChange() },
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Status Circle
        Box(
            modifier = Modifier
                .size(28.dp)
                .clip(CircleShape)
                .background(if (habit.completed) AppColors.CyberEmerald else Color.Transparent)
                .border(
                    width = 1.5.dp,
                    color = if (habit.completed) Color.Transparent else AppColors.SlateGray,
                    shape = CircleShape
                ),
            contentAlignment = Alignment.Center
        ) {
            if (habit.completed) {
                Icon(
                    imageVector = Icons.Rounded.Check,
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier.size(18.dp)
                )
            }
        }

        Spacer(modifier = Modifier.width(16.dp))

        // Habit Icon
        Box(
            modifier = Modifier
                .size(40.dp)
                .clip(RoundedCornerShape(12.dp))
                .background(Color.White.copy(alpha = 0.03f)),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = habitIcon,
                contentDescription = null,
                tint = if (habit.completed) AppColors.CyberEmerald else Color.White.copy(alpha = 0.6f),
                modifier = Modifier.size(22.dp)
            )
        }

        Spacer(modifier = Modifier.width(16.dp))

        // Text Content
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = habit.name,
                color = Color.White,
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold
            )
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = if (habit.completed) "Completed" else "Not started",
                    color = if (habit.completed) AppColors.TextSecondary else AppColors.SlateGray,
                    fontSize = 12.sp
                )
                if (habit.streakCount > 0) {
                    Text(
                        text = "  •  ${habit.streakCount} day streak",
                        color = AppColors.SlateGray,
                        fontSize = 11.sp
                    )
                }
            }
        }

        Icon(
            imageVector = Icons.Rounded.ChevronRight,
            contentDescription = null,
            tint = AppColors.Border,
            modifier = Modifier.size(20.dp)
        )
    }
}

private fun getIconForHabit(name: String): ImageVector {
    val lowerName = name.lowercase()
    return when {
        "meditation" in lowerName -> Icons.Rounded.Spa
        "sport" in lowerName || "gym" in lowerName || "workout" in lowerName -> Icons.Rounded.FitnessCenter
        "read" in lowerName -> Icons.Rounded.MenuBook
        "run" in lowerName -> Icons.Rounded.DirectionsRun
        "water" in lowerName -> Icons.Rounded.WaterDrop
        else -> Icons.Rounded.Bolt
    }
}
