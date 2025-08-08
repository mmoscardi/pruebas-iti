package com.educativo.bot.interfaces;

/**
 * CONCEPTO: INTERFACES (continuación)
 * 
 * Esta interfaz define el comportamiento para gestionar datos del bot.
 * Demuestra cómo las interfaces permiten definir contratos para diferentes
 * tipos de almacenamiento (archivo, base de datos, memoria, etc.)
 */
public interface GestorDatos {
    
    /**
     * Guarda datos en el sistema de almacenamiento
     * 
     * @param clave Identificador único del dato
     * @param valor Dato a guardar
     * @return true si se guardó correctamente, false si hubo error
     */
    boolean guardar(String clave, Object valor);
    
    /**
     * Obtiene un dato del sistema de almacenamiento
     * 
     * @param clave Identificador único del dato
     * @return El dato almacenado o null si no existe
     */
    Object obtener(String clave);
    
    /**
     * Elimina un dato del sistema de almacenamiento
     * 
     * @param clave Identificador único del dato
     * @return true si se eliminó correctamente, false si no existía o hubo error
     */
    boolean eliminar(String clave);
    
    /**
     * Verifica si existe un dato con la clave especificada
     * 
     * @param clave Identificador único del dato
     * @return true si existe, false si no
     */
    boolean existe(String clave);
    
    /**
     * Guarda todos los datos pendientes (flush)
     * Útil para sistemas que guardan en lotes
     */
    void sincronizar();
}
