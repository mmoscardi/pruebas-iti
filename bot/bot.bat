@echo off
REM Script principal para gestionar el Bot Educativo de Discord
REM Centraliza todas las operaciones comunes del bot

echo.
echo ==========================================
echo     Bot Educativo de Discord - v1.0.0
echo ==========================================
echo.

REM Verificar parámetros
if "%1"=="" goto mostrar_ayuda
if /I "%1"=="ayuda" goto mostrar_ayuda
if /I "%1"=="help" goto mostrar_ayuda
if /I "%1"=="compilar" goto compilar
if /I "%1"=="ejecutar" goto ejecutar
if /I "%1"=="manual" goto manual
if /I "%1"=="docs" goto docs

echo ERROR: Comando no reconocido "%1"
echo.
goto mostrar_ayuda

:mostrar_ayuda
echo COMANDOS DISPONIBLES:
echo.
echo   bot.bat compilar [TOKEN]    - Compila con Maven y ejecuta el bot
echo   bot.bat ejecutar [TOKEN]    - Ejecuta el bot (requiere compilación previa)
echo   bot.bat manual [TOKEN]      - Compilación y ejecución manual (sin Maven)
echo   bot.bat docs                - Abre la documentación
echo   bot.bat ayuda               - Muestra esta ayuda
echo.
echo EJEMPLOS:
echo   bot.bat compilar "tu_token_discord_aqui"
echo   bot.bat ejecutar "tu_token_discord_aqui"
echo   bot.bat manual "tu_token_discord_aqui"
echo   bot.bat docs
echo.
echo Para obtener un token de Discord, ver: docs\INSTALACION.md
goto fin

:compilar
if "%2"=="" (
    echo ERROR: Debes proporcionar el token del bot
    echo Uso: bot.bat compilar "TU_TOKEN"
    goto fin
)
echo 📦 Compilando con Maven y ejecutando...
call mvn clean package
if %errorlevel% neq 0 (
    echo ❌ Error en la compilación con Maven
    echo 💡 Intenta usar: bot.bat manual "%2"
    goto fin
)
call scripts\ejecutar_bot.bat "%2"
goto fin

:ejecutar
if "%2"=="" (
    echo ERROR: Debes proporcionar el token del bot
    echo Uso: bot.bat ejecutar "TU_TOKEN"
    goto fin
)
echo 🚀 Ejecutando bot...
call scripts\ejecutar_bot.bat "%2"
goto fin

:manual
if "%2"=="" (
    echo ERROR: Debes proporcionar el token del bot
    echo Uso: bot.bat manual "TU_TOKEN"
    goto fin
)
echo 🔧 Compilación manual...
call scripts\compilar_manual.bat
if %errorlevel% neq 0 (
    echo ❌ Error en la compilación manual
    goto fin
)
echo 🚀 Ejecutando bot...
call scripts\ejecutar_manual.bat "%2"
goto fin

:docs
echo 📚 Abriendo documentación...
echo.
echo Documentación disponible en el directorio 'docs':
echo - INSTALACION.md    - Guía de instalación y configuración
echo - GUIA_USO.md       - Manual de uso y ejemplos
echo - RESUMEN_PROYECTO.md - Resumen completo del proyecto
echo.
echo Scripts disponibles en el directorio 'scripts':
echo - ejecutar_bot.bat      - Ejecución con Maven
echo - compilar_manual.bat   - Compilación sin Maven
echo - ejecutar_manual.bat   - Ejecución sin Maven
echo.
if exist "docs\RESUMEN_PROYECTO.md" (
    echo Abriendo resumen del proyecto...
    start notepad "docs\RESUMEN_PROYECTO.md"
)
goto fin

:fin
echo.
pause
