package com.educativo.bot.comandos;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import com.educativo.bot.modelos.Materia;
import com.educativo.bot.modelos.Tarea;
import com.educativo.bot.modelos.Usuario;

/**
 * COMANDOS DE TAREAS UNIFICADOS
 * 
 * Esta clase agrupa todas las funcionalidades relacionadas con tareas:
 * - Crear tareas (generales o asociadas a materias)
 * - Listar tareas (todas, pendientes, completadas, vencidas)
 * - Completar tareas
 * - Eliminar tareas
 * - Establecer fechas de vencimiento
 * - Cambiar prioridades
 * 
 * Demuestra POLIMORFISMO con múltiples subcomandos especializados.
 */
public class ComandoTareas extends ComandoBase {
    
    // ENCAPSULAMIENTO: Datos estáticos compartidos
    private static final List<Tarea> tareas = new ArrayList<>();
    private static final List<Usuario> usuarios = new ArrayList<>();
    
    public ComandoTareas() {
        super(
            "tarea",
            "Gestión completa de tareas de estudio",
            "!tarea [crear|listar|completar|eliminar|vencimiento|prioridad] [parámetros]\n\n" +
            "**CREAR TAREA:**\n" +
            "• `!tarea crear \"<título>\" [\"descripción\"] [materia] [prioridad]`\n" +
            "• Ejemplo: `!tarea crear \"Estudiar capítulo 5\" \"Revisar ejemplos\" MAT101 2`\n" +
            "• Prioridad: 1=baja, 2=media, 3=alta (por defecto: 2)\n\n" +
            "**LISTAR TAREAS:**\n" +
            "• `!tarea listar` - Ver todas mis tareas\n" +
            "• `!tarea listar pendientes` - Solo pendientes\n" +
            "• `!tarea listar completadas` - Solo completadas\n" +
            "• `!tarea listar vencidas` - Solo vencidas\n" +
            "• `!tarea listar materia <código>` - Tareas de una materia específica\n\n" +
            "**OTRAS ACCIONES:**\n" +
            "• `!tarea completar <número>` - Marcar como completada\n" +
            "• `!tarea eliminar <número>` - Eliminar tarea\n" +
            "• `!tarea vencimiento <número> <fecha>` - Establecer fecha (dd/MM/yyyy HH:mm)\n" +
            "• `!tarea prioridad <número> <1-3>` - Cambiar prioridad",
            false
        );
    }
    
    @Override
    public String ejecutar(String[] args, String canalId, String usuarioId) {
        if (args.length == 0) {
            return "❌ Debes especificar una acción: `crear`, `listar`, `completar`, `eliminar`, `vencimiento` o `prioridad`\n" +
                   "Usa `!ayuda tarea` para ver todos los comandos disponibles.";
        }
        
        String accion = args[0].toLowerCase();
        
        switch (accion) {
            case "crear":
                return crearTarea(args, usuarioId);
            case "listar":
                return listarTareas(args, usuarioId);
            case "completar":
                return completarTarea(args, usuarioId);
            case "eliminar":
                return eliminarTarea(args, usuarioId);
            case "vencimiento":
                return establecerVencimiento(args, usuarioId);
            case "prioridad":
                return cambiarPrioridad(args, usuarioId);
            default:
                return "❌ Acción no válida: `" + accion + "`\n" +
                       "Acciones disponibles: `crear`, `listar`, `completar`, `eliminar`, `vencimiento`, `prioridad`";
        }
    }
    
    // ========================
    // MÉTODOS DE TAREAS
    // ========================
    
    private String crearTarea(String[] args, String usuarioId) {
        if (args.length < 2) {
            return "❌ **Uso incorrecto**\n" +
                   "Formato: `!tarea crear \"<título>\" [\"descripción\"] [materia] [prioridad]`\n\n" +
                   "**Ejemplo:**\n" +
                   "`!tarea crear \"Estudiar capítulo 5\" \"Revisar ejemplos\" MAT101 2`";
        }
        
        List<String> argumentosParsed = parsearArgumentosConComillas(args, 1);
        
        if (argumentosParsed.isEmpty()) {
            return "❌ Debes proporcionar un título para la tarea entre comillas.";
        }
        
        String titulo = argumentosParsed.get(0);
        String descripcion = argumentosParsed.size() > 1 ? argumentosParsed.get(1) : "";
        String codigoMateria = argumentosParsed.size() > 2 ? argumentosParsed.get(2).toUpperCase() : "General";
        int prioridad = 2; // Prioridad media por defecto
        
        // Parsear prioridad si se proporciona
        if (argumentosParsed.size() > 3) {
            try {
                prioridad = Integer.parseInt(argumentosParsed.get(3));
                if (prioridad < 1 || prioridad > 3) {
                    return "❌ La prioridad debe estar entre 1 (baja) y 3 (alta).";
                }
            } catch (NumberFormatException e) {
                return "❌ La prioridad debe ser un número entre 1 y 3.";
            }
        }
        
        // Verificar materia si no es "General"
        if (!codigoMateria.equals("General")) {
            Materia materia = ComandoMaterias.buscarMateria(codigoMateria);
            if (materia == null) {
                return "❌ Materia `" + codigoMateria + "` no encontrada.\n" +
                       "Usa `!materia listar` para ver materias disponibles, o crea la tarea sin materia específica.";
            }
        }
        
        // Crear tarea
        Tarea nuevaTarea = new Tarea(UUID.randomUUID().toString(), titulo, descripcion, codigoMateria, usuarioId, prioridad);
        tareas.add(nuevaTarea);
        
        // Agregar tarea a la lista estática compartida de ComandoMaterias
        ComandoMaterias.agregarTarea(nuevaTarea);
        
        // Obtener usuario y dar puntos
        Usuario usuario = obtenerOCrearUsuario(usuarioId);
        
        StringBuilder respuesta = new StringBuilder();
        respuesta.append("✅ **Tarea creada exitosamente**\n\n");
        respuesta.append("📝 **").append(titulo).append("**\n");
        if (!descripcion.isEmpty()) {
            respuesta.append("📄 ").append(descripcion).append("\n");
        }
        respuesta.append("📚 Materia: ").append(codigoMateria).append("\n");
        respuesta.append("⭐ Prioridad: ").append(getPrioridadTexto(prioridad)).append("\n");
        respuesta.append("🆔 ID: `").append(nuevaTarea.getId()).append("`");
        
        return respuesta.toString();
    }
    
    private String listarTareas(String[] args, String usuarioId) {
        List<Tarea> tareasUsuario = tareas.stream()
            .filter(t -> t.getCreadorId().equals(usuarioId))
            .collect(Collectors.toList());
        
        if (tareasUsuario.isEmpty()) {
            return "📝 **No tienes tareas registradas**\n\n" +
                   "Crea una tarea con: `!tarea crear \"<título>\"`";
        }
        
        // Verificar si es filtro por materia
        if (args.length > 2 && args[1].toLowerCase().equals("materia")) {
            String codigoMateria = args[2].toUpperCase();
            return listarTareasPorMateria(tareasUsuario, codigoMateria);
        }
        
        String filtro = args.length > 1 ? args[1].toLowerCase() : "todas";
        return formatearListaTareas(tareasUsuario, filtro, "Mis Tareas");
    }
    
    private String completarTarea(String[] args, String usuarioId) {
        if (args.length < 2) {
            return "❌ Debes especificar el número de la tarea a completar.\n" +
                   "Uso: `!tarea completar <número>`\n" +
                   "Usa `!tarea listar` para ver los números.";
        }
        
        try {
            int numero = Integer.parseInt(args[1]);
            List<Tarea> tareasUsuario = tareas.stream()
                .filter(t -> t.getCreadorId().equals(usuarioId))
                .collect(Collectors.toList());
            
            if (numero < 1 || numero > tareasUsuario.size()) {
                return "❌ Número de tarea inválido. Debe estar entre 1 y " + tareasUsuario.size();
            }
            
            Tarea tarea = tareasUsuario.get(numero - 1);
            
            if (tarea.isCompletada()) {
                return "❌ La tarea `" + tarea.getTitulo() + "` ya está completada.";
            }
            
            tarea.setCompletada(true);
            
            // Otorgar puntos basados en prioridad
            Usuario usuario = obtenerOCrearUsuario(usuarioId);
            int puntosBase = 10;
            int puntosBonus = tarea.getPrioridad() * 5; // 5, 10 o 15 puntos bonus
            int puntosTotal = puntosBase + puntosBonus;
            
            usuario.agregarPuntos(puntosTotal, "Tarea completada: " + tarea.getTitulo());
            
            return "✅ **Tarea completada**\n\n" +
                   "📝 " + tarea.getTitulo() + "\n" +
                   "⭐ Prioridad: " + getPrioridadTexto(tarea.getPrioridad()) + "\n" +
                   "🎉 +" + puntosTotal + " puntos otorgados (" + puntosBase + " base + " + puntosBonus + " por prioridad)\n" +
                   "🏆 Total de puntos: " + usuario.getPuntos();
            
        } catch (NumberFormatException e) {
            return "❌ El número de tarea debe ser un número válido.";
        }
    }
    
    private String eliminarTarea(String[] args, String usuarioId) {
        if (args.length < 2) {
            return "❌ Debes especificar el número de la tarea a eliminar.\n" +
                   "Uso: `!tarea eliminar <número>`";
        }
        
        try {
            int numero = Integer.parseInt(args[1]);
            List<Tarea> tareasUsuario = tareas.stream()
                .filter(t -> t.getCreadorId().equals(usuarioId))
                .collect(Collectors.toList());
            
            if (numero < 1 || numero > tareasUsuario.size()) {
                return "❌ Número de tarea inválido.";
            }
            
            Tarea tarea = tareasUsuario.get(numero - 1);
            tareas.remove(tarea);
            
            return "✅ Tarea `" + tarea.getTitulo() + "` eliminada exitosamente.";
            
        } catch (NumberFormatException e) {
            return "❌ El número de tarea debe ser un número válido.";
        }
    }
    
    private String establecerVencimiento(String[] args, String usuarioId) {
        if (args.length < 3) {
            return "❌ Uso: `!tarea vencimiento <número> <fecha>`\n" +
                   "Formato de fecha: dd/MM/yyyy HH:mm\n" +
                   "Ejemplo: `!tarea vencimiento 1 25/12/2024 23:59`";
        }
        
        try {
            int numero = Integer.parseInt(args[1]);
            List<Tarea> tareasUsuario = tareas.stream()
                .filter(t -> t.getCreadorId().equals(usuarioId))
                .collect(Collectors.toList());
            
            if (numero < 1 || numero > tareasUsuario.size()) {
                return "❌ Número de tarea inválido.";
            }
            
            // Parsear fecha
            String fechaStr = String.join(" ", java.util.Arrays.copyOfRange(args, 2, args.length));
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
            LocalDateTime fechaVencimiento = LocalDateTime.parse(fechaStr, formatter);
            
            // Verificar que la fecha no sea en el pasado
            if (fechaVencimiento.isBefore(LocalDateTime.now())) {
                return "❌ La fecha de vencimiento no puede ser en el pasado.";
            }
            
            Tarea tarea = tareasUsuario.get(numero - 1);
            tarea.setFechaVencimiento(fechaVencimiento);
            
            return "✅ **Fecha de vencimiento establecida**\n\n" +
                   "📝 Tarea: " + tarea.getTitulo() + "\n" +
                   "⏰ Vence: " + fechaVencimiento.format(formatter);
            
        } catch (NumberFormatException e) {
            return "❌ El número de tarea debe ser un número válido.";
        } catch (DateTimeParseException e) {
            return "❌ Formato de fecha inválido. Usa: dd/MM/yyyy HH:mm\n" +
                   "Ejemplo: 25/12/2024 23:59";
        }
    }
    
    private String cambiarPrioridad(String[] args, String usuarioId) {
        if (args.length < 3) {
            return "❌ Uso: `!tarea prioridad <número> <1-3>`\n" +
                   "Prioridades: 1=baja, 2=media, 3=alta";
        }
        
        try {
            int numero = Integer.parseInt(args[1]);
            int nuevaPrioridad = Integer.parseInt(args[2]);
            
            if (nuevaPrioridad < 1 || nuevaPrioridad > 3) {
                return "❌ La prioridad debe estar entre 1 (baja) y 3 (alta).";
            }
            
            List<Tarea> tareasUsuario = tareas.stream()
                .filter(t -> t.getCreadorId().equals(usuarioId))
                .collect(Collectors.toList());
            
            if (numero < 1 || numero > tareasUsuario.size()) {
                return "❌ Número de tarea inválido.";
            }
            
            Tarea tarea = tareasUsuario.get(numero - 1);
            int prioridadAnterior = tarea.getPrioridad();
            tarea.setPrioridad(nuevaPrioridad);
            
            return "✅ **Prioridad actualizada**\n\n" +
                   "📝 Tarea: " + tarea.getTitulo() + "\n" +
                   "⭐ Prioridad anterior: " + getPrioridadTexto(prioridadAnterior) + "\n" +
                   "⭐ Nueva prioridad: " + getPrioridadTexto(nuevaPrioridad);
            
        } catch (NumberFormatException e) {
            return "❌ Los números deben ser válidos.";
        }
    }
    
    // ========================
    // MÉTODOS AUXILIARES
    // ========================
    
    private String listarTareasPorMateria(List<Tarea> tareasUsuario, String codigoMateria) {
        List<Tarea> tareasMateria = tareasUsuario.stream()
            .filter(t -> t.getMateria().equalsIgnoreCase(codigoMateria))
            .collect(Collectors.toList());
        
        if (tareasMateria.isEmpty()) {
            return "📝 **Tareas de " + codigoMateria + "**\n\n" +
                   "No tienes tareas para esta materia.";
        }
        
        return formatearListaTareas(tareasMateria, "todas", "Tareas de " + codigoMateria);
    }
    
    private Usuario obtenerOCrearUsuario(String usuarioId) {
        return usuarios.stream()
            .filter(u -> u.getId().equals(usuarioId))
            .findFirst()
            .orElseGet(() -> {
                Usuario nuevoUsuario = new Usuario(usuarioId, "Usuario" + usuarios.size());
                usuarios.add(nuevoUsuario);
                return nuevoUsuario;
            });
    }
    
    private String getPrioridadTexto(int prioridad) {
        switch (prioridad) {
            case 1: return "🟢 Baja";
            case 2: return "🟡 Media";
            case 3: return "🔴 Alta";
            default: return "❓ Desconocida";
        }
    }
    
    private String formatearListaTareas(List<Tarea> listaTareas, String filtro, String titulo) {
        StringBuilder sb = new StringBuilder();
        sb.append("📝 **").append(titulo).append("**\n\n");
        
        List<Tarea> tareasFiltradas = listaTareas;
        
        switch (filtro) {
            case "pendientes":
                tareasFiltradas = listaTareas.stream()
                    .filter(t -> !t.isCompletada())
                    .collect(Collectors.toList());
                sb.setLength(0);
                sb.append("⏳ **").append(titulo).append(" - PENDIENTES**\n\n");
                break;
            case "completadas":
                tareasFiltradas = listaTareas.stream()
                    .filter(Tarea::isCompletada)
                    .collect(Collectors.toList());
                sb.setLength(0);
                sb.append("✅ **").append(titulo).append(" - COMPLETADAS**\n\n");
                break;
            case "vencidas":
                tareasFiltradas = listaTareas.stream()
                    .filter(t -> !t.isCompletada() && t.getFechaVencimiento() != null && 
                            t.getFechaVencimiento().isBefore(LocalDateTime.now()))
                    .collect(Collectors.toList());
                sb.setLength(0);
                sb.append("⚠️ **").append(titulo).append(" - VENCIDAS**\n\n");
                break;
        }
        
        if (tareasFiltradas.isEmpty()) {
            sb.append("*No hay tareas en esta categoría*");
            return sb.toString();
        }
        
        // Ordenar por prioridad (alta a baja) y luego por fecha de creación
        tareasFiltradas.sort((t1, t2) -> {
            int comparePrioridad = Integer.compare(t2.getPrioridad(), t1.getPrioridad());
            if (comparePrioridad != 0) return comparePrioridad;
            return t1.getFechaCreacion().compareTo(t2.getFechaCreacion());
        });
        
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM HH:mm");
        
        for (int i = 0; i < tareasFiltradas.size(); i++) {
            Tarea tarea = tareasFiltradas.get(i);
            sb.append("`").append(i + 1).append(".` ");
            sb.append(tarea.isCompletada() ? "✅" : "⏳");
            sb.append(" **").append(tarea.getTitulo()).append("**");
            sb.append(" ").append(getPrioridadTexto(tarea.getPrioridad()));
            
            if (!tarea.getMateria().equals("General")) {
                sb.append(" *(").append(tarea.getMateria()).append(")*");
            }
            
            sb.append("\n");
            
            if (!tarea.getDescripcion().isEmpty()) {
                sb.append("   📄 ").append(tarea.getDescripcion()).append("\n");
            }
            
            if (tarea.getFechaVencimiento() != null) {
                sb.append("   ⏰ Vence: ").append(tarea.getFechaVencimiento().format(formatter));
                if (tarea.getFechaVencimiento().isBefore(LocalDateTime.now()) && !tarea.isCompletada()) {
                    sb.append(" ⚠️ VENCIDA");
                }
                sb.append("\n");
            }
        }
        
        return sb.toString();
    }
    
    private List<String> parsearArgumentosConComillas(String[] args, int startIndex) {
        List<String> resultado = new ArrayList<>();
        StringBuilder argumentoActual = new StringBuilder();
        boolean dentroDeComillas = false;
        
        for (int i = startIndex; i < args.length; i++) {
            String arg = args[i];
            
            if (!dentroDeComillas) {
                if (arg.startsWith("\"")) {
                    dentroDeComillas = true;
                    argumentoActual.append(arg.substring(1));
                    if (arg.endsWith("\"") && arg.length() > 1) {
                        dentroDeComillas = false;
                        argumentoActual.setLength(argumentoActual.length() - 1);
                        resultado.add(argumentoActual.toString());
                        argumentoActual.setLength(0);
                    }
                } else {
                    resultado.add(arg);
                }
            } else {
                if (arg.endsWith("\"")) {
                    argumentoActual.append(" ").append(arg.substring(0, arg.length() - 1));
                    resultado.add(argumentoActual.toString());
                    argumentoActual.setLength(0);
                    dentroDeComillas = false;
                } else {
                    argumentoActual.append(" ").append(arg);
                }
            }
        }
        
        // Si quedó algo pendiente sin cerrar comillas
        if (argumentoActual.length() > 0) {
            resultado.add(argumentoActual.toString());
        }
        
        return resultado;
    }
    
    // ========================
    // MÉTODOS ESTÁTICOS PARA ACCESO COMPARTIDO
    // ========================
    
    /**
     * Permite acceso a la lista de tareas desde otros comandos
     */
    public static List<Tarea> getTareas() {
        return new ArrayList<>(tareas);
    }
    
    /**
     * Busca tareas por usuario
     */
    public static List<Tarea> getTareasPorUsuario(String usuarioId) {
        return tareas.stream()
            .filter(t -> t.getCreadorId().equals(usuarioId))
            .collect(Collectors.toList());
    }
    
    /**
     * Obtiene estadísticas de tareas por usuario
     */
    public static String getEstadisticasUsuario(String usuarioId) {
        List<Tarea> tareasUsuario = getTareasPorUsuario(usuarioId);
        
        if (tareasUsuario.isEmpty()) {
            return "Sin tareas registradas";
        }
        
        long completadas = tareasUsuario.stream().filter(Tarea::isCompletada).count();
        long pendientes = tareasUsuario.size() - completadas;
        long vencidas = tareasUsuario.stream()
            .filter(t -> !t.isCompletada() && t.getFechaVencimiento() != null && 
                    t.getFechaVencimiento().isBefore(LocalDateTime.now()))
            .count();
        
        return String.format("Total: %d | Completadas: %d | Pendientes: %d | Vencidas: %d", 
                           tareasUsuario.size(), completadas, pendientes, vencidas);
    }
}
