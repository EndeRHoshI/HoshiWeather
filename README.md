# HoshiWeather

Hoshi 天气，跟着 [PlayWeather](https://github.com/zhujiang521/PlayWeather/tree/desktop) 一步一步实现

## 

This is a Kotlin Multiplatform project targeting Android, Desktop.

* `/app` is for code that will be shared across your Compose Multiplatform applications.
  It contains several subfolders:
  - `commonMain` is for code that’s common for all targets.
  - Other folders are for Kotlin code that will be compiled for only the platform indicated in the folder name.
    For example, if you want to use Apple’s CoreCrypto for the iOS part of your Kotlin app,
    `iosMain` would be the right folder for such calls.


Learn more about [Kotlin Multiplatform](https://www.jetbrains.com/help/kotlin-multiplatform-dev/get-started.html)…