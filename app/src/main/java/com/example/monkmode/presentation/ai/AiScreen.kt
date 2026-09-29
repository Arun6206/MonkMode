package com.example.monkmode.presentation.ai

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.Send
import androidx.compose.material.icons.rounded.*
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
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.monkmode.ui.theme.AppColors

@Composable
fun AiScreen(
    navController: androidx.navigation.NavController,
    viewModel: AiViewModel = viewModel()
) {
    val listState = rememberLazyListState()

    LaunchedEffect(viewModel.messages.size, viewModel.isTyping) {
        if (viewModel.messages.isNotEmpty()) {
            listState.animateScrollToItem(viewModel.messages.size + 1) // +1 for the greeting and actions
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(AppColors.Obsidian)
    ) {
        // Distinct Status Bar Area
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .statusBarsPadding()
                .background(Color.Black.copy(alpha = 0.3f))
        )

        // Executive Header Section
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp, vertical = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = "Monk",
                        color = Color.White,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Black
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "AI",
                        color = AppColors.RoyalViolet,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Black
                    )
                }
                Text(
                    text = "Your personal guide for a better you.",
                    color = AppColors.TextSecondary,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Medium
                )
            }
            IconButton(onClick = { 
                navController.navigate(com.example.monkmode.navigation.Screen.Settings.route)
            }) {
                Icon(Icons.Rounded.Settings, null, tint = Color.White, modifier = Modifier.size(22.dp))
            }
        }

        // Active Chat Thread
        Box(modifier = Modifier.weight(1f)) {
            // Decorative background for empty space
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(
                    imageVector = Icons.Rounded.Psychology,
                    contentDescription = null,
                    tint = Color.White.copy(alpha = 0.03f),
                    modifier = Modifier.size(240.dp)
                )
            }
            
            LazyColumn(
                state = listState,
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(horizontal = 24.dp, vertical = 12.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                // Initial Greeting Section
                item {
                    AiGreetingCard()
                }

                // Quick Action Grid
                item {
                    QuickActionGrid(onActionClick = { viewModel.sendQuickPrompt(it) })
                }

                items(viewModel.messages.size) { index ->
                    ChatBubble(viewModel.messages[index])
                }

                if (viewModel.isTyping) {
                    item {
                        TypingIndicatorBubble()
                    }
                }
            }
            
            // Subtle top shadow
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(20.dp)
                    .background(Brush.verticalGradient(listOf(AppColors.Obsidian, Color.Transparent)))
            )
        }

        // Bottom Control Bar
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(AppColors.Obsidian)
                .padding(bottom = 24.dp)
        ) {
            // Suggestion Chips Row - Fixed with Horizontal Scroll to prevent squeezing
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp)
                    .horizontalScroll(rememberScrollState()),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                IconButton(
                    onClick = { /* Regenerate logic */ },
                    modifier = Modifier.size(36.dp).background(AppColors.Surface, CircleShape)
                ) {
                    Icon(Icons.Rounded.Refresh, null, tint = AppColors.TextSecondary, modifier = Modifier.size(18.dp))
                }
                
                val suggestions = listOf("Summarize my day", "Give me a habit plan", "Motivate me")
                suggestions.forEach { label ->
                    SuggestionChip(label = label, onClick = { viewModel.sendQuickPrompt(label) })
                }
            }

            // Input Field with subtle inner glow
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                color = AppColors.Surface,
                shape = RoundedCornerShape(32.dp),
                border = androidx.compose.foundation.BorderStroke(1.dp, AppColors.Border)
            ) {
                Box(
                    modifier = Modifier.background(
                        Brush.radialGradient(
                            colors = listOf(AppColors.RoyalViolet.copy(alpha = 0.05f), Color.Transparent),
                            radius = 400f
                        )
                    )
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 20.dp, vertical = 4.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        TextField(
                            value = viewModel.currentText,
                            onValueChange = { viewModel.onTextChange(it) },
                            placeholder = { Text("Ask Monk AI anything...", color = AppColors.SlateGray, fontSize = 14.sp) },
                            modifier = Modifier.weight(1f),
                            colors = TextFieldDefaults.colors(
                                focusedContainerColor = Color.Transparent,
                                unfocusedContainerColor = Color.Transparent,
                                focusedIndicatorColor = Color.Transparent,
                                unfocusedIndicatorColor = Color.Transparent,
                                focusedTextColor = Color.White,
                                unfocusedTextColor = Color.White
                            ),
                            singleLine = true
                        )

                        IconButton(
                            onClick = { viewModel.sendMessage() },
                            enabled = viewModel.currentText.isNotBlank() && !viewModel.isTyping,
                            modifier = Modifier
                                .size(40.dp)
                                .clip(CircleShape)
                                .background(
                                    if (viewModel.currentText.isNotBlank() && !viewModel.isTyping) 
                                        AppColors.RoyalViolet 
                                    else AppColors.Border
                                )
                        ) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Rounded.Send,
                                contentDescription = "Send",
                                tint = Color.White,
                                modifier = Modifier.size(18.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun AiGreetingCard() {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(160.dp),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = AppColors.SurfaceLight),
        border = androidx.compose.foundation.BorderStroke(1.dp, AppColors.Border)
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            // Background Gradient
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        brush = Brush.verticalGradient(
                            colors = listOf(Color(0xFF1E1E2E).copy(alpha = 0.8f), AppColors.Obsidian)
                        )
                    )
            )
            
            Column(
                modifier = Modifier
                    .align(Alignment.CenterStart)
                    .padding(24.dp)
            ) {
                Text(
                    text = "Good afternoon, Arun.",
                    color = Color.White,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "How can I help you today?",
                    color = AppColors.TextSecondary,
                    fontSize = 16.sp
                )
            }
        }
    }
}

@Composable
fun QuickActionGrid(onActionClick: (String) -> Unit) {
    val actions = listOf(
        Pair("I can't focus", Icons.Rounded.CenterFocusStrong),
        Pair("Plan my day", Icons.Rounded.CalendarToday),
        Pair("I feel lazy", Icons.Rounded.ChatBubble),
        Pair("I broke my streak", Icons.Rounded.LocalFireDepartment),
        Pair("Give me motivation", Icons.Rounded.Lightbulb),
        Pair("Ask anything", Icons.Rounded.QuestionAnswer)
    )

    Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
        for (i in actions.indices step 2) {
            Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                AiActionChip(actions[i].first, actions[i].second, Modifier.weight(1f)) { onActionClick(actions[i].first) }
                AiActionChip(actions[i + 1].first, actions[i + 1].second, Modifier.weight(1f)) { onActionClick(actions[i + 1].first) }
            }
        }
    }
}

@Composable
fun AiActionChip(label: String, icon: ImageVector, modifier: Modifier = Modifier, onClick: () -> Unit) {
    Surface(
        modifier = modifier.clickable { onClick() },
        color = AppColors.Surface,
        shape = RoundedCornerShape(14.dp),
        border = androidx.compose.foundation.BorderStroke(1.dp, AppColors.Border)
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(icon, null, tint = AppColors.TextSecondary, modifier = Modifier.size(18.dp))
            Spacer(modifier = Modifier.width(10.dp))
            Text(label, color = Color.White, fontSize = 13.sp, fontWeight = FontWeight.Medium)
        }
    }
}

@Composable
fun SuggestionChip(label: String, onClick: () -> Unit) {
    Surface(
        modifier = Modifier.clickable { onClick() },
        color = AppColors.Surface,
        shape = RoundedCornerShape(20.dp),
        border = androidx.compose.foundation.BorderStroke(1.dp, AppColors.Border)
    ) {
        Text(
            text = label,
            color = AppColors.TextPrimary,
            fontSize = 12.sp,
            maxLines = 1,
            softWrap = false,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 10.dp)
        )
    }
}
