package middleware.vista;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;
import java.util.Set;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import middleware.modelo.Publicacion;
import middleware.rmi.interfaces.ManagerDeSesion;
import middleware.rmi.interfaces.ManagerDeUsuario;

public class Perfil extends JPanel implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	ManagerDeUsuario usu;
	JButton publicar;
	JTextArea publicacion;

	public Perfil(App owner, ManagerDeUsuario usu, ManagerDeSesion sesion)
			throws RemoteException {
		super();
		this.usu = usu;

		Set<Publicacion> publicaciones = usu.getPublicaciones();
		JPanel panelDePubs = new JPanel();
		JTextArea mensaje;
		JTextField usuario;
		JPanel miPublicacion = new JPanel();
		publicacion = new JTextArea(3, 40);
		publicar = new JButton("Publicar");

		miPublicacion.add(publicacion);
		miPublicacion.add(publicar);
		miPublicacion.setVisible(owner.getUsu().equals(usu));

		for (Publicacion p : publicaciones) {
			JPanel panel = new JPanel();
			usuario = new JTextField(p.getUsu().getNombreUsuario());
			mensaje = new JTextArea(p.getMensaje());
			panel.add(usuario);
			panel.add(mensaje);
			panelDePubs.add(panel);
		}

	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub

	}

}
