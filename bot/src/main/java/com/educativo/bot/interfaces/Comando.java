package com.educativo.bot.interfaces;

/**
 * CONCEPTO: INTERFACES
 * 
 * Una interfaz en Java es un contrato que define qué métodos debe implementar una clase
 * sin especificar cómo deben implementarse. Esto permite el polimorfismo y la 
 * flexibilidad en el diseño.
 * 
 * Esta interfaz define el contrato para todos los comandos del bot.
 * Cualquier clase que implemente esta interfaz debe proporcionar una implementación
 * de todos los métodos declarados aquí.
 */
public interface Comando {
    
    /**
     * Ejecuta el comando con los parámetros dados
     * 
     * @param args Argumentos del comando (palabras después del comando)
     * @param canalId ID del canal donde se ejecutó el comando
     * @param usuarioId ID del usuario que ejecutó el comando
     * @return Mensaje de respuesta para enviar al canal
     */
    String ejecutar(String[] args, String canalId, String usuarioId);
    
    /**
     * Obtiene el nombre del comando (por ejemplo: "ayuda", "tarea", "puntos")
     * 
     * @return Nombre del comando
     */
    String getNombre();
    
    /**
     * Obtiene la descripción del comando para mostrar en la ayuda
     * 
     * @return Descripción del comando
     */
    String getDescripcion();
    
    /**
     * Obtiene el uso del comando (sintaxis)
     * 
     * @return Ejemplo de uso del comando
     */
    String getUso();
    
    /**
     * Verifica si el usuario tiene permisos para ejecutar este comando
     * 
     * @param usuarioId ID del usuario
     * @return true si tiene permisos, false si no
     */
    boolean tienePermisos(String usuarioId);
}
