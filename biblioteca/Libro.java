package biblioteca;

public class Libro {
    private String titulo;
    private String autor;
    private String isbn;
    private int cantidadTotal;
    private int cantidadDisponible;

    public Libro(String isbn, String titulo, String autor, int cantidadTotal) {
        this.isbn = isbn;
        this.titulo = titulo;
        this.autor = autor;
        this.cantidadTotal = cantidadTotal > 0 ? cantidadTotal : 1;
        this.cantidadDisponible = this.cantidadTotal;
    }

    // Getters y setters
    public String getTitulo() { return titulo; }
    public String getAutor() { return autor; }
    public String getIsbn() { return isbn; }
    public int getCantidadTotal() { return cantidadTotal; }
    public int getCantidadDisponible() { return cantidadDisponible; }

    public void setCantidadDisponible(int cantidadDisponible) {
        if (cantidadDisponible >= 0 && cantidadDisponible <= cantidadTotal) {
            this.cantidadDisponible = cantidadDisponible;
        }
    }

    public void incrementarCantidad(int cantidad) {
        if (cantidad > 0) {
            this.cantidadTotal += cantidad;
            this.cantidadDisponible += cantidad;
        }
    }

    @Override
    public String toString() {
        return "Libro{" +
                "titulo='" + titulo + '\'' +
                ", autor='" + autor + '\'' +
                ", isbn='" + isbn + '\'' +
                ", cantidadTotal=" + cantidadTotal +
                ", cantidadDisponible=" + cantidadDisponible +
                '}';
    }
}
