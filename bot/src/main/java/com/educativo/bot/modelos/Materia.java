package com.educativo.bot.modelos;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * CONCEPTO: ENCAPSULAMIENTO Y COMPOSICIÓN
 * 
 * Esta clase representa una materia académica que puede contener múltiples tareas.
 * Demuestra:
 * 1. Encapsulamiento - atributos privados con métodos de acceso controlado
 * 2. Composición - una materia "tiene" una lista de tareas
 * 3. Validación de datos en métodos setter
 * 4. Métodos utilitarios para obtener información procesada
 */
public class Materia {
    
    // ENCAPSULAMIENTO: Atributos privados
    private String id;                      // Identificador único de la materia
    private String nombre;                  // Nombre de la materia
    private String descripcion;             // Descripción de la materia
    private String codigo;                  // Código de la materia (ej: "MAT101")
    private String profesor;                // Nombre del profesor
    private LocalDateTime fechaCreacion;    // Cuándo se creó la materia
    private String creadorId;               // ID del usuario que creó la materia
    private List<String> tareasIds;         // IDs de las tareas asociadas (composición)
    private String color;                   // Color para identificar la materia
    private boolean activa;                 // Si la materia está activa o archivada
    
    /**
     * Constructor principal de la clase Materia
     * 
     * @param id Identificador único
     * @param nombre Nombre de la materia
     * @param codigo Código de la materia
     * @param descripcion Descripción
     * @param profesor Nombre del profesor
     * @param creadorId ID del usuario creador
     */
    public Materia(String id, String nombre, String codigo, String descripcion, 
                   String profesor, String creadorId) {
        // Validación de entrada
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre de la materia no puede estar vacío");
        }
        if (codigo == null || codigo.trim().isEmpty()) {
            throw new IllegalArgumentException("El código de la materia no puede estar vacío");
        }
        
        // Inicialización de atributos
        this.id = id;
        this.nombre = nombre.trim();
        this.codigo = codigo.trim().toUpperCase();
        this.descripcion = descripcion != null ? descripcion.trim() : "";
        this.profesor = profesor != null ? profesor.trim() : "Sin asignar";
        this.creadorId = creadorId;
        this.fechaCreacion = LocalDateTime.now();
        this.tareasIds = new ArrayList<>();
        this.color = generarColorAleatorio();
        this.activa = true;
    }
    
    /**
     * Constructor simplificado
     * 
     * @param id Identificador único
     * @param nombre Nombre de la materia
     * @param codigo Código de la materia
     * @param creadorId ID del usuario creador
     */
    public Materia(String id, String nombre, String codigo, String creadorId) {
        this(id, nombre, codigo, null, null, creadorId);
    }
    
    // MÉTODOS GETTER - Acceso controlado de lectura
    
    public String getId() {
        return id;
    }
    
    public String getNombre() {
        return nombre;
    }
    
    public String getDescripcion() {
        return descripcion;
    }
    
    public String getCodigo() {
        return codigo;
    }
    
    public String getProfesor() {
        return profesor;
    }
    
    public LocalDateTime getFechaCreacion() {
        return fechaCreacion;
    }
    
    public String getCreadorId() {
        return creadorId;
    }
    
    public List<String> getTareasIds() {
        return new ArrayList<>(tareasIds); // Devolvemos una copia para mantener encapsulamiento
    }
    
    public String getColor() {
        return color;
    }
    
    public boolean isActiva() {
        return activa;
    }
    
    // MÉTODOS SETTER - Acceso controlado de escritura con validación
    
    public void setNombre(String nombre) {
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre no puede estar vacío");
        }
        this.nombre = nombre.trim();
    }
    
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion != null ? descripcion.trim() : "";
    }
    
    public void setCodigo(String codigo) {
        if (codigo == null || codigo.trim().isEmpty()) {
            throw new IllegalArgumentException("El código no puede estar vacío");
        }
        this.codigo = codigo.trim().toUpperCase();
    }
    
    public void setProfesor(String profesor) {
        this.profesor = profesor != null ? profesor.trim() : "Sin asignar";
    }
    
    public void setColor(String color) {
        this.color = color;
    }
    
    public void setActiva(boolean activa) {
        this.activa = activa;
    }
    
    // MÉTODOS DE GESTIÓN DE TAREAS - Demuestran composición
    
    /**
     * Agrega una tarea a la materia
     * 
     * @param tareaId ID de la tarea a agregar
     */
    public void agregarTarea(String tareaId) {
        if (tareaId != null && !tareasIds.contains(tareaId)) {
            tareasIds.add(tareaId);
        }
    }
    
    /**
     * Elimina una tarea de la materia
     * 
     * @param tareaId ID de la tarea a eliminar
     * @return true si se eliminó, false si no existía
     */
    public boolean eliminarTarea(String tareaId) {
        return tareasIds.remove(tareaId);
    }
    
    /**
     * Verifica si la materia contiene una tarea específica
     * 
     * @param tareaId ID de la tarea a verificar
     * @return true si contiene la tarea
     */
    public boolean tieneTarea(String tareaId) {
        return tareasIds.contains(tareaId);
    }
    
    /**
     * Obtiene el número de tareas asociadas
     * 
     * @return Cantidad de tareas
     */
    public int getCantidadTareas() {
        return tareasIds.size();
    }
    
    // MÉTODOS UTILITARIOS
    
    /**
     * Genera un color aleatorio para la materia
     * 
     * @return Código de color en hexadecimal
     */
    private String generarColorAleatorio() {
        String[] colores = {
            "#FF6B6B", "#4ECDC4", "#45B7D1", "#96CEB4", "#FFEAA7",
            "#DDA0DD", "#98D8C8", "#F7DC6F", "#BB8FCE", "#85C1E9"
        };
        return colores[(int) (Math.random() * colores.length)];
    }
    
    /**
     * Obtiene el nombre completo de la materia (código + nombre)
     * 
     * @return Formato "CÓDIGO - Nombre"
     */
    public String getNombreCompleto() {
        return codigo + " - " + nombre;
    }
    
    /**
     * Obtiene información resumida de la materia
     * 
     * @return String con información básica
     */
    public String getResumen() {
        StringBuilder resumen = new StringBuilder();
        resumen.append("📚 **").append(getNombreCompleto()).append("**\n");
        
        if (!descripcion.isEmpty()) {
            resumen.append("📝 ").append(descripcion).append("\n");
        }
        
        if (!profesor.equals("Sin asignar")) {
            resumen.append("👨‍🏫 Profesor: ").append(profesor).append("\n");
        }
        
        resumen.append("📊 Tareas: ").append(getCantidadTareas());
        resumen.append(" | Estado: ").append(activa ? "Activa" : "Archivada");
        
        return resumen.toString();
    }
    
    /**
     * Obtiene información detallada de la materia
     * 
     * @return String con información completa
     */
    public String getInformacionDetallada() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        StringBuilder info = new StringBuilder();
        
        info.append("📚 **").append(getNombreCompleto()).append("**\n\n");
        
        if (!descripcion.isEmpty()) {
            info.append("📝 **Descripción:**\n").append(descripcion).append("\n\n");
        }
        
        info.append("👨‍🏫 **Profesor:** ").append(profesor).append("\n");
        info.append("📅 **Creada:** ").append(fechaCreacion.format(formatter)).append("\n");
        info.append("📊 **Tareas asociadas:** ").append(getCantidadTareas()).append("\n");
        info.append("🎨 **Color:** ").append(color).append("\n");
        info.append("📋 **Estado:** ").append(activa ? "✅ Activa" : "📦 Archivada").append("\n");
        
        return info.toString();
    }
    
    /**
     * Representa la materia como string
     * Sobrescribe el método toString() (herencia implícita de Object)
     * 
     * @return Representación en string
     */
    @Override
    public String toString() {
        return getResumen();
    }
    
    /**
     * Compara si dos materias son iguales basándose en su ID
     * Sobrescribe el método equals() (herencia implícita de Object)
     * 
     * @param obj Objeto a comparar
     * @return true si son iguales
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        
        Materia materia = (Materia) obj;
        return id != null ? id.equals(materia.id) : materia.id == null;
    }
    
    /**
     * Genera hash code basado en el ID
     * Sobrescribe el método hashCode() (herencia implícita de Object)
     * 
     * @return Hash code
     */
    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
