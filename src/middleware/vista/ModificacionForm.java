package middleware.vista;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.rmi.RemoteException;

import javax.swing.ButtonGroup;
import javax.swing.ButtonModel;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import middleware.rmi.interfaces.ManagerDeSesion;
import middleware.rmi.interfaces.ManagerDeUsuario;

public class ModificacionForm extends JPanel implements ActionListener {

	private static final long serialVersionUID = 1L;

	private JTextField nombreUsuario;
	private JTextField nombre;
	private JTextField apellido;

	private JTextField direccionWeb;
	private JFileChooser foto;
	private ButtonGroup publico;

	private ManagerDeSesion sesion;
	private ManagerDeUsuario usu;

	private JButton registrar;
	private JButton abrirArchivo;

	File archivo;

	public ModificacionForm(ManagerDeSesion sesion, ManagerDeUsuario usu)
			throws RemoteException {

		this.sesion = sesion;
		this.usu = usu;
		registrar = new JButton("Modificar Informacion");

		nombreUsuario = new JTextField(usu.getNombreUsuario());
		nombre = new JTextField(usu.getNombre());
		apellido = new JTextField(usu.getApellido());
		direccionWeb = new JTextField(usu.getDireccionWeb());
		foto = new JFileChooser();
		abrirArchivo = new JButton("Abrir Archivo");
		publico = new ButtonGroup();
		JRadioButton perfilPub = new JRadioButton("publico");
		JRadioButton perfilNoPub = new JRadioButton("no publico");
		publico.add(perfilPub);
		publico.add(perfilNoPub);
		JRadioButton selected;
		if (usu.isPublico())
			selected = perfilPub;
		else
			selected = perfilNoPub;
		publico.setSelected((ButtonModel) selected, true);
		this.add(nombreUsuario);
		this.add(nombre);
		this.add(apellido);

		this.add(direccionWeb);
		this.add(foto);
		abrirArchivo.addActionListener(this);
		this.add(abrirArchivo);
		this.add(perfilPub);
		this.add(perfilNoPub);

		registrar.addActionListener(this);
		this.add(registrar);

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		try {
			if (e.getSource() == abrirArchivo)
				archivo = foto.getSelectedFile();

			else if (e.getSource() == registrar) {

				if (!nombreUsuario.getText().equals(usu.getNombreUsuario())
						&& sesion.existeUsuario(nombreUsuario.getText()))
					return;

				boolean isPublico = publico.getSelection().getActionCommand() == "publico";
				usu.cambiarDatos(nombre.getText(), apellido.getText(),
						direccionWeb.getText(), null, isPublico,
						nombreUsuario.getText(), sesion);
				return;
			}
		} catch (NullPointerException e2) {
			return;

		} catch (RemoteException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

	}
}
