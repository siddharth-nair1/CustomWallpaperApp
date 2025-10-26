@rem ---------------------------------------------------------------------------
@rem
@rem  Gradle start up script for Windows
@rem
@rem ---------------------------------------------------------------------------

@rem Add default JVM options here. You can also use JAVA_OPTS and GRADLE_OPTS to pass JVM options to this script.
set DEFAULT_JVM_OPTS=

set APP_NAME=Gradle
set APP_BASE_NAME=%~n0

@rem ...existing code...

%JAVA_EXE% %DEFAULT_JVM_OPTS% %JAVA_OPTS% %GRADLE_OPTS% -classpath %CLASSPATH% org.gradle.wrapper.GradleWrapperMain %*
