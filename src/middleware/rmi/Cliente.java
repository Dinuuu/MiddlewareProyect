package middleware.rmi;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import middleware.rmi.interfaces.Buscador;
import middleware.rmi.interfaces.ManagerDeSesion;
import middleware.vista.App;

public class Cliente {
	public static void main(String[] args) {
		if (args.length != 1) {
			System.out.println("Se debe ingresar la direccion del Servidor");
			return;
		}
		try {
			Registry registro;
			registro = LocateRegistry.getRegistry(args[0], 1099);
			ManagerDeSesion stubSesion = (ManagerDeSesion) registro
					.lookup("ManagerDeSesion");

			Buscador stubBuscador = (Buscador) registro.lookup("Buscador");

			new App("MiddleBook", stubSesion, stubBuscador);

		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NotBoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
