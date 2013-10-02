package middleware.modelo;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class Publicacion extends Mensaje {


	private List<Mensaje> respuestas= new ArrayList<Mensaje>();

	public Publicacion(Usuario usu, String mensaje, Timestamp horaDePublicacion) {
		super(usu, mensaje, horaDePublicacion);
		// TODO Auto-generated constructor stub
	}

	public List<Mensaje> getRespuestas() {
		return respuestas;
	}
	
}
