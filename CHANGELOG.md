# Changelog

All notable changes to this project will be documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.0.0/),
and this project adheres to [Semantic Versioning](https://semver.org/spec/v2.0.0.html).

## [1.0.0] - 2025-01-07

### Added
- ✨ Initial release of TouchLock
- 🔒 Touch screen lock functionality via transparent overlay
- 📱 Notification bar quick control with toggle buttons
- 🎯 Physical buttons remain active during touch lock
- 🔄 Smart unlock after screen lock/unlock cycle
- 💡 Lightweight design focused on core functionality
- 🌙 E-ink screen device optimization
- 🛡️ Comprehensive permission management
- 📋 Persistent notification with action buttons
- ⚙️ Simple and intuitive user interface

### Features
- **Core Functionality**
  - One-tap touch screen lock/unlock
  - Transparent overlay service for touch interception
  - Notification service for quick access
  - Automatic state management

- **User Experience**
  - Easy permission setup workflow
  - Clear status indicators
  - Notification bar integration
  - Auto-restore on device unlock

- **Technical**
  - Android 7.0+ compatibility
  - Foreground service implementation
  - Screen state monitoring
  - Memory efficient operation

### Security
- All permissions clearly documented and justified
- No data collection or network access
- Local-only operation
- Open source for transparency

### Supported Devices
- ✅ E-readers with physical buttons (Hanvon, ONYX BOOX, etc.)
- ✅ Android tablets and phones
- ✅ E-ink and LCD displays
- ✅ Android 7.0 (API 24) and above

### Known Issues
- None reported in initial release

---

## Development Notes

### Version Naming
- **Major.Minor.Patch** format
- Major: Breaking changes or significant new features
- Minor: New features, backward compatible
- Patch: Bug fixes and small improvements

### Release Process
1. Update version in `app/build.gradle`
2. Update `CHANGELOG.md` with new changes
3. Create git tag with version number
4. Build and test release APK
5. Create GitHub release with APK attachment