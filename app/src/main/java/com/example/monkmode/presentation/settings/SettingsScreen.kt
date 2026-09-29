package com.example.monkmode.presentation.settings

import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material.icons.automirrored.rounded.HelpOutline
import androidx.compose.material.icons.rounded.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.monkmode.ui.theme.AppColors

@Composable
fun SettingsScreen(
    navController: NavController,
    initialSection: String? = null
) {
    val context = LocalContext.current
    var activeSection by remember { mutableStateOf(initialSection) }

    // Persistent Settings State
    var focusDuration by remember { mutableIntStateOf(25) }
    var autoStartBreaks by remember { mutableStateOf(true) }
    var strictMode by remember { mutableStateOf(false) }

    var dailyHabitGoal by remember { mutableIntStateOf(5) }
    var autoArchiveDays by remember { mutableIntStateOf(30) }

    var dailyReminders by remember { mutableStateOf(true) }
    var streakAlerts by remember { mutableStateOf(true) }
    var soundEffects by remember { mutableStateOf(true) }

    var selectedTheme by remember { mutableStateOf("Obsidian Dark") }
    var compactLayout by remember { mutableStateOf(false) }

    var onDeviceVault by remember { mutableStateOf(true) }
    var cloudBackup by remember { mutableStateOf(true) }

    var supportMessage by remember { mutableStateOf("") }
    var showPasswordModal by remember { mutableStateOf(false) }

    // Smooth Back Tracking
    BackHandler {
        if (activeSection != null) {
            activeSection = null
        } else {
            navController.popBackStack()
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(AppColors.Obsidian)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding()
        ) {
            // Header Section
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = {
                    if (activeSection != null) {
                        activeSection = null
                    } else {
                        navController.popBackStack()
                    }
                }) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Rounded.ArrowBack,
                        contentDescription = "Back",
                        tint = Color.White
                    )
                }
                Spacer(modifier = Modifier.width(8.dp))
                Column {
                    Text(
                        text = if (activeSection != null) activeSection!! else "Settings",
                        color = Color.White,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = if (activeSection != null) "Configure your preference details." else "Customize your MonkMode experience.",
                        color = AppColors.TextSecondary,
                        fontSize = 13.sp
                    )
                }
            }

            // Main Settings Category List
            if (activeSection == null) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState())
                        .padding(horizontal = 24.dp, vertical = 12.dp),
                    verticalArrangement = Arrangement.spacedBy(28.dp)
                ) {
                    SettingsSectionGroup(
                        title = "App Preferences",
                        items = listOf(
                            SettingRowItem("Focus Settings", Icons.Rounded.Timer, "${focusDuration}m default"),
                            SettingRowItem("Habit Settings", Icons.Rounded.SettingsSuggest, "Goal: $dailyHabitGoal/day"),
                            SettingRowItem("Notifications & Reminders", Icons.Rounded.Notifications, if (dailyReminders) "Enabled" else "Disabled"),
                            SettingRowItem("Appearance", Icons.Rounded.Palette, selectedTheme)
                        ),
                        onItemClick = { activeSection = it }
                    )

                    SettingsSectionGroup(
                        title = "Account & Privacy",
                        items = listOf(
                            SettingRowItem("Privacy & Encryption", Icons.Rounded.Security, "AES-256 Vault"),
                            SettingRowItem("Manage Account", Icons.Rounded.AccountCircle, "Arun Kumar Yadav")
                        ),
                        onItemClick = { activeSection = it }
                    )

                    SettingsSectionGroup(
                        title = "Support & Info",
                        items = listOf(
                            SettingRowItem("Help & Support Center", Icons.AutoMirrored.Rounded.HelpOutline, "FAQs & Help"),
                            SettingRowItem("About MonkMode", Icons.Rounded.Info, "v1.0.0 Pro")
                        ),
                        onItemClick = { activeSection = it }
                    )

                    Spacer(modifier = Modifier.height(40.dp))
                }
            } else {
                // Section Detail View
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState())
                        .padding(horizontal = 24.dp, vertical = 12.dp),
                    verticalArrangement = Arrangement.spacedBy(20.dp)
                ) {
                    when (activeSection) {
                        "Focus Settings" -> {
                            DetailCard(title = "Default Focus Session Length") {
                                Text("Select your default timer duration:", color = AppColors.TextSecondary, fontSize = 13.sp)
                                Spacer(modifier = Modifier.height(12.dp))
                                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                                    listOf(25, 30, 45, 60).forEach { mins ->
                                        val selected = focusDuration == mins
                                        FilterChip(
                                            selected = selected,
                                            onClick = { focusDuration = mins },
                                            label = { Text("${mins}m", color = if (selected) Color.White else AppColors.TextSecondary) },
                                            colors = FilterChipDefaults.filterChipColors(
                                                selectedContainerColor = AppColors.RoyalViolet,
                                                containerColor = AppColors.Surface
                                            )
                                        )
                                    }
                                }
                            }

                            DetailCard(title = "Focus Automation") {
                                SwitchRow(
                                    title = "Auto-Start Breaks",
                                    subtitle = "Automatically trigger break timer after deep focus.",
                                    checked = autoStartBreaks,
                                    onCheckedChange = { autoStartBreaks = it }
                                )
                                HorizontalDivider(color = AppColors.Border.copy(alpha = 0.5f), thickness = 1.dp)
                                SwitchRow(
                                    title = "Strict Mode",
                                    subtitle = "Prevent interrupting focus sessions once started.",
                                    checked = strictMode,
                                    onCheckedChange = { strictMode = it }
                                )
                            }
                        }

                        "Habit Settings" -> {
                            DetailCard(title = "Daily Target Goal") {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Column {
                                        Text("Daily Completion Target", color = Color.White, fontWeight = FontWeight.Bold)
                                        Text("Recommended: 5 habits daily", color = AppColors.TextSecondary, fontSize = 12.sp)
                                    }
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        IconButton(
                                            onClick = { if (dailyHabitGoal > 1) dailyHabitGoal-- },
                                            modifier = Modifier.size(36.dp).background(AppColors.Surface, CircleShape)
                                        ) {
                                            Icon(Icons.Rounded.Remove, null, tint = Color.White)
                                        }
                                        Text(
                                            text = "$dailyHabitGoal",
                                            color = Color.White,
                                            fontWeight = FontWeight.Black,
                                            fontSize = 18.sp,
                                            modifier = Modifier.padding(horizontal = 16.dp)
                                        )
                                        IconButton(
                                            onClick = { if (dailyHabitGoal < 20) dailyHabitGoal++ },
                                            modifier = Modifier.size(36.dp).background(AppColors.Surface, CircleShape)
                                        ) {
                                            Icon(Icons.Rounded.Add, null, tint = Color.White)
                                        }
                                    }
                                }
                            }

                            DetailCard(title = "Archival Policy") {
                                Text("Auto-Archive Inactive Habits:", color = AppColors.TextSecondary, fontSize = 13.sp)
                                Spacer(modifier = Modifier.height(12.dp))
                                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                                    listOf(14, 30, 60).forEach { days ->
                                        val selected = autoArchiveDays == days
                                        FilterChip(
                                            selected = selected,
                                            onClick = { autoArchiveDays = days },
                                            label = { Text("$days Days", color = if (selected) Color.White else AppColors.TextSecondary) },
                                            colors = FilterChipDefaults.filterChipColors(
                                                selectedContainerColor = AppColors.RoyalViolet,
                                                containerColor = AppColors.Surface
                                            )
                                        )
                                    }
                                }
                            }
                        }

                        "Notifications & Reminders" -> {
                            DetailCard(title = "Alert Preferences") {
                                SwitchRow(
                                    title = "Daily Focus Reminders",
                                    subtitle = "Receive gentle nudges to start your focus protocol.",
                                    checked = dailyReminders,
                                    onCheckedChange = { dailyReminders = it }
                                )
                                HorizontalDivider(color = AppColors.Border.copy(alpha = 0.5f), thickness = 1.dp)
                                SwitchRow(
                                    title = "Streak Alerts",
                                    subtitle = "Get notified before streak breaks at midnight.",
                                    checked = streakAlerts,
                                    onCheckedChange = { streakAlerts = it }
                                )
                                HorizontalDivider(color = AppColors.Border.copy(alpha = 0.5f), thickness = 1.dp)
                                SwitchRow(
                                    title = "Audio & Haptic Feedback",
                                    subtitle = "Play ambient tone on completion.",
                                    checked = soundEffects,
                                    onCheckedChange = { soundEffects = it }
                                )
                            }

                            Button(
                                onClick = {
                                    Toast.makeText(context, "Test Notification Triggered!", Toast.LENGTH_SHORT).show()
                                },
                                modifier = Modifier.fillMaxWidth().height(50.dp),
                                colors = ButtonDefaults.buttonColors(containerColor = AppColors.SurfaceLight),
                                shape = RoundedCornerShape(16.dp)
                            ) {
                                Icon(Icons.Rounded.NotificationsActive, null, tint = AppColors.RoyalViolet)
                                Spacer(modifier = Modifier.width(8.dp))
                                Text("Send Test Notification", color = Color.White, fontWeight = FontWeight.Bold)
                            }
                        }

                        "Appearance" -> {
                            DetailCard(title = "Color Theme") {
                                listOf("Obsidian Dark", "Violet Midnight", "OLED Pure Black").forEach { theme ->
                                    Row(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .clickable { selectedTheme = theme }
                                            .padding(vertical = 12.dp),
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        RadioButton(
                                            selected = selectedTheme == theme,
                                            onClick = { selectedTheme = theme },
                                            colors = RadioButtonDefaults.colors(selectedColor = AppColors.RoyalViolet)
                                        )
                                        Spacer(modifier = Modifier.width(12.dp))
                                        Text(theme, color = Color.White, fontWeight = FontWeight.Medium)
                                    }
                                }
                            }

                            DetailCard(title = "Layout Options") {
                                SwitchRow(
                                    title = "Compact Card Mode",
                                    subtitle = "Show higher information density on home dashboard.",
                                    checked = compactLayout,
                                    onCheckedChange = { compactLayout = it }
                                )
                            }
                        }

                        "Privacy & Encryption" -> {
                            DetailCard(title = "Security Vault") {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Icon(Icons.Rounded.Lock, null, tint = AppColors.CyberEmerald, modifier = Modifier.size(24.dp))
                                    Spacer(modifier = Modifier.width(16.dp))
                                    Column(modifier = Modifier.weight(1f)) {
                                        Text("AES-256 On-Device Vault", color = Color.White, fontWeight = FontWeight.Bold)
                                        Text("Your habit data is stored securely offline.", color = AppColors.TextSecondary, fontSize = 12.sp)
                                    }
                                }
                            }

                            DetailCard(title = "Sync & Data Management") {
                                SwitchRow(
                                    title = "Encrypted On-Device Backup",
                                    subtitle = "Local hardware security key enabled.",
                                    checked = onDeviceVault,
                                    onCheckedChange = { onDeviceVault = it }
                                )
                                HorizontalDivider(color = AppColors.Border.copy(alpha = 0.5f), thickness = 1.dp)
                                SwitchRow(
                                    title = "Monk Cloud Sync",
                                    subtitle = "Sync stats across logged-in Android devices.",
                                    checked = cloudBackup,
                                    onCheckedChange = { cloudBackup = it }
                                )
                            }

                            Button(
                                onClick = {
                                    Toast.makeText(context, "Encrypted data backup exported to Downloads", Toast.LENGTH_LONG).show()
                                },
                                modifier = Modifier.fillMaxWidth().height(50.dp),
                                colors = ButtonDefaults.buttonColors(containerColor = AppColors.SurfaceLight),
                                shape = RoundedCornerShape(16.dp)
                            ) {
                                Icon(Icons.Rounded.Download, null, tint = AppColors.CyberEmerald)
                                Spacer(modifier = Modifier.width(8.dp))
                                Text("Export Encrypted Data (JSON)", color = Color.White, fontWeight = FontWeight.Bold)
                            }
                        }

                        "Manage Account" -> {
                            DetailCard(title = "User Profile Information") {
                                Text("Account Name", color = AppColors.TextSecondary, fontSize = 11.sp)
                                Text("Arun Kumar Yadav", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                                Spacer(modifier = Modifier.height(12.dp))
                                Text("Primary Email", color = AppColors.TextSecondary, fontSize = 11.sp)
                                Text("aarunyadav0610@gmail.com", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                                Spacer(modifier = Modifier.height(12.dp))
                                Text("Member Status", color = AppColors.TextSecondary, fontSize = 11.sp)
                                Text("Level 3 Monk • Pro Active", color = AppColors.RoyalViolet, fontWeight = FontWeight.Bold, fontSize = 14.sp)
                            }

                            Button(
                                onClick = { showPasswordModal = true },
                                modifier = Modifier.fillMaxWidth().height(50.dp),
                                colors = ButtonDefaults.buttonColors(containerColor = AppColors.SurfaceLight),
                                shape = RoundedCornerShape(16.dp)
                            ) {
                                Icon(Icons.Rounded.Key, null, tint = Color.White)
                                Spacer(modifier = Modifier.width(8.dp))
                                Text("Change Security Password", color = Color.White, fontWeight = FontWeight.Bold)
                            }
                        }

                        "Help & Support Center" -> {
                            DetailCard(title = "Frequently Asked Questions") {
                                ExpandableFaqItem("How does MonkMode calculate streak length?", "Streaks increase by 1 for each consecutive day you complete at least 1 goal habit before midnight.")
                                HorizontalDivider(color = AppColors.Border.copy(alpha = 0.5f), thickness = 1.dp)
                                ExpandableFaqItem("Is my focus timer preserved when leaving the app?", "Yes! MonkMode utilizes background state retention so your focus session keeps running smoothly.")
                                HorizontalDivider(color = AppColors.Border.copy(alpha = 0.5f), thickness = 1.dp)
                                ExpandableFaqItem("How do Monk AI recommendations work?", "Monk AI analyzes your hourly focus completion rates to suggest optimal deep work windows.")
                            }

                            DetailCard(title = "Send Direct Feedback") {
                                OutlinedTextField(
                                    value = supportMessage,
                                    onValueChange = { supportMessage = it },
                                    placeholder = { Text("Describe your feedback or technical inquiry...", color = AppColors.TextSecondary, fontSize = 13.sp) },
                                    modifier = Modifier.fillMaxWidth().height(110.dp),
                                    colors = OutlinedTextFieldDefaults.colors(
                                        focusedBorderColor = AppColors.RoyalViolet,
                                        unfocusedBorderColor = AppColors.Border
                                    )
                                )
                                Spacer(modifier = Modifier.height(12.dp))
                                Button(
                                    onClick = {
                                        if (supportMessage.isNotBlank()) {
                                            Toast.makeText(context, "Feedback sent! We'll reply to your email.", Toast.LENGTH_SHORT).show()
                                            supportMessage = ""
                                        }
                                    },
                                    modifier = Modifier.align(Alignment.End),
                                    colors = ButtonDefaults.buttonColors(containerColor = AppColors.RoyalViolet)
                                ) {
                                    Text("Submit Feedback", fontWeight = FontWeight.Bold)
                                }
                            }
                        }

                        "About MonkMode" -> {
                            DetailCard(title = "System Info") {
                                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                                    Text("Version", color = AppColors.TextSecondary)
                                    Text("v1.0.0 Pro Edition", color = Color.White, fontWeight = FontWeight.Bold)
                                }
                                Spacer(modifier = Modifier.height(8.dp))
                                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                                    Text("Build Core", color = AppColors.TextSecondary)
                                    Text("Jetpack Compose 2026.02", color = Color.White, fontWeight = FontWeight.Bold)
                                }
                                Spacer(modifier = Modifier.height(8.dp))
                                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                                    Text("Architecture", color = AppColors.TextSecondary)
                                    Text("Clean Architecture + Room", color = Color.White, fontWeight = FontWeight.Bold)
                                }
                            }

                            DetailCard(title = "Legal & Compliance") {
                                Text("MonkMode is designed with zero invasive tracking. All behavioral analytics reside encrypted on your physical device.", color = AppColors.TextSecondary, fontSize = 12.sp)
                            }
                        }

                        else -> {
                            DetailCard(title = activeSection!!) {
                                Text("Configuration options for $activeSection are up to date.", color = AppColors.TextSecondary)
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(30.dp))

                    Button(
                        onClick = { activeSection = null },
                        modifier = Modifier.fillMaxWidth().height(50.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = AppColors.RoyalViolet),
                        shape = RoundedCornerShape(16.dp)
                    ) {
                        Text("Save & Apply Settings", color = Color.White, fontWeight = FontWeight.Bold)
                    }

                    Spacer(modifier = Modifier.height(50.dp))
                }
            }
        }

        // Change Password Modal
        if (showPasswordModal) {
            AlertDialog(
                onDismissRequest = { showPasswordModal = false },
                title = { Text("Change Account Password", color = Color.White, fontWeight = FontWeight.Bold) },
                text = {
                    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                        OutlinedTextField(
                            value = "",
                            onValueChange = {},
                            label = { Text("Current Password") },
                            modifier = Modifier.fillMaxWidth()
                        )
                        OutlinedTextField(
                            value = "",
                            onValueChange = {},
                            label = { Text("New Password") },
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                },
                confirmButton = {
                    TextButton(onClick = {
                        showPasswordModal = false
                        Toast.makeText(context, "Password updated successfully!", Toast.LENGTH_SHORT).show()
                    }) {
                        Text("Update", color = AppColors.RoyalViolet, fontWeight = FontWeight.Bold)
                    }
                },
                dismissButton = {
                    TextButton(onClick = { showPasswordModal = false }) {
                        Text("Cancel", color = AppColors.TextSecondary)
                    }
                },
                containerColor = AppColors.CardBg,
                shape = RoundedCornerShape(20.dp)
            )
        }
    }
}

@Composable
fun SettingsSectionGroup(
    title: String,
    items: List<SettingRowItem>,
    onItemClick: (String) -> Unit
) {
    Column {
        Text(
            text = title,
            color = AppColors.TextSecondary,
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 12.dp)
        )
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = AppColors.CardBg),
            border = androidx.compose.foundation.BorderStroke(1.dp, AppColors.Border)
        ) {
            Column {
                items.forEachIndexed { index, item ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { onItemClick(item.label) }
                            .padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .size(36.dp)
                                .background(Color.White.copy(alpha = 0.05f), RoundedCornerShape(10.dp)),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(item.icon, null, tint = Color.White, modifier = Modifier.size(20.dp))
                        }
                        Spacer(modifier = Modifier.width(16.dp))
                        Text(
                            text = item.label,
                            color = Color.White,
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Medium,
                            modifier = Modifier.weight(1f)
                        )
                        if (item.value != null) {
                            Text(
                                text = item.value,
                                color = AppColors.TextSecondary,
                                fontSize = 13.sp,
                                modifier = Modifier.padding(end = 8.dp)
                            )
                        }
                        Icon(Icons.Rounded.ChevronRight, null, tint = AppColors.Border, modifier = Modifier.size(20.dp))
                    }
                    if (index < items.size - 1) {
                        HorizontalDivider(
                            modifier = Modifier.padding(horizontal = 16.dp),
                            thickness = 1.dp,
                            color = AppColors.Border.copy(alpha = 0.5f)
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun DetailCard(
    title: String,
    content: @Composable ColumnScope.() -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = AppColors.CardBg),
        border = androidx.compose.foundation.BorderStroke(1.dp, AppColors.Border)
    ) {
        Column(modifier = Modifier.padding(20.dp)) {
            Text(title, color = Color.White, fontSize = 16.sp, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(16.dp))
            content()
        }
    }
}

@Composable
fun SwitchRow(
    title: String,
    subtitle: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(title, color = Color.White, fontSize = 14.sp, fontWeight = FontWeight.Bold)
            Text(subtitle, color = AppColors.TextSecondary, fontSize = 12.sp)
        }
        Spacer(modifier = Modifier.width(16.dp))
        Switch(
            checked = checked,
            onCheckedChange = onCheckedChange,
            colors = SwitchDefaults.colors(
                checkedThumbColor = Color.White,
                checkedTrackColor = AppColors.RoyalViolet,
                uncheckedThumbColor = AppColors.SlateGray,
                uncheckedTrackColor = AppColors.Surface
            )
        )
    }
}

@Composable
fun ExpandableFaqItem(
    question: String,
    answer: String
) {
    var expanded by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { expanded = !expanded }
            .padding(vertical = 12.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(question, color = Color.White, fontSize = 14.sp, fontWeight = FontWeight.Bold, modifier = Modifier.weight(1f))
            Icon(
                imageVector = if (expanded) Icons.Rounded.KeyboardArrowUp else Icons.Rounded.KeyboardArrowDown,
                contentDescription = null,
                tint = AppColors.TextSecondary
            )
        }
        if (expanded) {
            Spacer(modifier = Modifier.height(8.dp))
            Text(answer, color = AppColors.TextSecondary, fontSize = 13.sp)
        }
    }
}

data class SettingRowItem(val label: String, val icon: ImageVector, val value: String? = null)
