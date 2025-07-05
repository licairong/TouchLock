# Contributing to TouchLock

First off, thank you for considering contributing to TouchLock! It's people like you that make TouchLock such a great tool for e-reader enthusiasts.

## Code of Conduct

This project and everyone participating in it is governed by our Code of Conduct. By participating, you are expected to uphold this code.

## How Can I Contribute?

### Reporting Bugs

Before creating bug reports, please check the existing issues to avoid duplicates. When you are creating a bug report, please include as many details as possible:

#### Bug Report Template
```
**Describe the bug**
A clear and concise description of what the bug is.

**To Reproduce**
Steps to reproduce the behavior:
1. Go to '...'
2. Click on '....'
3. Scroll down to '....'
4. See error

**Expected behavior**
A clear and concise description of what you expected to happen.

**Device Information:**
- Device: [e.g. Hanvon Clear 7 Turbo]
- OS: [e.g. Android 10]
- App Version: [e.g. 1.0.0]

**Screenshots**
If applicable, add screenshots to help explain your problem.

**Additional context**
Add any other context about the problem here.
```

### Suggesting Enhancements

Enhancement suggestions are tracked as GitHub issues. When creating an enhancement suggestion, please include:

- **Use a clear and descriptive title**
- **Provide a step-by-step description** of the suggested enhancement
- **Provide specific examples** to demonstrate the steps
- **Describe the current behavior** and **explain which behavior you expected to see instead**
- **Explain why this enhancement would be useful** to most TouchLock users

### Pull Requests

1. Fork the repo and create your branch from `main`.
2. If you've added code that should be tested, add tests.
3. Ensure the test suite passes.
4. Make sure your code lints.
5. Update documentation if needed.
6. Issue that pull request!

## Development Setup

### Prerequisites

- Android Studio Arctic Fox or newer
- JDK 8 or higher
- Android SDK API 33
- Git

### Setting Up Development Environment

1. **Clone the repository**
   ```bash
   git clone https://github.com/username/TouchLock.git
   cd TouchLock
   ```

2. **Open in Android Studio**
   - Launch Android Studio
   - Select "Open an existing Android Studio project"
   - Navigate to the cloned directory and select it

3. **Configure SDK**
   - Make sure Android SDK API 33 is installed
   - Set up an emulator or connect a physical device

4. **Build the project**
   ```bash
   ./gradlew assembleDebug
   ```

## Coding Standards

### Java Style Guide

- Follow [Google Java Style Guide](https://google.github.io/styleguide/javaguide.html)
- Use meaningful variable and method names
- Add comments for complex logic
- Keep methods focused and concise

### Android Best Practices

- Follow [Android development best practices](https://developer.android.com/guide)
- Use appropriate lifecycle methods
- Handle permissions properly
- Implement proper error handling

### Git Commit Messages

- Use the present tense ("Add feature" not "Added feature")
- Use the imperative mood ("Move cursor to..." not "Moves cursor to...")
- Limit the first line to 72 characters or less
- Reference issues and pull requests liberally after the first line

Example:
```
Add notification permission check for Android 13+

- Check POST_NOTIFICATIONS permission before showing notification
- Request permission if not granted
- Add user-friendly error message
- Update documentation with new permission requirement

Fixes #123
```

## Testing

### Manual Testing

Before submitting a PR, please test on:
- At least one e-reader device (if available)
- Android emulator with different API levels
- Different screen sizes and orientations

### Test Cases

- [ ] App installation and first launch
- [ ] Permission granting workflow
- [ ] Touch lock activation/deactivation
- [ ] Notification controls
- [ ] Screen lock/unlock behavior
- [ ] App lifecycle (background/foreground)

## Documentation

- Update README.md if you change functionality
- Add inline code comments for complex logic
- Update CHANGELOG.md with your changes
- Include JSDoc comments for public methods

## Release Process

Releases are handled by maintainers, but contributors should:

1. Update version number in `app/build.gradle`
2. Update `CHANGELOG.md` with changes
3. Test thoroughly on multiple devices
4. Create PR with release notes

## Questions?

Feel free to contact the maintainers:
- Open an issue for public discussion
- Check existing issues and discussions

## Recognition

Contributors will be recognized in:
- README.md acknowledgments
- Release notes
- Git commit history

Thank you for contributing to TouchLock! 🎉