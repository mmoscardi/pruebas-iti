package Calculadora;
import java.util.Scanner;

// Samara González 2°MH

// Crear una calculadora, que tenga un menu impreso y que el usuario tenga que ingresar 
// Menu: ("1" para sumar, "2" restar, "3" multiplicar, "4" dividir, y "0" para salir.)
// y luego pida 2 numeros y devuelva el resultado.

public class calc {
	private static double num1, num2, resultado; //double para + precision
								//varias funciones comparten las variables num1, num2 y resultado
	public static void main(String[] args) {
		int op;  //opcion
		Scanner sc = new Scanner (System.in);
		
		while (true) { //bucle while para que siga apareciendo el menu luego de una operacion
			System.out.print("MENÚ:\nIngrese '1' para sumar\nIngrese '2' para restar\nIngrese '3' para multiplicar\nIngrese '4' para dividir\nO ingrese '0' para salir.\nOpción: ");
			op = sc.nextInt();
			
			if (op == 0) {
				System.exit(0);
				sc.close(); //cerrar scanner evita errores
				break; //rompe el while
			}
			if (op < 1 || op > 4) {
				System.out.println("OPCIÓN NO VÁLIDA, INTENTE DE NUEVO.");
				continue; //sigue el while, vuelve al menu
			}
			System.out.println("Ingresa un número: ");
			num1 = sc.nextDouble();
		
			System.out.println("Ingresa otro número: ");
			num2 = sc.nextDouble();
			switch (op) {
			case 1:
				suma(num1, num2);
				break;
			case 2:
				resta(num1, num2);
				break;
			case 3:
				mul(num1, num2);
				break;
			case 4:
				if (num2 != 0) {
					div(num1, num2);
				} else {
					System.out.println("NO SE PUEDE DIVIDIR POR 0."); //evitar posible error
				}
				break;
			}
		}
		sc.close();
	}
	public static void suma(double num1, double num2) {
		resultado = num1 + num2;
		System.out.println("El resultado es = "+resultado);
	}
	public static void resta(double num1, double num2) {
		resultado = num1 - num2;
		System.out.println("El resultado es = "+resultado);
	}
	public static void mul(double num1, double num2) {
		resultado = num1 * num2;
		System.out.println("El resultado es = "+resultado);
	}
	public static void div(double num1, double num2) {
		resultado = num1 / num2;
		System.out.println("El resultado es = "+resultado);
	}
}
