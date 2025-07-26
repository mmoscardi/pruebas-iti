package biblioteca;
import java.util.Scanner;
import java.util.HashMap;
import java.util.Map;

public class Biblioteca {
	private static Map<String, String> usuarios = new HashMap<>();
	
	public static void main(String[] args) {
		Map<String, String> usuarios = new HashMap<>();
		String usuario, contrasenia;
		
		Scanner sc = new Scanner (System.in);
		
		usuarios.put(usuario, contrasenia);
		sc.close();
	}
}
