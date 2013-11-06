package middleware.modelo;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
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

	@Override
	public synchronized boolean registrarse(String nombreUsuario,
			String contraseña, String nombre, String apellido,
			String direccionWeb, byte[] foto, boolean publico)
			throws RemoteException {

		if (existeUsuario(nombreUsuario))
			return false;

		ManagerDeUsuario usu = new Usuario(nombreUsuario, contraseña, nombre,
				apellido, direccionWeb, foto, publico);

		usuarios.put(nombreUsuario, usu);

		return true;
	}

	@Override
	public synchronized ManagerDeUsuario conectarse(String nombreUsu,
			String contraseña) throws RemoteException {

		if (!usuarios.containsKey(nombreUsu))
			return null;

		byte[] bytesDeContraseña = contraseña.getBytes();

		MessageDigest md = null;
		try {
			md = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
			System.out.println("No Existe MD5");
			e.printStackTrace();
		}

		byte[] contraseñaHASH = md.digest(bytesDeContraseña);

		ManagerDeUsuario resp = usuarios.get(nombreUsu);

		if (contraseñaHASH.toString().equals(resp.getContraseña()))
			return resp;
		else
			return null;

	}

	@Override
	public synchronized boolean desconectarse(ManagerDeUsuario usu)
			throws RemoteException {
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
	public ManagerDeUsuario buscar(String nombreUsuario) throws RemoteException {
		return getUsuario(nombreUsuario);
	}

	@Override
	public ManagerDeUsuario getUsuario(String nombreUsuario)
			throws RemoteException {
		return usuarios.get(nombreUsuario);
	}

}
