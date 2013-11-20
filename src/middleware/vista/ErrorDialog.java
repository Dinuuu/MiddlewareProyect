package middleware.vista;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class ErrorDialog extends JDialog implements ActionListener {

	private static final long serialVersionUID = 1L;
	private JButton aceptar;

	public ErrorDialog(App parent, String error) {
		super(parent, "Error", true);
		JPanel panel = new JPanel();
		aceptar = new JButton("aceptar");
		aceptar.addActionListener(this);
		panel.add(new JLabel(error));
		panel.add(aceptar);
		add(panel);
		setPreferredSize(new Dimension(600, 150));
		setSize(getPreferredSize());
		setVisible(true);

	}

	@Override
	public void actionPerformed(ActionEvent arg0) {

		setVisible(false);
		dispose();

	}

}
