# Bot Educativo - Comandos Unificados

## Resumen de la Refactorización

Este proyecto ha sido refactorizado para **unificar comandos relacionados** en archivos agrupados, mejorando la organización y mantenimiento del código.

### Estructura Anterior vs Nueva

#### ❌ **Estructura Anterior (Dispersa)**
```
comandos/
├── ComandoAyuda.java
├── ComandoBienvenida.java
├── ComandoCanal.java
├── ComandoCrearMateria.java
├── ComandoEliminarMateria.java
├── ComandoListarMaterias.java
├── ComandoMateria.java
├── ComandoPuntos.java
├── ComandoTarea.java
├── ComandoTareaLimpio.java
├── ComandoTareasMateria.java
└── ComandoBase.java
```

#### ✅ **Nueva Estructura (Unificada)**
```
comandos/
├── grupos/
│   ├── ComandosAcademicos.java    // Materias + Tareas + Tareas por Materia
│   └── ComandosSistema.java       // Ayuda + Puntos + Bienvenida + Config
├── ComandoBase.java               // Clase base (mantenida)
└── [archivos antiguos]            // Mantenidos para referencia
```

---

## 📚 Grupos de Comandos

### 1. **ComandosAcademicos** (`!academico`)

Agrupa toda la funcionalidad académica en un solo comando con subcomandos:

#### **Gestión de Materias**
```bash
!academico materia crear MAT101 "Matemáticas" "Álgebra básica" "Dr. Juan Pérez"
!academico materia listar [activas|archivadas|detalle]
!academico materia eliminar MAT101
!academico materia tareas MAT101 [pendientes|completadas|vencidas]
```

#### **Gestión de Tareas**
```bash
!academico tarea crear "Estudiar capítulo 5" "Revisar ejemplos" MAT101
!academico tarea listar [pendientes|completadas|vencidas]
!academico tarea completar 1
!academico tarea eliminar 2
```

#### **Tareas por Materia**
```bash
!academico tareasm MAT101 crear "Resolver ejercicios 1-10" "Página 45"
!academico tareasm MAT101 listar
!academico tareasm MAT101 completar 1
```

### 2. **ComandosSistema** (`!sistema`)

Agrupa funcionalidades del sistema, configuración y utilidades:

#### **Ayuda y Documentación**
```bash
!sistema ayuda                    # Ayuda general
!sistema ayuda academico          # Ayuda específica para comandos académicos
!sistema ayuda puntos             # Ayuda específica para sistema de puntos
!sistema info                     # Información del bot
```

#### **Sistema de Puntos**
```bash
!sistema puntos ver               # Ver mis puntos
!sistema puntos ver @usuario      # Ver puntos de otro usuario
!sistema puntos ranking           # Top 10 del servidor
!sistema puntos ranking 20        # Top 20 del servidor
!sistema puntos dar @usuario 50 "Excelente participación"     # Solo admin
!sistema puntos quitar @usuario 10 "Comportamiento inapropiado" # Solo admin
```

#### **Bienvenida**
```bash
!sistema bienvenida                                    # Mensaje estándar
!sistema bienvenida @NuevoEstudiante                   # Para usuario específico
!sistema bienvenida personalizada @usuario "¡Hola!"   # Mensaje personalizado (admin)
```

#### **Configuración y Estadísticas**
```bash
!sistema canal configurar bienvenida #canal-bienvenida
!sistema canal configurar tareas #canal-tareas
!sistema stats                     # Estadísticas del bot
```

---

## 🏗️ Beneficios de la Unificación

### **1. Organización Mejorada**
- **Menos archivos**: De 12+ comandos individuales a 2 grupos unificados
- **Responsabilidad clara**: Cada grupo maneja un aspecto específico
- **Navegación más fácil**: Encontrar funcionalidad relacionada es más simple

### **2. Mantenimiento Simplificado**
- **Cambios centralizados**: Modificar funcionalidad académica solo requiere editar `ComandosAcademicos.java`
- **Reutilización de código**: Métodos auxiliares compartidos dentro de cada grupo
- **Menos duplicación**: Lógica similar unificada en un solo lugar

### **3. Escalabilidad**
- **Fácil extensión**: Agregar nuevos subcomandos es más simple
- **Nuevos grupos**: Se pueden crear nuevos grupos temáticos fácilmente
- **Modularidad**: Cada grupo es independiente y puede desarrollarse por separado

### **4. Experiencia de Usuario Mejorada**
- **Comandos más intuitivos**: `!academico materia crear` es más claro que `!crearmateria`
- **Ayuda contextual**: `!sistema ayuda academico` proporciona ayuda específica
- **Consistencia**: Todos los comandos relacionados siguen el mismo patrón

---

## 🔧 Implementación Técnica

### **Polimorfismo Avanzado**
Cada grupo de comandos usa un **switch interno** para manejar subcomandos:

```java
@Override
public String ejecutar(String[] args, String canalId, String usuarioId) {
    if (args.length == 0) {
        return "❌ Especifica un subcomando: materia, tarea, tareasm";
    }
    
    String subcomando = args[0].toLowerCase();
    String[] subArgs = new String[args.length - 1];
    System.arraycopy(args, 1, subArgs, 0, subArgs.length);
    
    switch (subcomando) {
        case "materia":
            return ejecutarComandoMateria(subArgs, canalId, usuarioId);
        case "tarea":
            return ejecutarComandoTarea(subArgs, canalId, usuarioId);
        case "tareasm":
            return ejecutarComandoTareasMateria(subArgs, canalId, usuarioId);
        default:
            return "❌ Subcomando no válido: " + subcomando;
    }
}
```

### **Herencia Mantenida**
Los nuevos grupos siguen extendiendo de `ComandoBase`:

```java
public class ComandosAcademicos extends ComandoBase {
    public ComandosAcademicos() {
        super(
            "academico",
            "Comandos para gestión académica completa",
            "!academico [materia|tarea|tareasm] <subcomando> [parámetros]",
            false
        );
    }
}
```

### **Encapsulamiento Mejorado**
Métodos privados para cada funcionalidad específica:

```java
private String ejecutarComandoMateria(String[] args, String canalId, String usuarioId)
private String ejecutarComandoTarea(String[] args, String canalId, String usuarioId)
private String ejecutarComandoTareasMateria(String[] args, String canalId, String usuarioId)
```

---

## 🚀 Migración desde Comandos Antiguos

### **Equivalencias de Comandos**

| Comando Anterior | Nuevo Comando Unificado |
|------------------|-------------------------|
| `!ayuda` | `!sistema ayuda` |
| `!puntos` | `!sistema puntos ver` |
| `!ranking` | `!sistema puntos ranking` |
| `!bienvenida` | `!sistema bienvenida` |
| `!tarea crear "título"` | `!academico tarea crear "título"` |
| `!materia crear CODE "Nombre"` | `!academico materia crear CODE "Nombre"` |
| `!crearmateria` | `!academico materia crear` |
| `!listarmateria` | `!academico materia listar` |
| `!eliminarmateria` | `!academico materia eliminar` |

### **Compatibilidad**
- Los archivos antiguos se mantienen para referencia
- La nueva estructura es completamente funcional
- Se puede implementar retrocompatibilidad si es necesario

---

## 📝 Próximos Pasos

### **Posibles Nuevos Grupos**
1. **ComandosModeracion** - Comandos de moderación y administración
2. **ComandosEstadisticas** - Análisis detallados y reportes
3. **ComandosNotificaciones** - Sistema de recordatorios y alertas
4. **ComandosIntegracion** - Integración con APIs externas

### **Mejoras Futuras**
- **Persistencia real**: Reemplazar listas en memoria con base de datos
- **Permisos granulares**: Sistema de permisos más sofisticado
- **Comandos dinámicos**: Cargar comandos desde configuración
- **Plugins**: Sistema de plugins para comandos personalizados

---

## 🎯 Conclusión

La unificación de comandos representa una **evolución natural** del bot educativo, manteniendo todos los conceptos de POO (herencia, polimorfismo, encapsulamiento) mientras mejora significativamente la organización, mantenimiento y experiencia de usuario.

Esta estructura es **escalable, mantenible y extensible**, proporcionando una base sólida para el crecimiento futuro del bot.
