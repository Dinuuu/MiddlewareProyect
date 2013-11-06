package middleware.rmi.interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Buscador extends Remote {

	ManagerDeUsuario buscar(String nombre) throws RemoteException;
}
