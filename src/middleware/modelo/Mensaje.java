package middleware.modelo;

import java.sql.Timestamp;

public class Mensaje {

	private Usuario usu;
	private String mensaje;
	private Timestamp horaDePublicacion;
	
	
	
	public Mensaje(Usuario usu, String mensaje, Timestamp horaDePublicacion) {
		super();
		this.usu = usu;
		this.mensaje = mensaje;
		this.horaDePublicacion = horaDePublicacion;
	}
	public Usuario getUsu() {
		return usu;
	}
	public String getMensaje() {
		return mensaje;
	}
	public Timestamp getHoraDePublicacion() {
		return horaDePublicacion;
	}
}
