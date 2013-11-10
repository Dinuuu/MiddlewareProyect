package middleware.vista;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;
import java.util.Set;

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

	public Perfil(App owner, ManagerDeUsuario usu) throws RemoteException {
		
		super();
		this.usu = usu;

		JPanel panelPrincipal = new JPanel(new BorderLayout());

		JPanel panelCentral = new JPanel(new BorderLayout());

		JPanel miPublicacion = panelParaPublicar(owner.getUsu(), usu);
		panelCentral.add(miPublicacion, BorderLayout.NORTH);

		JPanel panelDePubs = panelDePublicaciones();
		panelCentral.add(panelDePubs);

		JPanel panelInformacion = panelDeInformacion();
		panelPrincipal.add(panelInformacion, BorderLayout.WEST);

		panelPrincipal.add(panelCentral);
		add(panelPrincipal);

		setSize(getPreferredSize());
		setVisible(true);
		owner.getContentPane().add(this);
	}

	private JPanel panelDeInformacion() throws RemoteException {

		JPanel panelDeInfo = new JPanel();

		panelDeInfo.add(new JLabel(usu.getNombreUsuario()));
		panelDeInfo.add(new JLabel(usu.getNombre() + " " + usu.getApellido()));
		panelDeInfo.add(new JLabel(usu.getDireccionWeb()));
		return panelDeInfo;
	}

	private JPanel panelParaPublicar(ManagerDeUsuario usuarioConectado,
			ManagerDeUsuario buscado) {
		JPanel miPublicacion = new JPanel();
		publicacion = new JTextArea(3, 40);
		publicar = new JButton("Publicar");
		publicar.addActionListener(this);

		miPublicacion.add(publicacion);
		miPublicacion.add(publicar);
		miPublicacion.setVisible(usuarioConectado.equals(buscado));

		return miPublicacion;

	}

	private JPanel panelDePublicaciones() throws RemoteException {
		JPanel panelDePubs = new JPanel();
		JLabel mensaje;
		Set<Publicacion> publicaciones = usu.getPublicaciones();
		for (Publicacion p : publicaciones) {
			JPanel panel = new JPanel();
			mensaje = new JLabel(p.getUsu().getNombreUsuario()
					+ " ha publicado : \n" + p.getMensaje());
			panel.add(mensaje);
			panelDePubs.add(panel);
		}
		return panelDePubs;
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		try {
			usu.escribirPublicacion(publicacion.getText(), usu);
		} catch (RemoteException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (NullPointerException e1) {
			return;
		}

	}

}
