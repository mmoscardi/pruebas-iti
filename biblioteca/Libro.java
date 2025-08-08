package biblioteca;

public class Libro { //clase publica para usarla desde Biblioteca
	private String nombre;
	private String autor;
	private String isbn; //id del libro
	private int cantidadTotal; //cantidad de ejemplares
	private int cantidadDisponible; //cantidad disponible para prestar
	
	public Libro(String nombre, String autor, String isbn, int cantidadTotal) {
		this.nombre = nombre;
		this.autor = autor;
		this.isbn = isbn;
		this.cantidadTotal = cantidadTotal;
		this.cantidadDisponible = cantidadTotal; //cuando se crea un libro q tiene X ejemplares total, automáticamente hay X disponibles
	}
	public String getNombre() {
		return nombre;
	}
	public String getAutor() {
		return autor;
	}
	public String getIsbn() {
		return isbn;
	}
	public int getCantidadTotal() {
		return cantidadTotal;
	}
	public int getCantidadDisponible() {
		return cantidadDisponible;
	}
	
	public void prestarLibro() {
		if (cantidadDisponible > 0) {
			cantidadDisponible--;
		}
	}
	public void devolverLibro() {
		if (cantidadDisponible < cantidadTotal) {
			cantidadDisponible++;
		}
	}
}
