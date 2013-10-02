package middleware.modelo;

import java.io.Serializable;
import java.util.List;

public class Perfil implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String nombreUsuario;
	private String contraseña;
	private String nombre;
	private String apellido;
	private String direccionWeb;
	private byte[] foto;
	private List<Publicacion> publicaciones;
	private boolean publico;

	public Perfil(String nombreUsuario, String contraseña, String nombre,
			String apellido, String direccionWeb, byte[] foto, boolean publico) {
		super();
		this.nombreUsuario = nombreUsuario;
		this.contraseña = contraseña;
		this.nombre = nombre;
		this.apellido = apellido;
		this.direccionWeb = direccionWeb;
		this.foto = foto;
		this.publico = publico;
	}

	public String getContraseña() {
		return contraseña;
	}

	public void setContraseña(String contraseña) {
		this.contraseña = contraseña;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellido() {
		return apellido;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	public String getDireccionWeb() {
		return direccionWeb;
	}

	public void setDireccionWeb(String direccionWeb) {
		this.direccionWeb = direccionWeb;
	}

	public byte[] getFoto() {
		return foto;
	}

	public void setFoto(byte[] foto) {
		this.foto = foto;
	}

	public boolean isPublico() {
		return publico;
	}

	public void setPublico(boolean publico) {
		this.publico = publico;
	}

	public String getNombreUsuario() {
		return nombreUsuario;
	}

	public List<Publicacion> getPublicaciones() {
		return publicaciones;
	}

}
