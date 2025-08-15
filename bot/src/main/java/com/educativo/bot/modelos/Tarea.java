package com.educativo.bot.modelos;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * CONCEPTO: ENCAPSULAMIENTO
 * 
 * El encapsulamiento es uno de los pilares de la POO. Consiste en:
 * 1. Mantener los atributos privados (private)
 * 2. Proporcionar métodos públicos (getters/setters) para acceder a ellos
 * 3. Controlar cómo se accede y modifica el estado interno del objeto
 * 
 * Esta clase representa una tarea de estudio que pueden crear los estudiantes.
 */
public class Tarea {
    
    // ENCAPSULAMIENTO: Atributos privados - no se pueden acceder directamente desde fuera
    private String id;                    // Identificador único de la tarea
    private String titulo;                // Título de la tarea
    private String descripcion;           // Descripción detallada
    private String materia;               // Materia a la que pertenece
    private LocalDateTime fechaCreacion;  // Cuándo se creó la tarea
    private LocalDateTime fechaVencimiento; // Cuándo vence la tarea
    private String creadorId;             // ID del usuario que creó la tarea
    private boolean completada;           // Si la tarea está completada
    private int prioridad;                // Prioridad (1=baja, 2=media, 3=alta)
    
    /**
     * Constructor de la clase Tarea
     * Demuestra cómo inicializar un objeto con valores específicos
     * 
     * @param id Identificador único
     * @param titulo Título de la tarea
     * @param descripcion Descripción de la tarea
     * @param materia Materia correspondiente
     * @param creadorId ID del creador
     * @param prioridad Nivel de prioridad (1-3)
     */
    public Tarea(String id, String titulo, String descripcion, String materia, 
                 String creadorId, int prioridad) {
        // Validación de entrada - parte del encapsulamiento
        if (titulo == null || titulo.trim().isEmpty()) {
            throw new IllegalArgumentException("El título no puede estar vacío");
        }
        if (prioridad < 1 || prioridad > 3) {
            throw new IllegalArgumentException("La prioridad debe estar entre 1 y 3");
        }
        
        // Inicialización de atributos
        this.id = id;
        this.titulo = titulo.trim();
        this.descripcion = descripcion != null ? descripcion.trim() : "";
        this.materia = materia != null ? materia.trim() : "General";
        this.creadorId = creadorId;
        this.prioridad = prioridad;
        this.fechaCreacion = LocalDateTime.now();
        this.completada = false;
        this.fechaVencimiento = null; // Se establece después con el setter
    }
    
    // MÉTODOS GETTER - Permiten leer los valores de los atributos privados
    
    /**
     * Obtiene el ID de la tarea
     * @return ID único de la tarea
     */
    public String getId() {
        return id;
    }
    
    /**
     * Obtiene el título de la tarea
     * @return Título de la tarea
     */
    public String getTitulo() {
        return titulo;
    }
    
    /**
     * Obtiene la descripción de la tarea
     * @return Descripción de la tarea
     */
    public String getDescripcion() {
        return descripcion;
    }
    
    /**
     * Obtiene la materia de la tarea
     * @return Materia correspondiente
     */
    public String getMateria() {
        return materia;
    }
    
    /**
     * Obtiene la fecha de creación
     * @return Fecha y hora de creación
     */
    public LocalDateTime getFechaCreacion() {
        return fechaCreacion;
    }
    
    /**
     * Obtiene la fecha de vencimiento
     * @return Fecha y hora de vencimiento, o null si no tiene
     */
    public LocalDateTime getFechaVencimiento() {
        return fechaVencimiento;
    }
    
    /**
     * Obtiene el ID del creador
     * @return ID del usuario que creó la tarea
     */
    public String getCreadorId() {
        return creadorId;
    }
    
    /**
     * Verifica si la tarea está completada
     * @return true si está completada, false si no
     */
    public boolean isCompletada() {
        return completada;
    }
    
    /**
     * Obtiene la prioridad de la tarea
     * @return Nivel de prioridad (1-3)
     */
    public int getPrioridad() {
        return prioridad;
    }
    
    // MÉTODOS SETTER - Permiten modificar los valores con validación
    
    /**
     * Establece el título de la tarea
     * Incluye validación para demostrar control del encapsulamiento
     * 
     * @param titulo Nuevo título
     */
    public void setTitulo(String titulo) {
        if (titulo == null || titulo.trim().isEmpty()) {
            throw new IllegalArgumentException("El título no puede estar vacío");
        }
        this.titulo = titulo.trim();
    }
    
    /**
     * Establece la descripción de la tarea
     * 
     * @param descripcion Nueva descripción
     */
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion != null ? descripcion.trim() : "";
    }
    
    /**
     * Establece la fecha de vencimiento
     * 
     * @param fechaVencimiento Nueva fecha de vencimiento
     */
    public void setFechaVencimiento(LocalDateTime fechaVencimiento) {
        this.fechaVencimiento = fechaVencimiento;
    }
    
    /**
     * Marca la tarea como completada o no completada
     * 
     * @param completada Estado de completado
     */
    public void setCompletada(boolean completada) {
        this.completada = completada;
    }
    
    /**
     * Establece la prioridad con validación
     * 
     * @param prioridad Nueva prioridad (1-3)
     */
    public void setPrioridad(int prioridad) {
        if (prioridad < 1 || prioridad > 3) {
            throw new IllegalArgumentException("La prioridad debe estar entre 1 y 3");
        }
        this.prioridad = prioridad;
    }
    
    // MÉTODOS DE UTILIDAD
    
    /**
     * Obtiene el texto de prioridad legible para humanos
     * Demuestra encapsulamiento al proporcionar una vista procesada de los datos
     * 
     * @return Texto de prioridad ("Baja", "Media", "Alta")
     */
    public String getPrioridadTexto() {
        switch (prioridad) {
            case 1: return "Baja";
            case 2: return "Media";
            case 3: return "Alta";
            default: return "Desconocida";
        }
    }
    
    /**
     * Verifica si la tarea está vencida
     * 
     * @return true si está vencida, false si no
     */
    public boolean estaVencida() {
        if (fechaVencimiento == null || completada) {
            return false;
        }
        return LocalDateTime.now().isAfter(fechaVencimiento);
    }
    
    /**
     * Obtiene una representación en texto de la tarea
     * Sobrescribe el método toString() de Object (concepto de herencia implícito)
     * 
     * @return Representación en string de la tarea
     */
    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        StringBuilder sb = new StringBuilder();
        
        sb.append("**").append(titulo).append("**");
        sb.append(" (").append(materia).append(")");
        sb.append("\n📝 ").append(descripcion);
        sb.append("\n🔥 Prioridad: ").append(getPrioridadTexto());
        sb.append("\n📅 Creada: ").append(fechaCreacion.format(formatter));
        
        if (fechaVencimiento != null) {
            sb.append("\n⏰ Vence: ").append(fechaVencimiento.format(formatter));
            if (estaVencida()) {
                sb.append(" ⚠️ **VENCIDA**");
            }
        }
        
        sb.append("\n✅ Estado: ").append(completada ? "Completada" : "Pendiente");
        
        return sb.toString();
    }
}
