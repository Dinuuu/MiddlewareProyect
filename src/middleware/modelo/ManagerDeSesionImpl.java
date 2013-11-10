package middleware.modelo;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import middleware.rmi.interfaces.Buscador;
import middleware.rmi.interfaces.ManagerDeSesion;
import middleware.rmi.interfaces.ManagerDeUsuario;

public class ManagerDeSesionImpl implements Serializable, ManagerDeSesion,
		Buscador {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	Map<String, ManagerDeUsuario> usuarios = new HashMap<String, ManagerDeUsuario>();
	List<String> conectados = new ArrayList<String>();

	@Override
	public synchronized boolean registrarse(String nombreUsuario,
			String contraseña, String nombre, String apellido,
			String direccionWeb, boolean publico) throws RemoteException {

		if (existeUsuario(nombreUsuario))
			return false;

		ManagerDeUsuario usu = new Usuario(nombreUsuario, contraseña, nombre,
				apellido, direccionWeb, publico);

		usuarios.put(nombreUsuario, usu);
		conectados.add(usu.getNombreUsuario());

		return true;
	}

	@Override
	public synchronized ManagerDeUsuario conectarse(String nombreUsu,
			String contraseña) throws RemoteException {

		if (!usuarios.containsKey(nombreUsu))
			return null;

		ManagerDeUsuario resp = usuarios.get(nombreUsu);

		if (contraseña.equals(resp.getContraseña())) {
			conectados.add(resp.getNombreUsuario());
			return resp;
		} else
			return null;

	}

	@Override
	public synchronized boolean desconectarse(ManagerDeUsuario usu)
			throws RemoteException {
		usuarios.remove(usu.getNombreUsuario());
		usuarios.put(usu.getNombreUsuario(), usu);
		conectados.remove(usu.getNombreUsuario());
		return false;
	}

	@Override
	public boolean existeUsuario(String nombreUsuario) throws RemoteException {

		if (usuarios.containsKey(nombreUsuario))
			return true;
		return false;
	}

	@Override
	public ManagerDeUsuario buscar(String nombreUsuario) throws RemoteException {
		return getUsuario(nombreUsuario);
	}

	@Override
	public ManagerDeUsuario getUsuario(String nombreUsuario)
			throws RemoteException {
		return usuarios.get(nombreUsuario);
	}

	@Override
	public synchronized void cambiarNombreUsuario(String nombreUsuarioViejo,
			String nombreUsuarioNuevo) throws RemoteException {

		ManagerDeUsuario usu = usuarios.remove(nombreUsuarioViejo);
		usuarios.put(nombreUsuarioNuevo, usu);
	}

}
