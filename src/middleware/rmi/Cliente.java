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
		try {
			Registry registro;
			registro = LocateRegistry.getRegistry();
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
