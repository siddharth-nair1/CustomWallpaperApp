@echo off
set DIRNAME=%~dp0
set GRADLEW_PATH=%DIRNAME%gradlew
if exist "%GRADLEW_PATH%" (
  call "%GRADLEW_PATH%" %*
) else (
  echo gradlew not found. Please add the Gradle wrapper to your project.
  exit /b 1
)
