package pantallas;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;
import javax.swing.table.AbstractTableModel;
import javax.swing.JButton;

import models.PartidoPolitico;
import bModel.ProcessManager;

import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import javax.swing.JScrollPane;
import javax.swing.JTable;

public class PartidoPoliticoPanel extends JPanel implements ActionListener{
	private JTextField txtFieldNombre;
	private JTextField txtFieldNRep;
	private JTextField txtFieldTel;
	private JTextField txtFieldCorreo;
	private JTable tblPartPol;
	MyTableModel partPolModel;
	private int idRow;
	private JButton btnAgregar;
	private JButton btnModificar;
	private JButton btnEliminar;

	/**
	 * Create the panel.
	 */
	public PartidoPoliticoPanel() {

		setLayout(null);

		JLabel lblNombre = new JLabel("Nombre");
		lblNombre.setBounds(28, 24, 46, 14);
		add(lblNombre);

		JLabel lblNombreRep = new JLabel("Nombre de representante");
		lblNombreRep.setBounds(28, 55, 143, 14);
		add(lblNombreRep);

		txtFieldNombre = new JTextField();
		txtFieldNombre.setBounds(181, 18, 174, 20);
		add(txtFieldNombre);
		txtFieldNombre.setColumns(10);

		txtFieldNRep = new JTextField();
		txtFieldNRep.setBounds(181, 52, 173, 20);
		add(txtFieldNRep);
		txtFieldNRep.setColumns(10);

		btnAgregar = new JButton("Agregar");
		
		btnAgregar.setBounds(34, 153, 89, 23);
		add(btnAgregar);

		btnModificar = new JButton("Modificar");		
		btnModificar.setBounds(150, 153, 89, 23);
		add(btnModificar);

		JLabel lblTelefono = new JLabel("Telefono Representante");
		lblTelefono.setBounds(28, 86, 129, 14);
		add(lblTelefono);

		txtFieldTel = new JTextField();
		txtFieldTel.setBounds(180, 83, 175, 20);
		add(txtFieldTel);
		txtFieldTel.setColumns(10);

		JLabel lblCorreo = new JLabel("Correo");
		lblCorreo.setBounds(28, 117, 46, 14);
		add(lblCorreo);

		txtFieldCorreo = new JTextField();
		txtFieldCorreo.setBounds(181, 114, 173, 20);
		add(txtFieldCorreo);
		txtFieldCorreo.setColumns(10);

		btnEliminar = new JButton("Eliminar");	
		btnEliminar.setBounds(266, 153, 89, 23);
		add(btnEliminar);

		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(null, "Datos de Partidos Politicos", TitledBorder.LEADING, TitledBorder.TOP,
				null, null));
		panel.setBounds(28, 205, 327, 198);
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
				int id = Integer.parseInt(tblPartPol.getValueAt(selRow, 0).toString());
				idRow=id;
				String nombre = tblPartPol.getValueAt(selRow, 1).toString();
				String rep = tblPartPol.getValueAt(selRow, 2).toString();
				String tel = tblPartPol.getValueAt(selRow, 3).toString();
				String correo = tblPartPol.getValueAt(selRow, 4).toString();

				txtFieldNombre.setText(nombre);
				txtFieldNRep.setText(rep);
				txtFieldTel.setText(tel);
				txtFieldCorreo.setText(correo);				
			}
		});
				
		btnAgregar.addActionListener(this);
		btnModificar.addActionListener(this);
		btnEliminar.addActionListener(this);
	}

	class MyTableModel extends AbstractTableModel {
		ArrayList<PartidoPolitico> partPolLst = ProcessManager.queryAllPartPol();
		String[] titles = { "Código", "Nombre", "Representante", "Telefono", "Correo" };

		@Override
		public int getColumnCount() {
			// TODO Auto-generated method stub
			return 5;
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
			return value;
		}

		public String getColumnName(int col) {
			return titles[col];
		}
	}

	public void refreshTblPartPol() {
		partPolModel.partPolLst = ProcessManager.queryAllPartPol();
		partPolModel.fireTableChanged(null);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource() == btnAgregar){			
			PartidoPolitico p = new PartidoPolitico();
			p.setCorreo(txtFieldCorreo.getText());
			p.setNombre(txtFieldNombre.getText());
			p.setNombreRep(txtFieldNRep.getText());
			p.setTelefono(txtFieldTel.getText());
			ProcessManager.addPartPolitico(p);
			refreshTblPartPol();
		}
		if(e.getSource() == btnModificar){			
			String nombre = txtFieldNombre.getText();
			String rep = txtFieldNRep.getText();
			String tel = txtFieldTel.getText();
			String correo = txtFieldCorreo.getText();

			PartidoPolitico p = new PartidoPolitico();
			p.setId(idRow);
			p.setNombre(nombre);
			p.setNombreRep(rep);
			p.setTelefono(tel);
			p.setCorreo(correo);

			ProcessManager.updatePartPol(p);
			refreshTblPartPol();
		}
		if(e.getSource() == btnEliminar){
			int res = JOptionPane.showConfirmDialog(null,"¿Está seguro?"); 
			if (res == JOptionPane.OK_OPTION) {					
				ProcessManager.deletePartPol(idRow);
				refreshTblPartPol();
			}
		}
	}
	
	
}
