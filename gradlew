#!/bin/sh
# Gradle wrapper script for Unix systems
dirname=$(dirname "$0")
GRADLEW_PATH="$dirname/gradlew"
if [ -f "$GRADLEW_PATH" ]; then
  exec "$GRADLEW_PATH" "$@"
else
  echo "gradlew not found. Please add the Gradle wrapper to your project."
  exit 1
fi
