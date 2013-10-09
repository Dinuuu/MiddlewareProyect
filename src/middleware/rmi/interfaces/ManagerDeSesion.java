package middleware.rmi.interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;

import middleware.modelo.Usuario;

public interface ManagerDeSesion extends Remote {

	public boolean registrarse(ManagerDeUsuario usu) throws RemoteException;

	public boolean conectarse(String nombreUsu, String contraseña)
			throws RemoteException;

	public boolean desconectarse(ManagerDeUsuario usu) throws RemoteException;

	public boolean existeUsuario(String nombreUsuario) throws RemoteException;

	public ManagerDeUsuario getUsuario(String nombreUsuario)
			throws RemoteException;

}
