package pantallas;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.border.TitledBorder;
import javax.swing.table.AbstractTableModel;

import models.PartidoPolitico;
import models.TipoProceso;
import pantallas.PartidoPoliticoPanel.MyTableModel;
import bModel.ProcessManager;
import businessModel.Dao.MySQLDAOTipoProceso;

public class TipoProcesoPanel extends JPanel {
	private JTextField textField;
	private JTextField textField_1;
	private JTable tblPartPol;
	private int id;
	MyTableModel partPolModel;

	/**
	 * Create the panel.
	 */
	public TipoProcesoPanel() {
		setLayout(null);
		

		JLabel lblDescripcion = new JLabel("Descripcion");
		lblDescripcion.setBounds(38, 47, 72, 14);
		add(lblDescripcion);
		
		JLabel lblPorcentaje = new JLabel("Porcentaje");
		lblPorcentaje.setBounds(38, 97, 62, 14);
		add(lblPorcentaje);
		
		
		textField_1 = new JTextField();
		textField_1.setBounds(131, 44, 147, 20);
		add(textField_1);
		textField_1.setColumns(10);

		final JSpinner spinner = new JSpinner();
		spinner.setBounds(131, 94 , 64, 20);
		add(spinner);

		
		JButton btnGuardar = new JButton("Guardar");
		btnGuardar.setBounds(24, 145, 89, 23);
		add(btnGuardar);

		JButton btnModificar = new JButton("Modificar");
		btnModificar.setBounds(154, 145, 89, 23);
		add(btnModificar);
		
		JButton btnEliminar = new JButton("Eliminar");
		btnEliminar.setBounds(284, 145, 89, 23);
		add(btnEliminar);
		
		btnGuardar.addActionListener(new ActionListener() { 
		    public void actionPerformed(ActionEvent e) { 
		    	String descripcion = textField_1.getText();
		    	int porcentaje = (int) spinner.getValue();	
		    	MySQLDAOTipoProceso pr = new MySQLDAOTipoProceso();
		    	pr.add(descripcion, porcentaje);
		    	refreshTblProducts();
		    	//almacenar base de datos
		    } 
		});
		
		btnModificar.addActionListener(new ActionListener() { 
		    public void actionPerformed(ActionEvent e) { 
		    	String descripcion = textField_1.getText();
		    	int porcentaje = (int) spinner.getValue();	
		    	MySQLDAOTipoProceso pr = new MySQLDAOTipoProceso();
		    	pr.modificar(descripcion, porcentaje, id);
		    	refreshTblProducts();
		    } 
		});
		
		btnEliminar.addActionListener(new ActionListener() { 
		    public void actionPerformed(ActionEvent e) { 
		    	String descripcion = textField_1.getText();
		    	int porcentaje = (int) spinner.getValue();	
		    	MySQLDAOTipoProceso pr = new MySQLDAOTipoProceso();
		    	pr.eliminar(id);
		    	refreshTblProducts();
		    } 
		});


		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(null, "Datos de Tipo de Procesos", TitledBorder.LEADING, TitledBorder.TOP,
				null, null));
		panel.setBounds(45, 195, 327, 198);
		add(panel);
		panel.setLayout(new GridLayout(1, 1, 0, 0));

		JScrollPane scrollPane = new JScrollPane();
		panel.add(scrollPane);

		tblPartPol = new JTable();
		scrollPane.setViewportView(tblPartPol);

		partPolModel = new MyTableModel();
		tblPartPol.setModel(partPolModel);

		tblPartPol.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {	
				int selRow = tblPartPol.getSelectedRow();
			    id = Integer.parseInt(tblPartPol.getValueAt(selRow, 0).toString());				
				String descripcion = tblPartPol.getValueAt(selRow, 1).toString();
				String rep = tblPartPol.getValueAt(selRow, 2).toString();
				int foo = Integer.parseInt(rep);
				textField_1.setText(descripcion);
				spinner.setValue(foo);
			}
		});
		
	}
	

	class MyTableModel extends AbstractTableModel {
		ArrayList<TipoProceso> partPolLst = MySQLDAOTipoProceso.queryAllPartPol();
		String[] titles = { "Código", "Descripción", "Porcentaje" };
		
		
		
		@Override
		public int getColumnCount() {
			// TODO Auto-generated method stub
			return 3;
		}

		@Override
		public int getRowCount() {
			// TODO Auto-generated method stub
			return partPolLst.size();
		}

		@Override
		public Object getValueAt(int row, int col) {
			String value = "";
			switch (col) {
			case 0:
				value = "" + partPolLst.get(row).getId();
				break;
			case 1:
				value = partPolLst.get(row).getDescripcion();

				break;
			case 2:
				value = "" + partPolLst.get(row).getPorcentaje();
				break;

			}
			return value;
		}

		public String getColumnName(int col) {
			return titles[col];
		}
	}

	public void refreshTblProducts() {
		partPolModel.partPolLst = MySQLDAOTipoProceso.queryAllPartPol();
		partPolModel.fireTableChanged(null);
	}

}
