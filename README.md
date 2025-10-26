# CustomWallpaper

An Android app that updates the home wallpaper every day at 8:00 AM with a black background and a custom message that shows the number of days left until a user-configured target date.

Features:
- Local black wallpaper image
- UI to set the target date (saved in SharedPreferences)
- Daily update scheduled at 8:00 AM using WorkManager
- Draws text "<N> left, Keep going!" onto the wallpaper image

Build:
- Open in Android Studio or push to GitHub and use the included GitHub Actions workflow to build.

GitHub Actions:
- `.github/workflows/android-build.yml` builds the app on push using Gradle.

License: MIT
