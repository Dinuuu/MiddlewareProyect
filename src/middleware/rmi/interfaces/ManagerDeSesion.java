package middleware.rmi.interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;

import middleware.modelo.Usuario;

public interface ManagerDeSesion extends Remote {

	public boolean registrarse(String nombreUsuario, String contraseña,
			String nombre, String apellido, String direccionWeb, byte[] foto,
			boolean publico) throws RemoteException;

	public ManagerDeUsuario conectarse(String nombreUsu, String contraseña)
			throws RemoteException;

	public boolean desconectarse(ManagerDeUsuario usu) throws RemoteException;

	public boolean existeUsuario(String nombreUsuario) throws RemoteException;

	public ManagerDeUsuario getUsuario(String nombreUsuario)
			throws RemoteException;

}
