package pantallas;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JButton;

public class PrimeraFase extends JPanel {

	/**
	 * Create the panel.
	 */
	public PrimeraFase() {
		setLayout(null);
		
		JLabel lblRutaDeLos = new JLabel("Ruta de los planillones");
		lblRutaDeLos.setBounds(90, 177, 126, 14);
		add(lblRutaDeLos);
		
		JLabel lblRutaDeLa = new JLabel("Ruta de la Base de Datos");
		lblRutaDeLa.setBounds(90, 308, 126, 14);
		add(lblRutaDeLa);
		
		JButton btnProcesar = new JButton("Procesar");
		btnProcesar.setBounds(153, 511, 89, 23);
		add(btnProcesar);
		
		JButton btnCancelar = new JButton("Cancelar");
		btnCancelar.setBounds(421, 511, 89, 23);
		add(btnCancelar);

	}
}
