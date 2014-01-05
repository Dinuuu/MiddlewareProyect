package middleware.vista;

import java.awt.BorderLayout;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import middleware.rmi.interfaces.ManagerDeUsuario;

public class PanelInfoUsuario extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JLabel nombreUsuario;
	private JLabel nombreCompleto;
	private JLabel direccionWeb;

	private JLabel imagen;

	public PanelInfoUsuario(ManagerDeUsuario usu) throws IOException {

		super();
		JPanel panelPrincipal = new JPanel(new BorderLayout());
		JPanel panelSecundario = new JPanel(new BorderLayout());
		JPanel panelCentral = new JPanel();
		JPanel panelSuperior = new JPanel();
		JPanel panelInferior = new JPanel(new BorderLayout());
		nombreUsuario = new JLabel(usu.getNombreUsuario());
		panelSuperior.add(nombreUsuario);

		imagen = new JLabel(new ImageIcon(ImageIO.read(usu.getFoto())));

		panelCentral.add(imagen);
		nombreCompleto = new JLabel("Nombre Completo: " + usu.getNombre() + " "
				+ usu.getApellido());
		panelInferior.add(nombreCompleto, BorderLayout.NORTH);
		direccionWeb = new JLabel("Direccion Web: " + usu.getDireccionWeb());
		panelInferior.add(direccionWeb);
		panelSecundario.add(panelSuperior, BorderLayout.NORTH);
		panelSecundario.add(panelCentral, BorderLayout.CENTER);
		panelSecundario.add(panelInferior, BorderLayout.SOUTH);

		panelPrincipal.add(panelSecundario);

		panelPrincipal.add(new ListaAmigosPanel(usu), BorderLayout.SOUTH);

		add(panelPrincipal);

		setSize(getPreferredSize());
		setVisible(true);

	}

	public void modificar(ManagerDeUsuario usu) throws IOException {

		nombreUsuario.setText(usu.getNombreUsuario());
		nombreCompleto.setText(usu.getNombre() + " " + usu.getApellido());
		direccionWeb.setText(usu.getDireccionWeb());
		modificarImagen(usu);
	}

	public void modificarImagen(ManagerDeUsuario usu) throws IOException {
		imagen.setIcon(new ImageIcon(ImageIO.read(usu.getFoto())));
	}

}
