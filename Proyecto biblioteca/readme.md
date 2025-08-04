# 📚 Sistema de Gestión de Biblioteca (Consola)

## 🎯 Objetivo

Desarrollar una aplicación de consola para gestionar una biblioteca.  
El sistema debe permitir administrar usuarios, libros y préstamos, aplicando ciertas reglas y restricciones definidas.

---

## 🚀 Instrucciones de Entrega

1. Crear una nueva rama a partir de `main` con el siguiente formato:
{nombreEstudiante}-branch-biblioteca

Ejemplo: `juanperez-branch-biblioteca`

2. Realizar *push* de los cambios a esa rama.

---

## 🗂️ Estructura del Sistema

### 📘 Libros

Cada libro debe almacenar la siguiente información:
- Nombre
- Autor
- ISBN
- Cantidad total
- Cantidad disponible

### 👤 Usuarios

Los usuarios deben contener:
- Número de documento
- Nombre
- Apellido
- Email
- Teléfono
- Domicilio

### 🔁 Préstamos

Cada préstamo debe guardar:
- Fecha del préstamo
- Cantidad de días
- Referencia al libro
- Referencia al usuario

---

## 📋 Funcionalidades del Menú

El sistema debe ofrecer un menú interactivo con las siguientes opciones:

1. Registrar un usuario  
2. Dar de baja un usuario  
3. Listar usuarios  
4. Registrar un libro  
5. Dar de baja un libro  
6. Listar libros disponibles  
7. Registrar un préstamo  
8. Registrar una devolución  

---

## ⚠️ Restricciones

- 📌 Un usuario **no puede solicitar más de 3 libros a la vez**.
- 📌 Un usuario **no puede solicitar más de un libro con el mismo ISBN**.

---

## 🛠️ Requisitos Técnicos

- Aplicación desarrollada en consola
- Código limpio, organizado y documentado
- Uso de estructuras de datos adecuadas para manejar colecciones (listas, sets, etc.)

---

## ✅ Recomendaciones

- Validar correctamente los datos ingresados por el usuario.
- Mantener separados los modelos de datos (clases) de la lógica del menú.
- Incluir comentarios para facilitar la comprensión del código.
- Controlar todos los posibles errores (por ejemplo, préstamos a usuarios inexistentes).

---