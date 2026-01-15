# Karm Android Conversion Guide

## Overview

This document describes the Android native conversion of the Karm project from a Spring Boot web application to a native Android app.

## Repository Structure

```
Karm/
├── src/                    # Original Spring Boot web application
│   ├── main/java/          # Java source code
│   └── main/resources/     # Web templates, static resources
├── karm-android/           # NEW: Android native application
│   ├── app/
│   │   ├── src/main/       # Android app source
│   │   └── build.gradle.kts
│   ├── build.gradle.kts
│   └── README.md           # Detailed Android setup instructions
├── pom.xml                 # Maven config (Spring Boot)
└── README.md               # Original project README
```

## Two Versions Available

### 1. Spring Boot Web Application (Original)
- **Location**: Root directory (`src/`, `pom.xml`)
- **Platform**: Web (runs on localhost:8080)
- **Tech**: Java 17, Spring Boot, Thymeleaf, Bootstrap
- **Start**: `mvn clean package && java -jar target/karm.jar`
- **Access**: Browser at `http://127.0.0.2:8080`

### 2. Android Native Application (New)
- **Location**: `karm-android/` directory
- **Platform**: Android devices (API 24+)
- **Tech**: Kotlin, Jetpack Compose, Material Design 3
- **Build**: Open in Android Studio
- **Access**: Native Android app on phone/tablet

## Feature Comparison

| Feature | Web Version | Android Version |
|---------|------------|-----------------|
| Projects | ✅ | ✅ |
| Work Items | ✅ | ✅ |
| Comments | ✅ | ✅ |
| Status Management | ✅ | ✅ |
| Sorting | ✅ | ✅ |
| Edit/Delete | ⚠️ Partial | ⚠️ Partial |
| Offline Support | ❌ Requires server | ✅ Fully offline |
| Mobile Optimized | ⚠️ Responsive | ✅ Native mobile |
| Server Required | ✅ Yes | ❌ No |
| Data Storage | JSON files (Windows) | JSON files (Android) |

## Data Model Compatibility

Both versions use the same data model structure:

**Project**
- `projectId`: String
- `projectName`: String
- `projectDescription`: String
- `projectStatus`: String (NEW, TO-DO, IN-PROGRESS, DONE, CANCELED)
- `workItemIds`: List<String>
- `createdOn`: String (ISO timestamp)
- `lastModifiedOn`: String (ISO timestamp)

**WorkItem**
- `workItemId`: String
- `workItemTitle`: String
- `workItemDescription`: String
- `status`: String (TO-DO, IN-PROGRESS, DONE, CANCELED)
- `comments`: List<String>
- `createdOn`: String (ISO timestamp)
- `lastModifiedOn`: String (ISO timestamp)

## Key Architecture Differences

### Spring Boot Version
```
Browser ← HTTP → Spring Controller ← Static Variables → File I/O
                        ↓
                  Thymeleaf Templates
```

**Issues**:
- Static mutable state (not thread-safe)
- No proper service layer
- Platform-specific (Windows paths)

### Android Version
```
Compose UI ← DataManager (Singleton) ← KarmStorage ← File I/O
                    ↓
              Data Classes (Kotlin)
```

**Improvements**:
- Proper singleton pattern
- Thread-safe data management
- Platform-independent storage
- Type-safe navigation
- Kotlin null-safety

## Getting Started

### For Web Version
See main [README.md](README.md) in repository root.

### For Android Version
See [karm-android/README.md](karm-android/README.md) for detailed instructions.

**Quick Start:**
1. Open Android Studio
2. Open the `karm-android` directory
3. Add app icons (required)
4. Build and run on device/emulator

## Data Migration

Currently, data is **NOT automatically shared** between versions.

### Manual Migration Path
1. Export from web version: Copy files from `%LOCALAPPDATA%\Karm\data\`
   - `projects.json`
   - `workitems.json`

2. Import to Android:
   - Use `adb push` to copy files to device:
   ```bash
   adb push projects.json /data/data/com.nemuos.karm/files/karm_data/
   adb push workitems.json /data/data/com.nemuos.karm/files/karm_data/
   ```

   Or manually recreate projects/work items in the Android app.

### Future Enhancement
A sync feature could be added to share data between versions.

## Development

### Adding Features to Both Versions

When adding a new feature, consider implementing it in both:

1. **Data Model Changes**
   - Update Java classes in `src/main/java/.../model/`
   - Update Kotlin data classes in `karm-android/.../model/`

2. **Storage Changes**
   - Update file I/O in Spring controllers
   - Update `KarmStorage.kt` in Android

3. **UI Changes**
   - Update Thymeleaf templates for web
   - Update Compose screens for Android

## Advantages of Each Version

### Web Version Advantages
- ✅ No app installation required
- ✅ Cross-platform (any OS with Java)
- ✅ Familiar web browser interface
- ✅ Easy to debug with browser DevTools
- ✅ Can be deployed to a server

### Android Version Advantages
- ✅ Native mobile experience
- ✅ No server setup required
- ✅ Always available (no need to start server)
- ✅ Offline-first design
- ✅ Modern Material Design UI
- ✅ Better data management architecture
- ✅ Type-safe with Kotlin
- ✅ Optimized for touch input
- ✅ Portable (phone/tablet)

## Testing

### Web Version
- Start server: `mvn spring-boot:run`
- Access: `http://127.0.0.2:8080`
- Test on different browsers

### Android Version
- Build in Android Studio
- Run on emulator (API 24+)
- Test on physical device
- Check different screen sizes

## Building for Production

### Web Version (JAR)
```bash
mvn clean package
# Output: target/karm.jar
```

### Android Version (APK)
```bash
cd karm-android
./gradlew assembleRelease
# Output: app/build/outputs/apk/release/app-release.apk
```

## Known Issues

### Both Versions
- ❌ No authentication/authorization
- ❌ No tests (test coverage: 0%)
- ❌ Limited error handling
- ⚠️ Edit/Delete operations not fully implemented

### Web Version Only
- ❌ Static mutable state (thread-unsafe)
- ❌ Platform-dependent file paths
- ⚠️ Missing validation

### Android Version Only
- ⚠️ Missing launcher icons (must be added)
- ⚠️ No data migration from web version

## Recommendations

### For Personal Use
- **Android Version** - Better user experience, always available, no setup

### For Team/Multi-User
- **Web Version** (with fixes) - Can be deployed to a shared server
- But first fix: Remove static state, add authentication, use database

### For Learning
- Both! Compare architectures and learn Spring Boot vs Android development

## Next Steps

### Priority Improvements (Both Versions)
1. Add comprehensive unit tests
2. Implement proper error handling
3. Add input validation
4. Complete edit/delete functionality
5. Add search/filter features

### Web Version Specific
1. Remove static mutable state → Use Spring beans
2. Add service layer architecture
3. Replace JSON files with database (H2/SQLite)
4. Add Spring Security

### Android Version Specific
1. Add launcher icons
2. Consider migrating to Room Database
3. Add data export/import
4. Implement Material Design animations
5. Add widget support

## Contributing

To contribute to either version:
1. Fork the repository
2. Create a feature branch
3. Make your changes (maintain compatibility if possible)
4. Add tests
5. Submit a pull request

## Support

- **Web Version Issues**: Check main README.md
- **Android Version Issues**: Check karm-android/README.md
- **General Questions**: File an issue in the repository

---

**Document Version**: 1.0
**Last Updated**: 2026-01-15
**Android Conversion**: Complete
