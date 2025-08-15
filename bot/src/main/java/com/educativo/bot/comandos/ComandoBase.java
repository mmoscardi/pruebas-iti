package com.educativo.bot.comandos;

import com.educativo.bot.interfaces.Comando;

/**
 * CONCEPTO: HERENCIA
 * 
 * La herencia es un mecanismo que permite crear nuevas clases basadas en clases existentes.
 * La clase hija (subclase) hereda atributos y métodos de la clase padre (superclase).
 * 
 * Esta clase abstracta es la CLASE PADRE de todos los comandos del bot.
 * Define comportamiento común que todas las clases hijas van a compartir.
 * 
 * Al ser abstracta, no se puede instanciar directamente, pero proporciona
 * una base común para todos los comandos específicos.
 */
public abstract class ComandoBase implements Comando {
    
    // ENCAPSULAMIENTO: Atributos protegidos - accesibles por clases hijas
    protected String nombre;           // Nombre del comando
    protected String descripcion;      // Descripción del comando
    protected String uso;              // Sintaxis de uso
    protected boolean requierePermisos; // Si necesita permisos especiales
    
    /**
     * Constructor de la clase base
     * Será llamado por los constructores de las clases hijas
     * 
     * @param nombre Nombre del comando
     * @param descripcion Descripción del comando
     * @param uso Sintaxis de uso
     * @param requierePermisos Si requiere permisos especiales
     */
    public ComandoBase(String nombre, String descripcion, String uso, boolean requierePermisos) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.uso = uso;
        this.requierePermisos = requierePermisos;
    }
    
    // IMPLEMENTACIÓN de métodos de la interfaz Comando
    // Estos métodos serán HEREDADOS por todas las clases hijas
    
    /**
     * Obtiene el nombre del comando
     * Método concreto que heredarán todas las clases hijas
     */
    @Override
    public String getNombre() {
        return nombre;
    }
    
    /**
     * Obtiene la descripción del comando
     * Método concreto que heredarán todas las clases hijas
     */
    @Override
    public String getDescripcion() {
        return descripcion;
    }
    
    /**
     * Obtiene el uso del comando
     * Método concreto que heredarán todas las clases hijas
     */
    @Override
    public String getUso() {
        return uso;
    }
    
    /**
     * Verifica permisos básicos
     * Las clases hijas pueden SOBRESCRIBIR este método si necesitan lógica específica
     * Esto demuestra POLIMORFISMO por sobrescritura
     */
    @Override
    public boolean tienePermisos(String usuarioId) {
        // Implementación básica: si no requiere permisos, siempre permite
        if (!requierePermisos) {
            return true;
        }
        
        // Aquí podrías verificar contra una lista de moderadores
        // Por simplicidad, asumimos que ciertos IDs son moderadores
        return esModerador(usuarioId);
    }
    
    /**
     * Método auxiliar para verificar si un usuario es moderador
     * Método protegido - accesible por clases hijas pero no por el exterior
     * 
     * @param usuarioId ID del usuario
     * @return true si es moderador
     */
    protected boolean esModerador(String usuarioId) {
        // Lista simple de moderadores - en una aplicación real esto vendría de una base de datos
        String[] moderadores = {"123456789", "987654321"}; // IDs de ejemplo
        
        for (String moderadorId : moderadores) {
            if (moderadorId.equals(usuarioId)) {
                return true;
            }
        }
        return false;
    }
    
    /**
     * Método auxiliar para validar argumentos
     * Método protegido que las clases hijas pueden usar
     * 
     * @param args Argumentos a validar
     * @param minArgs Mínimo número de argumentos requeridos
     * @return true si los argumentos son válidos
     */
    protected boolean validarArgumentos(String[] args, int minArgs) {
        return args != null && args.length >= minArgs;
    }
    
    /**
     * Método auxiliar para formatear respuestas de error
     * Método protegido común para todas las clases hijas
     * 
     * @param mensaje Mensaje de error
     * @return Mensaje formateado
     */
    protected String formatearError(String mensaje) {
        return "❌ **Error**: " + mensaje + "\n💡 **Uso**: `" + uso + "`";
    }
    
    /**
     * Método auxiliar para formatear respuestas exitosas
     * 
     * @param mensaje Mensaje de éxito
     * @return Mensaje formateado
     */
    protected String formatearExito(String mensaje) {
        return "✅ " + mensaje;
    }
    
    /**
     * Método auxiliar para formatear información
     * 
     * @param mensaje Mensaje informativo
     * @return Mensaje formateado
     */
    protected String formatearInfo(String mensaje) {
        return "ℹ️ " + mensaje;
    }
    
    // MÉTODO ABSTRACTO - Debe ser implementado por todas las clases hijas
    // Esto OBLIGA a cada clase hija a proporcionar su propia implementación
    /**
     * Ejecuta el comando - método abstracto
     * Cada clase hija DEBE implementar este método con su lógica específica
     * 
     * @param args Argumentos del comando
     * @param canalId ID del canal
     * @param usuarioId ID del usuario
     * @return Mensaje de respuesta
     */
    @Override
    public abstract String ejecutar(String[] args, String canalId, String usuarioId);
    
    /**
     * Método toString sobrescrito de Object (herencia implícita)
     * Todas las clases en Java heredan de Object automáticamente
     */
    @Override
    public String toString() {
        return String.format("Comando{nombre='%s', descripcion='%s'}", nombre, descripcion);
    }
    
    /**
     * MÉTODO UTILITARIO COMPARTIDO: Parsea argumentos que están entre comillas
     * 
     * Este método permite a los comandos manejar correctamente argumentos que contienen espacios
     * cuando están encerrados entre comillas dobles. Es especialmente útil para títulos,
     * descripciones y nombres que pueden contener múltiples palabras.
     * 
     * Ejemplo: "Mi título" "Una descripción larga" parametro_sin_espacios
     * Resultado: ["Mi título", "Una descripción larga", "parametro_sin_espacios"]
     * 
     * @param texto El texto completo a parsear
     * @return Array con los argumentos extraídos correctamente
     */
    protected String[] parsearArgumentosEntreComillas(String texto) {
        java.util.List<String> argumentos = new java.util.ArrayList<>();
        boolean dentroDeComillas = false;
        StringBuilder argActual = new StringBuilder();
        
        for (int i = 0; i < texto.length(); i++) {
            char c = texto.charAt(i);
            
            if (c == '"') {
                if (dentroDeComillas) {
                    // Fin de argumento entre comillas
                    argumentos.add(argActual.toString());
                    argActual = new StringBuilder();
                    dentroDeComillas = false;
                } else {
                    // Inicio de argumento entre comillas
                    dentroDeComillas = true;
                }
            } else if (c == ' ' && !dentroDeComillas) {
                // Espacio fuera de comillas - separador
                if (argActual.length() > 0) {
                    argumentos.add(argActual.toString());
                    argActual = new StringBuilder();
                }
            } else {
                // Carácter normal
                argActual.append(c);
            }
        }
        
        // Agregar último argumento si no está vacío
        if (argActual.length() > 0) {
            argumentos.add(argActual.toString());
        }
        
        return argumentos.toArray(new String[0]);
    }
}
