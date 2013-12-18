package middleware.rmi;

import java.io.IOException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import middleware.rmi.interfaces.ManagerDeSesion;
import middleware.rmi.interfaces.ManagerDeUsuario;

public class IniciadorDeDatos {

	public static void main(String[] args) {
		Registry registro;
		try {
			registro = LocateRegistry.getRegistry();
			ManagerDeSesion stubSesion = (ManagerDeSesion) registro
					.lookup("ManagerDeSesion");

			stubSesion.registrarse("Rafa", "asd", "Rafael", "Gallego",
					"www.rafagallego.com", true);
			ManagerDeUsuario rafa = stubSesion.conectarse("Rafa", "asd");
			stubSesion.registrarse("Dinu", "asd", "Federico", "Di Nucci",
					"www.dinu.com", false);

			ManagerDeUsuario dinu = stubSesion.getUsuario("Dinu");
			dinu.agregarAmigo(rafa);
			rafa.agregarAmigo(dinu);
			stubSesion.registrarse("Rodri", "asd", "Rodrigo", "Gonzalez",
					"www.rodri.com", true);

		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NotBoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
