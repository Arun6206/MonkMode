package com.example.monkmode.presentation.achievements

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material.icons.rounded.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.monkmode.ui.theme.AppColors

@Composable
fun AchievementsScreen(
    navController: NavController
) {
    // Demo Badges based on your request
    val badges = listOf(
        Achievement(
            title = "First Sprint",
            description = "Completed your first 30-minute focus session.",
            requirement = "Complete 1 focus session",
            icon = Icons.Rounded.Bolt,
            unlocked = true,
            level = 1
        ),
        Achievement(
            title = "7-Day Warrior",
            description = "Maintained a streak for a full week.",
            requirement = "7 day habit streak",
            icon = Icons.Rounded.LocalFireDepartment,
            unlocked = true,
            level = 1
        ),
        Achievement(
            title = "Deep Diver",
            description = "Completed a massive 90-minute deep work session.",
            requirement = "90m session completion",
            icon = Icons.Rounded.Waves,
            unlocked = false,
            level = 2
        ),
        Achievement(
            title = "Consistency King",
            description = "Maintain 100% completion for 3 days.",
            requirement = "100% completion rate (3d)",
            icon = Icons.Rounded.EmojiEvents,
            unlocked = false,
            level = 3
        ),
        Achievement(
            title = "Early Riser",
            description = "Completed a habit before 7:00 AM.",
            requirement = "Morning habit completion",
            icon = Icons.Rounded.WbSunny,
            unlocked = true,
            level = 1
        ),
        Achievement(
            title = "Zen Master",
            description = "Completed 10 hours of total meditation.",
            requirement = "600 total meditation mins",
            icon = Icons.Rounded.Spa,
            unlocked = false,
            level = 3
        )
    )

    val unlockedCount = badges.count { it.unlocked }
    val totalCount = badges.size

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(AppColors.Obsidian)
            .statusBarsPadding()
    ) {
        // Header
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = { navController.popBackStack() }) {
                Icon(Icons.AutoMirrored.Rounded.ArrowBack, null, tint = Color.White)
            }
            Spacer(modifier = Modifier.width(8.dp))
            Column {
                Text(
                    text = "Achievements",
                    color = Color.White,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Black
                )
                Text(
                    text = "Small wins. A bigger you.",
                    color = AppColors.TextSecondary,
                    fontSize = 13.sp
                )
            }
        }

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(24.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            // Stats Summary Card
            item {
                BadgeSummaryCard(unlockedCount, totalCount)
                Spacer(modifier = Modifier.height(12.dp))
            }

            items(badges) { badge ->
                EliteBadgeCard(badge)
            }

            item {
                Spacer(modifier = Modifier.height(100.dp))
            }
        }
    }
}

@Composable
fun BadgeSummaryCard(unlocked: Int, total: Int) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = AppColors.SurfaceLight),
        border = androidx.compose.foundation.BorderStroke(1.dp, AppColors.Border)
    ) {
        Row(
            modifier = Modifier.padding(24.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(64.dp)
                    .clip(CircleShape)
                    .background(AppColors.RoyalViolet.copy(alpha = 0.1f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(Icons.Rounded.MilitaryTech, null, tint = AppColors.RoyalViolet, modifier = Modifier.size(32.dp))
            }
            Spacer(modifier = Modifier.width(20.dp))
            Column {
                Text(
                    text = "$unlocked / $total Badges",
                    color = Color.White,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
                LinearProgressIndicator(
                    progress = { unlocked.toFloat() / total },
                    modifier = Modifier
                        .padding(vertical = 8.dp)
                        .width(150.dp)
                        .height(6.dp)
                        .clip(CircleShape),
                    color = AppColors.RoyalViolet,
                    trackColor = Color.White.copy(alpha = 0.05f)
                )
                Text(
                    text = "${total - unlocked} badges left to unlock",
                    color = AppColors.TextSecondary,
                    fontSize = 12.sp
                )
            }
        }
    }
}

@Composable
fun EliteBadgeCard(badge: Achievement) {
    val goldPremium = Color(0xFFFBBF24)
    val lockedColor = Color(0xFF4B5563)

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (badge.unlocked) AppColors.CardBg else AppColors.Surface
        ),
        border = androidx.compose.foundation.BorderStroke(
            1.dp, 
            if (badge.unlocked) goldPremium.copy(alpha = 0.2f) else AppColors.Border
        )
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Badge Icon
            Box(
                modifier = Modifier
                    .size(56.dp)
                    .clip(CircleShape)
                    .background(
                        if (badge.unlocked) goldPremium.copy(alpha = 0.1f) else Color.White.copy(alpha = 0.03f)
                    )
                    .border(
                        1.dp,
                        if (badge.unlocked) goldPremium.copy(alpha = 0.3f) else Color.Transparent,
                        CircleShape
                    ),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = badge.icon ?: Icons.Rounded.Lock,
                    contentDescription = null,
                    tint = if (badge.unlocked) goldPremium else lockedColor,
                    modifier = Modifier.size(28.dp)
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            Column(modifier = Modifier.weight(1f)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = badge.title,
                        color = Color.White,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    if (badge.level > 1) {
                        Surface(
                            color = AppColors.RoyalViolet.copy(alpha = 0.1f),
                            shape = RoundedCornerShape(4.dp)
                        ) {
                            Text(
                                text = "LVL ${badge.level}",
                                color = AppColors.RoyalViolet,
                                fontSize = 9.sp,
                                fontWeight = FontWeight.Black,
                                modifier = Modifier.padding(horizontal = 4.dp, vertical = 2.dp)
                            )
                        }
                    }
                }
                Text(
                    text = if (badge.unlocked) badge.description else "Requirement: ${badge.requirement}",
                    color = if (badge.unlocked) AppColors.TextSecondary else Color.Red.copy(alpha = 0.6f),
                    fontSize = 12.sp,
                    lineHeight = 16.sp
                )
            }

            if (badge.unlocked) {
                Icon(Icons.Rounded.CheckCircle, null, tint = AppColors.CyberEmerald, modifier = Modifier.size(24.dp))
            } else {
                Icon(Icons.Rounded.Lock, null, tint = lockedColor, modifier = Modifier.size(20.dp))
            }
        }
    }
}
