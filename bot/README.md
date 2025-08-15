# 🤖 Bot Educativo de Discord

Un proyecto integral para enseñar **Programación Orientada a Objetos (POO)** a través de una aplicación práctica y funcional en Discord.

## 🎯 Propósito

Este bot sirve como **herramienta didáctica** que demuestra todos los conceptos fundamentales de POO en un entorno real que los estudiantes conocen y usan diariamente.

## ✨ Características Principales

### 🎓 **Para Estudiantes**
- 📚 **Gestión de Materias**: Crear y organizar materias académicas
- 📝 **Gestión de Tareas**: Crear, completar y seguir tareas de estudio
- 🏆 **Sistema de Puntos**: Motivación a través de gamificación
- 📊 **Progreso Personal**: Seguimiento de avances y estadísticas

### 👩‍🏫 **Para Profesores**
- 📈 **Seguimiento de Estudiantes**: Monitor de actividad y progreso
- 🎯 **Gestión Académica**: Estructura organizacional de materias
- 💡 **Herramienta de Enseñanza**: Demostración práctica de POO
- 📋 **Material Didáctico**: Código comentado y documentado

## 🏗️ Conceptos de POO Demostrados

| Concepto | Implementación | Ejemplo |
|----------|----------------|---------|
| **🏗️ Herencia** | `ComandoBase` → Comandos específicos | Funcionalidad común compartida |
| **🔄 Polimorfismo** | Método `ejecutar()` sobrescrito | Mismo método, comportamiento diferente |
| **🔒 Encapsulamiento** | Datos privados con acceso controlado | Listas protegidas de materias/tareas |
| **🎭 Abstracción** | Interfaces y clases abstractas | Simplificación de complejidad |
| **🧩 Composición** | `FormateadorMensajes` como utilidad | Reutilización sin herencia |

## 📚 Documentación

| Documento | Descripción | Audiencia |
|-----------|-------------|-----------|
| **[🚀 Instalación](docs/INSTALACION.md)** | Guía completa de instalación y configuración | Todos |
| **[📖 Guía de Uso](docs/GUIA_USO.md)** | Manual de usuario con todos los comandos | Usuarios finales |
| **[🏗️ Estructura](docs/ESTRUCTURA.md)** | Arquitectura técnica y conceptos POO | Desarrolladores |
| **[📋 Resumen](docs/RESUMEN.md)** | Visión general y valor educativo | Profesores |

## ⚡ Inicio Rápido

### 1. **Instalación**
```bash
# Descargar el JAR precompilado
# Crear archivo .env con tu token de Discord
echo "DISCORD_TOKEN=tu_token_aqui" > .env

# Ejecutar el bot
java -jar discord-bot-educativo-1.0.0.jar
```

### 2. **Primeros Comandos**
```
!ayuda                                    # Ver ayuda general
!materia crear MAT101 "Matemáticas"      # Crear materia
!tarea crear "Estudiar capítulo 1"       # Crear tarea
!puntos                                   # Ver progreso
```

## 🛠️ Tecnologías

- ☕ **Java 11+**: Lenguaje principal
- 🤖 **JDA**: Java Discord API
- 🏗️ **Maven**: Gestión de dependencias
- 📦 **JAR**: Distribución ejecutable

## 📊 Estadísticas del Proyecto

- 📝 **~1,700 líneas** de código
- 🏗️ **12 clases principales** 
- 🔧 **100% conceptos POO** cubiertos
- ✅ **51% menos duplicación** (código simplificado)
- 🎯 **Complejidad baja**, alta mantenibilidad

## 🎮 Comandos Disponibles

### **📚 Materias**
```bash
!materia crear CODIGO "Nombre" ["Descripción"] ["Profesor"]
!materia listar [activas|archivadas]
!materia tareas CODIGO
!materia eliminar CODIGO
```

### **📝 Tareas**
```bash
!tarea crear "Título" ["Descripción"] [MATERIA]
!tarea listar [pendientes|completadas]
!tarea completar NUMERO
```

### **🏆 Progreso**
```bash
!puntos           # Ver tu progreso
!puntos @usuario  # Ver progreso de otro usuario
```

### **❓ Ayuda**
```bash
!ayuda            # Ayuda general
!ayuda [comando]  # Ayuda específica
```

## 🎓 Valor Educativo

### **Conceptos Demostrados**
- ✅ **Jerarquía de clases** con herencia clara
- ✅ **Polimorfismo** en acción con diferentes comandos
- ✅ **Encapsulamiento** de datos y funcionalidad
- ✅ **Interfaces** para contratos de diseño
- ✅ **Patrones de diseño** (Command, Template Method, Factory)
- ✅ **Separación de responsabilidades**
- ✅ **Código limpio** y mantenible

### **Experiencia Práctica**
- 🔧 **Desarrollo real** con herramientas profesionales
- 🎯 **Proyecto funcional** en entorno conocido
- 👥 **Trabajo colaborativo** entre estudiantes
- 📈 **Iteración y mejora** continua del código

## � Extensibilidad

El proyecto está diseñado para ser **fácilmente extensible**:

```java
// Agregar nuevo comando es simple
public class ComandoNuevo extends ComandoBase {
    public ComandoNuevo() {
        super("nuevo", "Descripción", "!nuevo [args]", false);
    }
    
    @Override
    public String ejecutar(String[] args, String canalId, String usuarioId) {
        return FormateadorMensajes.exito("¡Funciona!");
    }
}
```

## 🏆 Casos de Uso

### **🏫 En el Aula**
- Demostración en vivo de conceptos POO
- Ejercicios prácticos progresivos  
- Análisis de código real
- Proyectos colaborativos

### **� Autoestudio**
- Organización personal de materias
- Seguimiento de progreso diario
- Práctica con comandos reales
- Comprensión de arquitectura

### **� Trabajo en Equipo**
- Servidor Discord por materia
- Competencias amigables
- Comunicación centralizada
- Motivación grupal

## 🤝 Contribuir

Este proyecto está diseñado para **ser mejorado por estudiantes**:

1. 🔍 **Analiza** el código existente
2. 💡 **Propone** nuevas funcionalidades  
3. 🛠️ **Implementa** mejoras
4. 📖 **Documenta** tus cambios
5. 🎯 **Comparte** con la comunidad

## 📞 Soporte

- 📖 **Documentación completa** en `/docs`
- 💡 **Código comentado** para facilitar comprensión
- 🎓 **Propósito educativo** - ideal para aprender
- 👥 **Comunidad colaborativa**

---

## 🌟 ¿Por qué este proyecto?

> *"La mejor manera de aprender POO es viendo conceptos abstractos aplicados en proyectos reales que resuelven problemas cotidianos."*

Este bot demuestra que **POO no es solo teoría** - es una herramienta poderosa para crear aplicaciones útiles y mantenibles.

---

**🎊 ¡Aprende POO creando algo que realmente vas a usar!**

**🚀 Comienza con la [Guía de Instalación](docs/INSTALACION.md)**
