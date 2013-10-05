package middleware.rmi.interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

import middleware.modelo.Publicacion;
import middleware.modelo.Solicitud;
import middleware.modelo.Usuario;

public interface ManagerDeUsuario extends Remote {

	public boolean peticionAmistad(Usuario usu) throws RemoteException;

	public List<Usuario> listaAmigos() throws RemoteException;

	public boolean aceptarSolicitud(Solicitud s) throws RemoteException;
	
	public boolean escribirPublicacion(String mensaje, Usuario usu)
			throws RemoteException;

	public boolean responderPublicacion(Publicacion p, String mensaje)
			throws RemoteException;

}
