package middleware.modelo;

import java.rmi.RemoteException;

import middleware.rmi.interfaces.ManagerDeUsuario;

public class Solicitud {
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
