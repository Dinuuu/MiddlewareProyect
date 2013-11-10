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
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import middleware.rmi.interfaces.ManagerDeSesion;
import middleware.rmi.interfaces.ManagerDeUsuario;

public class ModificacionForm extends JDialog implements ActionListener {

	private static final long serialVersionUID = 1L;
	private static int MAX_LENGHT = 40;

	private JTextField nombreUsuario;
	private JTextField nombre;
	private JTextField apellido;
	private JTextField direccionWeb;

	private ButtonGroup publico;
	JRadioButton perfilPub;
	JRadioButton perfilNoPub;
	private JButton registrar;

	ManagerDeSesion sesion;
	ManagerDeUsuario usu;

	public ModificacionForm(JFrame parent, ManagerDeUsuario usu,
			ManagerDeSesion sesion) throws RemoteException {

		super(parent, "Cambio de Información", true);
		this.sesion = sesion;
		this.usu = usu;
		registrar = new JButton("Modificar Informacion");
		nombreUsuario = new JTextField(usu.getNombreUsuario(), MAX_LENGHT);
		nombre = new JTextField(usu.getNombre(), MAX_LENGHT);
		apellido = new JTextField(usu.getApellido(), MAX_LENGHT);
		direccionWeb = new JTextField(usu.getDireccionWeb(), MAX_LENGHT);
		publico = new ButtonGroup();
		perfilPub = new JRadioButton("publico", usu.isPublico());
		perfilNoPub = new JRadioButton("no publico", !usu.isPublico());
		publico.add(perfilPub);
		publico.add(perfilNoPub);
		JPanel panelPrincipal = new JPanel();
		JPanel panelCentral = new JPanel();
		JPanel p = new JPanel();
		panelPrincipal.setLayout(new BorderLayout());

		p.add(new JLabel("Nombre de Usuario *"));
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
		p.add(new JLabel("Dirección Web *"));
		p.add(direccionWeb);
		panelCentral.add(p);

		p = new JPanel();
		p.add(new JLabel("Como quiere que sea su perfil"));
		p.add(perfilPub);
		p.add(perfilNoPub);
		panelCentral.add(p);

		p = new JPanel();
		p.add(new JLabel("* Campo obligatorio"));
		panelCentral.add(p);

		p = new JPanel();

		registrar.addActionListener(this);
		p.add(registrar);
		panelCentral.add(p);

		panelPrincipal.add(panelCentral);

		this.add(panelPrincipal);

		pack();
		setSize(getPreferredSize());
		setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		try {
			if (!nombreUsuario.getText().equals(usu.getNombreUsuario())
					&& sesion.existeUsuario(nombreUsuario.getText()))
				return;
			String nombreViejo = usu.getNombreUsuario();
			boolean cambio = usu.cambiarDatos(nombre.getText(),
					apellido.getText(), direccionWeb.getText(),
					perfilPub.isSelected(), nombreUsuario.getText(), sesion);
			if (cambio)
				sesion.cambiarNombreUsuario(nombreViejo,
						nombreUsuario.getText());
			setVisible(false);
			dispose();
			return;

		} catch (NullPointerException e2) {
			return;

		} catch (RemoteException e1) {
			e1.printStackTrace();
		}

	}
}
