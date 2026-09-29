package com.example.monkmode.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Notifications
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.monkmode.ui.theme.AppColors

@Composable
fun PremiumTopBar(
    onNotificationClick: () -> Unit = {}
) {
    var showDirectives by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        // Distinct Status Bar Area
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .statusBarsPadding()
        )
        
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp, vertical = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text(
                    text = "MonkMode 👑",
                    color = Color.White,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.ExtraBold,
                    letterSpacing = (-0.5).sp
                )
                Text(
                    text = "Focus. Discipline. Freedom.",
                    color = AppColors.TextSecondary,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Medium
                )
            }

            IconButton(
                onClick = { showDirectives = true },
                modifier = Modifier
                    .size(40.dp)
                    .background(Color.White.copy(alpha = 0.05f), CircleShape)
            ) {
                Icon(
                    imageVector = Icons.Rounded.Notifications,
                    contentDescription = "Notifications",
                    tint = AppColors.RoyalViolet,
                    modifier = Modifier.size(22.dp)
                )
            }
        }
    }

    if (showDirectives) {
        AlertDialog(
            onDismissRequest = { showDirectives = false },
            containerColor = AppColors.Surface,
            shape = RoundedCornerShape(28.dp),
            title = {
                Text("Neural Directives", color = Color.White, fontWeight = FontWeight.Bold)
            },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                    DirectiveRow("Morning Protocol", "06:00 AM", true)
                    DirectiveRow("Peak Performance", "02:30 PM", true)
                    DirectiveRow("Daily De-brief", "10:30 PM", true)
                    
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        "Status: All protocols synchronized. Your discipline is your competitive advantage.",
                        color = AppColors.TextSecondary,
                        fontSize = 12.sp
                    )
                }
            },
            confirmButton = {
                TextButton(onClick = { showDirectives = false }) {
                    Text("Understood", color = AppColors.RoyalViolet)
                }
            }
        )
    }
}

@Composable
fun DirectiveRow(label: String, time: String, active: Boolean) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column {
            Text(label, color = Color.White, fontSize = 14.sp, fontWeight = FontWeight.Bold)
            Text(time, color = AppColors.TextSecondary, fontSize = 12.sp)
        }
        Box(
            modifier = Modifier
                .size(8.dp)
                .background(if (active) AppColors.CyberEmerald else AppColors.SlateGray, CircleShape)
        )
    }
}
