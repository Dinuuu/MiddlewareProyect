package middleware.vista;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ContainerEvent;
import java.awt.event.ContainerListener;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import middleware.modelo.Solicitud;
import middleware.rmi.interfaces.ManagerDeUsuario;

public class MisSolicitudes extends JPanel implements ActionListener,
		ContainerListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ManagerDeUsuario usu;
	private List<JButton> aceptar = new ArrayList<JButton>();
	private List<JButton> declinar = new ArrayList<JButton>();
	private List<Solicitud> solicitudes;
	private List<JPanel> paneles = new ArrayList<JPanel>();

	public MisSolicitudes(ManagerDeUsuario usu) throws RemoteException {
		super();
		this.usu = usu;
		solicitudes = usu.getSolicitudes();
		JPanel panelCentral = new JPanel(new GridLayout(0, 1));

		JPanel p;
		for (Solicitud s : solicitudes) {
			p = new JPanel();
			p.add(new JLabel("El usuario " + s.getUsu().getNombreUsuario()
					+ " desea ser su amigo"));
			JButton botonAceptar = new JButton("Aceptar");
			JButton botonDeclinar = new JButton("Declinar");
			botonAceptar.addActionListener(this);
			botonDeclinar.addActionListener(this);
			aceptar.add(botonAceptar);
			declinar.add(botonDeclinar);
			p.add(botonAceptar);
			p.add(botonDeclinar);
			p.setVisible(true);
			panelCentral.add(p);
			paneles.add(p);
		}
		add(panelCentral);
		setSize(getPreferredSize());
		setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		for (int i = 0; i < aceptar.size(); i++) {
			if (e.getSource() == aceptar.get(i)) {
				handleAceptar(i);
				remove(aceptar.get(i));
				remove(declinar.get(i));

				return;
			}
			if (e.getSource() == declinar.get(i)) {
				handleDeclinar(i);
				remove(aceptar.get(i));
				remove(declinar.get(i));

				return;
			}
		}

	}

	private void handleDeclinar(int i) {
		Solicitud solici = solicitudes.get(i);
		JPanel panel = paneles.get(i);
		try {
			usu.declinarSolicitud(solici);
			panel.setVisible(false);
			panel.setEnabled(false);
			remove(panel);
		} catch (RemoteException e) {
			// TODO Auto-generated catch blockU
			e.printStackTrace();
		}

	}

	private void handleAceptar(int i) {
		Solicitud solici = solicitudes.get(i);
		JPanel panel = paneles.get(i);
		try {
			usu.aceptarSolicitud(solici);
			panel.setVisible(false);
			panel.setEnabled(false);
			remove(panel);
		} catch (RemoteException e) {

		}

	}

	@Override
	public void componentAdded(ContainerEvent e) {
		repaint();

	}

	@Override
	public void componentRemoved(ContainerEvent e) {
		repaint();
	}
}
