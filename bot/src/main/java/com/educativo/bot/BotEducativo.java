package com.educativo.bot;

import java.util.HashMap;
import java.util.Map;

import com.educativo.bot.comandos.ComandoMaterias;
import com.educativo.bot.comandos.ComandoSistema;
import com.educativo.bot.comandos.ComandoTareas;
import com.educativo.bot.interfaces.Comando;
import com.educativo.bot.interfaces.GestorDatos;
import com.educativo.bot.servicios.GestorDatosArchivo;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.requests.GatewayIntent;

/**
 * CLASE PRINCIPAL DEL BOT EDUCATIVO
 * 
 * Esta clase demuestra TODOS los conceptos de POO dados en clase:
 * 
 * 1. POO: Organización en clases y objetos
 * 2. HERENCIA: Extiende ListenerAdapter de JDA
 * 3. POLIMORFISMO: Uso de interfaces y métodos sobrescribidos
 * 4. ENCAPSULAMIENTO: Atributos privados y métodos públicos controlados
 * 5. INTERFACES: Implementación y uso de Comando y GestorDatos
 * 6. FUNCIONES: Métodos estáticos utilitarios
 * 7. MÉTODOS: Métodos de instancia para funcionalidad específica
 * 
 * El bot está diseñado para ser educativo y útil para estudiantes.
 */
public class BotEducativo extends ListenerAdapter {
    
    // ENCAPSULAMIENTO: Atributos privados
    private static final String VERSION = "1.0.0";
    private static final String PREFIJO_COMANDO = "!";
    
    private final JDA jda;                                    // Instancia de la API de Discord
    private final Map<String, Comando> comandos;             // Mapa de comandos disponibles
    private final GestorDatos gestorDatos;                   // Sistema de persistencia de datos
    private final long tiempoInicio;                         // Timestamp de inicio del bot
    
    /**
     * Constructor del bot
     * Demuestra ENCAPSULAMIENTO al inicializar todos los componentes privados
     * 
     * @param token Token del bot de Discord
     * @throws Exception Si hay error al conectar con Discord
     */
    public BotEducativo(String token) throws Exception {
        this.tiempoInicio = System.currentTimeMillis();
        this.comandos = new HashMap<>();
        this.gestorDatos = new GestorDatosArchivo("datos/bot_educativo.json");
        
        // Configurar e inicializar JDA (Java Discord API)
        this.jda = JDABuilder.createDefault(token)
                .setActivity(Activity.playing("Ayudando a estudiar | !ayuda"))
                .enableIntents(GatewayIntent.MESSAGE_CONTENT, GatewayIntent.GUILD_MEMBERS)
                .addEventListeners(this)
                .build();
        
        // Registrar comandos usando POLIMORFISMO
        registrarComandos();
        
        // Esperar a que el bot esté listo
        jda.awaitReady();
        
        System.out.println("🤖 Bot Educativo v" + VERSION + " iniciado correctamente!");
        imprimirEstadisticasInicio();
    }
    
    /**
     * HERENCIA: Sobrescribimos el método de ListenerAdapter
     * POLIMORFISMO: Este método será llamado polimórficamente por JDA
     * 
     * @param event Evento de mensaje recibido
     */
    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        // Ignorar mensajes de bots (incluyendo el nuestro)
        if (event.getAuthor().isBot()) {
            return;
        }
        
        Message mensaje = event.getMessage();
        String contenido = mensaje.getContentRaw();
        
        // Verificar si es un comando (empieza con el prefijo)
        if (!contenido.startsWith(PREFIJO_COMANDO)) {
            return;
        }
        
        // Procesar el comando
        procesarComando(event, contenido);
    }
    
    /**
     * HERENCIA: Sobrescribimos el método de ListenerAdapter
     * POLIMORFISMO: Este método será llamado polimórficamente por JDA cuando un miembro se une
     * 
     * @param event Evento de miembro que se une al servidor
     */
    @Override
    public void onGuildMemberJoin(GuildMemberJoinEvent event) {
        try {
            // Buscar el canal de bienvenida
            TextChannel canalBienvenida = event.getGuild().getTextChannelsByName("bienvenida", true)
                    .stream()
                    .findFirst()
                    .orElse(null);
            
            if (canalBienvenida == null) {
                // Si no existe el canal "bienvenida", usar el canal general
                canalBienvenida = event.getGuild().getTextChannelsByName("general", true)
                        .stream()
                        .findFirst()
                        .orElse(null);
            }
            
            if (canalBienvenida != null) {
                // Crear mensaje de bienvenida simple
                String mensajeBienvenida = "👋 **¡Bienvenido " + "<@" + event.getUser().getId() + ">!" + "**\n\n" +
                    "🎓 Te damos la bienvenida al **Bot Educativo**, tu asistente para organizar tus estudios.\n\n" +
                    "🌟 **Comienza ahora:**\n" +
                    "• `!sistema ayuda` - Descubre todos los comandos\n" +
                    "• `!materia crear` - Crea tu primera materia\n" +
                    "• `!sistema puntos` - Consulta tus puntos\n\n" +
                    "💪 ¡Estamos aquí para ayudarte a alcanzar tus metas académicas!";
                
                // Enviar mensaje al canal
                canalBienvenida.sendMessage(mensajeBienvenida).queue();
                
                System.out.println("👋 Nuevo usuario: " + event.getUser().getName() + " - Mensaje de bienvenida enviado");
            }
            
        } catch (Exception e) {
            System.err.println("❌ Error al enviar mensaje de bienvenida: " + e.getMessage());
        }
    }
    
    /**
     * MÉTODOS: Procesa un comando recibido
     * Demuestra encapsulamiento al abstraer la lógica de procesamiento
     * 
     * @param event Evento del mensaje
     * @param contenidoMensaje Contenido completo del mensaje
     */
    private void procesarComando(MessageReceivedEvent event, String contenidoMensaje) {
        try {
            // Extraer comando y argumentos
            String[] partes = contenidoMensaje.substring(PREFIJO_COMANDO.length()).trim().split("\\s+");
            
            if (partes.length == 0 || partes[0].isEmpty()) {
                return;
            }
            
            String nombreComando = partes[0].toLowerCase();
            String[] argumentos = new String[partes.length - 1];
            System.arraycopy(partes, 1, argumentos, 0, argumentos.length);
            
            // POLIMORFISMO: Buscar y ejecutar comando
            Comando comando = comandos.get(nombreComando);
            
            if (comando == null) {
                enviarMensaje(event, "❌ Comando no encontrado. Usa `!sistema ayuda` para ver comandos disponibles.");
                return;
            }
            
            // Verificar permisos usando POLIMORFISMO
            String usuarioId = event.getAuthor().getId();
            if (!comando.tienePermisos(usuarioId)) {
                enviarMensaje(event, "❌ No tienes permisos para ejecutar este comando.");
                return;
            }
            
            // Ejecutar comando usando POLIMORFISMO
            String canalId = event.getChannel().getId();
            String respuesta = comando.ejecutar(argumentos, canalId, usuarioId);
            
            // Enviar respuesta
            if (respuesta != null && !respuesta.trim().isEmpty()) {
                enviarMensaje(event, respuesta);
            }
            
            // Registrar uso del comando
            registrarUsoComando(nombreComando, usuarioId);
            
        } catch (Exception e) {
            System.err.println("Error procesando comando: " + e.getMessage());
            enviarMensaje(event, "❌ Error interno del bot. Intenta de nuevo más tarde.");
        }
    }
    
    /**
     * MÉTODOS: Registra todos los comandos disponibles
     * Demuestra POLIMORFISMO al tratar todos los comandos como objetos Comando
     */
    private void registrarComandos() {
        // Crear instancias de comandos unificados por funcionalidad - POLIMORFISMO en acción
        Comando[] comandosDisponibles = {
            new ComandoMaterias(),        // Comandos de gestión de materias académicas
            new ComandoTareas(),          // Comandos de gestión de tareas y asignaciones
            new ComandoSistema(),         // Comandos de sistema: ayuda, puntos, bienvenida, info
            // Aquí podrías añadir más grupos de comandos que implementen la interfaz Comando
        };
        
        // Registrar cada comando usando POLIMORFISMO
        for (Comando comando : comandosDisponibles) {
            comandos.put(comando.getNombre(), comando);
            System.out.println("✅ Grupo de comandos registrado: " + comando.getNombre());
        }
    }
    
    /**
     * MÉTODOS: Envía un mensaje al canal
     * Encapsula la lógica de envío de mensajes
     * 
     * @param event Evento del mensaje original
     * @param contenido Contenido a enviar
     */
    private void enviarMensaje(MessageReceivedEvent event, String contenido) {
        // Dividir mensajes largos si es necesario
        if (contenido.length() > 2000) {
            String[] partes = dividirMensaje(contenido, 2000);
            for (String parte : partes) {
                event.getChannel().sendMessage(parte).queue();
            }
        } else {
            event.getChannel().sendMessage(contenido).queue();
        }
    }
    
    /**
     * MÉTODOS: Registra el uso de un comando para estadísticas
     * 
     * @param nombreComando Nombre del comando usado
     * @param usuarioId ID del usuario que lo usó
     */
    private void registrarUsoComando(String nombreComando, String usuarioId) {
        try {
            // Obtener estadísticas actuales
            String clave = "stats_comando_" + nombreComando;
            Integer usos = (Integer) gestorDatos.obtener(clave);
            if (usos == null) {
                usos = 0;
            }
            
            // Incrementar y guardar
            gestorDatos.guardar(clave, usos + 1);
            
            // Registrar última actividad del usuario
            gestorDatos.guardar("ultima_actividad_" + usuarioId, System.currentTimeMillis());
            
        } catch (Exception e) {
            System.err.println("Error registrando uso de comando: " + e.getMessage());
        }
    }
    
    /**
     * FUNCIONES: Función estática para dividir mensajes largos
     * Demuestra el concepto de FUNCIONES (métodos estáticos utilitarios)
     * 
     * @param mensaje Mensaje a dividir
     * @param tamanioMaximo Tamaño máximo por parte
     * @return Array de partes del mensaje
     */
    public static String[] dividirMensaje(String mensaje, int tamanioMaximo) {
        if (mensaje.length() <= tamanioMaximo) {
            return new String[]{mensaje};
        }
        
        // Calcular número de partes necesarias
        int numPartes = (int) Math.ceil((double) mensaje.length() / tamanioMaximo);
        String[] partes = new String[numPartes];
        
        for (int i = 0; i < numPartes; i++) {
            int inicio = i * tamanioMaximo;
            int fin = Math.min(inicio + tamanioMaximo, mensaje.length());
            partes[i] = mensaje.substring(inicio, fin);
        }
        
        return partes;
    }
    
    /**
     * FUNCIONES: Función estática para formatear tiempo transcurrido
     * 
     * @param milisegundos Tiempo en milisegundos
     * @return Tiempo formateado legible
     */
    public static String formatearTiempo(long milisegundos) {
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
    
    /**
     * MÉTODOS: Obtiene estadísticas del bot
     * 
     * @return String con estadísticas formateadas
     */
    public String getEstadisticas() {
        long tiempoActual = System.currentTimeMillis();
        long tiempoActividad = tiempoActual - tiempoInicio;
        
        StringBuilder stats = new StringBuilder();
        stats.append("📊 **Estadísticas del Bot Educativo**\n\n");
        stats.append("🕒 Tiempo activo: ").append(formatearTiempo(tiempoActividad)).append("\n");
        stats.append("⚡ Comandos registrados: ").append(comandos.size()).append("\n");
        stats.append("🏛️ Servidores conectados: ").append(jda.getGuilds().size()).append("\n");
        stats.append("👥 Usuarios visibles: ").append(jda.getUsers().size()).append("\n");
        stats.append("📁 Sistema de datos: ").append(gestorDatos.getClass().getSimpleName()).append("\n");
        stats.append("🔢 Versión: ").append(VERSION);
        
        return stats.toString();
    }
    
    /**
     * MÉTODOS: Imprime estadísticas al iniciar
     */
    private void imprimirEstadisticasInicio() {
        System.out.println("📊 Estadísticas de inicio:");
        System.out.println("   • Comandos cargados: " + comandos.size());
        System.out.println("   • Sistema de datos: " + gestorDatos.getClass().getSimpleName());
        System.out.println("   • Versión: " + VERSION);
        System.out.println("🚀 ¡Bot listo para ayudar a los estudiantes!");
    }
    
    /**
     * MÉTODOS: Cierra el bot de manera segura
     */
    public void cerrar() {
        System.out.println("🔄 Cerrando Bot Educativo...");
        
        // Sincronizar datos pendientes
        if (gestorDatos != null) {
            gestorDatos.sincronizar();
        }
        
        // Cerrar conexión de Discord
        if (jda != null) {
            jda.shutdown();
        }
        
        System.out.println("✅ Bot cerrado correctamente");
    }
    
    /**
     * FUNCIÓN PRINCIPAL (main)
     * Punto de entrada de la aplicación
     * 
     * @param args Argumentos de línea de comandos
     */
    public static void main(String[] args) {
        // Verificar que se proporcione el token
        if (args.length < 1) {
            System.err.println("❌ Error: Debes proporcionar el token del bot como argumento");
            System.err.println("💡 Uso: java -jar bot-educativo.jar <TOKEN>");
            System.exit(1);
        }
        
        String token = args[0];
        
        try {
            // Crear y inicializar el bot
            BotEducativo bot = new BotEducativo(token);
            
            // Configurar shutdown hook para cerrar limpiamente
            Runtime.getRuntime().addShutdownHook(new Thread(bot::cerrar));
            
            System.out.println("🎓 Bot Educativo está funcionando. Presiona Ctrl+C para detener.");
            
        } catch (Exception e) {
            System.err.println("❌ Error fatal al iniciar el bot: " + e.getMessage());
            System.exit(1);
        }
    }
}
