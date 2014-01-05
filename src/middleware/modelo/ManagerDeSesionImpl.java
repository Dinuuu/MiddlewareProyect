package middleware.modelo;

import java.io.IOException;
import java.io.Serializable;
import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

import middleware.rmi.interfaces.Buscador;
import middleware.rmi.interfaces.ManagerDeSesion;
import middleware.rmi.interfaces.ManagerDeUsuario;

public class ManagerDeSesionImpl implements Serializable, ManagerDeSesion,
		Buscador {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	List<String> conectados = new ArrayList<String>();
	List<String> usuarios = new ArrayList<String>();

	@Override
	public synchronized boolean registrarse(String nombreUsuario,
			String contraseña, String nombre, String apellido,
			String direccionWeb, boolean publico) throws IOException {

		if (existeUsuario(nombreUsuario))
			return false;

		Usuario obj_remoto = new Usuario(nombreUsuario, contraseña, nombre,
				apellido, direccionWeb, publico);
		ManagerDeUsuario stubSesion = (ManagerDeUsuario) UnicastRemoteObject
				.exportObject(obj_remoto, 0);
		Registry registro = LocateRegistry.getRegistry();
		try {
			registro.bind(nombreUsuario, stubSesion);
		} catch (AlreadyBoundException e) {
			return false;
		}

		usuarios.add(nombreUsuario);
		conectados.add(nombreUsuario);

		return true;
	}

	@Override
	public synchronized ManagerDeUsuario conectarse(String nombreUsu,
			String contraseña) throws RemoteException {

		if (!usuarios.contains(nombreUsu))
			return null;

		ManagerDeUsuario resp = getUsuario(nombreUsu);

		if (contraseña.equals(resp.getContraseña())) {
			conectados.add(resp.getNombreUsuario());
			return resp;
		} else
			return null;

	}

	@Override
	public synchronized boolean desconectarse(ManagerDeUsuario usu)
			throws RemoteException {

		conectados.remove(usu.getNombreUsuario());
		return false;
	}

	@Override
	public boolean existeUsuario(String nombreUsuario) throws RemoteException {

		if (usuarios.contains(nombreUsuario))
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

		if (!usuarios.contains(nombreUsuario))
			return null;

		Registry registro;
		registro = LocateRegistry.getRegistry();
		try {
			ManagerDeUsuario stubUsuario = (ManagerDeUsuario) registro
					.lookup(nombreUsuario);
			return stubUsuario;
		} catch (NotBoundException e) {
			return null;
		}

	}

	@Override
	public synchronized boolean cambiarNombreUsuario(String nombreUsuarioViejo,
			String nombreUsuarioNuevo) throws RemoteException {

		if (usuarios.contains(nombreUsuarioNuevo)
				&& !nombreUsuarioNuevo.equals(nombreUsuarioViejo))
			return false;

		ManagerDeUsuario usu = getUsuario(nombreUsuarioViejo);

		Registry registro = LocateRegistry.getRegistry();
		registro.rebind(nombreUsuarioNuevo, usu);

		usuarios.remove(nombreUsuarioViejo);
		usuarios.add(nombreUsuarioNuevo);
		conectados.remove(nombreUsuarioViejo);
		conectados.add(nombreUsuarioNuevo);
		return true;
	}
}
