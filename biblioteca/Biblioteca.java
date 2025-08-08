package biblioteca;
import java.util.*;
import java.time.LocalDate;

public class Biblioteca {
	private static List<Usuario> usuarios = new ArrayList<>();
    private static List<Libro> libros = new ArrayList<>();
    private static List<Prestamo> prestamos = new ArrayList<>();
    private static Scanner sc = new Scanner(System.in);
	
	public static void main(String[] args) {
		int op;
		Scanner sc = new Scanner (System.in);
		do {
			mostrarMenu();
			op = sc.nextInt();
			switch (op) {
			case 0:
				System.out.println("Adiós!");
				System.exit(0);
				sc.close();
				break;
			case 1:
				registrarUsuario();
				break;
			case 2:
				darBajaUsuario();
				break;
			case 3:
				listarUsuarios();
				break;
			case 4:
				listarLibros();
				break;
			case 5:
				registrarLibro();
				break;
			case 6:
				darBajaLibro();
				break;
			case 7:
				registrarPrestamo();
				break;
			case 8:
				registrarDevolucion();
				break;
			default:
				System.out.println("OPCIÓN NO VÁLIDA");
			}
		} while (op != 0);
		sc.close();
	}
	public static void mostrarMenu() {
		System.out.println("\nMENÚ");
		System.out.println("(0) Salir del menú");
		System.out.println("(1) Registrar usuario");
		System.out.println("(2) Dar de baja usuario");
		System.out.println("(3) Listar usuarios");
		System.out.println("(4) Listar libros disponibles");
		System.out.println("(5) Registrar un libro");
		System.out.println("(6) Dar de baja un libro");
		System.out.println("(7) Registrar un préstamo");
		System.out.println("(8) Registrar una devolución");
		System.out.println("Seleccione una opción:\n");
	}
	public static void registrarUsuario() {
		System.out.println("registrando usuario :D");
	}
	public static void darBajaUsuario() {
		System.out.println("dando de baja usuario :D");
	}
	public static void listarUsuarios() {
		System.out.println("listando usuarios :D");
	}
	public static void listarLibros() {
		System.out.println("listando libros :D");
	}
	public static void registrarLibro() {
		System.out.println("registrando libros :D");
	}
	public static void darBajaLibro() {
		System.out.println("dando de baja libro :D");
	}
	public static void registrarPrestamo() {
		System.out.println("registrando prestamo :D");
	}
	public static void registrarDevolucion() {
		System.out.println("registrando devolucion :D");
	}
}
