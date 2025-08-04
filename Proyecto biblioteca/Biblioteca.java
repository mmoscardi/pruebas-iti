package biblioteca;

import java.util.Scanner;

public class Biblioteca {
    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {

            String[] usuarios = new String[10];
            String[] libros = new String[10];
            String[] prestamos = new String[10];

            int totalUsuarios = 0;
            int totalLibros = 0;
            int totalPrestamos = 0;

            while (true) {
                System.out.println("--- MENÚ ---");
                System.out.println("1. Agregar usuario");
                System.out.println("2. Agregar libro");
                System.out.println("3. Ver usuarios");
                System.out.println("4. Ver libros");
                System.out.println("5. Prestar libro");
                System.out.println("6. Ver préstamos");
                System.out.println("7. Salir");
                System.out.print("Opción: ");
                int opcion = Integer.parseInt(scanner.nextLine());

                if (opcion == 1) {
                    System.out.print("Nombre del usuario: ");
                    usuarios[totalUsuarios] = scanner.nextLine();
                    totalUsuarios++;
                    System.out.println("Usuario agregado.");
                } else if (opcion == 2) {
                    System.out.print("Nombre del libro: ");
                    libros[totalLibros] = scanner.nextLine();
                    totalLibros++;
                    System.out.println("Libro agregado.");
                } else if (opcion == 3) {
                    System.out.println("Usuarios:");
                    for (int i = 0; i < totalUsuarios; i++) {
                        System.out.println((i + 1) + ". " + usuarios[i]);
                    }
                } else if (opcion == 4) {
                    System.out.println("Libros:");
                    for (int i = 0; i < totalLibros; i++) {
                        System.out.println((i + 1) + ". " + libros[i]);
                    }
                } else if (opcion == 5) {
                    System.out.print("Nombre del usuario: ");
                    String usuario = scanner.nextLine();
                    System.out.print("Nombre del libro: ");
                    String libro = scanner.nextLine();
                    prestamos[totalPrestamos] = usuario + " → " + libro;
                    totalPrestamos++;
                    System.out.println("Préstamo registrado.");
                } else if (opcion == 6) {
                    System.out.println("Préstamos:");
                    for (int i = 0; i < totalPrestamos; i++) {
                        System.out.println((i + 1) + ". " + prestamos[i]);
                    }
                } else if (opcion == 7) {
                    System.out.println("Saliendo...");
                    break;
                } else {
                    System.out.println("Opción inválida.");
                }
            }
        }
    }
}
