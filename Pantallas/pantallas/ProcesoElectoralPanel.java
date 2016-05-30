package pantallas;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.JSpinner;
import javax.swing.JButton;

import businessModel.Dao.MySQLDAOProceso;

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
		
		MySQLDAOProceso pr = new MySQLDAOProceso();
	  	System.out.println("xd");
    	List<String> tipoProcesos = pr.getProcesos();
    	
    	// add elements to comboBox
    	for (int i = 0; i<tipoProcesos.size(); i++) comboBox.addItem(tipoProcesos.get(i));    		
  

		btnGuardar.addActionListener(new ActionListener() { 
		    public void actionPerformed(ActionEvent e) { 
		    	String nombreProceso = textField.getText();
		    	String tipoProceso = comboBox.getSelectedItem().toString();
		    	int porcentaje = (int) spinner.getValue();
		    	//agregar a la base de datos
		    } 
		});

	}
}
