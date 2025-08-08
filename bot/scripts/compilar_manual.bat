@echo off
REM Script para compilar el Bot Educativo manualmente (sin Maven)
echo.
echo ========================================
echo   Compilacion Manual - Bot Educativo
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

REM Verificar si existe el directorio lib con las dependencias
if not exist "..\lib" (
    echo ERROR: No existe el directorio 'lib' en la raíz del proyecto
    echo.
    echo Necesitas crear el directorio 'lib' y descargar las siguientes dependencias:
    echo 1. JDA-5.0.0-beta.18-withDependencies.jar
    echo 2. gson-2.10.1.jar
    echo 3. logback-classic-1.4.11.jar
    echo.
    echo Ver docs\INSTALACION.md para instrucciones detalladas
    pause
    exit /b 1
)

echo Verificando dependencias en directorio lib...
dir ..\lib\*.jar >nul 2>&1
if %errorlevel% neq 0 (
    echo ERROR: No se encontraron archivos JAR en el directorio lib
    echo Revisa el archivo docs\INSTALACION.md para descargar las dependencias
    pause
    exit /b 1
)

echo ✅ Dependencias encontradas
echo.

REM Crear directorio de salida
echo Creando directorio de compilación...
if not exist "..\build" mkdir ..\build
if not exist "..\build\com" mkdir ..\build\com
if not exist "..\build\com\educativo" mkdir ..\build\com\educativo
if not exist "..\build\com\educativo\bot" mkdir ..\build\com\educativo\bot

echo ✅ Directorios creados
echo.

REM Compilar código Java
echo Compilando código fuente...
javac -cp "..\lib\*" -d ..\build -sourcepath ..\src\main\java ..\src\main\java\com\educativo\bot\*.java ..\src\main\java\com\educativo\bot\interfaces\*.java ..\src\main\java\com\educativo\bot\modelos\*.java ..\src\main\java\com\educativo\bot\comandos\*.java ..\src\main\java\com\educativo\bot\servicios\*.java

if %errorlevel% equ 0 (
    echo ✅ Compilación exitosa
) else (
    echo ❌ Error en la compilación
    echo Revisa los errores mostrados arriba
    pause
    exit /b 1
)

echo.

REM Copiar archivos de recursos si existen
if exist "..\src\main\resources" (
    echo Copiando archivos de recursos...
    xcopy "..\src\main\resources\*" "..\build\" /E /Y >nul
    echo ✅ Recursos copiados
    echo.
)

REM Crear archivo manifest
echo Creando archivo manifest...
echo Main-Class: com.educativo.bot.BotEducativo > manifest.txt
echo Class-Path: lib/JDA-5.0.0-beta.18-withDependencies.jar lib/gson-2.10.1.jar lib/logback-classic-1.4.11.jar >> manifest.txt

REM Crear JAR ejecutable
echo Creando archivo JAR...
jar cfm ..\bot-educativo.jar manifest.txt -C ..\build .

if %errorlevel% equ 0 (
    echo ✅ JAR creado exitosamente: bot-educativo.jar
    del manifest.txt
) else (
    echo ❌ Error creando JAR
    del manifest.txt
    pause
    exit /b 1
)

echo.
echo ========================================
echo        ¡Compilación Completada!
echo ========================================
echo.
echo El bot ha sido compilado exitosamente.
echo Archivo generado: bot-educativo.jar
echo.
echo Para ejecutar el bot, usa:
echo scripts\ejecutar_manual.bat "TU_TOKEN_DE_DISCORD"
echo.
echo Ver docs\INSTALACION.md para obtener el token de Discord.
echo.
pause
