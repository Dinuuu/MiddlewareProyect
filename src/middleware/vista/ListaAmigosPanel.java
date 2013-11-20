package middleware.vista;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.rmi.RemoteException;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import middleware.rmi.interfaces.ManagerDeUsuario;

public class ListaAmigosPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	ManagerDeUsuario usu;

	public ListaAmigosPanel(ManagerDeUsuario usu) throws RemoteException {

		super();
		setLayout(new BorderLayout());
		List<ManagerDeUsuario> amigos = usu.getAmigos();
		add(new JLabel("Amigos (" + amigos.size() + ")"), BorderLayout.NORTH);
		this.usu = usu;
		JScrollPane scroll = new JScrollPane();
		JPanel grid = new JPanel(new GridLayout(0, 1));

		for (ManagerDeUsuario u : amigos) {
			JPanel panel = new JPanel();
			panel.add(new JLabel(u.getNombreUsuario()));
			grid.add(panel);
		}
		scroll.getViewport().add(grid);
		scroll.setVisible(amigos.size() > 0);
		add(scroll);

		setVisible(true);
		setSize(getPreferredSize());
	}
}
