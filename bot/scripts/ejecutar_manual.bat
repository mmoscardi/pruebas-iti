@echo off
REM Script para ejecutar el Bot Educativo compilado manualmente
echo.
echo ========================================
echo   Ejecución Manual - Bot Educativo
echo ========================================
echo.

REM Verificar si se proporcionó el token
if "%1"=="" (
    echo ERROR: Debes proporcionar el token del bot
    echo.
    echo Uso: ejecutar_manual.bat "TU_TOKEN_DE_DISCORD"
    echo.
    echo Para obtener un token:
    echo 1. Ve a https://discord.com/developers/applications
    echo 2. Crea una nueva aplicación
    echo 3. Ve a la sección "Bot" y crea un bot
    echo 4. Copia el token del bot
    echo.
    echo Ver docs\INSTALACION.md para instrucciones detalladas
    pause
    exit /b 1
)

REM Verificar si Java está instalado
java -version >nul 2>&1
if %errorlevel% neq 0 (
    echo ERROR: Java no está instalado o no está en el PATH
    echo Por favor instala Java 11 o superior
    pause
    exit /b 1
)

REM Verificar si el JAR existe
if not exist "..\target\bot-educativo-1.0-SNAPSHOT.jar" (
    echo ERROR: El archivo bot-educativo-1.0-SNAPSHOT.jar no existe
    echo Por favor ejecuta scripts\compilar_manual.bat primero
    pause
    exit /b 1
)

REM Verificar dependencias
if not exist "..\lib" (
    echo ERROR: No existe el directorio 'lib' con las dependencias
    echo Ver docs\INSTALACION.md para configurar las dependencias
    pause
    exit /b 1
)

echo Verificando dependencias...
dir ..\lib\*.jar >nul 2>&1
if %errorlevel% neq 0 (
    echo ERROR: No se encontraron dependencias en el directorio lib
    pause
    exit /b 1
)

echo ✅ Dependencias verificadas
echo.

REM Crear directorios necesarios
echo Preparando directorios...
if not exist "..\logs" mkdir ..\logs
if not exist "..\datos" mkdir ..\datos
echo ✅ Directorios preparados
echo.

echo Iniciando Bot Educativo...
echo Token: %1
echo.

REM Ejecutar el bot con todas las librerías en el classpath
java -cp "..\lib\*;..\target\bot-educativo-1.0-SNAPSHOT.jar" com.educativo.bot.BotEducativo %1

if %errorlevel% neq 0 (
    echo.
    echo ERROR: El bot se cerró con código de error %errorlevel%
    echo.
    echo Posibles causas:
    echo - Token inválido
    echo - Problemas de conexión a Internet
    echo - Bot sin permisos en el servidor
    echo.
    echo Revisa el archivo ..\logs\bot-educativo.log para más detalles
    pause
)

echo.
echo Bot cerrado.
pause
