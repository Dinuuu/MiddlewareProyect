package middleware.vista;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import middleware.rmi.interfaces.ManagerDeSesion;
import middleware.rmi.interfaces.ManagerDeUsuario;

public class EnviarSolicitudDialog extends JDialog implements ActionListener {

	private static int MAX_LENGHT = 40;
	JButton enviarSolicitud;
	JTextField nombreUsuario;
	ManagerDeSesion sesion;
	ManagerDeUsuario usuarioConectado;

	public EnviarSolicitudDialog(JFrame owner, ManagerDeSesion sesion,
			ManagerDeUsuario usu) {
		super(owner, "Enviar Solicitud", true);
		this.sesion = sesion;
		this.usuarioConectado = usu;
		enviarSolicitud = new JButton("Enviar solicitud");
		enviarSolicitud.addActionListener(this);
		nombreUsuario = new JTextField(MAX_LENGHT);

		JPanel panelPrincipal = new JPanel();
		panelPrincipal.setLayout(new BorderLayout());
		JPanel panelCentral = new JPanel();
		JPanel p = new JPanel();

		p.add(new JLabel("Nombre de Usuario"));
		p.add(nombreUsuario);
		panelCentral.add(p);

		p = new JPanel();
		p.add(enviarSolicitud);
		panelCentral.add(p);

		panelPrincipal.add(panelCentral);

		add(panelPrincipal);
		
		pack();
		setSize(getPreferredSize());
		setVisible(true);

	}

	@Override
	public void actionPerformed(ActionEvent e) {

		try {
			ManagerDeUsuario usuario = sesion.getUsuario(nombreUsuario
					.getText());
			if (usuario == null
					|| usuario.getNombreUsuario().equals(
							usuarioConectado.getNombreUsuario()))
				return;
			usuarioConectado.peticionAmistad(usuario);
			setVisible(false);
			dispose();
		} catch (RemoteException e1) {
			e1.printStackTrace();
		}
	}

}
