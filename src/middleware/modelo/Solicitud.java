package middleware.modelo;

import java.io.Serializable;
import java.rmi.RemoteException;

import middleware.rmi.interfaces.ManagerDeUsuario;

public class Solicitud implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ManagerDeUsuario usu;

	public Solicitud(ManagerDeUsuario usu) {
		super();
		this.usu = usu;
	}

	public ManagerDeUsuario getUsu() {
		return usu;
	}

	@Override
	public boolean equals(Object o) {
		try {
			return ((Solicitud) o).getUsu().getNombreUsuario()
					.equals(usu.getNombreUsuario());
		} catch (RemoteException e) {
			e.printStackTrace();
			return false;
		}
	}
}
