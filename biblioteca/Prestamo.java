package biblioteca;
import java.time.LocalDate; //clase que obtiene fecha actual del reloj del sistema

public class Prestamo {
	private LocalDate fechaPrestamo;
	private int diasPrestamo;
	private Libro libro; //referencia al libro (direccion de memoria)
	private Usuario usuario; //referencia al usuario (direccion de memoria)
	
	public Prestamo (LocalDate fechaPrestamo, int diasPrestamo, Libro libro, Usuario usuario) {
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
