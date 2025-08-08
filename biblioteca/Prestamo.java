package biblioteca;

import java.time.LocalDate;

public class Prestamo {
    private LocalDate fechaPrestamo;
    private int cantidadDias;
    private Libro libro;
    private Usuario usuario;

    public Prestamo(LocalDate fechaPrestamo, int cantidadDias, Libro libro, Usuario usuario) {
        this.fechaPrestamo = fechaPrestamo;
        this.cantidadDias = cantidadDias;
        this.libro = libro;
        this.usuario = usuario;
    }

    // Getters y setters
    public LocalDate getFechaPrestamo() { return fechaPrestamo; }
    public int getCantidadDias() { return cantidadDias; }
    public Libro getLibro() { return libro; }
    public Usuario getUsuario() { return usuario; }

    public void setCantidadDias(int cantidadDias) { this.cantidadDias = cantidadDias; }

    @Override
    public String toString() {
        String nombreLibro = libro.getTitulo();
        return "Prestamo{" +
                "fechaPrestamo=" + fechaPrestamo +
                ", cantidadDias=" + cantidadDias +
                ", libro=" + nombreLibro +
                ", usuario=" + usuario.getNombre() + " " + usuario.getApellido() +
                '}';
    }
}
