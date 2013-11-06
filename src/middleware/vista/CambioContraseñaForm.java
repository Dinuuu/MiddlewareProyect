package middleware.vista;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JPasswordField;

import middleware.rmi.interfaces.ManagerDeUsuario;

public class CambioContraseñaForm extends JPanel implements ActionListener {

	private static final long serialVersionUID = 1L;
	private JPasswordField contraseña;
	private JPasswordField repContraseña;
	private JPasswordField contraseñaVieja;
	private JButton cambiarContraseña;

	private ManagerDeUsuario usu;

	public CambioContraseñaForm(ManagerDeUsuario usu) {

		this.usu = usu;

		contraseña = new JPasswordField("Ingrese Nueva Contraseña");
		repContraseña = new JPasswordField("Repita Nueva Contraseña");
		contraseñaVieja = new JPasswordField("Ingrese Contraseña Actual");
		cambiarContraseña = new JButton("Cambiar Contraseña");
		cambiarContraseña.addActionListener(this);

		this.add(contraseñaVieja);
		this.add(contraseña);
		this.add(repContraseña);
		this.add(cambiarContraseña);

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		try {

			if (!contraseña.getPassword().toString()
					.equals(repContraseña.getPassword().toString()))
				return;

			usu.cambiarContraseña(contraseñaVieja.getPassword().toString(),
					contraseña.getPassword().toString(), repContraseña
							.getPassword().toString());

		} catch (RemoteException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (NullPointerException e2) {
			return;
		}
	}

}
