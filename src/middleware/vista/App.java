package middleware.vista;

import javax.swing.JFrame;

import middleware.rmi.interfaces.Buscador;
import middleware.rmi.interfaces.ManagerDeSesion;
import middleware.rmi.interfaces.ManagerDeUsuario;

public class App extends JFrame {

	private static final long serialVersionUID = 1L;
	private ManagerDeSesion sesion;
	private ManagerDeUsuario usu;
	private Buscador buscador;

	public App(String titulo, ManagerDeSesion sesion, Buscador buscador) {
		super(titulo);
		setSize(800, 600);
		setLocation(50, 60);
		this.sesion = sesion;
		this.buscador = buscador;
		Menu menu = new Menu(this, buscador, sesion, null);
		this.setJMenuBar(menu);
		this.setVisible(true);
	}

	public ManagerDeSesion getSesion() {
		return sesion;
	}

	public void setSesion(ManagerDeSesion sesion) {
		this.sesion = sesion;
	}

	public ManagerDeUsuario getUsu() {
		return usu;
	}

	public void setUsu(ManagerDeUsuario usu) {
		this.usu = usu;
	}

	public Buscador getBuscador() {
		return buscador;
	}

	public void setBuscador(Buscador buscador) {
		this.buscador = buscador;
	}
	

}
