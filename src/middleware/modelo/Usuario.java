package middleware.modelo;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

import middleware.rmi.interfaces.ManagerDePerfil;
import middleware.rmi.interfaces.ManagerDeSesion;
import middleware.rmi.interfaces.ManagerDeUsuario;

public class Usuario implements Serializable, ManagerDeUsuario, ManagerDePerfil {

	/**
	 * 
	 */
	private ManagerDeSesion sesion;
	private static final long serialVersionUID = 1L;
	private List<Usuario> amigos = new ArrayList<Usuario>();
	private List<Solicitud> solicitudes = new ArrayList<Solicitud>();
	private Perfil perfil;

	public Usuario(String nombreUsuario, String contraseña, String nombre,
			String apellido, String direccionWeb, byte[] foto, boolean publico,
			ManagerDeSesion sesion) {
		super();
		this.perfil = new Perfil(nombreUsuario, contraseña, nombre, apellido,
				direccionWeb, foto, publico);
		this.sesion = sesion;
	}

	public List<Solicitud> getSolicitudes() throws RemoteException {
		return solicitudes;
	}

	public Perfil getPerfil() throws RemoteException {
		return perfil;
	}

	@Override
	public boolean peticionAmistad(Usuario usu) throws RemoteException {

		if (usu.esAmigo(this))
			return false;

		return agregarSolicitud(usu);

	}

	private boolean agregarSolicitud(Usuario usu) {
		Solicitud solici = new Solicitud(this);
		Solicitud aux = new Solicitud(usu);

		if (usu.solicitudes.contains(solici) || usu.solicitudes.contains(aux))
			return false;

		usu.solicitudes.add(solici);
		return true;

	}

	private boolean esAmigo(Usuario usuario) {

		if (usuario.amigos.contains(this))
			return true;
		return false;
	}

	@Override
	public List<Usuario> listaAmigos() throws RemoteException {
		return amigos;
	}

	@Override
	public boolean cambiarDatos(String nombre, String apellido,
			String direccionWeb, byte[] foto, boolean publico,
			String nombreUsuario) throws RemoteException {

		if (sesion.existeUsuario(nombreUsuario))
			return false;
		perfil.setNombreUsuario(nombreUsuario);
		perfil.setNombre(nombre);
		perfil.setApellido(apellido);
		perfil.setDireccionWeb(direccionWeb);
		perfil.setFoto(foto);
		perfil.setPublico(publico);
		return false;
	}

	@Override
	public boolean aceptarSolicitud(Solicitud s) throws RemoteException {

		if (esAmigo(s.getUsu()))
			return false;

		amigos.add(s.getUsu());
		s.getUsu().amigos.add(this);
		return true;

	}

	@Override
	public boolean cambiarContraseña(String contraseñaVieja,
			String contraseñaNueva, String contraseñaNuevaRep)
			throws RemoteException {

		if (!contraseñaVieja.equals(perfil.getContraseña())
				|| !contraseñaNueva.equals(contraseñaNuevaRep))
			return false;

		perfil.setContraseña(contraseñaNueva);
		return true;
	}

	@Override
	public boolean escribirPublicacion(String mensaje, Usuario usu)
			throws RemoteException {

		if (!this.esAmigo(usu))
			return false;

		usu.perfil.escribirPublicacion(mensaje, this, usu);
		return true;
	}

	@Override
	public boolean responderPublicacion(Publicacion p, String mensaje)
			throws RemoteException {

		p.responder(mensaje, this);
		return true;
	}

}
