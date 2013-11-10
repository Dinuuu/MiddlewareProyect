package middleware.vista;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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

	private static int MAX_LENGHT = 40;
	private static final long serialVersionUID = 1L;
	private JTextField nombreUsuario;
	private JTextField nombre;
	private JTextField apellido;
	private JPasswordField contraseña;
	private JPasswordField repContraseña;
	private JTextField direccionWeb;
	// private JFileChooser foto;
	private ButtonGroup publico;
	JRadioButton perfilPub;
	JRadioButton perfilNoPub;
	private ManagerDeSesion sesion;
	private Menu menu;

	private JButton registrar;
	private JButton abrirArchivo;

	// private File archivo;

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
		// foto = new JFileChooser();
		// abrirArchivo = new JButton("Abrir Archivo");
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

		// p = new JPanel();
		// p.add(new JLabel("Foto"));
		// p.add(foto);
		// abrirArchivo.addActionListener(this);
		// p.add(abrirArchivo);
		// panelCentral.add(p);

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
		pack();
		setSize(getPreferredSize());
		setVisible(true);

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		try {
			if (e.getSource() == abrirArchivo)
				handleAbrirArchivo();

			else if (e.getSource() == registrar) {
				handleRegistrar();
			}

		} catch (NullPointerException e1) {
			System.out.println("NULL POINTER EXCEPTION");
			return;
		} catch (RemoteException e1) {
			System.out.println("REMOTE EXCEPTION");
			e1.printStackTrace();
		}

	}

	private void handleRegistrar() throws RemoteException {
		if (!contraseña.getText().equals(repContraseña.getText())) {
			return;
		}
		if (sesion.existeUsuario(nombreUsuario.getText())) {
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

	private void handleAbrirArchivo() {
		// archivo = foto.getSelectedFile();
	}
}
