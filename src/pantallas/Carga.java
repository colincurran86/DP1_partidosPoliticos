package pantallas;

import javax.swing.JPanel;
import javax.swing.JButton;

public class Carga extends JPanel {

	/**
	 * Create the panel.
	 */
	public Carga() {
		setLayout(null);
		
		JButton btnPrimeraCarga = new JButton("Primera fase");
		btnPrimeraCarga.setBounds(162, 44, 120, 23);
		add(btnPrimeraCarga);
		
		JButton btnSegundaCarga = new JButton("Segunda fase");
		btnSegundaCarga.setBounds(162, 123, 120, 23);
		add(btnSegundaCarga);

	}
}
