@echo off
echo 🚀 Compilando Bot Educativo con sistema unificado de Materias...

cd /d "%~dp0\.."

echo 📂 Verificando estructura del proyecto...
if not exist "src\main\java\com\educativo\bot" (
    echo ❌ Error: No se encuentra la estructura del proyecto
    pause
    exit /b 1
)

echo 🔍 Verificando archivos necesarios...
set "FILES_OK=true"

if not exist "src\main\java\com\educativo\bot\modelos\Materia.java" (
    echo ❌ Falta: Materia.java
    set "FILES_OK=false"
)

if not exist "src\main\java\com\educativo\bot\comandos\ComandoMateria.java" (
    echo ❌ Falta: ComandoMateria.java (comando unificado)
    set "FILES_OK=false"
)

if not exist "src\main\java\com\educativo\bot\comandos\ComandoTarea.java" (
    echo ❌ Falta: ComandoTarea.java
    set "FILES_OK=false"
)

if "%FILES_OK%"=="false" (
    echo ❌ Faltan archivos necesarios. Verifica que todas las clases estén creadas.
    pause
    exit /b 1
)

echo ✅ Todos los archivos necesarios están presentes

echo 🧹 Limpiando compilaciones anteriores...
if exist "target" rmdir /s /q "target"
if exist "build" rmdir /s /q "build"

echo 📦 Compilando con Maven...
call mvn clean compile

if %ERRORLEVEL% neq 0 (
    echo ❌ Error en la compilación con Maven
    echo 🔄 Intentando compilación manual...
    
    echo 📁 Creando directorios...
    if not exist "build" mkdir "build"
    
    echo ☕ Compilando código Java...
    javac -cp "lib/*" -d "build" src/main/java/com/educativo/bot/*.java src/main/java/com/educativo/bot/modelos/*.java src/main/java/com/educativo/bot/comandos/*.java
    
    if %ERRORLEVEL% neq 0 (
        echo ❌ Error en compilación manual
        echo 📋 Revisa los errores de sintaxis en los archivos Java
        pause
        exit /b 1
    ) else (
        echo ✅ Compilación manual exitosa
    )
) else (
    echo ✅ Compilación con Maven exitosa
)

echo 🎉 ¡Bot compilado exitosamente con el sistema unificado de materias!
echo.
echo 📚 Comandos de materias (todos en uno):
echo   • !materia crear ^<código^> ^<nombre^> [descripción] [profesor]
echo   • !materia listar [activas^|archivadas^|detalle]
echo   • !materia eliminar ^<código^>
echo   • !materia tareas ^<código^> [pendientes^|completadas^|vencidas]
echo.
echo 📝 Ejemplo de uso:
echo   !materia crear MAT101 "Matemáticas Básicas" "Álgebra" "Dr. García"
echo   !materia listar
echo   !materia tareas MAT101 pendientes
echo.
echo 💡 Para ejecutar el bot: .\bot.bat
echo.
pause
