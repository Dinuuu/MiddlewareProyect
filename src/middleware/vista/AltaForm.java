package middleware.vista;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.rmi.RemoteException;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import middleware.rmi.interfaces.ManagerDeSesion;
import middleware.rmi.interfaces.ManagerDeUsuario;

public class AltaForm extends JDialog implements ActionListener {

	private static int MAX_LENGHT = 20;
	private static final long serialVersionUID = 1L;
	private JTextField nombreUsuario;
	private JTextField nombre;
	private JTextField apellido;
	private JPasswordField contraseña;
	private JPasswordField repContraseña;
	private JTextField direccionWeb;
	private ButtonGroup publico;
	JRadioButton perfilPub;
	JRadioButton perfilNoPub;
	private ManagerDeSesion sesion;
	private Menu menu;

	private JButton registrar;

	private App owner;

	public AltaForm(App owner, ManagerDeSesion sesion, Menu menu) {
		super(owner, "Registro", true);
		this.owner = owner;
		this.menu = menu;
		this.sesion = sesion;
		registrar = new JButton("Registrarse");

		nombreUsuario = new JTextField(MAX_LENGHT);
		nombre = new JTextField(MAX_LENGHT);
		apellido = new JTextField(MAX_LENGHT);
		contraseña = new JPasswordField(MAX_LENGHT);
		repContraseña = new JPasswordField(MAX_LENGHT);
		direccionWeb = new JTextField(MAX_LENGHT);
		publico = new ButtonGroup();
		perfilPub = new JRadioButton("publico", true);
		perfilNoPub = new JRadioButton("no publico");
		publico.add(perfilPub);
		publico.add(perfilNoPub);
		JPanel panelPrinciapal = new JPanel();
		JPanel panelCentral = new JPanel();
		panelPrinciapal.setLayout(new BorderLayout());

		JPanel p = new JPanel();
		p.add(new JLabel("Nombre de usuario *"));
		p.add(nombreUsuario);
		panelCentral.add(p);

		p = new JPanel();
		p.add(new JLabel("Nombre *"));
		p.add(nombre);
		panelCentral.add(p);

		p = new JPanel();
		p.add(new JLabel("Apellido *"));
		p.add(apellido);
		panelCentral.add(p);

		p = new JPanel();
		p.add(new JLabel("Contraseña *"));
		p.add(contraseña);
		panelCentral.add(p);

		p = new JPanel();
		p.add(new JLabel("Repetir Contraseña *"));
		p.add(repContraseña);
		panelCentral.add(p);

		p = new JPanel();
		p.add(new JLabel("Direccion Web *"));
		p.add(direccionWeb);
		panelCentral.add(p);

		p = new JPanel();
		p.add(new JLabel("Como quiere que sea su perfil"));
		p.add(perfilPub);
		p.add(perfilNoPub);
		panelCentral.add(p);

		p = new JPanel();
		p.add(new JLabel("* Estos campos son obligatorios"));
		panelCentral.add(p);

		p = new JPanel();
		registrar.addActionListener(this);
		p.add(registrar);
		panelCentral.add(p);

		panelPrinciapal.add(panelCentral);
		add(panelPrinciapal);
		setResizable(false);
		setPreferredSize(new Dimension(400, 400));
		setSize(getPreferredSize());
		setVisible(true);

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		try {

			if (e.getSource() == registrar) {
				handleRegistrar();
			}

		} catch (RemoteException e1) {
			new ErrorDialog(owner,
					"Se ha producido un error, intentelo nuevamente en unos minutos");
		} catch (IOException e1) {
			new ErrorDialog(owner, "Ingrese todos los datos obligatorios");
		} catch (NullPointerException e1) {
			new ErrorDialog(owner, "Ingrese todos los datos obligatorios");
		}

	}

	private void handleRegistrar() throws IOException {

		if (nombreUsuario.getText().equals("") || nombre.getText().equals("")
				|| apellido.getText().equals("")
				|| direccionWeb.getText().equals("")
				|| contraseña.getText().equals("")
				|| repContraseña.getText().equals(" "))
			throw new NullPointerException();

		if (!contraseña.getText().equals(repContraseña.getText())) {
			return;
		}
		if (sesion.existeUsuario(nombreUsuario.getText())) {
			new ErrorDialog(owner, "Ya existe un usuario con ese nombre");
			return;
		}
		sesion.registrarse(nombreUsuario.getText(), contraseña.getText(),
				nombre.getText(), apellido.getText(), direccionWeb.getText(),
				perfilPub.isSelected());
		ManagerDeUsuario usu = sesion.conectarse(nombreUsuario.getText(),
				contraseña.getText());
		this.menu.setUsuario(usu);
		this.menu.changeButtonStatus();
		this.owner.setUsu(usu);
		setVisible(false);
		dispose();
		return;
	}

}
