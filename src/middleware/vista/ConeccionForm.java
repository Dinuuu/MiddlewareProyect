package middleware.vista;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import middleware.rmi.interfaces.ManagerDeSesion;
import middleware.rmi.interfaces.ManagerDeUsuario;

public class ConeccionForm extends JDialog implements ActionListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static int MAX_LENGHT = 20;
	private Menu menu;
	private ManagerDeSesion sesion;
	private JTextField nombreDeUsuario;
	private JPasswordField contraseña;
	private JButton conectarse;

	private App parent;

	public ConeccionForm(App parent, ManagerDeSesion sesion, Menu menu) {
		super(parent, "Conectarse", true);
		this.menu = menu;
		this.sesion = sesion;
		this.parent = parent;
		nombreDeUsuario = new JTextField(MAX_LENGHT);
		contraseña = new JPasswordField(MAX_LENGHT);
		conectarse = new JButton("Conectarse");
		conectarse.addActionListener(this);
		JPanel panelPrincipal = new JPanel();
		panelPrincipal.setLayout(new BorderLayout());
		JPanel panelCentral = new JPanel();
		JPanel p = new JPanel();
		p.add(new JLabel("Nombre de usuario"));
		p.add(nombreDeUsuario);
		panelCentral.add(p);

		p = new JPanel();
		p.add(new JLabel("Contraseña"));
		p.add(contraseña);
		panelCentral.add(p);

		p = new JPanel();
		p.add(conectarse);
		panelCentral.add(p);

		panelPrincipal.add(panelCentral);
		add(panelPrincipal);

		setResizable(false);
		setPreferredSize(new Dimension(400, 200));
		setSize(getPreferredSize());

		setVisible(true);

	}

	@Override
	public void actionPerformed(ActionEvent arg0) {

		try {
			ManagerDeUsuario usu = sesion.conectarse(nombreDeUsuario.getText(),
					contraseña.getText());
			if (usu != null) {
				menu.setUsuario(usu);
				parent.setUsu(usu);
				menu.changeButtonStatus();
				setVisible(false);
				dispose();
				return;
			} else
				new ErrorDialog(parent,
						"El usuario o contraseña ingresada son incorrectos");

		} catch (RemoteException e) {
			new ErrorDialog(parent,
					"Se ha producido un error, intentelo nuevamente en unos minutos");

		}
	}

}
