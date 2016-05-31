package pantallas;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.JButton;
import javax.swing.border.TitledBorder;
import javax.swing.table.AbstractTableModel;
import javax.swing.text.DefaultFormatter;

import models.PartidoPolitico;
import models.TipoProceso;
import pantallas.PartidoPoliticoPanel.MyTableModel;
import bModel.ProcessManager;
import businessModel.Dao.MySQLDAOTipoProceso;

public class TipoProcesoPanel extends JPanel {
	private JTextField textField;
	private JTextField txtFieldDescripcion;
	private JTable tblPartPol;
	private int idRow;
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
		
		
		txtFieldDescripcion = new JTextField();
		txtFieldDescripcion.setBounds(131, 44, 147, 20);
		add(txtFieldDescripcion);
		txtFieldDescripcion.setColumns(10);
		
		
		SpinnerModel sm = new SpinnerNumberModel(0, 0, 100, 1); //default value,lower bound,upper bound,increment by 
		JSpinner spinner = new JSpinner();
		spinner.setBounds(131, 94 , 64, 20);
		JSpinner.NumberEditor jsEditor = (JSpinner.NumberEditor)spinner.getEditor();
		DefaultFormatter formatter = (DefaultFormatter) jsEditor.getTextField().getFormatter();
		formatter.setAllowsInvalid(false);		
		add(spinner);

		
		JButton btnAgregar = new JButton("Agregar");
		btnAgregar.setBounds(24, 145, 89, 23);
		add(btnAgregar);

		JButton btnModificar = new JButton("Modificar");
		btnModificar.setBounds(154, 145, 89, 23);
		add(btnModificar);
		
		JButton btnEliminar = new JButton("Eliminar");
		btnEliminar.setBounds(284, 145, 89, 23);
		add(btnEliminar);
		
		btnAgregar.addActionListener(new ActionListener() { 
		    public void actionPerformed(ActionEvent e) { 
		    	if (camposNull()) {
					JOptionPane.showMessageDialog(null, "Rellene los campos");
				} else {
					String descripcion = txtFieldDescripcion.getText();
			    	int porcentaje = (int) spinner.getValue();
			    	TipoProceso tp=new TipoProceso();
			    	tp.setDescripcion(descripcion);
			    	tp.setPorcentaje(porcentaje);		    
			    	ProcessManager.addTProc(tp);
			    	refreshTblTProc();
				}		    			    	
		    } 
		});
		
		btnModificar.addActionListener(new ActionListener() { 
		    public void actionPerformed(ActionEvent e) { 
		    	if (camposNull()) {
					JOptionPane.showMessageDialog(null, "Rellene los campos");
				} else {
					String descripcion = txtFieldDescripcion.getText();
			    	int porcentaje = (int) spinner.getValue();	
			    	TipoProceso tp=new TipoProceso();
			    	tp.setDescripcion(descripcion);
			    	tp.setPorcentaje(porcentaje);	
			    	tp.setId(idRow);
			    	ProcessManager.updateTProc(tp);		    	
			    	refreshTblTProc();
				}		    	
		    } 
		});
		
		btnEliminar.addActionListener(new ActionListener() { 
		    public void actionPerformed(ActionEvent e) { 
		    	int res = JOptionPane.showConfirmDialog(null,"¿Está seguro?"); 
				if (res == JOptionPane.OK_OPTION) {					
					ProcessManager.deleteTProc(idRow);
					refreshTblTProc();
				}		    	
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
			    idRow = Integer.parseInt(tblPartPol.getValueAt(selRow, 0).toString());				
				String descripcion = tblPartPol.getValueAt(selRow, 1).toString();
				String rep = tblPartPol.getValueAt(selRow, 2).toString();
				int foo = Integer.parseInt(rep);
				txtFieldDescripcion.setText(descripcion);
				spinner.setValue(foo);
			}
		});
		
	}
	

	class MyTableModel extends AbstractTableModel {
		ArrayList<TipoProceso> tProcLst=ProcessManager.queryAllTProc();
		String[] titles = { "Código", "Descripción", "Porcentaje" };		
		
		
		@Override
		public int getColumnCount() {
			// TODO Auto-generated method stub
			return 3;
		}

		@Override
		public int getRowCount() {
			// TODO Auto-generated method stub
			return tProcLst.size();
		}

		@Override
		public Object getValueAt(int row, int col) {
			String value = "";
			switch (col) {
			case 0:
				value = "" + tProcLst.get(row).getId();
				break;
			case 1:
				value = tProcLst.get(row).getDescripcion();

				break;
			case 2:
				value = "" + tProcLst.get(row).getPorcentaje();
				break;

			}
			return value;
		}

		public String getColumnName(int col) {
			return titles[col];
		}
	}

	public void refreshTblTProc() {
		partPolModel.tProcLst = ProcessManager.queryAllTProc();
		partPolModel.fireTableChanged(null);
	}
	
	private boolean camposNull() {
		boolean v = false;
		if (txtFieldDescripcion.getText().equals(""))
			v = true;
		return v;
	}
}
