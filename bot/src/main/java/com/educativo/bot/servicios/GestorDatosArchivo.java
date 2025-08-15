package com.educativo.bot.servicios;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.educativo.bot.interfaces.GestorDatos;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

/**
 * CONCEPTO: INTERFACES (Implementación)
 * 
 * Esta clase IMPLEMENTA la interfaz GestorDatos, lo que significa que:
 * 1. Debe proporcionar implementación concreta de todos los métodos de la interfaz
 * 2. Puede ser tratada polimórficamente como GestorDatos
 * 3. Demuestra el concepto de contrato definido por la interfaz
 * 
 * Esta implementación específica guarda datos en archivos JSON.
 * Podrías tener otras implementaciones que guarden en base de datos, memoria, etc.
 */
public class GestorDatosArchivo implements GestorDatos {
    
    // ENCAPSULAMIENTO: Atributos privados para controlar el estado interno
    private final String rutaArchivo;                    // Ruta donde se guardan los datos
    private final Map<String, Object> cacheDatos;       // Cache en memoria para mejorar rendimiento
    private final Gson gson;                             // Para serialización JSON
    private boolean datosModificados;                    // Flag para saber si hay cambios pendientes
    
    /**
     * Constructor que configura el gestor de datos
     * 
     * @param rutaArchivo Ruta del archivo donde se guardarán los datos
     */
    public GestorDatosArchivo(String rutaArchivo) {
        this.rutaArchivo = rutaArchivo;
        this.cacheDatos = new ConcurrentHashMap<>(); // Thread-safe para uso concurrente
        this.gson = new Gson();
        this.datosModificados = false;
        
        // Cargar datos existentes al inicializar
        cargarDatos();
    }
    
    /**
     * IMPLEMENTACIÓN del método de la interfaz
     * 
     * Guarda un dato en el sistema de almacenamiento.
     * Esta implementación específica lo guarda en memoria y marca para persistencia.
     */
    @Override
    public boolean guardar(String clave, Object valor) {
        // Validación de entrada
        if (clave == null || clave.trim().isEmpty()) {
            return false;
        }
        
        try {
            // Guardar en cache
            cacheDatos.put(clave, valor);
            datosModificados = true;
            
            // En una implementación real, podrías configurar auto-guardado
            // Por ahora, guardamos inmediatamente
            return persistirDatos();
            
        } catch (Exception e) {
            System.err.println("Error al guardar dato con clave '" + clave + "': " + e.getMessage());
            return false;
        }
    }
    
    /**
     * IMPLEMENTACIÓN del método de la interfaz
     * 
     * Obtiene un dato del sistema de almacenamiento.
     * Esta implementación busca primero en cache, luego en archivo.
     */
    @Override
    public Object obtener(String clave) {
        if (clave == null || clave.trim().isEmpty()) {
            return null;
        }
        
        // Buscar en cache primero (más rápido)
        return cacheDatos.get(clave);
    }
    
    /**
     * IMPLEMENTACIÓN del método de la interfaz
     * 
     * Elimina un dato del sistema de almacenamiento.
     */
    @Override
    public boolean eliminar(String clave) {
        if (clave == null || clave.trim().isEmpty()) {
            return false;
        }
        
        try {
            // Verificar si existe
            if (!cacheDatos.containsKey(clave)) {
                return false; // No existía
            }
            
            // Eliminar del cache
            cacheDatos.remove(clave);
            datosModificados = true;
            
            // Persistir cambios
            return persistirDatos();
            
        } catch (Exception e) {
            System.err.println("Error al eliminar dato con clave '" + clave + "': " + e.getMessage());
            return false;
        }
    }
    
    /**
     * IMPLEMENTACIÓN del método de la interfaz
     * 
     * Verifica si existe un dato con la clave especificada.
     */
    @Override
    public boolean existe(String clave) {
        if (clave == null || clave.trim().isEmpty()) {
            return false;
        }
        
        return cacheDatos.containsKey(clave);
    }
    
    /**
     * IMPLEMENTACIÓN del método de la interfaz
     * 
     * Sincroniza todos los datos pendientes con el almacenamiento persistente.
     */
    @Override
    public void sincronizar() {
        if (datosModificados) {
            persistirDatos();
        }
    }
    
    // MÉTODOS PRIVADOS - ENCAPSULAMIENTO de la lógica interna
    
    /**
     * Carga datos desde el archivo al cache en memoria
     * Método privado que encapsula la lógica de carga
     */
    private void cargarDatos() {
        File archivo = new File(rutaArchivo);
        
        // Si el archivo no existe, empezar con cache vacío
        if (!archivo.exists()) {
            return;
        }
        
        try (FileReader reader = new FileReader(archivo)) {
            // Usar Gson para deserializar el JSON
            Type tipoMap = new TypeToken<Map<String, Object>>(){}.getType();
            Map<String, Object> datosArchivo = gson.fromJson(reader, tipoMap);
            
            if (datosArchivo != null) {
                cacheDatos.putAll(datosArchivo);
            }
            
            datosModificados = false;
            
        } catch (IOException e) {
            System.err.println("Error al cargar datos desde " + rutaArchivo + ": " + e.getMessage());
        }
    }
    
    /**
     * Persiste los datos del cache al archivo
     * Método privado que encapsula la lógica de persistencia
     * 
     * @return true si se guardó correctamente, false si hubo error
     */
    private boolean persistirDatos() {
        try {
            // Crear directorio padre si no existe
            File archivo = new File(rutaArchivo);
            File directorioPadre = archivo.getParentFile();
            if (directorioPadre != null && !directorioPadre.exists()) {
                directorioPadre.mkdirs();
            }
            
            // Escribir datos al archivo usando Gson
            try (FileWriter writer = new FileWriter(archivo)) {
                gson.toJson(cacheDatos, writer);
                datosModificados = false;
                return true;
            }
            
        } catch (IOException e) {
            System.err.println("Error al persistir datos en " + rutaArchivo + ": " + e.getMessage());
            return false;
        }
    }
    
    // MÉTODOS ADICIONALES - Funcionalidad específica de esta implementación
    
    /**
     * Obtiene el número total de entradas almacenadas
     * Método específico de esta implementación (no está en la interfaz)
     * 
     * @return Número de entradas
     */
    public int getTamanio() {
        return cacheDatos.size();
    }
    
    /**
     * Obtiene todas las claves almacenadas
     * Método específico de esta implementación
     * 
     * @return Array con todas las claves
     */
    public String[] getClaves() {
        return cacheDatos.keySet().toArray(String[]::new);
    }
    
    /**
     * Limpia todos los datos (cache y archivo)
     * Método específico de esta implementación
     * 
     * @return true si se limpió correctamente
     */
    public boolean limpiarTodo() {
        try {
            cacheDatos.clear();
            datosModificados = true;
            
            // Eliminar archivo físico
            File archivo = new File(rutaArchivo);
            if (archivo.exists()) {
                archivo.delete();
            }
            
            datosModificados = false;
            return true;
            
        } catch (Exception e) {
            System.err.println("Error al limpiar datos: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Obtiene información de estado del gestor
     * Demuestra encapsulamiento al proporcionar vista controlada del estado interno
     * 
     * @return String con información de estado
     */
    public String getEstado() {
        return String.format(
            "GestorDatosArchivo{archivo='%s', entradas=%d, modificado=%s}",
            rutaArchivo, cacheDatos.size(), datosModificados
        );
    }
    
    /**
     * Sobrescritura de toString heredado de Object
     * Demuestra herencia implícita (todas las clases heredan de Object)
     */
    @Override
    public String toString() {
        return getEstado();
    }
}
