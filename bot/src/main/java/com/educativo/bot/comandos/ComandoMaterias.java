package com.educativo.bot.comandos;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import com.educativo.bot.modelos.Materia;
import com.educativo.bot.modelos.Tarea;
import com.educativo.bot.modelos.Usuario;

/**
 * COMANDOS DE MATERIAS UNIFICADOS
 * 
 * Esta clase agrupa todas las funcionalidades relacionadas con materias académicas:
 * - Crear materias
 * - Listar materias (activas, archivadas, con detalle)
 * - Eliminar materias
 * - Ver tareas de una materia específica
 * - Archivar/desarchivar materias
 * 
 * Demuestra POLIMORFISMO con múltiples subcomandos en una sola clase.
 */
public class ComandoMaterias extends ComandoBase {
    
    // ENCAPSULAMIENTO: Datos estáticos compartidos
    private static final List<Materia> materias = new ArrayList<>();
    private static final List<Tarea> tareas = new ArrayList<>();
    private static final List<Usuario> usuarios = new ArrayList<>();
    
    public ComandoMaterias() {
        super(
            "materia",
            "Gestión completa de materias académicas",
            "!materia [crear|listar|eliminar|tareas|archivar|desarchivar] [parámetros]\n\n" +
            "**CREAR MATERIA:**\n" +
            "• `!materia crear <código> \"<nombre>\" [\"descripción\"] [\"profesor\"]`\n" +
            "• Ejemplo: `!materia crear MAT101 \"Matemáticas\" \"Álgebra básica\" \"Dr. Juan Pérez\"`\n\n" +
            "**LISTAR MATERIAS:**\n" +
            "• `!materia listar` - Ver todas las materias\n" +
            "• `!materia listar activas` - Solo materias activas\n" +
            "• `!materia listar archivadas` - Solo materias archivadas\n" +
            "• `!materia listar detalle` - Vista detallada con estadísticas\n\n" +
            "**OTRAS ACCIONES:**\n" +
            "• `!materia eliminar <código>` - Eliminar materia\n" +
            "• `!materia tareas <código> [pendientes|completadas|vencidas]` - Ver tareas de materia\n" +
            "• `!materia archivar <código>` - Archivar materia\n" +
            "• `!materia desarchivar <código>` - Desarchivar materia",
            false
        );
    }
    
    @Override
    public String ejecutar(String[] args, String canalId, String usuarioId) {
        if (args.length == 0) {
            return "❌ Debes especificar una acción: `crear`, `listar`, `eliminar`, `tareas`, `archivar` o `desarchivar`\n" +
                   "Usa `!ayuda materia` para ver todos los comandos disponibles.";
        }
        
        String accion = args[0].toLowerCase();
        
        switch (accion) {
            case "crear":
                return crearMateria(args, usuarioId);
            case "listar":
                return listarMaterias(args);
            case "eliminar":
                return eliminarMateria(args, usuarioId);
            case "tareas":
                return listarTareasMateria(args);
            case "archivar":
                return archivarMateria(args, usuarioId);
            case "desarchivar":
                return desarchivarMateria(args, usuarioId);
            default:
                return "❌ Acción no válida: `" + accion + "`\n" +
                       "Acciones disponibles: `crear`, `listar`, `eliminar`, `tareas`, `archivar`, `desarchivar`";
        }
    }
    
    // ========================
    // MÉTODOS DE MATERIAS
    // ========================
    
    private String crearMateria(String[] args, String usuarioId) {
        if (args.length < 3) {
            return "❌ **Uso incorrecto**\n" +
                   "Formato: `!materia crear <código> \"<nombre>\" [\"descripción\"] [\"profesor\"]`\n\n" +
                   "**Ejemplo:**\n" +
                   "`!materia crear MAT101 \"Matemáticas\" \"Álgebra básica\" \"Dr. Juan Pérez\"`";
        }
        
        String codigo = args[1].toUpperCase();
        
        // Verificar si ya existe
        if (buscarMateriaPorCodigo(codigo) != null) {
            return "❌ Ya existe una materia con el código `" + codigo + "`";
        }
        
        // Parsear argumentos con comillas
        List<String> argumentosParsed = parsearArgumentosConComillas(args, 2);
        
        if (argumentosParsed.isEmpty()) {
            return "❌ Debes proporcionar un nombre para la materia entre comillas.";
        }
        
        String nombre = argumentosParsed.get(0);
        String descripcion = argumentosParsed.size() > 1 ? argumentosParsed.get(1) : "";
        String profesor = argumentosParsed.size() > 2 ? argumentosParsed.get(2) : "";
        
        // Crear materia
        Materia nuevaMateria = new Materia(UUID.randomUUID().toString(), codigo, nombre, descripcion, profesor, usuarioId);
        materias.add(nuevaMateria);
        
        // Dar puntos al usuario por crear su primera materia
        Usuario usuario = obtenerOCrearUsuario(usuarioId);
        if (materias.stream().filter(m -> m.getCreadorId().equals(usuarioId)).count() == 1) {
            usuario.agregarPuntos(5, "Primera materia creada");
        }
        
        return "✅ **Materia creada exitosamente**\n\n" +
               "📚 **" + codigo + "** - " + nombre + "\n" +
               (descripcion.isEmpty() ? "" : "📝 " + descripcion + "\n") +
               (profesor.isEmpty() ? "" : "👨‍🏫 " + profesor + "\n") +
               "🆔 ID: `" + nuevaMateria.getId() + "`\n" +
               (materias.stream().filter(m -> m.getCreadorId().equals(usuarioId)).count() == 1 ? 
                "🎉 +5 puntos por crear tu primera materia!" : "");
    }
    
    private String listarMaterias(String[] args) {
        if (materias.isEmpty()) {
            return "📚 **No hay materias registradas**\n\n" +
                   "Crea una materia con: `!materia crear <código> \"<nombre>\"`";
        }
        
        String filtro = args.length > 1 ? args[1].toLowerCase() : "todas";
        StringBuilder sb = new StringBuilder();
        
        switch (filtro) {
            case "activas":
                sb.append("📚 **MATERIAS ACTIVAS**\n\n");
                materias.stream()
                    .filter(m -> m.isActiva())
                    .forEach(m -> sb.append(formatearMateriaResumen(m)));
                break;
            case "archivadas":
                sb.append("📦 **MATERIAS ARCHIVADAS**\n\n");
                materias.stream()
                    .filter(m -> !m.isActiva())
                    .forEach(m -> sb.append(formatearMateriaResumen(m)));
                break;
            case "detalle":
                sb.append("📚 **TODAS LAS MATERIAS (DETALLE)**\n\n");
                materias.forEach(m -> sb.append(formatearMateriaDetalle(m)));
                break;
            default:
                sb.append("📚 **TODAS LAS MATERIAS**\n\n");
                materias.forEach(m -> sb.append(formatearMateriaResumen(m)));
                break;
        }
        
        if (sb.toString().trim().endsWith("**")) {
            sb.append("*No hay materias en esta categoría*");
        }
        
        return sb.toString();
    }
    
    private String eliminarMateria(String[] args, String usuarioId) {
        if (args.length < 2) {
            return "❌ Debes especificar el código de la materia a eliminar.\n" +
                   "Uso: `!materia eliminar <código>`";
        }
        
        String codigo = args[1].toUpperCase();
        Materia materia = buscarMateriaPorCodigo(codigo);
        
        if (materia == null) {
            return "❌ Materia `" + codigo + "` no encontrada.";
        }
        
        // Verificar permisos (solo el creador puede eliminar)
        if (!materia.getCreadorId().equals(usuarioId)) {
            return "❌ Solo el creador de la materia puede eliminarla.";
        }
        
        // Verificar si tiene tareas asociadas
        long tareasAsociadas = tareas.stream()
            .filter(t -> codigo.equals(t.getMateria()))
            .count();
        
        if (tareasAsociadas > 0) {
            return "❌ No se puede eliminar la materia `" + codigo + "` porque tiene " + 
                   tareasAsociadas + " tarea(s) asociada(s).\n" +
                   "Elimina primero las tareas o archiva la materia con `!materia archivar " + codigo + "`.";
        }
        
        materias.remove(materia);
        return "✅ Materia `" + codigo + "` eliminada exitosamente.";
    }
    
    private String listarTareasMateria(String[] args) {
        if (args.length < 2) {
            return "❌ Debes especificar el código de la materia.\n" +
                   "Uso: `!materia tareas <código> [pendientes|completadas|vencidas]`";
        }
        
        String codigo = args[1].toUpperCase();
        Materia materia = buscarMateriaPorCodigo(codigo);
        
        if (materia == null) {
            return "❌ Materia `" + codigo + "` no encontrada.";
        }
        
        List<Tarea> tareasMateria = tareas.stream()
            .filter(t -> codigo.equals(t.getMateria()))
            .collect(Collectors.toList());
        
        if (tareasMateria.isEmpty()) {
            return "📝 **Materia: " + materia.getNombre() + "**\n\n" +
                   "No hay tareas para esta materia.\n" +
                   "Crea una con: `!tarea crear \"<título>\" \"<descripción>\" " + codigo + "`";
        }
        
        String filtro = args.length > 2 ? args[2].toLowerCase() : "todas";
        return formatearListaTareas(tareasMateria, filtro, "Materia: " + materia.getNombre());
    }
    
    private String archivarMateria(String[] args, String usuarioId) {
        if (args.length < 2) {
            return "❌ Debes especificar el código de la materia a archivar.\n" +
                   "Uso: `!materia archivar <código>`";
        }
        
        String codigo = args[1].toUpperCase();
        Materia materia = buscarMateriaPorCodigo(codigo);
        
        if (materia == null) {
            return "❌ Materia `" + codigo + "` no encontrada.";
        }
        
        if (!materia.getCreadorId().equals(usuarioId)) {
            return "❌ Solo el creador de la materia puede archivarla.";
        }
        
        if (!materia.isActiva()) {
            return "❌ La materia `" + codigo + "` ya está archivada.";
        }
        
        materia.setActiva(false);
        return "📦 **Materia archivada**\n\n" +
               "📚 " + materia.getCodigo() + " - " + materia.getNombre() + "\n" +
               "💡 Usa `!materia desarchivar " + codigo + "` para restaurarla.";
    }
    
    private String desarchivarMateria(String[] args, String usuarioId) {
        if (args.length < 2) {
            return "❌ Debes especificar el código de la materia a desarchivar.\n" +
                   "Uso: `!materia desarchivar <código>`";
        }
        
        String codigo = args[1].toUpperCase();
        Materia materia = buscarMateriaPorCodigo(codigo);
        
        if (materia == null) {
            return "❌ Materia `" + codigo + "` no encontrada.";
        }
        
        if (!materia.getCreadorId().equals(usuarioId)) {
            return "❌ Solo el creador de la materia puede desarchivarla.";
        }
        
        if (materia.isActiva()) {
            return "❌ La materia `" + codigo + "` no está archivada.";
        }
        
        materia.setActiva(true);
        return "✅ **Materia restaurada**\n\n" +
               "📚 " + materia.getCodigo() + " - " + materia.getNombre() + "\n" +
               "🎯 La materia está ahora activa nuevamente.";
    }
    
    // ========================
    // MÉTODOS AUXILIARES
    // ========================
    
    private Materia buscarMateriaPorCodigo(String codigo) {
        return materias.stream()
            .filter(m -> m.getCodigo().equalsIgnoreCase(codigo))
            .findFirst()
            .orElse(null);
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
    
    private String formatearMateriaResumen(Materia materia) {
        return "📚 **" + materia.getCodigo() + "** - " + materia.getNombre() + 
               (!materia.isActiva() ? " *(archivada)*" : "") + "\n";
    }
    
    private String formatearMateriaDetalle(Materia materia) {
        StringBuilder sb = new StringBuilder();
        sb.append("📚 **").append(materia.getCodigo()).append("** - ").append(materia.getNombre());
        if (!materia.isActiva()) sb.append(" *(archivada)*");
        sb.append("\n");
        
        if (!materia.getDescripcion().isEmpty()) {
            sb.append("📝 ").append(materia.getDescripcion()).append("\n");
        }
        if (!materia.getProfesor().isEmpty()) {
            sb.append("👨‍🏫 ").append(materia.getProfesor()).append("\n");
        }
        
        long tareasCount = tareas.stream()
            .filter(t -> materia.getCodigo().equals(t.getMateria()))
            .count();
        sb.append("📋 Tareas: ").append(tareasCount).append("\n\n");
        
        return sb.toString();
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
                            t.getFechaVencimiento().isBefore(java.time.LocalDateTime.now()))
                    .collect(Collectors.toList());
                sb.setLength(0);
                sb.append("⚠️ **").append(titulo).append(" - VENCIDAS**\n\n");
                break;
        }
        
        if (tareasFiltradas.isEmpty()) {
            sb.append("*No hay tareas en esta categoría*");
            return sb.toString();
        }
        
        for (int i = 0; i < tareasFiltradas.size(); i++) {
            Tarea tarea = tareasFiltradas.get(i);
            sb.append("`").append(i + 1).append(".` ");
            sb.append(tarea.isCompletada() ? "✅" : "⏳");
            sb.append(" **").append(tarea.getTitulo()).append("**");
            
            sb.append("\n");
            if (!tarea.getDescripcion().isEmpty()) {
                sb.append("   📄 ").append(tarea.getDescripcion()).append("\n");
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
     * Permite acceso a la lista de materias desde otros comandos
     */
    public static List<Materia> getMaterias() {
        return new ArrayList<>(materias);
    }
    
    /**
     * Permite acceso a la lista de tareas desde otros comandos
     */
    public static List<Tarea> getTareas() {
        return new ArrayList<>(tareas);
    }
    
    /**
     * Busca una materia por código desde otros comandos
     */
    public static Materia buscarMateria(String codigo) {
        return materias.stream()
            .filter(m -> m.getCodigo().equalsIgnoreCase(codigo))
            .findFirst()
            .orElse(null);
    }
    
    /**
     * Agrega una tarea a la lista desde otros comandos
     */
    public static void agregarTarea(Tarea tarea) {
        tareas.add(tarea);
    }
}
