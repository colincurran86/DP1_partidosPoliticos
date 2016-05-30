package pantallas;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;
import javax.swing.table.AbstractTableModel;
import javax.swing.JComboBox;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.JButton;

import businessModel.Dao.MySQLDAOProceso;
import models.PartidoPolitico;
import pantallas.PartidoPoliticoPanel.MyTableModel;

import com.toedter.calendar.JDateChooser;

import bModel.ProcessManager;

public class ProcesoElectoralPanel extends JPanel implements ActionListener{
	private JTextField textField;
	private JTable tblProcesos;
	private JSpinner spinner;
	private JDateChooser dateIni;
	private JDateChooser dateFin;
	private JTable tblProc;
	MyTableModel procModel;
	private int idRow;
	private JComboBox comboBox;
	private JButton btnAgregar;
	private JButton btnModificar;
	private JButton btnEliminar;
	

	/**
	 * Create the panel.
	 */
	public ProcesoElectoralPanel() {
		setLayout(null);
		
		JLabel lblNombre = new JLabel("Nombre");
		lblNombre.setBounds(45, 46, 46, 14);
		add(lblNombre);
		
		JLabel lblTipoProceso = new JLabel("Tipo Proceso");
		lblTipoProceso.setBounds(45, 78, 75, 14);
		add(lblTipoProceso);
		
		textField = new JTextField();
		textField.setBounds(142, 43, 172, 20);
		add(textField);
		textField.setColumns(10);
		
		comboBox = new JComboBox();
		comboBox.setBounds(142, 75, 172, 20);
		add(comboBox);
		
		JLabel lblPorcentaje = new JLabel("Porcentaje");
		lblPorcentaje.setBounds(45, 114, 64, 14);
		add(lblPorcentaje);
		
		spinner = new JSpinner();
		spinner.setBounds(142, 111, 64, 20);
		add(spinner);
		
		btnAgregar = new JButton("Agregar");
		btnAgregar.setBounds(43, 211, 89, 23);
		add(btnAgregar);
		
		btnModificar = new JButton("Modificar");
		btnModificar.setBounds(152, 211, 89, 23);
		add(btnModificar);
		
		dateIni = new JDateChooser();
		dateIni.setBounds(142, 142, 87, 20);
		add(dateIni);
		
		JLabel lblFechaInicial = new JLabel("Fecha Inicial");
		lblFechaInicial.setBounds(45, 149, 75, 14);
		add(lblFechaInicial);
		
		JLabel lblFechaFin = new JLabel("Fecha Fin");
		lblFechaFin.setBounds(45, 178, 64, 14);
		add(lblFechaFin);
		
		JDateChooser dateFin = new JDateChooser();
		dateFin.setBounds(142, 173, 87, 20);
		add(dateFin);
		
		btnEliminar = new JButton("Eliminar");
		btnEliminar.setBounds(265, 211, 89, 23);
		add(btnEliminar);
		
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(null, "Datos de Procesos", TitledBorder.LEADING, TitledBorder.TOP,
				null, null));
		panel.setBounds(31, 258, 327, 198);
		add(panel);
		panel.setLayout(new GridLayout(1, 1, 0, 0));

		JScrollPane scrollPane = new JScrollPane();
		panel.add(scrollPane);
		
		tblProc = new JTable();
		scrollPane.setViewportView(tblProc);

		procModel = new MyTableModel();
		tblProc.setModel(procModel);
		
		
		/*MySQLDAOProceso pr = new MySQLDAOProceso();	  	
    	List<String> tipoProcesos = pr.getProcesos();
    	
    	// add elements to comboBox
    	for (int i = 0; i<tipoProcesos.size(); i++) comboBox.addItem(tipoProcesos.get(i));*/    		
  
    	btnAgregar.addActionListener(this);
    	btnModificar.addActionListener(this);
    	btnEliminar.addActionListener(this);
    	
		/*btnAgregar.addActionListener(new ActionListener() { 
		    public void actionPerformed(ActionEvent e) { 
		    	String nombreProceso = textField.getText();
		    	String tipoProceso = comboBox.getSelectedItem().toString();
		    	int porcentaje = (int) spinner.getValue();
		    	//agregar a la base de datos
		    } 
		});*/

	}
	class MyTableModel extends AbstractTableModel {
		//ArrayList<PartidoPolitico> partPolLst = ProcessManager.queryAllPartPol();
		String[] titles = { "Código", "Nombre", "Tipo de Proc.", " % ", "Inicio", "Fin" };
		
		@Override
		public int getColumnCount() {
			// TODO Auto-generated method stub
			return 6;
		}

		@Override
		public int getRowCount() {
			// TODO Auto-generated method stub
			//return partPolLst.size();
			return 0;
		}

		@Override
		public Object getValueAt(int arg0, int arg1) {
			// TODO Auto-generated method stub
			/*String value = "";
			switch (col) {
			case 0:
				value = "" + partPolLst.get(row).getId();
				break;
			case 1:
				value = partPolLst.get(row).getNombre();
				break;
			case 2:
				value = "" + partPolLst.get(row).getNombreRep();
				break;
			case 3:
				value = "" + partPolLst.get(row).getTelefono();
				break;
			case 4:
				value = "" + partPolLst.get(row).getCorreo();
				break;
			}
			return value;*/
			return null;
		}
		
		public String getColumnName(int col) {
			return titles[col];
		}
		
	}
	
	public void refreshTblProc() {
		//procModel.partPolLst = ProcessManager.queryAllPartPol();
		//procModel.fireTableChanged(null);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource() == btnAgregar){	
			
		}
		if(e.getSource() == btnModificar){	
			
		}
		if(e.getSource() == btnEliminar){	
			
		}
	}
}
