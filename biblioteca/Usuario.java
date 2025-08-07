package biblioteca;

public class Usuario {
    private String numeroDocumento;
    private String nombre;
    private String apellido;
    private String email;
    private String telefono;
    private String domicilio;

    public Usuario(String numeroDocumento, String nombre, String apellido, String email, String telefono, String domicilio) {
        this.numeroDocumento = numeroDocumento;
        this.nombre = nombre;
        this.apellido = apellido;
        this.email = email;
        this.telefono = telefono;
        this.domicilio = domicilio;
    }

    // Getters y setters
    public String getNumeroDocumento() { return numeroDocumento; }
    public String getNombre() { return nombre; }
    public String getApellido() { return apellido; }
    public String getEmail() { return email; }
    public String getTelefono() { return telefono; }
    public String getDomicilio() { return domicilio; }

    public void setNombre(String nombre) { this.nombre = nombre; }
    public void setApellido(String apellido) { this.apellido = apellido; }
    public void setEmail(String email) { this.email = email; }
    public void setTelefono(String telefono) { this.telefono = telefono; }
    public void setDomicilio(String domicilio) { this.domicilio = domicilio; }

    @Override
    public String toString() {
        return "Usuario{" +
                "numeroDocumento='" + numeroDocumento + '\'' +
                ", nombre='" + nombre + '\'' +
                ", apellido='" + apellido + '\'' +
                ", email='" + email + '\'' +
                ", telefono='" + telefono + '\'' +
                ", domicilio='" + domicilio + '\'' +
                '}';
    }
}