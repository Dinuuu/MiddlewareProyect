package middleware.vista;

import java.awt.CardLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.rmi.RemoteException;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
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
	private JMenuItem editarContraseña;
	private JMenuItem cambiarFoto;
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
		editarInformacion = new JMenuItem("Editar Informacion ...");
		editarInformacion.addActionListener(this);
		editarInformacion.setEnabled(false);

		cambiarFoto = new JMenuItem("Cambiar Foto ...");
		cambiarFoto.addActionListener(this);
		cambiarFoto.setEnabled(false);

		editarContraseña = new JMenuItem("Editar Contraseña ...");
		editarContraseña.addActionListener(this);
		editarContraseña.setEnabled(false);

		ir = new JMenuItem("Ir a mi perfil");
		ir.addActionListener(this);
		ir.setEnabled(false);

		perfil.add(editarInformacion);
		perfil.add(cambiarFoto);
		perfil.add(editarContraseña);
		perfil.addSeparator();
		perfil.add(ir);

		JMenu ultimasNoticias = new JMenu("Ultimas Noticias");
		irAUltimas = new JMenuItem("ir");
		irAUltimas.setEnabled(false);
		irAUltimas.addActionListener(this);

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
			else if (source == editarContraseña)
				handleEdicionContraseña();
			else if (source == cambiarFoto)
				handleCambiarFoto();
			else
				System.out.println("ME OLVIDE DE ALGUNO");
		} catch (RemoteException e1) {
			new ErrorDialog(parent,
					"Se ha producido un error, intentelo nuevamente en unos minutos");

		} catch (IOException e1) {
			new ErrorDialog(parent, "Todos los campos son obligatorios");
		}
	}

	private void handleCambiarFoto() throws IOException {

		File currentDir = new File(System.getProperty("user.dir"));
		JFileChooser fc = new JFileChooser(currentDir);
		fc.setMultiSelectionEnabled(false);
		ImgFilter filtro = new ImgFilter();
		fc.setFileFilter(filtro);

		if (fc.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
			File f = fc.getSelectedFile();
			if (f != null) {

				parent.getUsu().cambiarFoto(f);
				Perfil perfil = (Perfil) parent.getCards().get("PERFIL");
				perfil.modificar(usuarioConectado);

			}

		}
	}

	private void handleEdicionContraseña() {

		new CambioContraseñaForm(parent, usuarioConectado);
	}

	private void handleEnviarSolicitud() {
		new EnviarSolicitudDialog(parent, sesion, usuarioConectado);
	}

	private void handleBusqueda() throws IOException {
		ManagerDeUsuario usu = buscador.buscar(nombreUsuario.getText());

		if (usu != null) {
			Perfil perfilBusqueda = new Perfil(parent, usu);

			parent.getContentPane().add(perfilBusqueda, "BUSQUEDA");
			CardLayout cl = (CardLayout) parent.getContentPane().getLayout();
			cl.show(parent.getContentPane(), "BUSQUEDA");
			parent.getCards().remove("BUSQUEDA");
			parent.getCards().put("BUSQUEDA", perfilBusqueda);
		} else
			new ErrorDialog(parent, "El usuario seleccionado no existe");

	}

	private void handleUltimasNoticias() throws IOException {
		Perfil perfil = (Perfil) parent.getCards().get("PERFIL");

		UltimasNoticias ultimasNoticias = new UltimasNoticias(parent);

		parent.getContentPane().add(ultimasNoticias, "ULTIMASNOTICIAS");
		CardLayout cl = (CardLayout) parent.getContentPane().getLayout();
		cl.show(parent.getContentPane(), "ULTIMASNOTICIAS");
		parent.getCards().put("ULTIMASNOTICIAS", ultimasNoticias);

	}

	private void handleSolicitudes() throws RemoteException {

		MisSolicitudes solici = new MisSolicitudes(usuarioConectado);

		parent.getContentPane().add(solici, "MISSOLICITUDES");
		CardLayout cl = (CardLayout) parent.getContentPane().getLayout();
		cl.show(parent.getContentPane(), "MISSOLICITUDES");
		parent.getCards().put("MISSOLICITUDES", solici);

	}

	private void handlePerfil() throws RemoteException {

		CardLayout cl = (CardLayout) parent.getContentPane().getLayout();
		cl.show(parent.getContentPane(), "PERFIL");
		validate();

	}

	private void handleEdicionInformacion() throws IOException {

		new ModificacionForm(parent, usuarioConectado, sesion);
		Perfil perfil = (Perfil) parent.getCards().get("PERFIL");
		perfil.modificar(usuarioConectado);
		UltimasNoticias ultimas = (UltimasNoticias) parent.getCards().get(
				"ULTIMASNOTICIAS");
		ultimas.modificar(usuarioConectado);

	}

	private void handleSalir() throws RemoteException {

		if (usuarioConectado != null)
			sesion.desconectarse(usuarioConectado);
		System.exit(0);

	}

	private void handleDesconeccion() throws RemoteException {

		CardLayout cl = (CardLayout) parent.getContentPane().getLayout();
		cl.show(parent.getContentPane(), "DESCONECTADO");

		validate();
		sesion.desconectarse(usuarioConectado);
		setUsuario(null);
		parent.setUsu(null);
		changeButtonStatus();

	}

	private void handleConeccion() throws IOException {
		new ConeccionForm(parent, sesion, this);
		if (usuarioConectado != null) {
			JPanel perfil = new Perfil(parent, usuarioConectado);

			parent.getContentPane().add(perfil, "PERFIL");
			CardLayout cl = (CardLayout) parent.getContentPane().getLayout();
			cl.show(parent.getContentPane(), "PERFIL");
			parent.getCards().put("PERFIL", perfil);

			JPanel desconeccion = new JPanel();
			parent.getContentPane().add(desconeccion, "DESCONECTADO");
			parent.getCards().put("DESCONECTADO", desconeccion);
		}

	}

	private void handleRegistro() throws IOException {
		new AltaForm(parent, sesion, this);
		if (usuarioConectado != null) {

			JPanel perfil = new Perfil(parent, usuarioConectado);

			parent.getContentPane().add(perfil, "PERFIL");
			CardLayout cl = (CardLayout) parent.getContentPane().getLayout();
			cl.show(parent.getContentPane(), "PERFIL");
			parent.getCards().put("PERFIL", perfil);

			JPanel desconeccion = new JPanel();
			parent.getContentPane().add(desconeccion, "DESCONECTADO");
			parent.getCards().put("DESCONECTADO", desconeccion);
		}
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
		editarContraseña.setEnabled(!editarContraseña.isEnabled());
		cambiarFoto.setEnabled(!cambiarFoto.isEnabled());
	}
}
