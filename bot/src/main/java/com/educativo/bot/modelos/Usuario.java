package com.educativo.bot.modelos;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * CONCEPTO: ENCAPSULAMIENTO (continuación)
 * 
 * Esta clase representa un usuario del servidor de Discord.
 * Demuestra encapsulamiento al mantener todos los datos privados
 * y proporcionar métodos controlados para acceder y modificar la información.
 */
public class Usuario {
    
    // ENCAPSULAMIENTO: Todos los atributos son privados
    private String id;                           // ID de Discord del usuario
    private String nombre;                       // Nombre del usuario
    private int puntos;                          // Puntos acumulados por actividad
    private int nivel;                           // Nivel basado en puntos
    private LocalDateTime fechaRegistro;        // Cuándo se registró en el bot
    private LocalDateTime ultimaActividad;      // Última vez que usó el bot
    private Map<String, Integer> puntosPorMateria; // Puntos específicos por materia
    private boolean esModerador;                 // Si tiene permisos de moderador
    private String materiaFavorita;              // Materia en la que más participa
    
    /**
     * Constructor para crear un nuevo usuario
     * 
     * @param id ID de Discord
     * @param nombre Nombre del usuario
     */
    public Usuario(String id, String nombre) {
        // Validación de entrada
        if (id == null || id.trim().isEmpty()) {
            throw new IllegalArgumentException("El ID no puede estar vacío");
        }
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre no puede estar vacío");
        }
        
        // Inicialización de atributos
        this.id = id.trim();
        this.nombre = nombre.trim();
        this.puntos = 0;
        this.nivel = 1;
        this.fechaRegistro = LocalDateTime.now();
        this.ultimaActividad = LocalDateTime.now();
        this.puntosPorMateria = new HashMap<>();
        this.esModerador = false;
        this.materiaFavorita = "General";
    }
    
    // MÉTODOS GETTER - Acceso controlado a los atributos privados
    
    public String getId() {
        return id;
    }
    
    public String getNombre() {
        return nombre;
    }
    
    public int getPuntos() {
        return puntos;
    }
    
    public int getNivel() {
        return nivel;
    }
    
    public LocalDateTime getFechaRegistro() {
        return fechaRegistro;
    }
    
    public LocalDateTime getUltimaActividad() {
        return ultimaActividad;
    }
    
    public boolean isEsModerador() {
        return esModerador;
    }
    
    public String getMateriaFavorita() {
        return materiaFavorita;
    }
    
    /**
     * Obtiene los puntos de una materia específica
     * 
     * @param materia Nombre de la materia
     * @return Puntos en esa materia, 0 si no tiene
     */
    public int getPuntosPorMateria(String materia) {
        return puntosPorMateria.getOrDefault(materia, 0);
    }
    
    /**
     * Obtiene una copia del mapa de puntos por materia
     * Demuestra encapsulamiento: no exponemos el mapa interno directamente
     * 
     * @return Copia del mapa de puntos por materia
     */
    public Map<String, Integer> getPuntosPorMateriaMap() {
        return new HashMap<>(puntosPorMateria);
    }
    
    // MÉTODOS SETTER - Modificación controlada con validación
    
    public void setNombre(String nombre) {
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre no puede estar vacío");
        }
        this.nombre = nombre.trim();
    }
    
    public void setEsModerador(boolean esModerador) {
        this.esModerador = esModerador;
    }
    
    /**
     * Actualiza la última actividad al momento actual
     * Método que encapsula la lógica de actualización de actividad
     */
    public void actualizarActividad() {
        this.ultimaActividad = LocalDateTime.now();
    }
    
    // MÉTODOS DE LÓGICA DE NEGOCIO - Demuestran encapsulamiento de comportamiento
    
    /**
     * Añade puntos al usuario y actualiza su nivel
     * Encapsula la lógica compleja de puntuación y nivelación
     * 
     * @param puntosAAgregar Cantidad de puntos a añadir
     * @param materia Materia en la que se ganan los puntos (opcional)
     */
    public void agregarPuntos(int puntosAAgregar, String materia) {
        if (puntosAAgregar < 0) {
            throw new IllegalArgumentException("No se pueden agregar puntos negativos");
        }
        
        // Actualizar puntos totales
        this.puntos += puntosAAgregar;
        
        // Actualizar puntos por materia si se especifica
        if (materia != null && !materia.trim().isEmpty()) {
            String materiaLimpia = materia.trim();
            int puntosActuales = puntosPorMateria.getOrDefault(materiaLimpia, 0);
            puntosPorMateria.put(materiaLimpia, puntosActuales + puntosAAgregar);
            
            // Actualizar materia favorita si es necesario
            actualizarMateriaFavorita();
        }
        
        // Recalcular nivel
        actualizarNivel();
        
        // Actualizar actividad
        actualizarActividad();
    }
    
    /**
     * Quita puntos al usuario y actualiza su nivel
     * Encapsula la lógica de penalización por puntos
     * 
     * @param puntosAQuitar Cantidad de puntos a quitar
     */
    public void quitarPuntos(int puntosAQuitar) {
        if (puntosAQuitar < 0) {
            throw new IllegalArgumentException("No se pueden quitar puntos negativos");
        }
        
        // Actualizar puntos totales (no permitir puntos negativos)
        this.puntos = Math.max(0, this.puntos - puntosAQuitar);
        
        // Recalcular nivel
        actualizarNivel();
        
        // Actualizar actividad
        actualizarActividad();
    }
    
    /**
     * Calcula y actualiza el nivel basado en los puntos totales
     * Método privado que encapsula la lógica de nivelación
     */
    private void actualizarNivel() {
        // Fórmula simple: nivel = raíz cuadrada de (puntos / 100) + 1
        int nuevoNivel = (int) Math.sqrt(puntos / 100.0) + 1;
        this.nivel = Math.max(1, nuevoNivel); // Mínimo nivel 1
    }
    
    /**
     * Actualiza la materia favorita basada en los puntos por materia
     * Método privado que encapsula esta lógica específica
     */
    private void actualizarMateriaFavorita() {
        String mejorMateria = "General";
        int maxPuntos = 0;
        
        for (Map.Entry<String, Integer> entrada : puntosPorMateria.entrySet()) {
            if (entrada.getValue() > maxPuntos) {
                maxPuntos = entrada.getValue();
                mejorMateria = entrada.getKey();
            }
        }
        
        this.materiaFavorita = mejorMateria;
    }
    
    /**
     * Calcula los puntos necesarios para el siguiente nivel
     * 
     * @return Puntos necesarios para subir de nivel
     */
    public int puntosParaSiguienteNivel() {
        int puntosNivelSiguiente = nivel * nivel * 100;
        return Math.max(0, puntosNivelSiguiente - puntos);
    }
    
    /**
     * Verifica si el usuario está activo (última actividad en las últimas 24 horas)
     * 
     * @return true si está activo, false si no
     */
    public boolean estaActivo() {
        LocalDateTime hace24Horas = LocalDateTime.now().minusHours(24);
        return ultimaActividad.isAfter(hace24Horas);
    }
    
    /**
     * Obtiene un resumen del progreso del usuario
     * Encapsula la presentación de datos del usuario
     * 
     * @return String con el resumen del usuario
     */
    public String getResumenProgreso() {
        StringBuilder sb = new StringBuilder();
        
        sb.append("👤 **").append(nombre).append("**");
        if (esModerador) {
            sb.append(" 👑 *Moderador*");
        }
        sb.append("\n🏆 Nivel: ").append(nivel);
        sb.append(" (").append(puntos).append(" puntos)");
        sb.append("\n📚 Materia favorita: ").append(materiaFavorita);
        sb.append("\n🎯 Faltan ").append(puntosParaSiguienteNivel());
        sb.append(" puntos para nivel ").append(nivel + 1);
        
        if (estaActivo()) {
            sb.append("\n💚 Usuario activo");
        } else {
            sb.append("\n💤 Usuario inactivo");
        }
        
        return sb.toString();
    }
    
    /**
     * Representación en string del usuario
     * Sobrescribe toString() heredado de Object
     */
    @Override
    public String toString() {
        return String.format("Usuario{id='%s', nombre='%s', nivel=%d, puntos=%d}", 
                           id, nombre, nivel, puntos);
    }
}
