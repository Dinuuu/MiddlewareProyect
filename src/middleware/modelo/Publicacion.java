package middleware.modelo;

import java.sql.Timestamp;
import java.util.SortedSet;
import java.util.TreeSet;

public class Publicacion extends Mensaje {

	private SortedSet<Mensaje> respuestas = new TreeSet<Mensaje>();

	public Publicacion(Usuario usu, String mensaje, Timestamp horaDePublicacion) {
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
