@echo off
title Student Management System
echo =========================================
echo   Student Management System - DSA Edition
echo =========================================
echo.
echo Opening in your default browser...
echo.

:: Try to open index.html in the default browser
start "" "%~dp0index.html"

echo If the browser didn't open automatically,
echo manually open the file: index.html
echo.
pause
