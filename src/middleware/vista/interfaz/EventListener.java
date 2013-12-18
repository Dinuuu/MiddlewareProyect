package middleware.vista.interfaz;

import java.rmi.RemoteException;

import middleware.rmi.interfaces.ManagerDeUsuario;

public interface EventListener {

	public void notify(String mensaje, ManagerDeUsuario usu)
			throws RemoteException;
}
