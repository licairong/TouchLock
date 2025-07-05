# TouchLock - 触屏锁定

<div align="center">

![TouchLock Logo](app/src/main/res/drawable/ic_touch_lock.xml)

**专为电子阅读器设计的触屏锁定应用**

[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)
[![API](https://img.shields.io/badge/API-24%2B-brightgreen.svg?style=flat)](https://android-arsenal.com/api?level=24)
[![Build Status](https://img.shields.io/badge/build-passing-brightgreen)](https://github.com)

[English](README_EN.md) | 简体中文

</div>

## 📖 简介

TouchLock 是一个专为有物理按键的电子阅读器（如汉王 Clear 7 Turbo）设计的触屏锁定应用。通过在通知栏提供快捷控制按钮，让您在阅读时轻松锁定触屏，避免误触影响阅读体验。

## ✨ 特性

- 🔒 **一键锁定触屏** - 通过通知栏按钮快速锁定/解锁触屏
- 📱 **兼容性强** - 支持 Android 7.0+ 的所有设备
- 🎯 **物理按键保持有效** - 锁定触屏时，物理翻页按键依然可用
- 🔄 **智能解锁** - 按电源键锁屏再解锁后自动恢复触屏功能
- 💡 **轻量级** - 专注核心功能，占用资源少
- 🌙 **墨水屏友好** - 特别优化适配墨水屏设备

## 📱 使用场景

- **电子阅读器** - 汉王、文石、掌阅等有物理按键的设备
- **平板阅读** - 长时间阅读时避免误触
- **单手操作** - 配合物理按键实现单手翻页
- **床上阅读** - 防止侧躺时手掌误触屏幕

## 🚀 快速开始

### 安装要求

- Android 7.0 (API 24) 或更高版本
- 悬浮窗权限
- 通知权限（Android 13+）

### 安装步骤

1. **下载安装**
   ```bash
   # 使用 ADB 安装
   adb install app-debug.apk
   
   # 或直接下载安装到设备
   ```

2. **授予权限**
   - 打开应用
   - 点击"授予权限"按钮
   - 在系统设置中开启"显示在其他应用的上层"权限

3. **启用通知控制**
   - 返回应用，点击"启用通知栏控制"
   - 通知栏将显示"触屏锁定控制"通知

### 使用方法

1. **锁定触屏**
   - 下拉通知栏
   - 点击"锁定触屏"按钮
   - 触屏被锁定，只有物理按键有效

2. **解锁触屏**
   - 下拉通知栏，点击"解锁触屏"按钮
   - 或者按电源键锁屏再解锁（自动恢复）

3. **关闭功能**
   - 在通知中点击"关闭"按钮
   - 或在应用中点击"关闭通知栏控制"

## 🏗️ 技术架构

### 核心组件

- **NotificationService** - 通知栏控制服务
- **OverlayService** - 透明覆盖层服务
- **TouchLockManager** - 锁定状态管理
- **ScreenReceiver** - 屏幕状态监听

### 实现原理

1. **透明覆盖层** - 创建全屏透明窗口拦截触摸事件
2. **通知栏控制** - 提供持久通知和操作按钮
3. **状态管理** - 统一管理锁定状态和服务协调
4. **自动恢复** - 监听屏幕锁定事件实现智能解锁

### 权限说明

```xml
<!-- 悬浮窗权限 - 用于创建触屏锁定覆盖层 -->
<uses-permission android:name="android.permission.SYSTEM_ALERT_WINDOW" />

<!-- 前台服务权限 - 保持服务稳定运行 -->
<uses-permission android:name="android.permission.FOREGROUND_SERVICE" />

<!-- 通知权限 - 显示控制通知 -->
<uses-permission android:name="android.permission.POST_NOTIFICATIONS" />

<!-- 屏幕状态监听 - 实现自动解锁 -->
<uses-permission android:name="android.permission.WAKE_LOCK" />
```

## 🔧 开发

### 环境要求

- Android Studio Arctic Fox 或更高版本
- JDK 8 或更高版本
- Android SDK API 33
- Gradle 8.5+

### 编译项目

```bash
# 克隆项目
git clone https://github.com/licairong/TouchLock.git
cd TouchLock

# 编译调试版本
./gradlew assembleDebug

# 编译发布版本
./gradlew assembleRelease
```

### 项目结构

```
app/src/main/
├── java/com/lockscreen/reader/
│   ├── MainActivity.java           # 主界面
│   ├── NotificationService.java    # 通知服务
│   ├── OverlayService.java        # 覆盖层服务
│   ├── TouchLockManager.java      # 状态管理
│   ├── ScreenReceiver.java        # 屏幕监听
│   └── PermissionHelper.java      # 权限工具
├── res/
│   ├── drawable/                  # 图标资源
│   ├── layout/                    # 布局文件
│   ├── values/                    # 字符串和样式
│   └── xml/                       # 配置文件
└── AndroidManifest.xml           # 应用清单
```

## 🤝 贡献

欢迎贡献代码！请遵循以下步骤：

1. Fork 本项目
2. 创建特性分支 (`git checkout -b feature/AmazingFeature`)
3. 提交更改 (`git commit -m 'Add some AmazingFeature'`)
4. 推送到分支 (`git push origin feature/AmazingFeature`)
5. 创建 Pull Request

### 开发规范

- 遵循 Android 开发最佳实践
- 添加适当的注释和文档
- 确保代码通过所有测试
- 更新相关文档

## 🐛 问题反馈

如果您遇到问题或有建议，请：

1. 查看 [Issues](https://github.com/licairong/TouchLock/issues) 是否已有相关问题
2. 创建新的 Issue 并提供以下信息：
   - 设备型号和 Android 版本
   - 应用版本
   - 问题的详细描述
   - 如果可能，提供日志信息

## 📄 许可证

本项目采用 MIT 许可证 - 查看 [LICENSE](LICENSE) 文件了解详情。

## 🙏 致谢

- 感谢所有贡献者的付出
- 特别感谢电子阅读器社区的测试和反馈
- 图标设计灵感来源于 Material Design

## 📞 联系

- **项目主页**: https://github.com/licairong/TouchLock
- **Issues**: https://github.com/licairong/TouchLock/issues
- **讨论**: https://github.com/licairong/TouchLock/discussions

---

<div align="center">

**如果这个项目对您有帮助，请给个 ⭐ Star！**

Made with ❤️ for e-reader enthusiasts

</div>