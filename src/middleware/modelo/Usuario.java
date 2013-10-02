package middleware.modelo;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

import middleware.rmi.interfaces.ManagerDeUsuario;

public class Usuario implements Serializable, ManagerDeUsuario {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<Usuario> amigos = new ArrayList<Usuario>();
	private List<Notificacion> notificaciones = new ArrayList<Notificacion>();
	private Perfil perfil;

	public Usuario(String nombreUsuario, String contraseña, String nombre,
			String apellido, String direccionWeb, byte[] foto, boolean publico) {
		super();
		this.perfil = new Perfil(nombreUsuario, contraseña, nombre, apellido,
				direccionWeb, foto, publico);
	}

	public List<Notificacion> getNotificaciones() {
		return notificaciones;
	}

	public Perfil getPerfil() throws RemoteException {
		return perfil;
	}

	@Override
	public int peticionAmistad(Usuario usu) throws RemoteException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<Usuario> listaAmigos() throws RemoteException {
		return amigos;
	}

}
