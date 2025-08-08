package com.educativo.bot.utils;

import java.util.List;

import com.educativo.bot.modelos.Materia;
import com.educativo.bot.modelos.Tarea;

/**
 * CONCEPTO: SEPARACIÓN DE RESPONSABILIDADES
 * 
 * Esta clase utilitaria centraliza todo el formateo de mensajes,
 * evitando duplicación de código y simplificando los comandos.
 * 
 * Demuestra:
 * - Métodos estáticos utilitarios
 * - Responsabilidad única (solo formatear)
 * - Reutilización de código
 */
public class FormateadorMensajes {
    
    // Emojis constantes para consistencia
    private static final String EXITO = "✅";
    private static final String ERROR = "❌";
    private static final String INFO = "ℹ️";
    private static final String LIBRO = "📚";
    private static final String TAREA = "📝";
    private static final String PUNTO = "•";
    
    /**
     * Formatea un mensaje de éxito simple
     */
    public static String exito(String mensaje) {
        return EXITO + " " + mensaje;
    }
    
    /**
     * Formatea un mensaje de error simple
     */
    public static String error(String mensaje) {
        return ERROR + " **Error**: " + mensaje;
    }
    
    /**
     * Formatea un mensaje informativo simple
     */
    public static String info(String mensaje) {
        return INFO + " " + mensaje;
    }
    
    /**
     * Crea una lista simple de materias
     */
    public static String listaMaterias(List<Materia> materias) {
        if (materias.isEmpty()) {
            return info(LIBRO + " **No hay materias creadas**\n\n" +
                       "Usa: `!materia crear CODIGO \"Nombre\"`");
        }
        
        StringBuilder sb = new StringBuilder();
        sb.append(LIBRO).append(" **Materias** (").append(materias.size()).append("):\n\n");
        
        for (int i = 0; i < materias.size(); i++) {
            Materia m = materias.get(i);
            sb.append("**").append(i + 1).append(".** ")
              .append(m.getCodigo()).append(" - ")
              .append(m.getNombre());
            
            if (m.getCantidadTareas() > 0) {
                sb.append(" (").append(m.getCantidadTareas()).append(" tareas)");
            }
            sb.append("\n");
        }
        
        return info(sb.toString());
    }
    
    /**
     * Crea una lista simple de tareas
     */
    public static String listaTareas(List<Tarea> tareas, String materia) {
        if (tareas.isEmpty()) {
            return info(TAREA + " **No hay tareas**" + 
                       (materia != null ? " para " + materia : "") + "\n\n" +
                       "Usa: `!tarea crear \"Título\" [materia]`");
        }
        
        StringBuilder sb = new StringBuilder();
        sb.append(TAREA).append(" **Tareas**");
        if (materia != null) {
            sb.append(" - ").append(materia);
        }
        sb.append(" (").append(tareas.size()).append("):\n\n");
        
        for (int i = 0; i < tareas.size(); i++) {
            Tarea t = tareas.get(i);
            sb.append("**").append(i + 1).append(".** ");
            
            // Estado
            if (t.isCompletada()) {
                sb.append("✅ ");
            } else if (t.estaVencida()) {
                sb.append("⚠️ ");
            } else {
                sb.append("📝 ");
            }
            
            // Información básica
            sb.append("**").append(t.getTitulo()).append("**");
            if (!t.getMateria().isEmpty()) {
                sb.append(" (").append(t.getMateria()).append(")");
            }
            sb.append("\n");
        }
        
        return info(sb.toString());
    }
    
    /**
     * Formatea información de una materia creada
     */
    public static String materiaCreada(Materia materia) {
        return exito(LIBRO + " **Materia creada**\n\n" +
                    PUNTO + " **Código:** " + materia.getCodigo() + "\n" +
                    PUNTO + " **Nombre:** " + materia.getNombre() + "\n" +
                    (materia.getDescripcion().isEmpty() ? "" : 
                     PUNTO + " **Descripción:** " + materia.getDescripcion() + "\n") +
                    (materia.getProfesor().equals("Sin asignar") ? "" :
                     PUNTO + " **Profesor:** " + materia.getProfesor() + "\n"));
    }
    
    /**
     * Formatea información de una tarea creada
     */
    public static String tareaCreada(Tarea tarea) {
        return exito(TAREA + " **Tarea creada**\n\n" +
                    PUNTO + " **Título:** " + tarea.getTitulo() + "\n" +
                    PUNTO + " **Materia:** " + tarea.getMateria() + "\n" +
                    (tarea.getDescripcion().isEmpty() ? "" :
                     PUNTO + " **Descripción:** " + tarea.getDescripcion() + "\n") +
                    PUNTO + " **Prioridad:** " + tarea.getPrioridadTexto());
    }
    
    /**
     * Mensaje de ayuda para uso incorrecto
     */
    public static String usoIncorrecto(String comando, String usoCorrector) {
        return error("Uso incorrecto\n\n**Uso correcto:** `" + usoCorrector + "`\n" +
                    "💡 Usa `!ayuda " + comando + "` para más información");
    }
    
    /**
     * Mensaje cuando no se encuentra un elemento
     */
    public static String noEncontrado(String tipo, String identificador) {
        return error(tipo + " no encontrada: **" + identificador + "**\n\n" +
                    "💡 Usa `!" + tipo.toLowerCase() + " listar` para ver disponibles");
    }
}
