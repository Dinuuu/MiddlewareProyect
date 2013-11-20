package middleware.vista;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.rmi.RemoteException;
import java.util.SortedSet;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import middleware.modelo.Publicacion;
import middleware.rmi.interfaces.ManagerDeUsuario;

public class Perfil extends JPanel implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ManagerDeUsuario usu;
	private JButton publicar;
	private JTextArea publicacion;
	private JPanel panelDePubs;
	private PanelDePublicaciones publicaciones;
	private PanelInfoUsuario infoUsuario;
	private App owner;

	public Perfil(App owner, ManagerDeUsuario usu) throws IOException {

		super();
		this.owner = owner;
		this.usu = usu;

		JPanel panelPrincipal = new JPanel(new BorderLayout());

		JPanel panelCentral = new JPanel(new BorderLayout());

		JPanel miPublicacion = panelParaPublicar(owner.getUsu());
		panelCentral.add(miPublicacion, BorderLayout.NORTH);

		SortedSet<Publicacion> pubs = usu.getPublicaciones();
		panelDePubs = new JPanel(new BorderLayout());
		panelDePubs.add(new JLabel("Publicaciones"), BorderLayout.NORTH);
		publicaciones = new PanelDePublicaciones(pubs);
		panelDePubs.add(publicaciones);
		panelDePubs.setVisible(usu.isPublico()
				|| (owner.getUsu() == null ? false : usu.getNombreUsuario()
						.equals(owner.getUsu().getNombreUsuario()) ? true : usu
						.esAmigo(owner.getUsu())));
		System.out.println(panelDePubs.isVisible());
		panelCentral.add(panelDePubs);

		infoUsuario = new PanelInfoUsuario(usu);
		panelPrincipal.add(infoUsuario, BorderLayout.WEST);

		panelPrincipal.add(panelCentral);
		add(panelPrincipal);

		setSize(getPreferredSize());
		setVisible(true);

	}

	public PanelInfoUsuario getInfoUsuario() {
		return infoUsuario;
	}

	private JPanel panelParaPublicar(ManagerDeUsuario usuarioConectado) {
		JPanel miPublicacion = new JPanel();
		publicacion = new JTextArea(3, 25);
		publicar = new JButton("Publicar");
		publicar.addActionListener(this);

		miPublicacion.add(publicacion);
		miPublicacion.add(publicar);
		miPublicacion.setVisible(usuarioConectado == null ? false
				: usuarioConectado.equals(usu));

		return miPublicacion;

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		try {
			usu.escribirPublicacion(publicacion.getText(), usu);
			publicaciones.agregarPublicacion(publicacion.getText(), usu);

		} catch (RemoteException e1) {

			new ErrorDialog(owner,
					"Se ha producido un error, intentelo nuevamente en unos minutos");

		} catch (NullPointerException e1) {
			new ErrorDialog(owner, "Debe ingresar algo para publicar");
		}

	}

	public void modificar(ManagerDeUsuario usuarioConectado) throws IOException {
		infoUsuario.modificar(usuarioConectado);

	}

}
