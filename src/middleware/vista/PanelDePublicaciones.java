package middleware.vista;

import java.awt.GridLayout;
import java.rmi.RemoteException;
import java.util.SortedSet;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import middleware.modelo.Publicacion;
import middleware.rmi.interfaces.ManagerDeUsuario;
import middleware.vista.interfaz.EventListener;

public class PanelDePublicaciones extends JScrollPane implements EventListener {

	private static final long serialVersionUID = 1L;
	JPanel panelCentral;

	public PanelDePublicaciones(SortedSet<Publicacion> publicaciones)
			throws RemoteException {
		super();
		JLabel mensaje;
		panelCentral = new JPanel(new GridLayout(0, 1));
		for (Publicacion p : publicaciones) {
			JPanel panel = new JPanel();
			mensaje = new JLabel(p.getUsu().getNombreUsuario()
					+ " ha publicado : \n" + p.getMensaje());
			panel.add(mensaje);
			panelCentral.add(panel);

		}
		viewport.add(panelCentral);

		setVisible(true);
		setSize(getPreferredSize());
	}

	public void agregarPublicacion(String mensaje, ManagerDeUsuario usu)
			throws RemoteException {
		JLabel pub;
		JPanel panel = new JPanel();
		pub = new JLabel(usu.getNombreUsuario() + " ha publicado : \n"
				+ mensaje);
		panel.add(pub);
		panelCentral.add(panel, 0);

		validate();
		repaint();
		revalidate();

	}

	@Override
	public void notify(String mensaje, ManagerDeUsuario usu)
			throws RemoteException {
		System.out.println("ENTRO");
		agregarPublicacion(mensaje, usu);

	}
}
