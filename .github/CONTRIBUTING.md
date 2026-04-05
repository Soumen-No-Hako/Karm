# Contributing to Karm

Thank you for your interest in contributing to Karm! This guide will help you get started.

## 🏗️ Project Structure

Karm has two versions:

- **Web Version** (Spring Boot): `src/`, `pom.xml`
- **Android Version** (Kotlin): `karm-android/`

## 📥 Getting Started

### For Web Version

1. Fork and clone the repository
2. Install Java 17 and Maven
3. Run: `mvn spring-boot:run`
4. Access: `http://127.0.0.2:8080`

### For Android Version

1. Fork and clone the repository
2. Open `karm-android/` in Android Studio
3. Sync Gradle dependencies
4. Add launcher icons (required)
5. Build and run on device/emulator

## 🔄 CI/CD Workflows

### Android Build Workflow

**Triggered on**: Push to `main`, `develop`, `claude/**` branches, or PRs

The workflow automatically:
- ✅ Builds debug APK
- ✅ Uploads APK as artifact (30 days retention)
- ✅ Comments on PRs with download link

**View builds**: [Actions Tab](https://github.com/Soumen-No-Hako/Karm/actions/workflows/android-build.yml)

### Android Release Workflow

**Triggered on**: Git tags `v*.*.*` or `android-v*.*.*`, or manual dispatch

The workflow automatically:
- ✅ Builds debug and release APKs
- ✅ Generates SHA-256 checksums
- ✅ Creates GitHub Release with APKs
- ✅ Adds comprehensive release notes

**Create a release**:
```bash
git tag android-v1.0.0
git push origin android-v1.0.0
```

Or use manual workflow dispatch from Actions tab with version input.

## 🚀 Making Contributions

### Workflow for Contributors

1. **Fork** the repository
2. **Create a branch** for your feature/fix
   ```bash
   git checkout -b feature/your-feature-name
   ```

3. **Make your changes**
   - Follow existing code style
   - Add comments for complex logic
   - Update documentation if needed

4. **Test your changes**
   - Web: Run and test locally
   - Android: Build APK and test on device

5. **Commit** with clear messages
   ```bash
   git commit -m "feat: add new feature X"
   ```

6. **Push** to your fork
   ```bash
   git push origin feature/your-feature-name
   ```

7. **Create Pull Request**
   - Describe your changes
   - Reference related issues
   - Wait for CI checks to pass
   - Download and test the auto-built Android APK from PR artifacts

### CI/CD in Pull Requests

When you open a PR:

1. **Automated Checks Run**:
   - Android build workflow triggers
   - Builds debug APK
   - Uploads artifact

2. **Bot Comments on PR**:
   - Links to download APK
   - Installation instructions
   - Build status

3. **Manual Testing**:
   - Download APK from artifacts
   - Test on Android device
   - Verify changes work as expected

## 📝 Commit Message Guidelines

Use conventional commits format:

- `feat:` - New feature
- `fix:` - Bug fix
- `docs:` - Documentation changes
- `style:` - Code style changes (formatting)
- `refactor:` - Code refactoring
- `test:` - Adding tests
- `chore:` - Build process, dependencies

**Examples**:
```bash
feat: add search functionality to projects
fix: resolve crash on work item creation
docs: update Android installation guide
refactor: migrate to proper service layer
```

## 🧪 Testing

### Web Version
- Run application: `mvn spring-boot:run`
- Manual testing in browser
- TODO: Add unit tests (contributions welcome!)

### Android Version
- Build in Android Studio
- Test on emulator (API 24+)
- Test on physical device
- TODO: Add instrumented tests (contributions welcome!)

## 📋 Areas for Contribution

### High Priority

1. **Add Tests** (Critical!)
   - Unit tests for business logic
   - Integration tests for data storage
   - UI tests for Android screens
   - Currently: 0% test coverage 😢

2. **Fix Architecture Issues** (Web version)
   - Remove static mutable state
   - Add service layer
   - Implement proper error handling
   - Add input validation

3. **Complete Missing Features** (Both versions)
   - Edit functionality for projects/work items
   - Delete functionality
   - Search/filter capabilities

### Medium Priority

4. **Improve Android App**
   - Add proper launcher icons
   - Implement Material Design animations
   - Add data export/import
   - Consider migrating to Room Database

5. **Add Documentation**
   - API documentation
   - Architecture decision records
   - User guide
   - Developer guide

6. **Security Enhancements**
   - Add authentication (web version)
   - Input sanitization
   - Add validation annotations
   - Security audit

### Low Priority

7. **Nice to Have**
   - Dark mode support (Android)
   - Widget support (Android)
   - Data sync between versions
   - Cloud backup options
   - Multi-language support

## 🐛 Reporting Issues

When reporting issues, please include:

1. **Version**: Web or Android
2. **Environment**:
   - Web: Java version, OS
   - Android: Device model, Android version
3. **Steps to reproduce**
4. **Expected behavior**
5. **Actual behavior**
6. **Screenshots** (if applicable)
7. **Error logs** (if applicable)

## 📦 Releasing

### Creating a Release (Maintainers)

**For Android releases**:

```bash
# Update version in build.gradle.kts
# Commit version bump
git commit -m "chore: bump version to 1.1.0"

# Create and push tag
git tag android-v1.1.0
git push origin android-v1.1.0

# GitHub Actions will automatically:
# - Build APKs
# - Create release
# - Upload APKs with checksums
```

**For web releases**:
```bash
# Update version in pom.xml
# Build JAR
mvn clean package

# Create release manually on GitHub
# Upload target/karm.jar
```

## 🏆 Recognition

Contributors will be:
- Listed in release notes
- Mentioned in commit history
- Added to CONTRIBUTORS.md (future)
- Recognized in project documentation

## 📞 Questions?

- Open an issue for questions
- Check existing documentation
- Review closed issues/PRs

## 📄 Code of Conduct

- Be respectful and inclusive
- Provide constructive feedback
- Help others learn and grow
- Follow project conventions
- Test your changes thoroughly

## ✅ Pull Request Checklist

Before submitting a PR:

- [ ] Code follows project style
- [ ] Changes are tested (manual or automated)
- [ ] Documentation is updated
- [ ] Commit messages are clear
- [ ] No sensitive data (keys, passwords) included
- [ ] CI/CD checks pass
- [ ] APK builds successfully (for Android changes)
- [ ] Changes work in both debug and release builds

## 🙏 Thank You!

Your contributions help make Karm better for everyone. Whether it's fixing a typo, adding a feature, or reporting a bug - every contribution matters!

---

**Happy Coding!** 🚀
