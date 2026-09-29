package com.example.monkmode.presentation.focus

import androidx.activity.compose.BackHandler
import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material.icons.rounded.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.monkmode.R
import com.example.monkmode.ui.theme.AppColors

@Composable
fun FocusScreen(
    navController: androidx.navigation.NavController,
    viewModel: FocusViewModel
) {
    val timeLeft by viewModel.timeLeft.collectAsState()
    val isRunning by viewModel.isRunning.collectAsState()
    val currentMode by viewModel.currentMode.collectAsState()
    val deepWorkDuration by viewModel.deepWorkDuration.collectAsState()

    // Handle back button logically to preserve timer state
    BackHandler {
        // Simply navigate back to previous screen (Home) without calling resetTimer()
        navController.popBackStack()
    }

    val totalModeSeconds = if (currentMode == FocusMode.DEEP_WORK) deepWorkDuration * 60 else currentMode.defaultMinutes * 60
    val progressSweepAngle by animateFloatAsState(
        targetValue = if (totalModeSeconds == 0) 0f else timeLeft.toFloat() / totalModeSeconds.toFloat(),
        animationSpec = tween(durationMillis = 1000, easing = LinearOutSlowInEasing),
        label = "RadialTimerSweep"
    )

    val formattedTime = String.format("%02d:%02d", timeLeft / 60, timeLeft % 60)

    Box(modifier = Modifier.fillMaxSize()) {
        // Professional Background Image Layer
        val backgroundImage = if (isRunning) R.drawable.bg_focus_running else R.drawable.bg_focus_setup
        
        Image(
            painter = painterResource(id = backgroundImage),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        // Dark Overlay for readability
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            Color.Black.copy(alpha = if (isRunning) 0.6f else 0.4f),
                            AppColors.Obsidian.copy(alpha = 0.85f)
                        )
                    )
                )
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(bottom = 80.dp) // Proper padding for bottom nav
        ) {
            // Header Zone
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .statusBarsPadding()
                    .padding(horizontal = 24.dp, vertical = 20.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = { 
                    // Simply go back to Home, timer will keep running in ViewModel
                    navController.popBackStack()
                }) {
                    Icon(Icons.AutoMirrored.Rounded.ArrowBack, null, tint = Color.White)
                }

                Text(
                    text = "Focus",
                    color = Color.White,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.ExtraBold
                )

                IconButton(onClick = { 
                    navController.navigate(com.example.monkmode.navigation.Screen.Settings.route)
                }) {
                    Icon(Icons.Rounded.Settings, null, tint = Color.White)
                }
            }

            Crossfade(targetState = isRunning, label = "FocusLayoutTransition") { running ->
                if (running) {
                    RunningLayout(
                        formattedTime = formattedTime,
                        currentMode = currentMode,
                        progressSweepAngle = progressSweepAngle,
                        onCancel = { viewModel.resetTimer() },
                        onPauseToggle = { if (isRunning) viewModel.stopTimer() else viewModel.startTimer() },
                        onAddFive = { viewModel.addFiveMinutes() },
                        isRunning = true
                    )
                } else {
                    SetupLayout(
                        viewModel = viewModel,
                        currentMode = currentMode,
                        deepWorkDuration = deepWorkDuration,
                        formattedTime = formattedTime,
                        progressSweepAngle = progressSweepAngle,
                        onStart = { viewModel.startTimer() }
                    )
                }
            }
            
            Spacer(modifier = Modifier.height(32.dp))

            // Shared bottom component: Distraction Free
            DistractionFreeCard()
        }
    }
}

@Composable
fun SetupLayout(
    viewModel: FocusViewModel,
    currentMode: FocusMode,
    deepWorkDuration: Int,
    formattedTime: String,
    progressSweepAngle: Float,
    onStart: () -> Unit
) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = "Enter a focused state.\nDo the work that matters.",
            color = AppColors.TextSecondary,
            fontSize = 14.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(horizontal = 24.dp)
        )

        Spacer(modifier = Modifier.height(32.dp))

        // Mode Selector
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp)
                .clip(RoundedCornerShape(16.dp))
                .background(AppColors.Surface)
                .padding(6.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            FocusMode.entries.forEach { mode ->
                val isActive = currentMode == mode
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .clip(RoundedCornerShape(12.dp))
                        .background(if (isActive) AppColors.RoyalViolet else Color.Transparent)
                        .clickable { viewModel.switchMode(mode) }
                        .padding(vertical = 12.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = mode.label,
                        color = if (isActive) Color.White else AppColors.TextSecondary,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Session Length
        Column(modifier = Modifier.padding(horizontal = 24.dp)) {
            Text(
                text = "Session Length",
                color = Color.White,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(12.dp))
            Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                listOf(25, 30, 60, 90).forEach { mins ->
                    val isSelected = deepWorkDuration == mins && currentMode == FocusMode.DEEP_WORK
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .clip(RoundedCornerShape(12.dp))
                            .background(if (isSelected) AppColors.RoyalViolet else AppColors.Surface)
                            .border(
                                width = 1.dp,
                                color = if (isSelected) AppColors.RoyalViolet else AppColors.Border,
                                shape = RoundedCornerShape(12.dp)
                            )
                            .clickable { viewModel.updateDeepWorkDuration(mins) }
                            .padding(vertical = 14.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "${mins}m",
                            color = if (isSelected) Color.White else AppColors.TextSecondary,
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(40.dp))

        // Radial Timer (Ready State)
        Box(contentAlignment = Alignment.Center, modifier = Modifier.size(280.dp)) {
            CircularProgressIndicator(
                progress = { 1f },
                modifier = Modifier.size(240.dp),
                color = AppColors.Border.copy(alpha = 0.3f),
                strokeWidth = 8.dp,
                strokeCap = StrokeCap.Round
            )
            CircularProgressIndicator(
                progress = { progressSweepAngle },
                modifier = Modifier.size(240.dp),
                color = AppColors.RoyalViolet,
                strokeWidth = 8.dp,
                trackColor = Color.Transparent,
                strokeCap = StrokeCap.Round
            )
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = formattedTime,
                    color = Color.White,
                    fontSize = 56.sp,
                    fontWeight = FontWeight.Black
                )
                Text(
                    text = "READY",
                    color = AppColors.TextSecondary,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 2.sp
                )
            }
        }

        Spacer(modifier = Modifier.height(40.dp))

        Button(
            onClick = onStart,
            modifier = Modifier.height(60.dp).width(240.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
            contentPadding = PaddingValues(0.dp),
            shape = RoundedCornerShape(30.dp)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Brush.horizontalGradient(listOf(AppColors.VioletGradientStart, AppColors.VioletGradientEnd))),
                contentAlignment = Alignment.Center
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Rounded.PlayArrow, null, tint = Color.White)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Start Focus", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                }
            }
        }
    }
}

@Composable
fun RunningLayout(
    formattedTime: String,
    currentMode: FocusMode,
    progressSweepAngle: Float,
    onCancel: () -> Unit,
    onPauseToggle: () -> Unit,
    onAddFive: () -> Unit,
    isRunning: Boolean
) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Spacer(modifier = Modifier.height(48.dp))

        // Large Radial Timer (Active State)
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.fillMaxWidth().height(320.dp)
        ) {
            CircularProgressIndicator(
                progress = { 1f },
                modifier = Modifier.size(280.dp),
                color = AppColors.Border.copy(alpha = 0.3f),
                strokeWidth = 8.dp,
                strokeCap = StrokeCap.Round
            )
            CircularProgressIndicator(
                progress = { progressSweepAngle },
                modifier = Modifier.size(280.dp),
                color = AppColors.RoyalViolet,
                strokeWidth = 8.dp,
                trackColor = Color.Transparent,
                strokeCap = StrokeCap.Round
            )
            
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Icon(Icons.Rounded.HourglassBottom, null, tint = AppColors.RoyalViolet, modifier = Modifier.size(32.dp))
                Text(
                    text = formattedTime,
                    color = Color.White,
                    fontSize = 72.sp,
                    fontWeight = FontWeight.Black,
                    letterSpacing = (-2).sp
                )
                Text(
                    text = currentMode.label,
                    color = AppColors.TextSecondary,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(12.dp))
                Icon(Icons.Rounded.Spa, null, tint = AppColors.CyberEmerald, modifier = Modifier.size(20.dp))
            }
        }

        Spacer(modifier = Modifier.height(60.dp))

        // Control Bar
        Row(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 40.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                IconButton(
                    onClick = onCancel,
                    modifier = Modifier.size(56.dp).background(AppColors.Surface, CircleShape)
                ) {
                    Icon(Icons.Rounded.Close, null, tint = Color.White)
                }
                Text("Cancel", color = AppColors.TextSecondary, fontSize = 11.sp, modifier = Modifier.padding(top = 4.dp))
            }

            // Large Toggle Button
            Box(
                modifier = Modifier
                    .size(84.dp)
                    .clip(CircleShape)
                    .background(Color.White.copy(alpha = 0.05f))
                    .border(2.dp, AppColors.RoyalViolet.copy(alpha = 0.3f), CircleShape)
                    .clickable { onPauseToggle() },
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = if (isRunning) Icons.Rounded.Pause else Icons.Rounded.PlayArrow,
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier.size(40.dp)
                )
            }

            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                IconButton(
                    onClick = onAddFive,
                    modifier = Modifier.size(56.dp).background(AppColors.Surface, CircleShape)
                ) {
                    Icon(Icons.Rounded.Add, null, tint = Color.White)
                }
                Text("+5 min", color = AppColors.TextSecondary, fontSize = 11.sp, modifier = Modifier.padding(top = 4.dp))
            }
        }
    }
}

@Composable
fun DistractionFreeCard() {
    Card(
        modifier = Modifier.fillMaxWidth().padding(horizontal = 24.dp),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = AppColors.CardBg),
        border = androidx.compose.foundation.BorderStroke(1.dp, AppColors.Border)
    ) {
        Row(
            modifier = Modifier.padding(20.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier.size(40.dp).background(AppColors.CyberEmerald.copy(alpha = 0.1f), CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Icon(Icons.Rounded.PhoneInTalk, null, tint = AppColors.CyberEmerald, modifier = Modifier.size(20.dp))
            }
            Spacer(modifier = Modifier.width(16.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text("Distraction Free", color = Color.White, fontSize = 15.sp, fontWeight = FontWeight.Bold)
                Text("You're doing great!", color = AppColors.TextSecondary, fontSize = 12.sp)
            }
            Icon(Icons.Rounded.ChevronRight, null, tint = AppColors.Border)
        }
    }
}
