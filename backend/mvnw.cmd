@REM Maven Wrapper for Windows
@REM https://maven.apache.org/wrapper/

@echo off

set MAVEN_OPTS=%MAVEN_OPTS%
set WRAPPER_JAR=.mvn\wrapper\maven-wrapper.jar

if exist %WRAPPER_JAR% (
    java %MAVEN_OPTS% -Dmaven.multiModuleProjectDirectory="%~dp0." -classpath "%WRAPPER_JAR%" org.apache.maven.wrapper.MavenWrapperMain %*
) else (
    echo Maven wrapper JAR not found. Run the project from an IDE or install Maven globally.
    exit /b 1
)
