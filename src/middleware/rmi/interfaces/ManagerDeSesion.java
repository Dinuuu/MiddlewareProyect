package middleware.rmi.interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;

import middleware.modelo.Usuario;

public interface ManagerDeSesion extends Remote {

	public boolean registrarse(Usuario usu) throws RemoteException;

	public boolean conectarse(String nombreUsu, String contraseña)
			throws RemoteException;

	public boolean desconectarse(Usuario usu) throws RemoteException;

	public boolean existeUsuario(String nombreUsuario) throws RemoteException;
	
}
