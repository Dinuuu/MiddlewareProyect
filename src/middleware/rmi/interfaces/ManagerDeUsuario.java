package middleware.rmi.interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;
import java.util.SortedSet;

import middleware.modelo.Publicacion;
import middleware.modelo.Solicitud;

public interface ManagerDeUsuario extends Remote {

	public boolean peticionAmistad(ManagerDeUsuario usu) throws RemoteException;

	public boolean aceptarSolicitud(Solicitud s) throws RemoteException;

	public boolean escribirPublicacion(String mensaje, ManagerDeUsuario usu)
			throws RemoteException;

	public boolean responderPublicacion(Publicacion p, String mensaje)
			throws RemoteException;

	public boolean cambiarDatos(String nombre, String apellido,
			String direccionWeb, boolean publico, String nombreUsuario,
			ManagerDeSesion sesion) throws RemoteException;

	public boolean cambiarContraseña(String contraseñaVieja,
			String contraseñaNueva, String contraseñaNuevaRep)
			throws RemoteException;

	public String getNombreUsuario() throws RemoteException;

	public String getNombre() throws RemoteException;

	public String getApellido() throws RemoteException;

	public boolean esAmigo(ManagerDeUsuario usuario) throws RemoteException;

	public List<Solicitud> getSolicitudes() throws RemoteException;

	public SortedSet<Publicacion> getPublicaciones() throws RemoteException;

	public String getContraseña() throws RemoteException;

	public boolean isPublico() throws RemoteException;

	public byte[] getFoto() throws RemoteException;

	public String getDireccionWeb() throws RemoteException;

	public List<ManagerDeUsuario> getAmigos() throws RemoteException;

	public void declinarSolicitud(Solicitud s) throws RemoteException;
}
