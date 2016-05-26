package pantallas;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;

public class PartidoPoliticoPanel extends JPanel {
	private JTextField textField;
	private JTextField textField_1;

	/**
	 * Create the panel.
	 */
	public PartidoPoliticoPanel() {
		setLayout(null);
		
		JLabel lblNombre = new JLabel("Nombre");
		lblNombre.setBounds(44, 40, 46, 14);
		add(lblNombre);
		
		JLabel lblFechaEntregaPadron = new JLabel("Fecha entrega padron");
		lblFechaEntregaPadron.setBounds(44, 88, 118, 14);
		add(lblFechaEntregaPadron);
		
		textField = new JTextField();
		textField.setBounds(183, 37, 174, 20);
		add(textField);
		textField.setColumns(10);
		
		textField_1 = new JTextField();
		textField_1.setBounds(184, 85, 173, 20);
		add(textField_1);
		textField_1.setColumns(10);
		
		JButton btnGuardar = new JButton("Guardar");
		btnGuardar.setBounds(82, 193, 89, 23);
		add(btnGuardar);
		
		JButton btnCancelar = new JButton("Cancelar");
		btnCancelar.setBounds(229, 193, 89, 23);
		add(btnCancelar);

	}

}
