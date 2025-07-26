package biblioteca;
import java.util.Scanner;

public class Biblioteca {
	
	public static void main(String[] args) {
		String usuario = "";
		String contrasenia = "";
		int op;
		Scanner sc = new Scanner (System.in);
		
		while (true) {
			System.out.print("Bienvenido a la Biblioteca!\n-Para salir del menú digite 0.\n-Para registrar un usuario digite 1.\n-Para dar de baja un usuario digite 2.\n-Para listar los usuarios digite 3.\n-Para ver los libros digite 4.\n-Registrar un libro (5)\n-Dar de baja un libro (6)\n-Digitar un préstamo (7)\n-Digitar una devolución (8)\n");
			op = sc.nextInt();
		
			if (op == 0) {
				System.exit(0);
				sc.close();
				break;
			}
			switch (op) {
			case 1:
				break;
			case 2:
				break;
			case 3:
				break;
			case 4:
				break;
			case 5:
				break;
			case 6:
				break;
			case 7:
				break;
			case 8:
				break;
			}
		}
	}
}
