## 📱 Screenshots

<table>
  <tr>
    <td align="center">
      <img src="https://github.com/user-attachments/assets/af719114-07a5-46ef-af09-513e9ac64fd2" width="220" alt="Screenshot"/>
      
  </td>
   <td align="center">
  <img src="https://github.com/user-attachments/assets/5064e1e7-9ccc-46da-b3d5-d54635a37590" width="220" alt="Screenshot"/>
    </td>
    <td align="center">
  <img src="https://github.com/user-attachments/assets/bd55fa1b-4091-4970-9af7-b5f0f0ca5775" width="220" alt="Purple Progress Analytics Dashboard"/>
    </td>
  </tr>

  <tr>
    <td align="center">
      <img src="https://github.com/user-attachments/assets/e5ca1e3a-d939-4e20-b7f9-36acae9b6e24" width="220" alt="Twilight Mountain Focus Timer"/>
    </td>
    <td align="center">
      <img src="https://github.com/user-attachments/assets/3274f06b-fb20-4b62-a506-cbbba085d413" width="220" alt="MonkMode Focus and Discipline Dashboard"/>
    </td>
    <td align="center">
      <img src="https://github.com/user-attachments/assets/02231183-dd28-4651-ac34-0ec2695ceb15" width="220" alt="Monk AI Meditation Coach Interface"/>
    </td>
  </tr>

  <tr>
    <td align="center">
      <img src="https://github.com/user-attachments/assets/1935093c-190e-4d7a-b9ab-4f9aec814989" width="220" alt="Dark Purple Productivity Profile Dashboard"/>
    </td>
  </tr>
</table>
# MonkMode

### Build Discipline. Improve Focus. Stay Consistent.

MonkMode is a modern Android productivity and habit-tracking application designed to help users build consistent habits, improve focus, monitor daily progress, and develop a disciplined lifestyle.

Unlike a basic habit tracker, MonkMode analyzes the user's habits, routine, progress, and consistency to identify areas that need improvement. It helps users understand their weaknesses, provides actionable suggestions, and gradually builds a more personalized routine based on their behavior and goals.

## ✨ Features

- 🧠 **Smart Habit Tracking**
  - Track daily habits and monitor consistency over time.
  - Understand which habits are improving and which need attention.

- 🎯 **Personalized Routine**
  - Build a routine around the user's habits, goals, and daily progress.
  - Adapt the experience based on individual consistency and performance.

- 🔍 **Weakness & Progress Detection**
  - Identify patterns in missed habits, low consistency, and productivity gaps.
  - Highlight areas where the user needs improvement.

- 💡 **Actionable Improvement Suggestions**
  - Provide practical suggestions to improve weak areas.
  - Help users understand what they can change in their daily routine.

- ⏱️ **Focus Timer**
  - Track focused work sessions and improve deep-work consistency.

- 📊 **Daily Statistics & Insights**
  - Monitor daily activity, focus time, habit completion, and overall progress.

- 🔥 **Habit Streaks**
  - Maintain streaks and build consistency through daily progress.

- 🏆 **Achievements & Badges**
  - Earn badges as you progress through different levels.
  - Reward consistency, completed goals, streaks, and personal milestones.

- 📈 **Progress Levels**
  - Progress through different levels based on your consistency and performance.
  - Turn daily discipline into measurable progress.

- 🔔 **Smart Notifications**
  - Receive reminders to stay consistent with your habits and routine.

- 🔐 **Firebase Authentication**
  - Secure user authentication and account management.

- 💾 **Local Data Storage**
  - Store habit and productivity data locally using Room Database.

- 🤖 **AI Coach — Coming Soon**
  - Personalized AI-based coaching and recommendations.
  - Analyze user behavior and provide more intelligent improvement strategies.

  > **MonkMode is not just about tracking what you do — it's about understanding your patterns, identifying where you fall short, and helping you build a better routine.**

  ## 🛠️ Tech Stack

MonkMode is built using a modern native Android development stack focused on scalability, maintainability, reactive state management, and a clean user experience.

### 🚀 Language & Core

| Technology | Details |
|---|---|
| **Kotlin** | Kotlin 2.2.x — Primary programming language |
| **Android SDK** | minSdk 24 — Android 7.0+ |
| **Target / Compile SDK** | SDK 36 |
| **JDK** | Java 11 |
| **Build System** | Gradle Kotlin DSL (`build.gradle.kts`) |

### 🎨 UI & Design

| Technology | Usage |
|---|---|
| **Jetpack Compose** | Declarative and modern Android UI development |
| **Material 3** | Modern design system and UI components |
| **Material Icons Extended** | Extended icon library |
| **Compose Animation API** | UI animations and transitions |
| **animateFloatAsState** | Smooth state-based animations |
| **animateColorAsState** | Dynamic color transitions |
| **Crossfade** | Screen/content transition animations |

### 🏗️ Architecture & State Management

MonkMode follows a **Clean Architecture + MVVM** approach to keep the application modular, maintainable, and scalable.

| Technology | Usage |
|---|---|
| **MVVM** | Separation of UI, business logic, and state |
| **Clean Architecture** | Structured separation of presentation, data, and domain responsibilities |
| **ViewModel** | Lifecycle-aware state and business logic management |
| **Kotlin Coroutines** | Asynchronous and non-blocking operations |
| **Kotlin Flow** | Reactive data streams |
| **StateFlow** | Observable UI state management |
| **SharedFlow** | One-time events and reactive communication |
| **collectAsState** | Collecting reactive state inside Compose |
| **Lifecycle ViewModel Compose** | Lifecycle-aware ViewModel integration |

### 💾 Local Data & Persistence

| Technology | Usage |
|---|---|
| **Room Database 2.7.2** | Local structured data persistence |
| **Room KTX** | Kotlin and Coroutines integration with Room |
| **KSP** | Compile-time code generation for Room |
| **DataStore / Preferences** | Lightweight user preferences and focus settings |

### ☁️ Backend, Cloud & Authentication

| Technology | Usage |
|---|---|
| **Firebase Authentication** | User registration, login, and authentication |
| **Firebase App Check** | Application integrity and abuse protection |
| **Firebase BOM** | Centralized Firebase dependency management |

### 🧠 AI & Networking

| Technology | Usage |
|---|---|
| **Google Generative AI SDK** | AI-powered coaching and intelligent assistance |
| **Retrofit 2.11.0** | REST API communication |
| **Gson 2.13.1** | JSON serialization and deserialization |
| **Retrofit Gson Converter** | JSON conversion for API responses |

> **AI Coach:** The AI coaching module is currently under development and temporarily disabled. It will be re-enabled after the API integration and security configuration are finalized.

### 🧭 Navigation & Background Processing

| Technology | Usage |
|---|---|
| **Navigation Compose 2.9.0** | Type-safe in-app navigation and screen transitions |
| **Deep Link Support** | Navigation to specific app destinations |
| **Back Stack State Preservation** | Maintaining navigation state across screens |
| **WorkManager KTX 2.10.2** | Reliable background task execution |
| **Notification Scheduler** | Habit and productivity reminders |

### 🛠️ Build & Development Tools

| Tool | Purpose |
|---|---|
| **Android Studio** | Android development environment |
| **Gradle Kotlin DSL** | Project and dependency configuration |
| **KSP** | Kotlin Symbol Processing and compile-time code generation |
| **Git & GitHub** | Version control and source code management |

---

## 🧩 Architecture Overview

MonkMode follows a **Clean Architecture + MVVM** structure:

```text
┌──────────────────────────────┐
│        Presentation Layer    │
│                              │
│  Jetpack Compose UI          │
│  Screens / Components        │
└──────────────┬───────────────┘
               │
               ▼
┌──────────────────────────────┐
│        ViewModel Layer       │
│                              │
│  UI State / Business Logic   │
│  StateFlow / SharedFlow      │
└──────────────┬───────────────┘
               │
               ▼
┌──────────────────────────────┐
│        Repository Layer      │
│                              │
│  Data coordination           │
│  Business data operations    │
└──────────────┬───────────────┘
               │
        ┌──────┴──────┐
        ▼             ▼
┌─────────────┐ ┌──────────────┐
│ Room        │ │ Firebase     │
│ Database    │ │ Auth/Cloud   │
└─────────────┘ └──────────────┘

## 🏗️ Data Flow Architecture

MonkMode uses a reactive data flow built around **Room Database, DAO, Repository, ViewModel, Kotlin Flow, and Jetpack Compose**.

```text
┌─────────────────────────────────────────────────────────┐
│                 SQLite Database                         │
│                 Room Database Engine                    │
│                                                         │
│  Stores habits, habit logs, focus logs and app data    │
└───────────────────────────┬─────────────────────────────┘
                            │
                            │ Room Database Observation
                            ▼
┌─────────────────────────────────────────────────────────┐
│                    HabitDao                              │
│                    Room DAO                              │
│                                                         │
│  Exposes reactive data such as:                         │
│  Flow<List<HabitEntity>>, Flow<Int>, etc.               │
└───────────────────────────┬─────────────────────────────┘
                            │
                            │ Reactive Kotlin Flow
                            ▼
┌─────────────────────────────────────────────────────────┐
│               HabitRepository                            │
│                  Data Layer                              │
│                                                         │
│  • Coordinates data operations                          │
│  • Combines and transforms data streams                 │
│  • Maintains a single source of truth                   │
└───────────────────────────┬─────────────────────────────┘
                            │
                            │ Coroutines + Flow
                            ▼
┌─────────────────────────────────────────────────────────┐
│                    ViewModel                             │
│               Presentation Logic                         │
│                                                         │
│  • Collects repository data                             │
│  • Transforms Flow<T> into UI State                     │
│  • Exposes StateFlow / UI State                         │
└───────────────────────────┬─────────────────────────────┘
                            │
                            │ StateFlow Observation
                            ▼
┌─────────────────────────────────────────────────────────┐
│                 Jetpack Compose UI                       │
│                   Presentation Layer                     │
│                                                         │
│  Observes state using Compose state collection          │
│  and automatically recomposes when data changes         │
└─────────────────────────────────────────────────────────┘

Reactive Data Flow : 

User Interaction
       │
       ▼
Jetpack Compose UI
       │
       ▼
ViewModel
       │
       ▼
Repository
       │
       ▼
Room DAO
       │
       ▼
SQLite / Room Database
       │
       │ Database changes
       ▼
Kotlin Flow
       │
       ▼
ViewModel StateFlow
       │
       ▼
Compose UI Recomposition


## 📂 Project Structure

MonkMode follows a modular **Clean Architecture + MVVM** structure that separates data management, presentation logic, navigation, notifications, and UI design.

```text
com.example.monkmode/
│<img width="870" height="1808" alt="Dark Purple Productivity Profile Dashboard" src="https://github.com/user-attachments/assets/0ee1ebbb-fbd3-4a61-a473-706a8fa303a3" />
<img width="870" height="1808" alt="Monk AI Meditation Coach Interface" src="https://github.com/user-attachments/assets/62456eef-d091-4f0b-a67f-f2b70f473841" />
<img width="870" height="1808" alt="Purple Progress Analytics Dashboard" src="https://github.com/user-attachments/assets/c45f073b-b56e-4956-a4b4-162cf381faa5" />
<img width="870" height="1808" alt="Twilight Mountain Focus Timer" src="https://github.com/user-attachments/assets/eb3c4c76-460e-4205-8a9d-fe2068bb94fb" />
<img width="840" height="1871" alt="MonkMode_ Focus and Discipline Dashboard" src="https://github.com/user-attachments/assets/d913d3ea-4e54-4fd7-8e8a-54ea429cb6da" />

<img width="262" height="582" alt="Screenshot 2026-09-29 121441" src="https://github.com/user-attachments/assets/c6aa1089-03aa-4940-8609-779f9fc4cb5b" />
<img width="256" height="582" alt="Screenshot 2026-09-29 121430" src="https://github.com/user-attachments/assets/c5507e8c-e40a-4746-8333-60bfe3b1f889" />

├── MainActivity.kt
│   └── Application entry point and Compose initialization
│
├── data/
│   │
│   ├── auth/
│   │   └── AuthRepository.kt
│   │       └── Firebase Authentication and user session handling
│   │
│   ├── local/
│   │   ├── DatabaseProvider.kt
│   │   │   └── Room database instance provider
│   │   │
│   │   ├── MonkDatabase.kt
│   │   │   └── Room database configuration
│   │   │
│   │   ├── HabitEntity.kt
│   │   │   └── Habit database entity
│   │   │
│   │   ├── HabitLogEntity.kt
│   │   │   └── Daily habit completion history
│   │   │
│   │   ├── FocusLogEntity.kt
│   │   │   └── Focus session history
│   │   │
│   │   └── HabitDao.kt
│   │       └── Room DAO and reactive Flow queries
│   │
│   ├── preferences/
│   │   └── FocusPreferences.kt
│   │       └── Focus-related user preferences
│   │
│   └── repository/
│       └── HabitRepository.kt
│           └── Coordinates data operations and provides
│               a single source of truth for habit data
│
├── presentation/
│   │
│   ├── splash/
│   │   └── SplashScreen.kt
│   │       └── Splash screen and initial session handling
│   │
│   ├── login/
│   │   ├── LoginScreen.kt
│   │   ├── SignUpScreen.kt
│   │   └── LoginViewModel.kt
│   │       └── Authentication UI and state management
│   │
│   ├── main/
│   │   └── MainScreen.kt
│   │       └── Main application container and bottom navigation
│   │
│   ├── home/
│   │   ├── HomeScreen.kt
│   │   ├── HabitsScreen.kt
│   │   ├── HomeViewModel.kt
│   │   ├── HomeViewModelFactory.kt
│   │   ├── DopamineViewModel.kt
│   │   └── DopamineViewModelFactory.kt
│   │       └── Dashboard, habits and daily productivity state
│   │
│   ├── focus/
│   │   ├── FocusScreen.kt
│   │   ├── FocusViewModel.kt
│   │   └── FocusViewModelFactory.kt
│   │       └── Focus timer and deep-work state management
│   │
│   ├── stats/
│   │   ├── StatsScreen.kt
│   │   ├── StatsViewModel.kt
│   │   ├── StatsViewModelFactory.kt
│   │   ├── StatsCard.kt
│   │   └── InsightsScreen.kt
│   │       └── Productivity statistics and progress insights
│   │
│   ├── achievements/
│   │   ├── Achievement.kt
│   │   ├── AchievementCard.kt
│   │   └── AchievementsScreen.kt
│   │       └── Achievement and badge system
│   │
│   ├── ai/
│   │   ├── AiScreen.kt
│   │   ├── AiViewModel.kt
│   │   ├── ChatBubble.kt
│   │   └── ChatMessage.kt
│   │       └── AI coaching interface
│   │
│   ├── profile/
│   │   └── ProfileScreen.kt
│   │       └── User profile and progress information
│   │
│   ├── settings/
│   │   └── SettingsScreen.kt
│   │       └── Application and user preferences
│   │
│   └── components/
│       ├── BottomNavBar.kt
│       ├── FocusTimerCard.kt
│       ├── HabitTile.kt
│       ├── HeroBanner.kt
│       ├── MonkButton.kt
│       ├── PremiumFAB.kt
│       ├── PremiumMetricCard.kt
│       ├── PremiumTopBar.kt
│       └── SecttionTitle.kt
│           └── Reusable Compose UI components
│
├── navigation/
│   ├── Screen.kt
│   │   └── Application navigation routes
│   │
│   └── AppNavigation.kt
│       └── Root NavHost and screen transitions
│
├── notifications/
│   ├── NotificationHelper.kt
│   ├── NotificationScheduler.kt
│   └── ReminderWorker.kt
│       └── Notifications, reminders and background scheduling
│
└── ui/
    └── theme/
        ├── AppColors.kt
        ├── AppDimens.kt
        ├── AppShapes.kt
        ├── AppTypography.kt
        ├── Color.kt
        ├── Theme.kt
        └── Type.kt
            └── Material 3 design system and theme configuration
