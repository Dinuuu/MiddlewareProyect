package middleware.modelo;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.Map;

import middleware.rmi.interfaces.ManagerDeSesion;
import middleware.rmi.interfaces.ManagerDeUsuario;

public class ManagerDeSesionImpl implements Serializable, ManagerDeSesion {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	Map<String, ManagerDeUsuario> usuarios = new HashMap<String, ManagerDeUsuario>();

	@Override
	public boolean registrarse(ManagerDeUsuario usu) throws RemoteException {

		String nombreUsuario = usu.getNombreUsuario();
		if (existeUsuario(nombreUsuario))
			return false;

		usuarios.put(nombreUsuario, usu);

		return true;
	}

	@Override
	public boolean conectarse(String nombreUsu, String contraseña)
			throws RemoteException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean desconectarse(ManagerDeUsuario usu) throws RemoteException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean existeUsuario(String nombreUsuario) throws RemoteException {

		if (usuarios.containsKey(nombreUsuario))
			return true;
		return false;
	}

	@Override
	public ManagerDeUsuario getUsuario(String nombreUsuario)
			throws RemoteException {
		return usuarios.get(nombreUsuario);
	}

}
