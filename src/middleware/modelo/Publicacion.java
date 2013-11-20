package middleware.modelo;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.SortedSet;
import java.util.TreeSet;

import middleware.rmi.interfaces.ManagerDeUsuario;

public class Publicacion extends Mensaje implements Serializable {

	private static final long serialVersionUID = 1L;
	private SortedSet<Mensaje> respuestas = new TreeSet<Mensaje>();

	public Publicacion(ManagerDeUsuario usu, String mensaje,
			Timestamp horaDePublicacion) {
		super(usu, mensaje, horaDePublicacion);
	}

	public SortedSet<Mensaje> getRespuestas() {
		return respuestas;
	}

	public void responder(String mensaje, Usuario usuario) {

		respuestas.add(new Mensaje(usuario, mensaje, new Timestamp(System
				.currentTimeMillis())));
	}

}
