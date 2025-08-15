@echo off
REM Script para ejecutar el Bot Educativo de Discord
REM Este script demuestra cómo ejecutar el bot en Windows

echo.
echo ========================================
echo    Bot Educativo de Discord - v1.0.0
echo ========================================
echo.

REM Verificar si Java está instalado
java -version >nul 2>&1
if %errorlevel% neq 0 (
    echo ERROR: Java no está instalado o no está en el PATH
    echo Por favor instala Java 11 o superior
    pause
    exit /b 1
)

REM Verificar si el JAR existe
if not exist "target\discord-bot-educativo-1.0.0.jar" (
    if not exist "..\target\discord-bot-educativo-1.0.0.jar" (
        echo ERROR: El archivo JAR no existe
        echo Por favor ejecuta: mvn clean package
        echo.
        echo 💡 O intenta usar el modo manual: bot.bat manual "tu_token"
        pause
        exit /b 1
    )
    set "JAR_PATH=..\target\discord-bot-educativo-1.0.0.jar"
) else (
    set "JAR_PATH=target\discord-bot-educativo-1.0.0.jar"
)

REM Verificar si se proporcionó el token
if "%1"=="" (
    echo ERROR: Debes proporcionar el token del bot
    echo.
    echo Uso: ejecutar_bot.bat "TU_TOKEN_AQUI"
    echo.
    echo Para obtener un token:
    echo 1. Ve a https://discord.com/developers/applications
    echo 2. Crea una nueva aplicación
    echo 3. Ve a la sección "Bot" y crea un bot
    echo 4. Copia el token del bot
    echo.
    pause
    exit /b 1
)

echo Iniciando Bot Educativo...
echo Token: %1
echo.

REM Crear directorio de logs si no existe
if not exist "logs" mkdir logs
if not exist "..\logs" mkdir ..\logs

REM Crear directorio de datos si no existe
if not exist "datos" mkdir datos
if not exist "..\datos" mkdir ..\datos

REM Ejecutar el bot
java -jar "%JAR_PATH%" %1

if %errorlevel% neq 0 (
    echo.
    echo ERROR: El bot se cerró con código de error %errorlevel%
    pause
)

echo.
echo Bot cerrado.
pause
