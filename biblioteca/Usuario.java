package biblioteca;

public class Usuario {
	private String documento;
	private String nombre;
	private String apellido;
	private String email;
	private String telefono;
	private String domicilio;
	
	public Usuario(String documento, String nombre, String apellido, String email, String telefono, String domicilio) {
		this.documento = documento;
		this.nombre = nombre;
		this.apellido = apellido;
		this.email = email;
		this.telefono = telefono;
		this.domicilio = domicilio;
	}
	public String getDocumento() {
		return documento;
	}
	public String getNombre() {
		return nombre;
	}
	public String getApellido() {
		return apellido;
	}
	public String getEmail() {
		return email;
	}
	public String getTelefono() {
		return telefono;
	}
	public String getDomicilio() {
		return domicilio;
	}
}
