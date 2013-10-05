package middleware.rmi.interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;

import middleware.modelo.Perfil;
import middleware.modelo.Publicacion;
import middleware.modelo.Usuario;

public interface ManagerDePerfil extends Remote {

	public Perfil getPerfil() throws RemoteException;

	public boolean cambiarDatos(String nombre, String apellido,
			String direccionWeb, byte[] foto, boolean publico,
			String nombreUsuario) throws RemoteException;

	public boolean cambiarContraseña(String contraseñaVieja,
			String contraseñaNueva, String contraseñaNuevaRep)
			throws RemoteException;


}
