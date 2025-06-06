@echo off
echo.
echo ===============================================
echo   SCHOOL MEDICAL MANAGEMENT SYSTEM - CONSOLE
echo ===============================================
echo.

REM Check if Java is installed
java -version > nul 2>&1
if %errorlevel% neq 0 (
    echo ERROR: Java is not installed or not in PATH
    pause
    exit /b 1
)

REM Check if classes are compiled
if not exist "build\classes\pe\controller\MainController.class" (
    echo Compiling application first...
    call build.bat
    if %errorlevel% neq 0 (
        echo ERROR: Compilation failed!
        pause
        exit /b 1
    )
)

REM Run the main controller
echo Starting console application...
echo.

java -cp "build\classes;lib\*" pe.controller.MainController

echo.
echo Console application terminated.
pause
