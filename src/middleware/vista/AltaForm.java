package middleware.vista;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.rmi.RemoteException;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
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
	private JFileChooser foto;
	private ButtonGroup publico;

	private ManagerDeSesion sesion;
	private Menu menu;

	private JButton registrar;
	private JButton abrirArchivo;

	private File archivo;

	private JFrame owner;

	public AltaForm(JFrame owner, ManagerDeSesion sesion, Menu menu) {
		super(owner, "Registro", true);
		this.owner = owner;
		this.menu = menu;
		this.sesion = sesion;
		registrar = new JButton("Registrarse");

		nombreUsuario = new JTextField("nombre de usuario", MAX_LENGHT);
		nombre = new JTextField("Nombre", MAX_LENGHT);
		apellido = new JTextField("Apellido", MAX_LENGHT);
		contraseña = new JPasswordField("Contraseña", MAX_LENGHT);
		repContraseña = new JPasswordField("Repita contraseña", MAX_LENGHT);
		direccionWeb = new JTextField("Direccion Web", MAX_LENGHT);
		foto = new JFileChooser();
		abrirArchivo = new JButton("Abrir Archivo");
		publico = new ButtonGroup();
		JRadioButton perfilPub = new JRadioButton("publico");
		JRadioButton perfilNoPub = new JRadioButton("no publico");
		publico.add(perfilPub);
		publico.add(perfilNoPub);

		JPanel p = new JPanel();
		p.add(nombreUsuario);
		p.add(nombre);
		p.add(apellido);
		p.add(contraseña);
		p.add(repContraseña);
		p.add(direccionWeb);
		p.add(foto);
		abrirArchivo.addActionListener(this);
		p.add(abrirArchivo);
		p.add(perfilPub);
		p.add(perfilNoPub);
		registrar.addActionListener(this);
		p.add(registrar);
		add(p);
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
			return;
		} catch (RemoteException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

	}

	private void handleRegistrar() throws RemoteException {
		if (!contraseña.getPassword().equals(repContraseña.getPassword()))
			return;

		if (sesion.existeUsuario(nombreUsuario.getSelectedText()))
			return;

		boolean isPublico = publico.getSelection().getActionCommand() == "publico";

		sesion.registrarse(nombreUsuario.getText(), contraseña.getPassword()
				.toString(), nombre.getText(), apellido.getText(), direccionWeb
				.getText(), null, isPublico);
		ManagerDeUsuario usu = sesion.conectarse(nombreUsuario.getText(),
				contraseña.getPassword().toString());
		this.menu.setUsuario(usu);
		setVisible(false);
		dispose();

	}

	private void handleAbrirArchivo() {
		archivo = foto.getSelectedFile();
	}
}
