package middleware.vista;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.rmi.RemoteException;

import javax.swing.JButton;
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
	private JMenuItem misSolicitudes;
	private JMenuItem enviarSolicitud;
	private JMenuItem irAUltimas;
	private JButton buscar;
	private JTextField nombreUsuario;
	private App parent;

	public Menu(App parent, Buscador buscador, ManagerDeSesion sesion,
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
		desconectarse.setEnabled(false);

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
		editarInformacion.setEnabled(false);

		ir = new JMenuItem("Ir a mi perfil");
		ir.addActionListener(this);
		ir.setEnabled(false);

		perfil.add(editarInformacion);
		perfil.addSeparator();
		perfil.add(ir);

		JMenu ultimasNoticias = new JMenu("Ultimas Noticias");
		irAUltimas = new JMenuItem("ir");
		irAUltimas.setEnabled(false);

		ultimasNoticias.add(irAUltimas);

		JMenu solicitudes = new JMenu("Solicitudes");
		misSolicitudes = new JMenuItem("Ver mis Solicitudes");
		misSolicitudes.addActionListener(this);
		misSolicitudes.setEnabled(false);

		enviarSolicitud = new JMenuItem("Enviar Solicitud...");
		enviarSolicitud.addActionListener(this);
		enviarSolicitud.setEnabled(false);

		solicitudes.add(misSolicitudes);
		solicitudes.add(enviarSolicitud);

		nombreUsuario = new JTextField();
		buscar = new JButton("Buscar");
		buscar.addActionListener(this);

		add(file);
		add(perfil);
		add(ultimasNoticias);
		add(solicitudes);
		add(nombreUsuario);
		add(buscar);

	}

	void setUsuario(ManagerDeUsuario usu) {
		this.usuarioConectado = usu;
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		try {
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
			else if (source == misSolicitudes)
				handleSolicitudes();
			else if (source == irAUltimas)
				handleUltimasNoticias();
			else if (source == buscar)
				handleBusqueda();
			else if (source == enviarSolicitud)
				handleEnviarSolicitud();
			else
				System.out.println("ME OLVIDE DE ALGUNO");
		} catch (RemoteException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

	private void handleEnviarSolicitud() {
		new EnviarSolicitudDialog(parent, sesion, usuarioConectado);
	}

	private void handleBusqueda() throws RemoteException {
		ManagerDeUsuario usu = buscador.buscar(nombreUsuario.getText());
		new Perfil(parent, usu);

	}

	private void handleUltimasNoticias() {
		// TODO Auto-generated method stub

	}

	private void handleSolicitudes() throws RemoteException {

		parent.getContentPane().add(new MisSolicitudes(usuarioConectado));

	}

	private void handlePerfil() throws RemoteException {

		new Perfil(parent, usuarioConectado);

	}

	private void handleEdicionInformacion() throws RemoteException {

		new ModificacionForm(parent, usuarioConectado, sesion);

	}

	private void handleSalir() throws RemoteException {

		sesion.desconectarse(usuarioConectado);
		System.exit(0);

	}

	private void handleDesconeccion() throws RemoteException {

		sesion.desconectarse(usuarioConectado);
		setUsuario(null);
		parent.setUsu(null);
		changeButtonStatus();

	}

	private void handleConeccion() {
		new ConeccionForm(parent, sesion, this);
	}

	private void handleRegistro() {
		new AltaForm(parent, sesion, this);
	}

	void changeButtonStatus() {
		registrarse.setEnabled(!registrarse.isEnabled());
		conectarse.setEnabled(!conectarse.isEnabled());
		desconectarse.setEnabled(!desconectarse.isEnabled());
		editarInformacion.setEnabled(!editarInformacion.isEnabled());
		ir.setEnabled(!ir.isEnabled());
		misSolicitudes.setEnabled(!misSolicitudes.isEnabled());
		enviarSolicitud.setEnabled(!enviarSolicitud.isEnabled());
		irAUltimas.setEnabled(!irAUltimas.isEnabled());
	}
}
