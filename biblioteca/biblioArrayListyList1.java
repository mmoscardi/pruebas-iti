package biblioteca;

import java.util.ArrayList;
import java.util.List;
import java.time.LocalDate;
import java.util.Scanner;

public class biblioArrayListyList1 {
    private static List<Usuario> usuarios = new ArrayList<>();
    private static List<Libro> libros = new ArrayList<>();
    private static List<Prestamo> prestamos = new ArrayList<>();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        int opcion = -1;
        do {
            System.out.println("\n--- Menú Biblioteca ---");
            System.out.println("1. Registrar un usuario");
            System.out.println("2. Dar de baja un usuario");
            System.out.println("3. Listar usuarios");
            System.out.println("4. Registrar un libro");
            System.out.println("5. Dar de baja un libro");
            System.out.println("6. Listar libros disponibles");
            System.out.println("7. Registrar un préstamo");
            System.out.println("8. Registrar una devolución");
            System.out.println("0. Salir");
            System.out.print("Seleccione una opción: ");
            try {
                opcion = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Debe ingresar un número válido.");
                opcion = -1; // Para que no salga del bucle
                continue;
            }

            switch (opcion) {
                case 1:
                    registrarUsuario();
                    break;
                case 2:
                    darDeBajaUsuario();
                    break;
                case 3:
                    listarUsuarios();
                    break;
                case 4:
                    registrarLibro();
                    break;
                case 5:
                    darDeBajaLibro();
                    break;
                case 6:
                    listarLibrosDisponibles();
                    break;
                case 7:
                    registrarPrestamo();
                    break;
                case 8:
                    registrarDevolucion();
                    break;
                case 0:
                    System.out.println("¡Hasta luego!");
                    break;
                default:
                    System.out.println("Opción inválida.");
            }
        } while (opcion != 0);
    }

    private static void registrarPrestamo() {
        System.out.print("Ingrese número de documento del usuario: ");
        String doc = scanner.nextLine();
        Usuario usuario = usuarios.stream()
            .filter(u -> u.getNumeroDocumento().equals(doc))
            .findFirst()
            .orElse(null);

        if (usuario == null) {
            System.out.println("Usuario no encontrado.");
            return;
        }

        long prestamosActivos = prestamos.stream()
            .filter(p -> p.getUsuario().getNumeroDocumento().equals(doc))
            .count();
        if (prestamosActivos >= 3) {
            System.out.println("El usuario ya tiene 3 préstamos activos.");
            return;
        }

        System.out.print("Ingrese ISBN del libro: ");
        String isbn = scanner.nextLine();
        Libro libro = libros.stream()
            .filter(l -> l.getIsbn().equals(isbn))
            .findFirst()
            .orElse(null);

        if (libro == null) {
            System.out.println("Libro no encontrado.");
            return;
        }

        boolean yaTieneEseLibro = prestamos.stream()
            .anyMatch(p -> p.getUsuario().getNumeroDocumento().equals(doc) && p.getLibro().getIsbn().equals(isbn));
        if (yaTieneEseLibro) {
            System.out.println("El usuario ya tiene un préstamo activo de este libro.");
            return;
        }

        if (libro.getCantidadDisponible() <= 0) {
            System.out.println("No hay ejemplares disponibles de este libro.");
            return;
        }

        int dias = 0;
        while (true) {
            System.out.print("Ingrese cantidad de días del préstamo: ");
            String input = scanner.nextLine();
            try {
                dias = Integer.parseInt(input);
                if (dias < 1) {
                    System.out.println("La cantidad de días debe ser mayor a 0.");
                    continue;
                }
                break;
            } catch (NumberFormatException e) {
                System.out.println("Debe ingresar un número válido.");
            }
        }

        Prestamo prestamo = new Prestamo(LocalDate.now(), dias, libro, usuario);
        prestamos.add(prestamo);
        libro.setCantidadDisponible(libro.getCantidadDisponible() - 1);

        System.out.println("Préstamo registrado exitosamente.");
    }

    private static void registrarUsuario() {
        System.out.print("Ingrese número de documento: ");
        String numeroDocumento = scanner.nextLine();
        System.out.print("Ingrese nombre: ");
        String nombre = scanner.nextLine();
        System.out.print("Ingrese teléfono: ");
        String telefono = scanner.nextLine();
        System.out.print("Ingrese apellido: ");
        String apellido = scanner.nextLine();
        System.out.print("Ingrese email: ");
        String email = scanner.nextLine();
        System.out.print("Ingrese dirección: ");
        String direccion = scanner.nextLine();

        Usuario usuario = new Usuario(numeroDocumento, nombre, telefono, apellido, email, direccion);
        usuarios.add(usuario);

        System.out.println("Usuario registrado exitosamente.");
    }

    private static void registrarLibro() {
        System.out.print("Ingrese ISBN: ");
        String isbn = scanner.nextLine();
        System.out.print("Ingrese título: ");
        String titulo = scanner.nextLine();
        System.out.print("Ingrese autor: ");
        String autor = scanner.nextLine();

        int cantidad = 0;
        while (true) {
            System.out.print("Ingrese cantidad de ejemplares: ");
            String input = scanner.nextLine();
            try {
                cantidad = Integer.parseInt(input);
                if (cantidad < 1) {
                    System.out.println("La cantidad debe ser mayor a 0.");
                    continue;
                }
                break;
            } catch (NumberFormatException e) {
                System.out.println("Debe ingresar un número válido.");
            }
        }

        Libro libro = new Libro(isbn, titulo, autor, cantidad);
        libros.add(libro);

        System.out.println("Libro registrado exitosamente.");
    }

    private static void darDeBajaLibro() {
        System.out.print("Ingrese ISBN del libro a dar de baja: ");
        String isbn = scanner.nextLine();
        Libro libro = libros.stream()
            .filter(l -> l.getIsbn().equals(isbn))
            .findFirst()
            .orElse(null);

        if (libro == null) {
            System.out.println("Libro no encontrado.");
            return;
        }

        libros.remove(libro);
        System.out.println("Libro dado de baja exitosamente.");
    }

    private static void darDeBajaUsuario() {
        System.out.print("Ingrese número de documento del usuario a dar de baja: ");
        String doc = scanner.nextLine();
        Usuario usuario = usuarios.stream()
            .filter(u -> u.getNumeroDocumento().equals(doc))
            .findFirst()
            .orElse(null);

        if (usuario == null) {
            System.out.println("Usuario no encontrado.");
            return;
        }

        // Verificar si tiene préstamos activos
        boolean tienePrestamos = prestamos.stream()
            .anyMatch(p -> p.getUsuario().getNumeroDocumento().equals(doc));
        if (tienePrestamos) {
            System.out.println("No se puede dar de baja: el usuario tiene préstamos activos.");
            return;
        }

        usuarios.remove(usuario);
        System.out.println("Usuario dado de baja exitosamente.");
    }

    private static void listarUsuarios() {
        if (usuarios.isEmpty()) {
            System.out.println("No hay usuarios registrados.");
            return;
        }
        System.out.println("--- Lista de usuarios ---");
        for (Usuario u : usuarios) {
            System.out.println("Documento: " + u.getNumeroDocumento() +
                               ", Nombre: " + u.getNombre() +
                               ", Apellido: " + u.getApellido() +
                               ", Email: " + u.getEmail() +
                               ", Teléfono: " + u.getTelefono() +
                               ", Dirección: " + u.getDomicilio());
        }
    }

    private static void listarLibrosDisponibles() {
        if (libros.isEmpty()) {
            System.out.println("No hay libros registrados.");
            return;
        }
        System.out.println("--- Libros disponibles ---");
        boolean hayDisponibles = false;
        for (Libro l : libros) {
            if (l.getCantidadDisponible() > 0) {
                System.out.println("ISBN: " + l.getIsbn() +
                    ", Título: " + l.getTitulo() +
                    ", Autor: " + l.getAutor() +
                    ", Ejemplares disponibles: " + l.getCantidadDisponible());
                hayDisponibles = true;
            }
        }
        if (!hayDisponibles) {
            System.out.println("No hay ejemplares disponibles de ningún libro.");
        }
    }

    private static void registrarDevolucion() {
        System.out.print("Ingrese número de documento del usuario: ");
        String doc = scanner.nextLine();
        Usuario usuario = usuarios.stream()
            .filter(u -> u.getNumeroDocumento().equals(doc))
            .findFirst()
            .orElse(null);

        if (usuario == null) {
            System.out.println("Usuario no encontrado.");
            return;
        }

        System.out.print("Ingrese ISBN del libro a devolver: ");
        String isbn = scanner.nextLine();
        Prestamo prestamo = prestamos.stream()
            .filter(p -> p.getUsuario().getNumeroDocumento().equals(doc) && p.getLibro().getIsbn().equals(isbn))
            .findFirst()
            .orElse(null);

        if (prestamo == null) {
            System.out.println("No se encontró un préstamo activo para ese usuario y libro.");
            return;
        }

        prestamos.remove(prestamo);
        Libro libro = prestamo.getLibro();
        libro.setCantidadDisponible(libro.getCantidadDisponible() + 1);

        System.out.println("Devolución registrada exitosamente.");
    }
}

class Libro {
    private String isbn;
    private String titulo;
    private String autor;
    private int cantidadTotal;
    private int cantidadDisponible;

    public Libro(String isbn, String titulo, String autor, int cantidad) {
        this.isbn = isbn;
        this.titulo = titulo;
        this.autor = autor;
        this.cantidadTotal = cantidad;
        this.cantidadDisponible = cantidad;
    }

    public String getIsbn() {
        return isbn;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getAutor() {
        return autor;
    }

    public int getCantidadTotal() {
        return cantidadTotal;
    }

    public int getCantidadDisponible() {
        return cantidadDisponible;
    }

    public void setCantidadDisponible(int cantidadDisponible) {
        this.cantidadDisponible = cantidadDisponible;
    }
}

class Usuario {
    private String numeroDocumento;
    private String nombre;
    private String telefono;
    private String apellido;
    private String email;
    private String domicilio;

    public Usuario(String numeroDocumento, String nombre, String telefono, String apellido, String email, String domicilio) {
        this.numeroDocumento = numeroDocumento;
        this.nombre = nombre;
        this.telefono = telefono;
        this.apellido = apellido;
        this.email = email;
        this.domicilio = domicilio;
    }

    public String getNumeroDocumento() {
        return numeroDocumento;
    }

    public String getNombre() {
        return nombre;
    }

    public String getTelefono() {
        return telefono;
    }

    public String getApellido() {
        return apellido;
    }

    public String getEmail() {
        return email;
    }

    public String getDomicilio() {
        return domicilio;
    }
}

class Prestamo {
    private LocalDate fechaPrestamo;
    private int diasPrestamo;
    private Libro libro;
    private Usuario usuario;

    public Prestamo(LocalDate fechaPrestamo, int diasPrestamo, Libro libro, Usuario usuario) {
        this.fechaPrestamo = fechaPrestamo;
        this.diasPrestamo = diasPrestamo;
        this.libro = libro;
        this.usuario = usuario;
    }

    public LocalDate getFechaPrestamo() {
        return fechaPrestamo;
    }

    public int getDiasPrestamo() {
        return diasPrestamo;
    }

    public Libro getLibro() {
        return libro;
    }

    public Usuario getUsuario() {
        return usuario;
    }
}
