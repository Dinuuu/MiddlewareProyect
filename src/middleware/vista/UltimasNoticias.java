package middleware.vista;

import java.awt.BorderLayout;
import java.rmi.RemoteException;
import java.util.List;
import java.util.SortedSet;

import javax.swing.JLabel;
import javax.swing.JPanel;

import middleware.modelo.Publicacion;
import middleware.rmi.interfaces.ManagerDeUsuario;

public class UltimasNoticias extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	SortedSet<Publicacion> publicaciones;
	PanelInfoUsuario infoUsuario;
	PanelDePublicaciones pubs;

	public UltimasNoticias(App parent, PanelInfoUsuario infousuario)
			throws RemoteException {
		super();
		this.infoUsuario = infousuario;
		JPanel panelPrincipal = new JPanel(new BorderLayout());
		JPanel panelCentral = new JPanel(new BorderLayout());
		ManagerDeUsuario usu = parent.getUsu();
		publicaciones = usu.getPublicaciones();

		List<ManagerDeUsuario> amigos = usu.getAmigos();

		for (ManagerDeUsuario amigo : amigos) {
			publicaciones.addAll(amigo.getPublicaciones());
		}
		panelPrincipal.add(infoUsuario, BorderLayout.WEST);

		pubs = new PanelDePublicaciones(publicaciones);
		panelCentral.add(new JLabel("Ultimas Noticias"), BorderLayout.NORTH);
		panelCentral.add(pubs);
		panelPrincipal.add(panelCentral);

		add(panelPrincipal);

		setSize(getPreferredSize());
		setVisible(true);
		parent.getUsu().setListener(pubs);

	}
}
