package middleware.rmi;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

import middleware.modelo.ManagerDeSesionImpl;
import middleware.rmi.interfaces.Buscador;
import middleware.rmi.interfaces.ManagerDeSesion;

public class Servidor {

	public static void main(String[] args) {

		try {
			ManagerDeSesionImpl obj_remoto = new ManagerDeSesionImpl();
			ManagerDeSesion stubSesion = (ManagerDeSesion) UnicastRemoteObject
					.exportObject(obj_remoto, 0);
			Registry registro = LocateRegistry.getRegistry();
			registro.bind("ManagerDeSesion", stubSesion);

			Buscador stubBuscador = (Buscador) stubSesion;
			registro.bind("Buscador", stubBuscador);

		} catch (Exception e) {
			// TODO: handle exception
		}
	}
}
