# 🚀 Guía de Instalación - Bot Educativo Discord

## 📋 Requisitos Previos

### **Software Necesario**
- ☕ **Java 11 o superior** ([Descargar aquí](https://adoptium.net/))
- 🏗️ **Maven 3.6+** ([Descargar aquí](https://maven.apache.org/download.cgi))
- 🎮 **Cuenta de Discord** y permisos para crear aplicaciones

### **Verificar Instalación**
```bash
# Verificar Java
java -version

# Verificar Maven
mvn -version
```

## 🤖 Configuración del Bot en Discord

### **1. Crear Aplicación Discord**
1. Ve a [Discord Developer Portal](https://discord.com/developers/applications)
2. Haz clic en **"New Application"**
3. Nombra tu aplicación: `Bot Educativo`
4. Acepta los términos y crea la aplicación

### **2. Crear Bot**
1. En el panel izquierdo, ve a **"Bot"**
2. Haz clic en **"Add Bot"**
3. Confirma con **"Yes, do it!"**

### **3. Configurar Permisos**
En la sección **"Privileged Gateway Intents"**:
- ✅ **Message Content Intent** (obligatorio)
- ✅ **Server Members Intent** (recomendado)

### **4. Obtener Token**
1. En la sección **"Token"**, haz clic en **"Copy"**
2. **⚠️ IMPORTANTE**: Nunca compartas este token

### **5. Invitar Bot al Servidor**
1. Ve a **"OAuth2"** → **"URL Generator"**
2. En **"Scopes"** selecciona: `bot`
3. En **"Bot Permissions"** selecciona:
   - ✅ Send Messages
   - ✅ Read Message History
   - ✅ Use Slash Commands
   - ✅ Embed Links
4. Copia la URL generada y ábrela en el navegador
5. Selecciona tu servidor e invita el bot

## 💻 Instalación del Proyecto

### **Opción 1: Descargar JAR Precompilado** (Recomendado)
```bash
# 1. Descargar el JAR del proyecto
# (El archivo discord-bot-educativo-1.0.0.jar ya está compilado)

# 2. Crear archivo de configuración
echo "DISCORD_TOKEN=tu_token_aqui" > .env

# 3. Ejecutar el bot
java -jar discord-bot-educativo-1.0.0.jar
```

### **Opción 2: Compilar desde Código Fuente**
```bash
# 1. Clonar o descargar el proyecto
cd "ruta/del/proyecto"

# 2. Compilar el proyecto
mvn clean package -DskipTests

# 3. El JAR se genera en target/
ls target/*.jar

# 4. Crear configuración
echo "DISCORD_TOKEN=tu_token_aqui" > .env

# 5. Ejecutar
java -jar target/discord-bot-educativo-1.0.0.jar
```

## ⚙️ Configuración

### **Archivo .env** (Requerido)
Crea un archivo `.env` en el directorio del JAR:
```env
# Token del bot de Discord (OBLIGATORIO)
DISCORD_TOKEN=MTIzNDU2Nzg5MC5H...

# Prefijo de comandos (opcional, por defecto es !)
BOT_PREFIX=!

# Canal de logs (opcional)
LOG_CHANNEL_ID=123456789012345678
```

### **Variables de Entorno** (Alternativa)
```bash
# Windows
set DISCORD_TOKEN=tu_token_aqui
java -jar discord-bot-educativo-1.0.0.jar

# Linux/Mac
export DISCORD_TOKEN=tu_token_aqui
java -jar discord-bot-educativo-1.0.0.jar
```

## 🎯 Verificación de Instalación

### **1. Verificar que el Bot está Online**
- El bot debe aparecer **online** en tu servidor
- Su estado debe mostrar: `🤖 Bot educativo activo`

### **2. Probar Comandos Básicos**
```
!ayuda
!materia crear TEST "Materia de Prueba"
!tarea crear "Mi primera tarea"
!puntos
```

### **3. Logs de Inicio Exitoso**
```
[INFO] Bot Educativo iniciando...
[INFO] Comandos registrados: 5
[INFO] ✅ Bot conectado exitosamente como: BotEducativo#1234
[INFO] 🎓 Sistema educativo activo en 1 servidor(es)
```

## 🐛 Solución de Problemas

### **Error: "Invalid Token"**
```
❌ Problema: Token inválido o expirado
✅ Solución: 
   1. Ve al Discord Developer Portal
   2. Regenera el token del bot
   3. Actualiza el archivo .env
```

### **Error: "Missing Permissions"**
```
❌ Problema: Bot sin permisos suficientes
✅ Solución:
   1. Verifica permisos en Discord Developer Portal
   2. Re-invita el bot con permisos correctos
   3. Verifica permisos del canal específico
```

### **Error: "Port already in use"**
```
❌ Problema: Puerto ocupado (rare)
✅ Solución:
   1. Cierra otras instancias del bot
   2. Reinicia la aplicación
```

### **Error: "Java version"**
```
❌ Problema: Versión de Java incorrecta
✅ Solución:
   1. Actualiza a Java 11+
   2. Verifica con: java -version
```

## 🔧 Configuraciones Avanzadas

### **Modo de Desarrollo**
```bash
# Ejecutar con logs detallados
java -jar -Dlogging.level.root=DEBUG discord-bot-educativo-1.0.0.jar

# Ejecutar con perfil específico
java -jar -Dspring.profiles.active=dev discord-bot-educativo-1.0.0.jar
```

### **Memoria JVM**
```bash
# Para servidores grandes (más de 1000 usuarios)
java -Xmx512m -jar discord-bot-educativo-1.0.0.jar

# Para uso básico
java -Xmx256m -jar discord-bot-educativo-1.0.0.jar
```

## 📱 Instalación en Hosting

### **VPS/Cloud (Ubuntu)**
```bash
# 1. Instalar Java
sudo apt update
sudo apt install openjdk-11-jre-headless

# 2. Crear usuario para el bot
sudo useradd -m -s /bin/bash botuser

# 3. Subir archivos
scp discord-bot-educativo-1.0.0.jar user@server:/home/botuser/
scp .env user@server:/home/botuser/

# 4. Crear servicio systemd
sudo nano /etc/systemd/system/bot-educativo.service
```

### **Servicio Systemd** (Linux)
```ini
[Unit]
Description=Bot Educativo Discord
After=network.target

[Service]
Type=simple
User=botuser
WorkingDirectory=/home/botuser
ExecStart=/usr/bin/java -jar discord-bot-educativo-1.0.0.jar
Restart=always
RestartSec=10

[Install]
WantedBy=multi-user.target
```

```bash
# Activar servicio
sudo systemctl enable bot-educativo.service
sudo systemctl start bot-educativo.service
sudo systemctl status bot-educativo.service
```

## ✅ Lista de Verificación Final

- [ ] Java 11+ instalado y funcionando
- [ ] Token de Discord obtenido
- [ ] Archivo .env creado con token correcto
- [ ] Bot invitado al servidor con permisos
- [ ] JAR ejecutándose sin errores
- [ ] Bot aparece online en Discord
- [ ] Comando `!ayuda` responde correctamente
- [ ] Comandos básicos funcionan

## 🆘 Soporte

Si tienes problemas con la instalación:

1. **Verifica los logs** de la aplicación
2. **Revisa permisos** del bot en Discord
3. **Confirma la configuración** del archivo .env
4. **Prueba comandos básicos** uno por uno

---

**🎉 ¡Listo! Tu Bot Educativo está funcionando correctamente.**

Continúa con la [Guía de Uso](GUIA_USO.md) para aprender a usar todas las funcionalidades.
