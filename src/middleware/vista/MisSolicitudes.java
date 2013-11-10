package middleware.vista;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneLayout;

import middleware.modelo.Solicitud;
import middleware.rmi.interfaces.ManagerDeUsuario;

public class MisSolicitudes extends JPanel implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ManagerDeUsuario usu;
	private List<JButton> aceptar = new ArrayList<JButton>();
	private List<JButton> declinar = new ArrayList<JButton>();
	private List<Solicitud> solicitudes;

	public MisSolicitudes(ManagerDeUsuario usu) throws RemoteException {
		super();
		solicitudes = usu.getSolicitudes();

		setLayout(new BorderLayout());
		JScrollPane panelCentral = new JScrollPane();
		panelCentral.setLayout(new ScrollPaneLayout());

		for (Solicitud s : solicitudes) {
			JPanel p = new JPanel();
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
			panelCentral.add(p);
		}
		panelCentral.add(new JLabel("ALGO"));
		add(panelCentral);
		setSize(getPreferredSize());
		setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		for (int i = 0; i < aceptar.size(); i++) {
			if (e.getSource() == aceptar.get(i)) {
				handleAceptar(i);
				return;
			}
			if (e.getSource() == declinar.get(i)) {
				handleDeclinar(i);
				return;
			}
		}

	}

	private void handleDeclinar(int i) {
		Solicitud solici = solicitudes.get(i);
		try {
			usu.declinarSolicitud(solici);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private void handleAceptar(int i) {
		Solicitud solici = solicitudes.get(i);
		try {
			usu.aceptarSolicitud(solici);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
