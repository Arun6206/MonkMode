package com.example.monkmode.presentation.stats

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material.icons.automirrored.rounded.TrendingUp
import androidx.compose.material.icons.rounded.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.monkmode.ui.theme.AppColors

@Composable
fun InsightsScreen(
    navController: NavController
) {
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
                    text = "Intelligence Insights",
                    color = Color.White,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Black
                )
                Text(
                    text = "Behavioral analysis and protocols.",
                    color = AppColors.TextSecondary,
                    fontSize = 13.sp
                )
            }
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 24.dp, vertical = 20.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                "Based on your activity this week",
                color = AppColors.RoyalViolet,
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            InsightFullCard(
                icon = Icons.Rounded.WbSunny,
                title = "Circadian Optimization",
                description = "You are strongest in the morning. Your focus sessions are usually 40% longer before 12 PM. Capitalize on this peak by scheduling your hardest tasks then.",
                color = Color(0xFFFBBF24)
            )

            InsightFullCard(
                icon = Icons.AutoMirrored.Rounded.TrendingUp,
                title = "Momentum Shift",
                description = "Your focus sessions increased by 32% compared to last week. You are building neuroplasticity. Keep the pressure on.",
                color = AppColors.CyberEmerald
            )

            InsightFullCard(
                icon = Icons.Rounded.CalendarToday,
                title = "Weekend Protocol Breach",
                description = "You missed 60% of your habits on Saturday and Sunday. Discipline doesn't take days off. Set strict weekend reminders.",
                color = Color(0xFFEF4444)
            )

            InsightFullCard(
                icon = Icons.Rounded.Psychology,
                title = "Neural Flow Pattern",
                description = "After 45 minutes of work, your productivity dips. Try the Pomodoro technique or shorter sprints to maintain elite intensity.",
                color = AppColors.NeuralCyan
            )
            
            Spacer(modifier = Modifier.height(100.dp))
        }
    }
}

@Composable
fun InsightFullCard(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    title: String,
    description: String,
    color: Color
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = AppColors.CardBg),
        border = androidx.compose.foundation.BorderStroke(1.dp, AppColors.Border)
    ) {
        Column(modifier = Modifier.padding(20.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .background(color.copy(alpha = 0.1f), RoundedCornerShape(12.dp)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(icon, null, tint = color, modifier = Modifier.size(22.dp))
                }
                Spacer(modifier = Modifier.width(16.dp))
                Text(
                    text = title,
                    color = Color.White,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
            }
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = description,
                color = AppColors.TextSecondary,
                fontSize = 14.sp,
                lineHeight = 22.sp
            )
        }
    }
}
