# 📖 Guía de Uso - Bot Educativo Discord

## 🎯 Introducción

El Bot Educativo está diseñado para ayudar a estudiantes y profesores a gestionar tareas, materias y seguimiento de progreso académico en Discord. Utiliza una arquitectura de **comandos unificados** que agrupa funcionalidades relacionadas.

## � Comandos Unificados Disponibles

### **� ComandoMaterias - Gestión de Materias (`!materia`)**

El comando `!materia` unifica toda la gestión de materias académicas en un solo lugar.

#### **Crear Materia**
```
!materia crear <CODIGO> "<Nombre>" ["Descripción"] ["Profesor"]
```

**Ejemplos:**
```
!materia crear MAT101 "Matemáticas Básicas"
!materia crear FIS201 "Física II" "Mecánica y termodinámica" "Dr. García"
!materia crear PROG "Programación" "" "Prof. López"
```

**Parámetros:**
- `CODIGO`: Identificador único (sin espacios)
- `"Nombre"`: Nombre completo (entre comillas si tiene espacios)
- `"Descripción"`: Opcional, descripción de la materia
- `"Profesor"`: Opcional, nombre del docente

#### **Listar Materias**
```
!materia listar [filtro]
```

**Ejemplos:**
```
!materia listar           # Todas las materias
!materia listar activas   # Solo materias activas
!materia listar archivadas # Solo materias archivadas
```

#### **Ver Tareas de Materia**
```
!materia tareas CODIGO [filtro]
```

**Ejemplos:**
```
!materia tareas MAT101              # Todas las tareas
```
!materia crear MAT101 "Matemáticas Básicas"
!materia crear FIS201 "Física General" "Mecánica y termodinámica" "Dr. García"
!materia crear PROG "Programación" "Java y POO"
```

#### **Listar Materias**
```
!materia listar [filtro]
```

**Opciones de filtro:**
- `activas` - Solo materias activas
- `archivadas` - Solo materias archivadas
- `detalle` - Vista completa con estadísticas

**Ejemplos:**
```
!materia listar              # Todas las materias
!materia listar activas      # Solo activas
!materia listar detalle      # Vista detallada con estadísticas
```

#### **Ver Tareas de una Materia**
```
!materia tareas <CODIGO> [filtro]
```

**Ejemplos:**
```
!materia tareas PROG              # Todas las tareas de Programación
!materia tareas MAT101 pendientes # Solo tareas pendientes
!materia tareas FIS201 completadas # Solo tareas completadas
```

#### **Archivar/Desarchivar Materia**
```
!materia archivar <CODIGO>
!materia desarchivar <CODIGO>
```

**Ejemplos:**
```
!materia archivar MAT101    # Archivar materia terminada
!materia desarchivar FIS201 # Restaurar materia archivada
```

#### **Eliminar Materia**
```
!materia eliminar <CODIGO>
```

**Ejemplo:**
```
!materia eliminar TEST
```

⚠️ **Nota**: Solo se puede eliminar si no tiene tareas asociadas.

---

### **📝 ComandoTareas - Gestión de Tareas (`!tarea`)**

El comando `!tarea` unifica toda la gestión de tareas y asignaciones.

#### **Crear Tarea**
```
!tarea crear "<Título>" ["Descripción"] [MATERIA] [PRIORIDAD]
```

**Parámetros:**
- `"Título"`: Obligatorio, entre comillas
- `"Descripción"`: Opcional, entre comillas
- `MATERIA`: Opcional, código de materia
- `PRIORIDAD`: Opcional, 1=baja, 2=media, 3=alta

**Ejemplos:**
```
!tarea crear "Estudiar capítulo 5"
!tarea crear "Resolver ejercicios" "Problemas 1-20" MAT101 3
!tarea crear "Preparar examen" "" FIS201 2
```

#### **Listar Tareas**
```
!tarea listar [filtro]
```

**Filtros disponibles:**
- `pendientes` - Solo tareas no completadas
- `completadas` - Solo tareas completadas
- `vencidas` - Solo tareas con fecha vencida
- `materia <código>` - Tareas de una materia específica

**Ejemplos:**
```
!tarea listar                    # Todas las tareas
!tarea listar pendientes         # Solo pendientes
!tarea listar materia PROG       # Solo de Programación
```

#### **Completar Tarea**
```
!tarea completar <número>
```

**Sistema de puntos:**
- 🏆 **Base**: 10 puntos por tarea
- 🔥 **Bonus prioridad**: +5 (baja), +10 (media), +15 (alta)
- 💎 **Total**: 15-25 puntos por tarea

**Ejemplo:**
```
!tarea completar 3        # Completa la tarea #3 y gana puntos
```

#### **Establecer Fecha de Vencimiento**
```
!tarea vencimiento <número> <dd/MM/yyyy HH:mm>
```

**Ejemplo:**
```
!tarea vencimiento 1 25/12/2024 23:59
```

#### **Cambiar Prioridad**
```
!tarea prioridad <número> <1-3>
```

**Ejemplo:**
```
!tarea prioridad 2 3      # Cambiar tarea #2 a prioridad alta
```

#### **Eliminar Tarea**
```
!tarea eliminar <número>
```

**Ejemplo:**
```
!tarea eliminar 5         # Eliminar tarea #5
```

---

### **⚙️ ComandoSistema - Sistema y Utilidades (`!sistema`)**

El comando `!sistema` unifica ayuda, puntos, estadísticas y configuración.

#### **Sistema de Ayuda**
```
!sistema ayuda [comando]
```

**Ejemplos:**
```
!sistema ayuda              # Ayuda general completa
!sistema ayuda materia      # Ayuda específica para materias
!sistema ayuda tarea        # Ayuda específica para tareas
!sistema ayuda puntos       # Ayuda sobre sistema de puntos
```

#### **Sistema de Puntos**
```
!sistema puntos [opción]
```

**Opciones:**
- Sin parámetros: Ver tus puntos
- `@usuario`: Ver puntos de otro usuario
- `ranking [número]`: Ver ranking del servidor

**Ejemplos:**
```
!sistema puntos              # Tus puntos y estadísticas
!sistema puntos @usuario     # Puntos de otro usuario
!sistema puntos ranking      # Top 10 del servidor
!sistema puntos ranking 20   # Top 20 del servidor
```

**Respuesta típica:**
```
🏆 PUNTOS DEL USUARIO

👤 Tus puntos actuales
💎 147 puntos

📊 Estadísticas:
• 🏆 Nivel actual: 3
• � Materia favorita: Programación
• 🎯 Puntos para siguiente nivel: 53
• 💚 Estado: Activo
• 📝 Tareas: 12 completadas, 3 pendientes
• 🏅 Posición en ranking: #5 de 23
```

#### **Mensajes de Bienvenida**
```
!sistema bienvenida [@usuario]
```

**Ejemplos:**
```
!sistema bienvenida          # Mensaje de bienvenida general
!sistema bienvenida @nuevo   # Bienvenida específica para usuario
```

#### **Información del Bot**
```
!sistema info
```

**Muestra:**
- 🏷️ Versión del bot
- ⚡ Estado del sistema
- � Tiempo activo
- 👨‍💻 Información técnica

#### **Estadísticas Generales**
```
!sistema stats
```

**Muestra estadísticas completas:**
- 👥 Usuarios registrados y activos
- 📚 Materias creadas y activas
- 📝 Tareas totales y completadas
- 💎 Puntos en circulación
- ⚡ Estado de todos los sistemas

---

## � Funcionalidades Automáticas

### **👋 Bienvenida Automática**

El bot detecta automáticamente cuando un nuevo usuario se une al servidor y:

1. **🎯 Busca el canal adecuado**:
   - Prioriza el canal `#bienvenida` si existe
   - Si no existe, usa el canal `#general`

2. **� Envía mensaje de bienvenida simplificado**:
   - Saludo personalizado mencionando al usuario
   - Comandos principales para comenzar
   - Información básica sobre el bot

3. **📊 Registro en consola**:
   - Confirma cuando un usuario se une
   - Registra si el mensaje fue enviado exitosamente

**Ejemplo de mensaje automático:**
```
👋 ¡Bienvenido @NuevoUsuario!

🎓 Te damos la bienvenida al Bot Educativo, tu asistente para organizar tus estudios.

🌟 Comienza ahora:
• !sistema ayuda - Descubre todos los comandos
• !materia crear - Crea tu primera materia
• !sistema puntos - Consulta tus puntos

💪 ¡Estamos aquí para ayudarte a alcanzar tus metas académicas!
```
---

## 📋 Casos de Uso Prácticos

### **Escenario 1: Profesor Configurando Materias**

```
# 1. Crear materias del semestre
!materia crear MAT101 "Cálculo I" "Límites y derivadas" "Prof. Martínez"
!materia crear FIS201 "Física II" "Electromagnetismo" "Dr. García"
!materia crear PROG "Programación" "Java y POO" "Prof. López"

# 2. Verificar materias creadas
!materia listar detalle

# 3. Ver ayuda para compartir con estudiantes
!sistema ayuda materia
```

### **Escenario 2: Estudiante Organizándose**

```
# 1. Ver materias disponibles
!materia listar

# 2. Crear tareas para diferentes materias
!tarea crear "Estudiar límites" "Capítulo 1-3" MAT101 3
!tarea crear "Laboratorio de campo eléctrico" "" FIS201 2
!tarea crear "Proyecto POO" "Sistema de biblioteca" PROG 3

# 3. Establecer fechas de vencimiento
!tarea vencimiento 1 30/12/2024 23:59
!tarea vencimiento 3 15/01/2025 18:00

# 4. Ver progreso
!tarea listar pendientes
!sistema puntos
```

### **Escenario 3: Seguimiento de Progreso**

```
# 1. Completar tareas
!tarea completar 1    # Gana 25 puntos (prioridad alta)
!tarea completar 2    # Gana 20 puntos (prioridad media)

# 2. Ver estadísticas personales
!sistema puntos

# 3. Comparar con otros estudiantes
!sistema puntos ranking

# 4. Ver estadísticas generales del servidor
!sistema stats
```

### **Escenario 4: Gestión de Materias por Semestre**

```
# Al final del semestre
!materia archivar MAT101
!materia archivar FIS201

# Nuevo semestre
!materia crear MAT201 "Cálculo II" "Integrales" "Prof. Martínez"
!materia desarchivar PROG    # Continúa el próximo semestre

# Ver todas las materias
!materia listar detalle
```

---

## 💡 Consejos y Mejores Prácticas

### **Para Estudiantes**

1. **🎯 Organización**:
   - Usa códigos descriptivos para materias (`MAT101`, `FIS201`)
   - Establece prioridades realistas en las tareas
   - Configura fechas de vencimiento para todas las tareas importantes

2. **🏆 Maximizar Puntos**:
   - Completa tareas de alta prioridad para más puntos
   - Mantén un progreso constante para subir de nivel
   - Consulta tu ranking regularmente para motivarte

3. **📚 Seguimiento**:
   - Usa `!tarea listar vencidas` para identificar tareas atrasadas
   - Revisa `!materia tareas CODIGO` para ver progreso por materia
   - Consulta `!sistema stats` para ver estadísticas generales

### **Para Profesores**

1. **🎓 Configuración Inicial**:
   - Crea materias con códigos estándar de la institución
   - Incluye información del profesor en la descripción
   - Usa `!sistema ayuda` para generar guías para estudiantes

2. **📊 Monitoreo**:
   - Revisa `!sistema stats` para ver actividad general
   - Usa `!sistema puntos ranking` para identificar estudiantes destacados
   - Consulta `!materia tareas CODIGO` para ver progreso por materia

### **Para Administradores**

1. **⚙️ Configuración del Servidor**:
   - Crea canales `#bienvenida` y `#general` para mensajes automáticos
   - Configura permisos apropiados para los comandos
   - Mantén respaldos regulares si usas persistencia

2. **📈 Mantenimiento**:
   - Monitorea `!sistema info` para verificar estado del bot
   - Usa `!sistema stats` para análisis de uso
   - Archiva materias de semestres anteriores regularmente

---

## ❓ Preguntas Frecuentes

### **P: ¿Cómo puedo ver todos los comandos disponibles?**
R: Usa `!sistema ayuda` para ver la lista completa de comandos con ejemplos.

### **P: ¿Puedo cambiar la prioridad de una tarea después de crearla?**
R: Sí, usa `!tarea prioridad <número> <1-3>` para cambiar la prioridad.

### **P: ¿Qué pasa si elimino una materia con tareas?**
R: No puedes eliminar una materia que tiene tareas asociadas. Primero elimina o completa todas las tareas.

### **P: ¿Cómo funciona el sistema de puntos?**
R: Ganas 10 puntos base + bonus de prioridad (5/10/15) por completar tareas. Usa `!sistema ayuda puntos` para más detalles.

### **P: ¿Puedo ver las tareas de una materia específica?**
R: Sí, usa `!materia tareas <código>` o `!tarea listar materia <código>`.

### **P: ¿Se guardan los datos si reinicio el bot?**
R: Actualmente los datos se mantienen en memoria. En producción se implementaría persistencia.

### **P: ¿Puedo cambiar el prefijo de comandos?**
R: Actualmente usa `!` pero se puede configurar en el código fuente.

---

## 🔧 Configuración Avanzada

### **Personalización de Mensajes**
Los mensajes del bot se pueden personalizar modificando directamente cada comando unificado:
- `ComandoMaterias.java` - Mensajes relacionados con materias
- `ComandoTareas.java` - Mensajes relacionados con tareas  
- `ComandoSistema.java` - Mensajes de ayuda, puntos y sistema

### **Agregar Nuevas Funcionalidades**
Para agregar nuevos subcomandos:
1. Añade un nuevo `case` al `switch` del comando correspondiente
2. Implementa el método específico
3. Actualiza la documentación de uso en el constructor

### **Integración con Bases de Datos**
Para producción, implementa la interfaz `GestorDatos` con:
- Conexión a base de datos (MySQL, PostgreSQL)
- Serialización JSON para archivos
- Servicios en la nube (Firebase, AWS DynamoDB)

---

**🎓 El Bot Educativo está diseñado para crecer con las necesidades de tu comunidad educativa. ¡Úsalo, personalízalo y mejóralo según tus requerimientos!**

Para información técnica detallada, consulta la [Estructura del Proyecto](ESTRUCTURA.md).
!materia listar

# 2. Crear tareas para la semana
!tarea crear "Estudiar límites" "Capítulo 3, ejemplos 1-15" MAT101
!tarea crear "Laboratorio física" "Experimento de ondas" FIS201
!tarea crear "Proyecto POO" "Implementar patrón Observer" PROG

# 3. Ver todas las tareas
!tarea listar

# 4. Completar tarea terminada
!tarea completar 1

# 5. Ver progreso
!puntos
```

### **Escenario 3: Seguimiento de Progreso**

```
# 1. Profesor verifica actividad de estudiantes
!materia tareas MAT101

# 2. Estudiante compara su progreso
!puntos @companero

# 3. Ver tareas pendientes del grupo
!tarea listar pendientes
```

---

## 🎨 Formato de Respuestas

### **Mensajes de Éxito**
```
✅ Materia creada exitosamente

• Código: MAT101
• Nombre: Matemáticas Básicas
• Descripción: Álgebra y geometría
• Profesor: Prof. Martínez
```

### **Listas de Elementos**
```
📚 Materias (3):

1. MAT101 - Matemáticas Básicas (5 tareas)
2. FIS201 - Física II (3 tareas)
3. PROG - Programación (8 tareas)
```

### **Mensajes de Error**
```
❌ Error: Ya existe una materia con código: MAT101

💡 Usa !materia listar para ver materias disponibles
```

---

## ⚡ Consejos de Uso

### **Mejores Prácticas**

#### **Para Profesores:**
- 📝 **Códigos claros**: Usa códigos descriptivos (MAT101, FIS201)
- 📚 **Descripciones útiles**: Incluye información relevante del curso
- 🎯 **Organización**: Crea materias al inicio del semestre
- 📊 **Seguimiento**: Revisa regularmente las tareas de los estudiantes

#### **Para Estudiantes:**
- 🎯 **Títulos descriptivos**: "Estudiar capítulo 5" mejor que "Estudiar"
- 📝 **Usa descripciones**: Incluye detalles importantes
- 🏷️ **Asocia a materias**: Siempre especifica la materia
- ✅ **Marca completadas**: No olvides completar tareas terminadas
- 📈 **Revisa progreso**: Usa `!puntos` regularmente

### **Comandos Útiles del Día a Día**

```bash
# Rutina matutina del estudiante
!tarea listar pendientes    # Ver qué hay que hacer hoy

# Al terminar una tarea
!tarea completar 2          # Marcar como completada

# Rutina nocturna
!puntos                     # Ver progreso del día
!tarea crear "..." ...      # Planificar tareas del siguiente día

# Cada semana
!materia listar             # Revisar todas las materias
!tarea listar completadas   # Ver logros de la semana
```

---

## 🔧 Personalización

### **Modificar Prefijo de Comandos**
Por defecto, los comandos usan `!`. Si quieres cambiarlo:

1. Modifica el archivo `.env`:
```env
BOT_PREFIX=?
```

2. Reinicia el bot

3. Ahora usa: `?materia crear ...`

### **Para Profesores: Configuraciones Recomendadas**

#### **Canales Sugeridos**
- `#tareas-matematicas` - Para MAT101
- `#tareas-fisica` - Para FIS201
- `#tareas-programacion` - Para PROG
- `#progreso-general` - Para comando `!puntos`

#### **Roles y Permisos**
- **Profesores**: Pueden eliminar cualquier materia/tarea
- **Estudiantes**: Solo pueden gestionar sus propias tareas
- **Ayudantes**: Pueden ver progreso de todos los estudiantes

---

## 🎯 Objetivos Pedagógicos

### **Para Estudiantes**
- 📝 **Organización**: Gestionar tareas de múltiples materias
- 🎯 **Responsabilidad**: Seguimiento propio del progreso
- 🏆 **Motivación**: Sistema de puntos y niveles
- 👥 **Colaboración**: Visibilidad del progreso grupal

### **Para Profesores**
- 📊 **Seguimiento**: Monitorear actividad de estudiantes
- 🎯 **Engagement**: Gamificación del aprendizaje
- 📚 **Organización**: Estructura clara de materias
- 💬 **Comunicación**: Canal directo con estudiantes

---

## ❓ Preguntas Frecuentes

### **¿Puedo editar una tarea ya creada?**
Actualmente no. Debes completar la tarea actual y crear una nueva si necesitas cambios.

### **¿Los puntos se pierden?**
No, los puntos son acumulativos y permanentes.

### **¿Puedo ver tareas de otros estudiantes?**
Solo puedes ver el progreso general, no tareas específicas de otros usuarios.

### **¿Qué pasa si elimino una materia?**
Se eliminan todas las tareas asociadas. Esta acción no se puede deshacer.

### **¿Hay límite de materias o tareas?**
No hay límites técnicos, pero se recomienda mantener una organización clara.

---

**🎓 ¡Disfruta gestionando tu progreso académico con el Bot Educativo!**

Para más información técnica, consulta la [Estructura del Proyecto](ESTRUCTURA.md).
