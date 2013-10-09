package middleware.modelo;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

import middleware.rmi.interfaces.ManagerDeSesion;
import middleware.rmi.interfaces.ManagerDeUsuario;

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
	private byte[] foto;
	private SortedSet<Publicacion> publicaciones = new TreeSet<Publicacion>();
	private boolean publico;

	public Usuario(String nombreUsuario, String contraseña, String nombre,
			String apellido, String direccionWeb, byte[] foto, boolean publico) {
		super();
		this.nombreUsuario = nombreUsuario;
		setContraseña(contraseña);
		this.nombre = nombre;
		this.apellido = apellido;
		this.direccionWeb = direccionWeb;
		this.foto = foto;
		this.publico = publico;
	}

	public String getNombreUsuario() throws RemoteException{
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

	public byte[] getFoto() throws RemoteException {
		return foto;
	}

	public void setFoto(byte[] foto) {
		this.foto = foto;
	}

	public boolean isPublico() throws RemoteException {
		return publico;
	}

	public void setPublico(boolean publico) {
		this.publico = publico;
	}

	public List<ManagerDeUsuario> getAmigos() throws RemoteException{
		return amigos;
	}

	public String getContraseña() throws RemoteException {
		return contraseña;
	}

	public SortedSet<Publicacion> getPublicaciones() throws RemoteException {
		return publicaciones;
	}

	public void setContraseña(String contraseña) {
		byte[] bytesDeContraseña = contraseña.getBytes();

		MessageDigest md = null;
		try {
			md = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
			System.out.println("No Existe MD5");
			e.printStackTrace();
		}

		byte[] contraseñaHASH = md.digest(bytesDeContraseña);

		this.contraseña = contraseñaHASH.toString();
	}

	public List<Solicitud> getSolicitudes() throws RemoteException {
		return solicitudes;
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

	public boolean esAmigo(Usuario usuario) throws RemoteException{

		if (usuario.amigos.contains(this))
			return true;
		return false;
	}

	@Override
	public boolean cambiarDatos(String nombre, String apellido,
			String direccionWeb, byte[] foto, boolean publico,
			String nombreUsuario, ManagerDeSesion sesion)
			throws RemoteException {

		if (!getNombreUsuario().equals(nombreUsuario)
				&& !sesion.existeUsuario(nombreUsuario)
				|| getNombreUsuario().equals(nombreUsuario)) {
			setNombreUsuario(nombreUsuario);
			setNombre(nombre);
			setApellido(apellido);
			setDireccionWeb(direccionWeb);
			setFoto(foto);
			setPublico(publico);
			return true;
		}
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

		byte[] bytesDeContraseña = contraseñaVieja.getBytes();

		MessageDigest md = null;
		try {
			md = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
			System.out.println("No Existe MD5");
			e.printStackTrace();
		}

		byte[] contraseñaViejaHASH = md.digest(bytesDeContraseña);

		if (!contraseñaViejaHASH.equals(getContraseña())
				|| !contraseñaNueva.equals(contraseñaNuevaRep))
			return false;

		setContraseña(contraseñaNueva);
		return true;
	}

	@Override
	public boolean escribirPublicacion(String mensaje, Usuario usu)
			throws RemoteException {

		if (!this.esAmigo(usu))
			return false;

		usu.publicaciones.add(new Publicacion(this, mensaje, new Timestamp(
				System.currentTimeMillis())));
		return true;
	}

	@Override
	public boolean responderPublicacion(Publicacion p, String mensaje)
			throws RemoteException {

		p.responder(mensaje, this);
		return true;
	}

}
