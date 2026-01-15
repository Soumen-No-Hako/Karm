# Karm - Android Native Application

A lightweight, local-first project management and productivity application for Android, converted from the Spring Boot web application.

## Overview

Karm is a native Android application that helps you organize projects, track work items (tasks), manage statuses, and add comments/notes. All data is stored locally on your device in JSON files for complete privacy and offline access.

## Features

- ✅ **Project Management**: Create and organize multiple projects with descriptions and statuses
- ✅ **Work Item Tracking**: Break projects down into individual work items (tasks)
- ✅ **Status Management**: Track progress with statuses (NEW, TO-DO, IN-PROGRESS, DONE, CANCELED)
- ✅ **Comment System**: Add timestamped comments/notes to work items
- ✅ **Sorting**: Sort projects by last modified, status, work item count, or name
- ✅ **Material Design 3**: Modern UI built with Jetpack Compose
- ✅ **Local Storage**: All data stored in JSON files on device
- ✅ **Offline First**: No internet connection required

## Tech Stack

### Core
- **Kotlin** - Modern programming language for Android
- **Jetpack Compose** - Modern declarative UI framework
- **Material Design 3** - Latest Material Design guidelines

### Architecture
- **MVC Pattern** - Simple Model-View-Controller architecture
- **Navigation Component** - Type-safe navigation between screens
- **JSON Storage** - Gson for JSON serialization/deserialization

### Libraries
- `androidx.core:core-ktx` - Kotlin extensions for Android
- `androidx.compose.material3` - Material Design 3 components
- `androidx.navigation:navigation-compose` - Navigation for Compose
- `com.google.code.gson` - JSON parsing (same as Spring Boot version)
- `androidx.lifecycle:lifecycle-viewmodel-compose` - ViewModel support

## Requirements

- **Android Studio**: Hedgehog (2023.1.1) or later
- **Minimum SDK**: API 24 (Android 7.0 Nougat)
- **Target SDK**: API 34 (Android 14)
- **Compile SDK**: API 34
- **Gradle**: 8.2
- **Kotlin**: 1.9.20
- **Java**: 17

## Project Structure

```
karm-android/
├── app/
│   ├── src/main/
│   │   ├── java/com/nemuos/karm/
│   │   │   ├── model/              # Data models
│   │   │   │   ├── Project.kt
│   │   │   │   ├── WorkItem.kt
│   │   │   │   └── Status.kt
│   │   │   ├── storage/            # Data persistence
│   │   │   │   ├── KarmStorage.kt
│   │   │   │   └── DataManager.kt
│   │   │   ├── ui/
│   │   │   │   ├── screens/        # App screens
│   │   │   │   │   ├── DashboardScreen.kt
│   │   │   │   │   ├── ProjectDetailScreen.kt
│   │   │   │   │   └── WorkItemDetailScreen.kt
│   │   │   │   ├── components/     # Reusable UI components
│   │   │   │   │   └── StatusBadge.kt
│   │   │   │   └── theme/          # App theming
│   │   │   │       ├── Color.kt
│   │   │   │       ├── Theme.kt
│   │   │   │       └── Type.kt
│   │   │   ├── MainActivity.kt
│   │   │   └── KarmApplication.kt
│   │   ├── res/                    # Resources
│   │   │   ├── values/
│   │   │   │   ├── strings.xml
│   │   │   │   ├── colors.xml
│   │   │   │   └── themes.xml
│   │   │   └── xml/
│   │   │       ├── backup_rules.xml
│   │   │       └── data_extraction_rules.xml
│   │   └── AndroidManifest.xml
│   ├── build.gradle.kts
│   └── proguard-rules.pro
├── gradle/
│   └── wrapper/
│       └── gradle-wrapper.properties
├── build.gradle.kts
├── settings.gradle.kts
├── gradle.properties
└── README.md
```

## Setup Instructions

### 1. Prerequisites

Ensure you have the following installed:
- Android Studio (latest version)
- Android SDK with API 34
- Java Development Kit (JDK) 17

### 2. Open Project in Android Studio

1. Launch Android Studio
2. Select **File → Open**
3. Navigate to the `karm-android` directory
4. Click **OK**

Android Studio will automatically:
- Download Gradle dependencies
- Sync the project
- Index files

### 3. Add App Icons (Required)

The project is missing launcher icons. You need to add them:

**Option A: Use Android Studio's Asset Studio**
1. Right-click `app/src/main/res` folder
2. Select **New → Image Asset**
3. Choose **Launcher Icons**
4. Upload your icon image or use clipart
5. Click **Next** then **Finish**

**Option B: Manual Icon Setup**
Place icon files in these directories:
- `app/src/main/res/mipmap-mdpi/ic_launcher.png` (48x48)
- `app/src/main/res/mipmap-hdpi/ic_launcher.png` (72x72)
- `app/src/main/res/mipmap-xhdpi/ic_launcher.png` (96x96)
- `app/src/main/res/mipmap-xxhdpi/ic_launcher.png` (144x144)
- `app/src/main/res/mipmap-xxxhdpi/ic_launcher.png` (192x192)

For round icons, add `ic_launcher_round.png` to each directory.

### 4. Build the Project

**Via Android Studio:**
- Select **Build → Make Project** (or press `Ctrl+F9` / `Cmd+F9`)

**Via Command Line:**
```bash
cd karm-android
./gradlew build
```

### 5. Run on Device/Emulator

**Physical Device:**
1. Enable Developer Options on your Android device
2. Enable USB Debugging
3. Connect device via USB
4. Click **Run** (green play button) in Android Studio
5. Select your device

**Emulator:**
1. Open **AVD Manager** in Android Studio
2. Create a new Virtual Device (API 24+)
3. Click **Run** and select the emulator

## Building APK

### Debug APK (for testing)
```bash
cd karm-android
./gradlew assembleDebug
```
APK location: `app/build/outputs/apk/debug/app-debug.apk`

### Release APK (for distribution)
```bash
./gradlew assembleRelease
```
APK location: `app/build/outputs/apk/release/app-release.apk`

**Note**: For release builds, you need to set up signing configuration in `app/build.gradle.kts`.

## Data Storage

### Location
- Data is stored in the app's internal storage: `/data/data/com.nemuos.karm/files/karm_data/`
- Files:
  - `projects.json` - All project data
  - `workitems.json` - All work item data

### Backup
- Data is automatically backed up via Android Auto Backup (if enabled)
- Backup rules defined in `res/xml/backup_rules.xml`

### Manual Data Export (Future Enhancement)
Currently, data is only accessible via internal storage. A future version could add:
- Export to external storage
- Import/export functionality
- Cloud sync options

## Key Differences from Spring Boot Version

| Feature | Spring Boot | Android |
|---------|-------------|---------|
| **Platform** | Web (localhost) | Native Android |
| **UI Framework** | Thymeleaf + Bootstrap | Jetpack Compose |
| **Architecture** | Spring MVC | Simple MVC |
| **Navigation** | HTTP URLs | Navigation Component |
| **Storage Location** | `%LOCALAPPDATA%\Karm\data\` | Internal app storage |
| **Data Access** | Static variables (not thread-safe) | DataManager singleton |
| **Language** | Java 17 | Kotlin |
| **Server** | Runs on 127.0.0.2:8080 | No server needed |

## Architecture Details

### Data Flow
1. **UI Layer** (Compose Screens) → User interactions
2. **Controller Layer** (DataManager) → Business logic
3. **Storage Layer** (KarmStorage) → JSON file I/O
4. **Model Layer** (Data Classes) → Domain objects

### Key Components

**KarmApplication**
- Singleton application class
- Initializes DataManager on app startup
- Provides global access to data manager

**DataManager**
- Central controller for all CRUD operations
- Manages projects and work items
- Handles sorting and filtering
- Thread-safe operations (unlike Spring Boot version)

**KarmStorage**
- Low-level JSON file operations
- Uses Gson for serialization
- Handles file creation and error handling

### Navigation Structure
```
DashboardScreen (/)
  ├─→ ProjectDetailScreen (/project/{id})
  │     └─→ WorkItemDetailScreen (/workitem/{id})
  └─→ [Back navigation]
```

## Advantages Over Web Version

✅ **No Server Required** - Runs natively, no localhost setup
✅ **Mobile Optimized** - Touch-friendly UI, swipe gestures
✅ **Always Available** - No need to start a server
✅ **Better Performance** - Native code execution
✅ **Offline First** - Works without internet
✅ **Material Design** - Modern Android UI patterns
✅ **Type Safety** - Kotlin's null-safety features
✅ **Better Data Management** - Proper singleton pattern instead of static variables

## Future Enhancements

- [ ] Add edit/delete functionality for projects and work items
- [ ] Implement search functionality
- [ ] Add data export/import features
- [ ] Dark mode toggle (currently follows system)
- [ ] Add animations and transitions
- [ ] Implement undo/redo functionality
- [ ] Add widgets for quick access
- [ ] Integrate with Android Wear
- [ ] Add notification reminders for tasks
- [ ] Implement tags/labels system
- [ ] Add data migration from web version

## Troubleshooting

### Build Errors

**"Cannot resolve symbol R"**
- Solution: **Build → Clean Project**, then **Build → Rebuild Project**

**"Gradle sync failed"**
- Check internet connection
- Verify Gradle version compatibility
- Invalidate caches: **File → Invalidate Caches / Restart**

**Missing launcher icons**
- Follow the "Add App Icons" instructions above

### Runtime Errors

**App crashes on launch**
- Check Logcat for stack traces
- Verify minimum SDK is API 24+
- Ensure Gson is properly included in dependencies

**Data not persisting**
- Check app has proper storage permissions
- Verify KarmStorage is creating files correctly
- Check Logcat for IOException messages

## Contributing

Contributions are welcome! Areas for improvement:
1. Add unit tests (currently none)
2. Implement Room Database (instead of JSON files)
3. Add proper error handling with user-friendly messages
4. Implement Material Design animations
5. Add accessibility features (content descriptions, TalkBack support)

## License

This project is part of the Karm productivity suite. See main repository for license information.

## Credits

- Original Spring Boot version by Nemuos
- Android conversion maintains the same data model and business logic
- Built with modern Android development best practices

## Support

For issues or questions:
1. Check this README
2. Review Android Studio's Logcat output
3. Check the main Karm repository
4. File an issue with detailed error messages

---

**Version**: 1.0.0
**Last Updated**: 2026-01-15
**Minimum Android Version**: 7.0 (Nougat)
**Target Android Version**: 14
