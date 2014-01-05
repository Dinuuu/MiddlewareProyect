package middleware.modelo;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.rmi.RemoteException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

import middleware.rmi.interfaces.ManagerDeSesion;
import middleware.rmi.interfaces.ManagerDeUsuario;
import middleware.vista.interfaz.EventListener;

public class Usuario implements Serializable, ManagerDeUsuario {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<ManagerDeUsuario> amigos = new ArrayList<ManagerDeUsuario>();
	private List<Solicitud> solicitudes = new ArrayList<Solicitud>();
	private String nombreUsuario;
	private String contraseña;
	private String nombre;
	private String apellido;
	private String direccionWeb;
	private File foto;
	private SortedSet<Publicacion> publicaciones = new TreeSet<Publicacion>();
	private boolean publico;
	private EventListener e;

	public Usuario(String nombreUsuario, String contraseña, String nombre,
			String apellido, String direccionWeb, boolean publico)
			throws IOException {
		super();
		this.nombreUsuario = nombreUsuario;
		setContraseña(contraseña);
		this.nombre = nombre;
		this.apellido = apellido;
		this.direccionWeb = direccionWeb;
		this.foto = new File("./default.jpeg");
		this.publico = publico;
	}

	public String getNombreUsuario() throws RemoteException {
		return nombreUsuario;
	}

	public void setNombreUsuario(String nombreUsuario) {
		this.nombreUsuario = nombreUsuario;
	}

	public String getNombre() throws RemoteException {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellido() throws RemoteException {
		return apellido;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	public String getDireccionWeb() throws RemoteException {
		return direccionWeb;
	}

	public void setDireccionWeb(String direccionWeb) {
		this.direccionWeb = direccionWeb;
	}

	public File getFoto() throws RemoteException {
		return foto;
	}

	public boolean isPublico() throws RemoteException {
		return publico;
	}

	public void setPublico(boolean publico) {
		this.publico = publico;
	}

	public List<ManagerDeUsuario> getAmigos() throws RemoteException {
		return amigos;
	}

	public String getContraseña() throws RemoteException {
		return contraseña;
	}

	public SortedSet<Publicacion> getPublicaciones() throws RemoteException {
		return publicaciones;
	}

	public void setContraseña(String contraseña) {

		this.contraseña = contraseña;
	}

	public List<Solicitud> getSolicitudes() throws RemoteException {
		return solicitudes;
	}

	@Override
	public boolean peticionAmistad(ManagerDeUsuario usu) throws RemoteException {

		if (usu.esAmigo(this))
			return false;

		return agregarSolicitud(usu);

	}

	private boolean agregarSolicitud(ManagerDeUsuario usu)
			throws RemoteException {

		if (usu.existeSolicitud(this) || existeSolicitud(usu))
			return false;

		usu.añadirSolicitud(new Solicitud(this));
		return true;

	}

	public boolean existeSolicitud(ManagerDeUsuario usuario)
			throws RemoteException {
		for (Solicitud s : solicitudes) {
			if (s.getUsu().getNombreUsuario()
					.equals(usuario.getNombreUsuario()))
				return true;
		}
		return false;
	}

	public boolean esAmigo(ManagerDeUsuario usuario) throws RemoteException {

		for (ManagerDeUsuario u : amigos) {
			if (u.getNombreUsuario().equals(usuario.getNombreUsuario())) {
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean cambiarDatos(String nombre, String apellido,
			String direccionWeb, boolean publico, String nombreUsuario,
			ManagerDeSesion sesion) throws RemoteException {

		if ((!getNombreUsuario().equals(nombreUsuario) && !sesion
				.existeUsuario(nombreUsuario))
				|| getNombreUsuario().equals(nombreUsuario)) {
			setNombreUsuario(nombreUsuario);
			setNombre(nombre);
			setApellido(apellido);
			setDireccionWeb(direccionWeb);
			setPublico(publico);
			return true;
		}
		return false;
	}

	@Override
	public boolean aceptarSolicitud(Solicitud s) throws RemoteException {

		if (esAmigo(s.getUsu()))
			return false;

		agregarAmigo(s.getUsu());
		s.getUsu().agregarAmigo(this);
		eliminarSolicitud(s);
		return true;

	}

	@Override
	public boolean cambiarContraseña(String contraseñaVieja,
			String contraseñaNueva, String contraseñaNuevaRep)
			throws RemoteException {

		if (!contraseñaVieja.equals(getContraseña())
				|| !contraseñaNueva.equals(contraseñaNuevaRep))
			return false;

		setContraseña(contraseñaNueva);
		return true;
	}

	@Override
	public boolean escribirPublicacion(String mensaje, ManagerDeUsuario usu)
			throws RemoteException {

		if (!this.getNombreUsuario().equals(usu.getNombreUsuario())
				&& !this.esAmigo(usu)) {

			return false;
		}
		usu.añadirPublicacion(new Publicacion(this, mensaje, new Timestamp(
				System.currentTimeMillis())));
		for (ManagerDeUsuario u : amigos) {
			u.notify(mensaje, usu);
		}
		return true;
	}

	@Override
	public void añadirPublicacion(Publicacion pub) throws RemoteException {
		publicaciones.add(pub);

	}

	@Override
	public boolean responderPublicacion(Publicacion p, String mensaje)
			throws RemoteException {

		p.responder(mensaje, this);
		return true;
	}

	public boolean equals(Object o) {
		if (o.getClass() != getClass())
			return false;
		try {
			return getNombreUsuario().equals(((Usuario) o).getNombreUsuario());
		} catch (RemoteException e) {
			return false;
		}
	}

	@Override
	public void declinarSolicitud(Solicitud s) throws RemoteException {

		eliminarSolicitud(s);

	}

	@Override
	public void cambiarFoto(File bimg) throws RemoteException {

		this.foto = bimg;
	}

	@Override
	public void añadirSolicitud(Solicitud solici) throws RemoteException {
		solicitudes.add(solici);
	}

	@Override
	public void agregarAmigo(ManagerDeUsuario usu) throws RemoteException {
		amigos.add(usu);

	}

	@Override
	public void eliminarSolicitud(Solicitud s) throws RemoteException {

		solicitudes.remove(s);
	}

	@Override
	public void setListener(EventListener e) throws RemoteException {
		this.e = e;

	}

	@Override
	public void notify(String mensaje, ManagerDeUsuario usu)
			throws RemoteException {
		if (e != null) {
			System.out.println("NO ES NULLLL");
			e.notify(mensaje, usu);
		} else
			System.out.println("ES NULLLL");
	}
}
