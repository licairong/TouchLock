# TouchLock - Touch Screen Lock

<div align="center">

![TouchLock Logo](app/src/main/res/drawable/ic_touch_lock.xml)

**Touch screen lock app designed for e-readers**

[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)
[![API](https://img.shields.io/badge/API-24%2B-brightgreen.svg?style=flat)](https://android-arsenal.com/api?level=24)
[![Build Status](https://img.shields.io/badge/build-passing-brightgreen)](https://github.com)

English | [简体中文](README.md)

</div>

## 📖 Introduction

TouchLock is a touch screen lock application designed specifically for e-readers with physical buttons (such as Hanvon Clear 7 Turbo). By providing quick control buttons in the notification bar, it allows you to easily lock the touch screen while reading, avoiding accidental touches that affect your reading experience.

## ✨ Features

- 🔒 **One-tap touch lock** - Quick lock/unlock touch screen via notification bar buttons
- 📱 **Strong compatibility** - Supports all devices with Android 7.0+
- 🎯 **Physical buttons remain active** - Physical page-turn buttons still work when touch screen is locked
- 🔄 **Smart unlock** - Automatically restore touch function after screen lock/unlock
- 💡 **Lightweight** - Focused on core functionality with minimal resource usage
- 🌙 **E-ink friendly** - Specially optimized for e-ink screen devices

## 📱 Use Cases

- **E-readers** - Hanvon, ONYX BOOX, iReader and other devices with physical buttons
- **Tablet reading** - Avoid accidental touches during long reading sessions
- **One-handed operation** - Use with physical buttons for one-handed page turning
- **Bedtime reading** - Prevent palm touches when lying on your side

## 🚀 Quick Start

### Requirements

- Android 7.0 (API 24) or higher
- System alert window permission
- Notification permission (Android 13+)

### Installation

1. **Download and install**
   ```bash
   # Install via ADB
   adb install app-debug.apk
   
   # Or download and install directly on device
   ```

2. **Grant permissions**
   - Open the app
   - Tap "Grant Permission" button
   - Enable "Display over other apps" in system settings

3. **Enable notification control**
   - Return to app, tap "Enable Notification Control"
   - "Touch Lock Control" notification will appear in notification bar

### Usage

1. **Lock touch screen**
   - Pull down notification bar
   - Tap "Lock Touch" button
   - Touch screen is locked, only physical buttons work

2. **Unlock touch screen**
   - Pull down notification bar, tap "Unlock Touch" button
   - Or press power button to lock screen then unlock (auto restore)

3. **Disable feature**
   - Tap "Close" button in notification
   - Or tap "Disable Notification Control" in app

## 🏗️ Architecture

### Core Components

- **NotificationService** - Notification bar control service
- **OverlayService** - Transparent overlay service
- **TouchLockManager** - Lock state management
- **ScreenReceiver** - Screen state listener

### Implementation

1. **Transparent overlay** - Creates full-screen transparent window to intercept touch events
2. **Notification control** - Provides persistent notification with action buttons
3. **State management** - Unified lock state management and service coordination
4. **Auto restore** - Monitors screen lock events for smart unlock

### Permissions

```xml
<!-- System alert window - for creating touch lock overlay -->
<uses-permission android:name="android.permission.SYSTEM_ALERT_WINDOW" />

<!-- Foreground service - keep service running stably -->
<uses-permission android:name="android.permission.FOREGROUND_SERVICE" />

<!-- Notification permission - display control notification -->
<uses-permission android:name="android.permission.POST_NOTIFICATIONS" />

<!-- Screen state monitoring - implement auto unlock -->
<uses-permission android:name="android.permission.WAKE_LOCK" />
```

## 🔧 Development

### Requirements

- Android Studio Arctic Fox or higher
- JDK 8 or higher
- Android SDK API 33
- Gradle 8.5+

### Build

```bash
# Clone project
git clone https://github.com/licairong/TouchLock.git
cd TouchLock

# Build debug version
./gradlew assembleDebug

# Build release version
./gradlew assembleRelease
```

### Project Structure

```
app/src/main/
├── java/com/lockscreen/reader/
│   ├── MainActivity.java           # Main activity
│   ├── NotificationService.java    # Notification service
│   ├── OverlayService.java        # Overlay service
│   ├── TouchLockManager.java      # State manager
│   ├── ScreenReceiver.java        # Screen listener
│   └── PermissionHelper.java      # Permission helper
├── res/
│   ├── drawable/                  # Icon resources
│   ├── layout/                    # Layout files
│   ├── values/                    # Strings and styles
│   └── xml/                       # Config files
└── AndroidManifest.xml           # App manifest
```

## 🤝 Contributing

Contributions are welcome! Please follow these steps:

1. Fork this project
2. Create feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to branch (`git push origin feature/AmazingFeature`)
5. Create Pull Request

### Development Guidelines

- Follow Android development best practices
- Add appropriate comments and documentation
- Ensure code passes all tests
- Update relevant documentation

## 🐛 Issue Reporting

If you encounter issues or have suggestions:

1. Check [Issues](https://github.com/licairong/TouchLock/issues) for existing reports
2. Create new Issue with the following information:
   - Device model and Android version
   - App version
   - Detailed problem description
   - Log information if possible

## 📄 License

This project is licensed under the MIT License - see [LICENSE](LICENSE) file for details.

## 🙏 Acknowledgments

- Thanks to all contributors
- Special thanks to e-reader community for testing and feedback
- Icon design inspired by Material Design

## 📞 Contact

- **Homepage**: https://github.com/licairong/TouchLock
- **Issues**: https://github.com/licairong/TouchLock/issues
- **Discussions**: https://github.com/licairong/TouchLock/discussions

---

<div align="center">

**If this project helps you, please give it a ⭐ Star!**

Made with ❤️ for e-reader enthusiasts

</div>