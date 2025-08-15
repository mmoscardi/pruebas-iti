package com.educativo.bot.comandos;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.educativo.bot.modelos.Usuario;

/**
 * COMANDOS DE SISTEMA UNIFICADOS
 * 
 * Esta clase agrupa todas las funcionalidades del sistema del bot:
 * - Ayuda y documentación
 * - Gestión de puntos y estadísticas
 * - Mensajes de bienvenida
 * - Información del bot
 * - Estadísticas generales
 * 
 * Demuestra POLIMORFISMO con múltiples funcionalidades de sistema.
 */
public class ComandoSistema extends ComandoBase {
    
    // ENCAPSULAMIENTO: Lista de usuarios para gestión de puntos
    private static final List<Usuario> usuarios = new ArrayList<>();
    private static final String VERSION_BOT = "2.0.0";
    
    public ComandoSistema() {
        super(
            "sistema",
            "Comandos de sistema, ayuda y configuración del bot",
            "!sistema [ayuda|puntos|bienvenida|info|stats] [parámetros]\n\n" +
            "**AYUDA Y DOCUMENTACIÓN:**\n" +
            "• `!sistema ayuda [comando]` - Ayuda general o específica\n" +
            "• `!sistema info` - Información del bot\n\n" +
            "**GESTIÓN DE PUNTOS:**\n" +
            "• `!sistema puntos` - Ver mis puntos\n" +
            "• `!sistema puntos [@usuario]` - Ver puntos de otro usuario\n" +
            "• `!sistema puntos ranking [top]` - Ver ranking de puntos\n\n" +
            "**BIENVENIDA:**\n" +
            "• `!sistema bienvenida [@usuario]` - Mensaje de bienvenida\n\n" +
            "**ESTADÍSTICAS:**\n" +
            "• `!sistema stats` - Estadísticas del bot y usuarios",
            false
        );
    }
    
    @Override
    public String ejecutar(String[] args, String canalId, String usuarioId) {
        if (args.length == 0) {
            return "❌ Debes especificar un tipo de comando: `ayuda`, `puntos`, `bienvenida`, `info` o `stats`\n" +
                   "Usa `!sistema ayuda` para ver todos los comandos disponibles.";
        }
        
        String tipoComando = args[0].toLowerCase();
        
        switch (tipoComando) {
            case "ayuda":
                return ejecutarComandoAyuda(args);
            case "puntos":
                return ejecutarComandoPuntos(args, usuarioId);
            case "bienvenida":
                return ejecutarComandoBienvenida(args, usuarioId);
            case "info":
                return ejecutarComandoInfo();
            case "stats":
                return ejecutarComandoStats();
            default:
                return "❌ Tipo de comando no válido: `" + tipoComando + "`\n" +
                       "Tipos disponibles: `ayuda`, `puntos`, `bienvenida`, `info`, `stats`";
        }
    }
    
    /**
     * SISTEMA DE AYUDA
     * Proporciona información detallada sobre todos los comandos
     */
    private String ejecutarComandoAyuda(String[] args) {
        if (args.length <= 1) {
            return generarAyudaGeneral();
        }
        
        String comandoEspecifico = args[1].toLowerCase();
        return generarAyudaEspecifica(comandoEspecifico);
    }
    
    private String generarAyudaGeneral() {
        StringBuilder sb = new StringBuilder();
        sb.append("🤖 **BOT EDUCATIVO - AYUDA GENERAL**\n\n");
        sb.append("**📚 GESTIÓN DE MATERIAS:**\n");
        sb.append("• `!materia crear <código> \"<nombre>\" [\"descripción\"] [\"profesor\"]`\n");
        sb.append("• `!materia listar [activas|archivadas|detalle]`\n");
        sb.append("• `!materia eliminar <código>`\n");
        sb.append("• `!materia tareas <código> [filtro]`\n");
        sb.append("• `!materia archivar/desarchivar <código>`\n\n");
        
        sb.append("**📝 GESTIÓN DE TAREAS:**\n");
        sb.append("• `!tarea crear \"<título>\" [\"descripción\"] [materia] [prioridad]`\n");
        sb.append("• `!tarea listar [pendientes|completadas|vencidas]`\n");
        sb.append("• `!tarea completar <número>`\n");
        sb.append("• `!tarea eliminar <número>`\n");
        sb.append("• `!tarea vencimiento <número> <fecha>`\n");
        sb.append("• `!tarea prioridad <número> <1-3>`\n\n");
        
        sb.append("**⚙️ COMANDOS DE SISTEMA:**\n");
        sb.append("• `!sistema ayuda [comando]` - Esta ayuda\n");
        sb.append("• `!sistema puntos [usuario|ranking]` - Sistema de puntos\n");
        sb.append("• `!sistema bienvenida` - Mensajes de bienvenida\n");
        sb.append("• `!sistema info` - Información del bot\n");
        sb.append("• `!sistema stats` - Estadísticas generales\n\n");
        
        sb.append("**📖 AYUDA ESPECÍFICA:**\n");
        sb.append("Usa `!sistema ayuda <comando>` para obtener ayuda detallada.\n");
        sb.append("Comandos disponibles: `materia`, `tarea`, `puntos`\n\n");
        
        sb.append("**🎯 CARACTERÍSTICAS PRINCIPALES:**\n");
        sb.append("• ✅ Gestión completa de materias académicas\n");
        sb.append("• ✅ Sistema de tareas con prioridades y fechas\n");
        sb.append("• ✅ Ranking de estudiantes por puntos\n");
        sb.append("• ✅ Organización por materias específicas\n");
        sb.append("• ✅ Estadísticas de progreso académico\n\n");
        
        sb.append("**💡 CONSEJOS:**\n");
        sb.append("• Usa comillas para argumentos con espacios\n");
        sb.append("• Los códigos de materia no distinguen mayúsculas\n");
        sb.append("• Completa tareas para ganar puntos\n");
        sb.append("• Las tareas de mayor prioridad dan más puntos\n");
        sb.append("• Consulta el ranking para motivarte\n\n");
        
        sb.append("🆔 **Bot Educativo v").append(VERSION_BOT).append("**");
        
        return sb.toString();
    }
    
    private String generarAyudaEspecifica(String comando) {
        switch (comando) {
            case "materia":
                return "📚 **AYUDA: COMANDOS DE MATERIAS**\n\n" +
                       "**CREAR MATERIA:**\n" +
                       "• `!materia crear <código> \"<nombre>\" [\"descripción\"] [\"profesor\"]`\n" +
                       "• El código debe ser único (ej: MAT101, FIS201)\n" +
                       "• El nombre es obligatorio y debe ir entre comillas\n" +
                       "• Descripción y profesor son opcionales\n\n" +
                       "**LISTAR MATERIAS:**\n" +
                       "• `!materia listar` - Todas las materias\n" +
                       "• `!materia listar activas` - Solo activas\n" +
                       "• `!materia listar archivadas` - Solo archivadas\n" +
                       "• `!materia listar detalle` - Vista completa con estadísticas\n\n" +
                       "**GESTIÓN:**\n" +
                       "• `!materia eliminar <código>` - Eliminar (solo si no tiene tareas)\n" +
                       "• `!materia archivar <código>` - Archivar materia\n" +
                       "• `!materia desarchivar <código>` - Restaurar materia archivada\n" +
                       "• `!materia tareas <código> [filtro]` - Ver tareas de la materia\n\n" +
                       "**EJEMPLOS:**\n" +
                       "```\n" +
                       "!materia crear MAT101 \"Matemáticas\" \"Álgebra básica\" \"Dr. Smith\"\n" +
                       "!materia listar detalle\n" +
                       "!materia tareas MAT101 pendientes\n" +
                       "```";
            
            case "tarea":
                return "📝 **AYUDA: COMANDOS DE TAREAS**\n\n" +
                       "**CREAR TAREA:**\n" +
                       "• `!tarea crear \"<título>\" [\"descripción\"] [materia] [prioridad]`\n" +
                       "• Título obligatorio entre comillas\n" +
                       "• Prioridad: 1=baja, 2=media (por defecto), 3=alta\n" +
                       "• Si no especificas materia, se asigna como \"General\"\n\n" +
                       "**LISTAR TAREAS:**\n" +
                       "• `!tarea listar` - Todas mis tareas\n" +
                       "• `!tarea listar pendientes` - Solo pendientes\n" +
                       "• `!tarea listar completadas` - Solo completadas\n" +
                       "• `!tarea listar vencidas` - Solo vencidas\n" +
                       "• `!tarea listar materia <código>` - De una materia específica\n\n" +
                       "**GESTIÓN:**\n" +
                       "• `!tarea completar <número>` - Marcar como completada\n" +
                       "• `!tarea eliminar <número>` - Eliminar tarea\n" +
                       "• `!tarea vencimiento <número> <dd/MM/yyyy HH:mm>` - Establecer fecha\n" +
                       "• `!tarea prioridad <número> <1-3>` - Cambiar prioridad\n\n" +
                       "**SISTEMA DE PUNTOS:**\n" +
                       "• Completar tarea: 10 puntos base\n" +
                       "• Bonus por prioridad: +5 (baja), +10 (media), +15 (alta)\n" +
                       "• Total posible: 15-25 puntos por tarea\n\n" +
                       "**EJEMPLOS:**\n" +
                       "```\n" +
                       "!tarea crear \"Estudiar capítulo 5\" \"Revisar ejemplos\" MAT101 3\n" +
                       "!tarea vencimiento 1 25/12/2024 23:59\n" +
                       "!tarea completar 1\n" +
                       "```";
            
            case "puntos":
                return "🏆 **AYUDA: SISTEMA DE PUNTOS**\n\n" +
                       "**CONSULTAR PUNTOS:**\n" +
                       "• `!sistema puntos` - Tus puntos actuales\n" +
                       "• `!sistema puntos @usuario` - Puntos de otro usuario\n" +
                       "• `!sistema puntos ranking` - Top 10 del servidor\n" +
                       "• `!sistema puntos ranking 20` - Top 20 del servidor\n\n" +
                       "**FORMAS DE GANAR PUNTOS:**\n" +
                       "• ✅ Crear primera materia: +5 puntos\n" +
                       "• ✅ Completar tarea prioridad baja: +15 puntos (10+5)\n" +
                       "• ✅ Completar tarea prioridad media: +20 puntos (10+10)\n" +
                       "• ✅ Completar tarea prioridad alta: +25 puntos (10+15)\n\n" +
                       "**NIVELES:**\n" +
                       "• El nivel se calcula automáticamente según puntos\n" +
                       "• Fórmula: √(puntos/100) + 1\n" +
                       "• Cada nivel requiere más puntos que el anterior\n\n" +
                       "**EJEMPLOS:**\n" +
                       "```\n" +
                       "!sistema puntos\n" +
                       "!sistema puntos ranking\n" +
                       "!sistema puntos @EstudianteEjemplo\n" +
                       "```";
            
            default:
                return "❌ No hay ayuda específica disponible para: `" + comando + "`\n" +
                       "Comandos con ayuda específica: `materia`, `tarea`, `puntos`\n" +
                       "Usa `!sistema ayuda` para la ayuda general.";
        }
    }
    
    /**
     * SISTEMA DE PUNTOS
     * Gestiona puntos, rankings y recompensas
     */
    private String ejecutarComandoPuntos(String[] args, String usuarioId) {
        if (args.length <= 1) {
            // Mostrar puntos propios por defecto
            return verPuntos(usuarioId, null);
        }
        
        String parametro = args[1].toLowerCase();
        
        if (parametro.equals("ranking")) {
            String limite = args.length > 2 ? args[2] : "10";
            return verRanking(limite);
        } else if (parametro.startsWith("@")) {
            // Ver puntos de otro usuario
            return verPuntos(usuarioId, parametro);
        } else {
            return verPuntos(usuarioId, null);
        }
    }
    
    private String verPuntos(String usuarioId, String usuarioObjetivo) {
        String idConsulta = usuarioObjetivo != null ? extraerIdUsuario(usuarioObjetivo) : usuarioId;
        Usuario usuario = obtenerOCrearUsuario(idConsulta);
        
        StringBuilder sb = new StringBuilder();
        sb.append("🏆 **PUNTOS DEL USUARIO**\n\n");
        
        if (usuarioObjetivo != null) {
            sb.append("👤 Usuario: ").append(usuarioObjetivo).append("\n");
        } else {
            sb.append("👤 Tus puntos actuales\n");
        }
        
        sb.append("💎 **").append(usuario.getPuntos()).append(" puntos**\n\n");
        
        sb.append("📊 **Estadísticas:**\n");
        sb.append("• 🏆 Nivel actual: ").append(usuario.getNivel()).append("\n");
        sb.append("• 📚 Materia favorita: ").append(usuario.getMateriaFavorita()).append("\n");
        sb.append("• 🎯 Puntos para siguiente nivel: ").append(usuario.puntosParaSiguienteNivel()).append("\n");
        sb.append("• 💚 Estado: ").append(usuario.estaActivo() ? "Activo" : "Inactivo").append("\n");
        
        // Agregar estadísticas de tareas si están disponibles
        String estadisticasTareas = ComandoTareas.getEstadisticasUsuario(idConsulta);
        sb.append("• 📝 Tareas: ").append(estadisticasTareas).append("\n");
        
        // Calcular posición en ranking
        List<Usuario> ranking = usuarios.stream()
            .sorted((u1, u2) -> Integer.compare(u2.getPuntos(), u1.getPuntos()))
            .collect(Collectors.toList());
        
        int posicion = ranking.indexOf(usuario) + 1;
        sb.append("• 🏅 Posición en ranking: #").append(posicion).append(" de ").append(usuarios.size());
        
        return sb.toString();
    }
    
    private String verRanking(String limitStr) {
        try {
            int limite = Integer.parseInt(limitStr);
            limite = Math.max(1, Math.min(limite, 50)); // Entre 1 y 50
            
            List<Usuario> ranking = usuarios.stream()
                .sorted((u1, u2) -> Integer.compare(u2.getPuntos(), u1.getPuntos()))
                .limit(limite)
                .collect(Collectors.toList());
            
            if (ranking.isEmpty()) {
                return "🏆 **No hay usuarios con puntos registrados**\n\n" +
                       "¡Sé el primero en ganar puntos completando tareas!";
            }
            
            StringBuilder sb = new StringBuilder();
            sb.append("🏆 **RANKING DE PUNTOS - TOP ").append(limite).append("**\n\n");
            
            for (int i = 0; i < ranking.size(); i++) {
                Usuario usuario = ranking.get(i);
                String medalla = i == 0 ? "🥇" : i == 1 ? "🥈" : i == 2 ? "🥉" : "🏅";
                
                sb.append(medalla).append(" **#").append(i + 1).append("** ");
                sb.append(usuario.getNombre()).append(" - ");
                sb.append("**").append(usuario.getPuntos()).append(" puntos**");
                sb.append(" (Nivel ").append(usuario.getNivel()).append(")\n");
            }
            
            return sb.toString();
            
        } catch (NumberFormatException e) {
            return "❌ El límite debe ser un número entre 1 y 50.";
        }
    }
    
    /**
     * SISTEMA DE BIENVENIDA
     * Genera mensajes de bienvenida personalizados
     */
    private String ejecutarComandoBienvenida(String[] args, String usuarioId) {
        if (args.length <= 1) {
            return crearMensajeBienvenidaGeneral();
        } else {
            // Bienvenida para usuario específico
            return crearMensajeBienvenidaEspecifica(args[1]);
        }
    }
    
    private String crearMensajeBienvenidaGeneral() {
        return "👋 **¡BIENVENIDO AL BOT EDUCATIVO!**\n\n" +
               "🎓 **¿Qué puedes hacer aquí?**\n" +
               "• 📚 Crear y gestionar tus materias académicas\n" +
               "• 📝 Organizar tareas con prioridades y fechas\n" +
               "• 🏆 Ganar puntos completando actividades\n" +
               "• 📊 Competir en el ranking de estudiantes\n\n" +
               "🚀 **Primeros pasos:**\n" +
               "1️⃣ Usa `!sistema ayuda` para ver todos los comandos\n" +
               "2️⃣ Crea tu primera materia: `!materia crear MAT101 \"Matemáticas\"`\n" +
               "3️⃣ Añade tareas: `!tarea crear \"Estudiar capítulo 1\" \"\" MAT101 2`\n" +
               "4️⃣ ¡Completa tareas y gana puntos!\n\n" +
               "💡 **Consejos importantes:**\n" +
               "• Usa comillas para argumentos con espacios\n" +
               "• Las tareas de mayor prioridad dan más puntos\n" +
               "• Establece fechas de vencimiento para organizarte mejor\n\n" +
               "¡Que tengas una excelente experiencia de estudio! 🌟";
    }
    
    private String crearMensajeBienvenidaEspecifica(String usuarioMencionado) {
        return "👋 **¡Bienvenido " + usuarioMencionado + "!**\n\n" +
               "🎓 Te damos la bienvenida al **Bot Educativo**, tu asistente para organizar tus estudios.\n\n" +
               "🌟 **Comienza ahora:**\n" +
               "• `!sistema ayuda` - Descubre todos los comandos\n" +
               "• `!materia crear` - Crea tu primera materia\n" +
               "• `!sistema puntos` - Consulta tus puntos\n\n" +
               "💪 ¡Estamos aquí para ayudarte a alcanzar tus metas académicas!";
    }
    
    /**
     * INFORMACIÓN DEL BOT
     */
    private String ejecutarComandoInfo() {
        long tiempoInicio = System.currentTimeMillis() - (1000 * 60 * 60 * 2); // Simular 2 horas activo
        long tiempoActivo = System.currentTimeMillis() - tiempoInicio;
        
        return "🤖 **INFORMACIÓN DEL BOT EDUCATIVO**\n\n" +
               "📋 **Detalles técnicos:**\n" +
               "• 🏷️ Versión: " + VERSION_BOT + "\n" +
               "• ⚡ Estado: Activo y funcionando\n" +
               "• 🕒 Tiempo activo: " + formatearTiempo(tiempoActivo) + "\n" +
               "• 💾 Sistema de datos: En memoria (simulado)\n\n" +
               "🎯 **Funcionalidades principales:**\n" +
               "• ✅ Gestión completa de materias académicas\n" +
               "• ✅ Sistema de tareas con prioridades y fechas\n" +
               "• ✅ Ranking competitivo de estudiantes\n" +
               "• ✅ Sistema de puntos gamificado\n" +
               "• ✅ Organización por materias específicas\n" +
               "• ✅ Estadísticas detalladas de progreso\n\n" +
               "👨‍💻 **Tecnología:**\n" +
               "• 📚 Lenguaje: Java con JDA (Java Discord API)\n" +
               "• 🏗️ Arquitectura: POO con herencia y polimorfismo\n" +
               "• 🔧 Patrón: Command Pattern especializado por funcionalidad\n" +
               "• 📦 Organización: Comandos agrupados por responsabilidad\n\n" +
               "🆔 **Identificación del bot**\n" +
               "Bot desarrollado para demostrar conceptos de programación orientada a objetos aplicados a un sistema educativo funcional.";
    }
    
    /**
     * ESTADÍSTICAS DEL BOT
     */
    private String ejecutarComandoStats() {
        int totalUsuarios = usuarios.size();
        int usuariosActivos = (int) usuarios.stream().filter(Usuario::estaActivo).count();
        int totalPuntos = usuarios.stream().mapToInt(Usuario::getPuntos).sum();
        
        // Obtener estadísticas de materias y tareas
        List<com.educativo.bot.modelos.Materia> materias = ComandoMaterias.getMaterias();
        List<com.educativo.bot.modelos.Tarea> tareas = ComandoTareas.getTareas();
        
        long materiasActivas = materias.stream().filter(com.educativo.bot.modelos.Materia::isActiva).count();
        long tareasCompletadas = tareas.stream().filter(com.educativo.bot.modelos.Tarea::isCompletada).count();
        
        return "📊 **ESTADÍSTICAS DEL BOT**\n\n" +
               "👥 **Usuarios:**\n" +
               "• Total registrados: " + totalUsuarios + "\n" +
               "• Usuarios activos: " + usuariosActivos + "\n" +
               "• Promedio puntos/usuario: " + (totalUsuarios > 0 ? totalPuntos / totalUsuarios : 0) + "\n\n" +
               "📚 **Materias:**\n" +
               "• Total creadas: " + materias.size() + "\n" +
               "• Materias activas: " + materiasActivas + "\n" +
               "• Materias archivadas: " + (materias.size() - materiasActivas) + "\n\n" +
               "📝 **Tareas:**\n" +
               "• Total creadas: " + tareas.size() + "\n" +
               "• Tareas completadas: " + tareasCompletadas + "\n" +
               "• Tareas pendientes: " + (tareas.size() - tareasCompletadas) + "\n" +
               "• Tasa de completación: " + (tareas.size() > 0 ? (tareasCompletadas * 100 / tareas.size()) : 0) + "%\n\n" +
               "💎 **Sistema de puntos:**\n" +
               "• Total puntos en circulación: " + totalPuntos + "\n" +
               "• Usuario con más puntos: " + obtenerUsuarioConMasPuntos() + "\n\n" +
               "⚡ **Estado del sistema:**\n" +
               "• ✅ Comandos de materias: Operativo\n" +
               "• ✅ Comandos de tareas: Operativo\n" +
               "• ✅ Sistema de puntos: Operativo\n" +
               "• ✅ Gestión de usuarios: Operativo\n\n" +
               "🔄 **Última actualización:** Bot Educativo v" + VERSION_BOT;
    }
    
    // ========================
    // MÉTODOS AUXILIARES
    // ========================
    
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
    
    private String extraerIdUsuario(String mencion) {
        // Simular extracción de ID de mención de Discord
        if (mencion.startsWith("<@") && mencion.endsWith(">")) {
            return mencion.substring(2, mencion.length() - 1).replace("!", "");
        }
        return mencion;
    }
    
    private String obtenerUsuarioConMasPuntos() {
        return usuarios.stream()
            .max((u1, u2) -> Integer.compare(u1.getPuntos(), u2.getPuntos()))
            .map(u -> u.getNombre() + " (" + u.getPuntos() + " pts)")
            .orElse("Ninguno");
    }
    
    private String formatearTiempo(long milisegundos) {
        long segundos = milisegundos / 1000;
        long minutos = segundos / 60;
        long horas = minutos / 60;
        long dias = horas / 24;
        
        if (dias > 0) {
            return String.format("%d días, %d horas", dias, horas % 24);
        } else if (horas > 0) {
            return String.format("%d horas, %d minutos", horas, minutos % 60);
        } else if (minutos > 0) {
            return String.format("%d minutos, %d segundos", minutos, segundos % 60);
        } else {
            return String.format("%d segundos", segundos);
        }
    }
    
    // ========================
    // MÉTODOS ESTÁTICOS PARA ACCESO COMPARTIDO
    // ========================
    
    /**
     * Permite acceso a la lista de usuarios desde otros comandos
     */
    public static List<Usuario> getUsuarios() {
        return new ArrayList<>(usuarios);
    }
    
    /**
     * Busca un usuario por ID
     */
    public static Usuario buscarUsuario(String usuarioId) {
        return usuarios.stream()
            .filter(u -> u.getId().equals(usuarioId))
            .findFirst()
            .orElse(null);
    }
    
    /**
     * Crea o obtiene un usuario
     */
    public static Usuario obtenerOCrearUsuarioEstatico(String usuarioId) {
        return usuarios.stream()
            .filter(u -> u.getId().equals(usuarioId))
            .findFirst()
            .orElseGet(() -> {
                Usuario nuevoUsuario = new Usuario(usuarioId, "Usuario" + usuarios.size());
                usuarios.add(nuevoUsuario);
                return nuevoUsuario;
            });
    }
}
