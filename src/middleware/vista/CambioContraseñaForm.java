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

import middleware.rmi.interfaces.ManagerDeUsuario;

public class CambioContraseñaForm extends JDialog implements ActionListener {
	private static int MAX_LENGHT = 20;
	private static final long serialVersionUID = 1L;
	private JPasswordField contraseña;
	private JPasswordField repContraseña;
	private JPasswordField contraseñaVieja;
	private JButton cambiarContraseña;
	private App parent;
	private ManagerDeUsuario usu;

	public CambioContraseñaForm(App parent, ManagerDeUsuario usu) {
		super(parent, "Cambiar Contraseña", true);
		this.usu = usu;
		this.parent = parent;
		JPanel panelPrincipal = new JPanel(new BorderLayout());
		JPanel panelCentral = new JPanel();

		contraseña = new JPasswordField(MAX_LENGHT);
		repContraseña = new JPasswordField(MAX_LENGHT);
		contraseñaVieja = new JPasswordField(MAX_LENGHT);
		cambiarContraseña = new JButton("Cambiar Contraseña");
		cambiarContraseña.addActionListener(this);

		JPanel p = new JPanel();

		p.add(new JLabel("Contraseña Actual"));
		p.add(contraseñaVieja);
		panelCentral.add(p);

		p = new JPanel();
		p.add(new JLabel("Nueva Contraseña"));
		p.add(contraseña);
		panelCentral.add(p);

		p = new JPanel();
		p.add(new JLabel("Repita Nueva Contraseña"));
		p.add(repContraseña);
		panelCentral.add(p);

		panelCentral.add(cambiarContraseña);
		panelPrincipal.add(panelCentral);
		add(panelPrincipal);
		setPreferredSize(new Dimension(450, 300));
		setSize(getPreferredSize());
		setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		try {

			if (contraseña.getText().equals("")
					|| repContraseña.getText().equals("")
					|| contraseñaVieja.getText().equals(""))
				throw new NullPointerException();

			if (!contraseña.getText().equals(repContraseña.getText())
					|| !contraseñaVieja.getText().equals(usu.getContraseña())) {
				new ErrorDialog(parent, "Las contraseñas no coinciden");

				return;
			}

			usu.cambiarContraseña(contraseñaVieja.getText(),
					contraseña.getText(), repContraseña.getText());
			setVisible(false);
			dispose();

		} catch (RemoteException e1) {
			new ErrorDialog(parent,
					"Se ha producido un error, intentelo nuevamente en unos minutos");

		} catch (NullPointerException e2) {
			new ErrorDialog(parent, "Ingrese todos los campos obligatorios");

		}
	}

}
