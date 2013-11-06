package middleware.vista;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.rmi.RemoteException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JTextField;

import middleware.rmi.interfaces.Buscador;
import middleware.rmi.interfaces.ManagerDeSesion;
import middleware.rmi.interfaces.ManagerDeUsuario;

public class Menu extends JMenuBar implements ActionListener {

	private static final long serialVersionUID = 1L;
	private Buscador buscador;
	private ManagerDeSesion sesion;
	private ManagerDeUsuario usuarioConectado;
	private JMenuItem registrarse;
	private JMenuItem conectarse;
	private JMenuItem desconectarse;
	private JMenuItem salir;
	private JMenuItem editarInformacion;
	private JMenuItem ir;
	private JMenuItem solicitudes;
	private JMenuItem irAUltimas;
	private JButton buscar;

	private JFrame parent;

	public Menu(JFrame parent, Buscador buscador, ManagerDeSesion sesion,
			ManagerDeUsuario usuario) {
		super();
		this.parent = parent;
		this.buscador = buscador;
		this.sesion = sesion;
		this.usuarioConectado = usuario;

		JMenu file = new JMenu("File");

		registrarse = new JMenuItem("Registrarse...");
		registrarse.addActionListener(this);

		conectarse = new JMenuItem("Conectarse...");
		conectarse.addActionListener(this);

		desconectarse = new JMenuItem("Desconectarse");
		desconectarse.addActionListener(this);

		salir = new JMenuItem("Salir");
		salir.setMnemonic(KeyEvent.VK_E);
		salir.setToolTipText("Exit application");
		salir.addActionListener(this);

		file.add(registrarse);
		file.addSeparator();
		file.add(conectarse);
		file.add(desconectarse);
		file.addSeparator();
		file.add(salir);

		JMenu perfil = new JMenu("Perfil");
		editarInformacion = new JMenuItem("Editar Informacion...");
		editarInformacion.addActionListener(this);
		ir = new JMenuItem("Ir a mi perfil");
		ir.addActionListener(this);

		solicitudes = new JMenuItem("Ver mis Solicitudes");
		solicitudes.addActionListener(this);

		perfil.add(editarInformacion);
		perfil.addSeparator();
		perfil.add(ir);

		perfil.add(solicitudes);

		JMenu ultimasNoticias = new JMenu("Ultimas Noticias");
		irAUltimas = new JMenuItem("ir");

		ultimasNoticias.add(irAUltimas);

		JTextField nombreUsuario = new JTextField("Buscar");
		buscar = new JButton("Buscar");
		buscar.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// getBuscador().buscar(nombreUsuario.getText());
				// TODO VER COMO HACER CON ESTO
			}
		});

		add(file);
		add(perfil);
		add(ultimasNoticias);
		add(nombreUsuario);
		add(buscar);

	}

	void setUsuario(ManagerDeUsuario usu) {
		this.usuarioConectado = usu;
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		Object source = e.getSource();

		if (source == registrarse)
			handleRegistro();
		else if (source == conectarse)
			handleConeccion();
		else if (source == desconectarse)
			handleDesconeccion();
		else if (source == salir)
			handleSalir();
		else if (source == editarInformacion)
			handleEdicionInformacion();
		else if (source == ir)
			handlePerfil();
		else if (source == solicitudes)
			handleSolicitudes();
		else if (source == irAUltimas)
			handleUltimasNoticias();
		else if (source == buscar)
			handleBusqueda();
		else
			System.out.println("ME OLVIDE DE ALGUNO");
	}

	private void handleBusqueda() {
		// TODO Auto-generated method stub

	}

	private void handleUltimasNoticias() {
		// TODO Auto-generated method stub

	}

	private void handleSolicitudes() {
		// TODO Auto-generated method stub

	}

	private void handlePerfil() {
		// TODO Auto-generated method stub

	}

	private void handleEdicionInformacion() {
		// TODO Auto-generated method stub

	}

	private void handleSalir() {
		System.exit(0);
	}

	private void handleDesconeccion() {
		try {
			sesion.desconectarse(usuarioConectado);
			setUsuario(null);
		} catch (RemoteException e1) {
			e1.printStackTrace();
		}

	}

	private void handleConeccion() {
		// TODO Auto-generated method stub

	}

	private void handleRegistro() {
		new AltaForm(parent, sesion, this);
	}
}
