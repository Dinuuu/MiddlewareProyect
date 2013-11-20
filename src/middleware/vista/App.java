package middleware.vista;

import java.awt.CardLayout;
import java.awt.event.ContainerEvent;
import java.awt.event.ContainerListener;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JFrame;
import javax.swing.JPanel;

import middleware.rmi.interfaces.Buscador;
import middleware.rmi.interfaces.ManagerDeSesion;
import middleware.rmi.interfaces.ManagerDeUsuario;

public class App extends JFrame implements ContainerListener {

	private static final long serialVersionUID = 1L;
	private ManagerDeSesion sesion;
	private ManagerDeUsuario usu;
	private Buscador buscador;
	private Map<String, JPanel> cards = new HashMap<String, JPanel>();

	public Map<String, JPanel> getCards() {
		return cards;
	}

	public App(String titulo, ManagerDeSesion sesion, Buscador buscador) {
		super(titulo);
		setSize(800, 600);
		setLocation(50, 60);
		this.sesion = sesion;
		this.buscador = buscador;
		Menu menu = new Menu(this, buscador, sesion, null);
		getContentPane().setLayout(new CardLayout());
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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

	@Override
	public void componentAdded(ContainerEvent e) {
		this.getContentPane().repaint();
	}

	@Override
	public void componentRemoved(ContainerEvent e) {
		this.getContentPane().repaint();
	}

}
