package middleware.modelo;

import java.io.Serializable;
import java.sql.Timestamp;

import middleware.rmi.interfaces.ManagerDeUsuario;

public class Mensaje implements Comparable<Mensaje>, Serializable {

	private static final long serialVersionUID = 1L;
	private ManagerDeUsuario usu;
	private String mensaje;
	private Timestamp horaDePublicacion;

	public Mensaje(ManagerDeUsuario usu, String mensaje,
			Timestamp horaDePublicacion) {
		super();
		this.usu = usu;
		this.mensaje = mensaje;
		this.horaDePublicacion = horaDePublicacion;
	}

	public ManagerDeUsuario getUsu() {
		return usu;
	}

	public String getMensaje() {
		return mensaje;
	}

	public Timestamp getHoraDePublicacion() {
		return horaDePublicacion;
	}

	@Override
	public int compareTo(Mensaje o) {
		return o.horaDePublicacion.compareTo(horaDePublicacion);
	}
}
