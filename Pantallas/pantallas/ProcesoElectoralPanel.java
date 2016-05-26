package pantallas;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.JSpinner;
import javax.swing.JButton;

public class ProcesoElectoralPanel extends JPanel {
	private JTextField textField;

	/**
	 * Create the panel.
	 */
	public ProcesoElectoralPanel() {
		setLayout(null);
		
		JLabel lblNombre = new JLabel("Nombre");
		lblNombre.setBounds(45, 46, 46, 14);
		add(lblNombre);
		
		JLabel lblTipoProceso = new JLabel("Tipo Proceso");
		lblTipoProceso.setBounds(45, 89, 75, 14);
		add(lblTipoProceso);
		
		textField = new JTextField();
		textField.setBounds(142, 43, 172, 20);
		add(textField);
		textField.setColumns(10);
		
		JComboBox comboBox = new JComboBox();
		comboBox.setBounds(145, 86, 172, 20);
		add(comboBox);
		
		JLabel lblPorcentaje = new JLabel("Porcentaje");
		lblPorcentaje.setBounds(45, 147, 64, 14);
		add(lblPorcentaje);
		
		JSpinner spinner = new JSpinner();
		spinner.setBounds(142, 144, 64, 20);
		add(spinner);
		
		JButton btnGuardar = new JButton("Guardar");
		btnGuardar.setBounds(75, 208, 89, 23);
		add(btnGuardar);
		
		JButton btnCancelar = new JButton("Cancelar");
		btnCancelar.setBounds(202, 208, 89, 23);
		add(btnCancelar);

	}
}
