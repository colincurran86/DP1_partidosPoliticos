package pantallas;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;
import javax.swing.table.AbstractTableModel;
import javax.swing.text.NumberFormatter;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;

import models.PartidoPolitico;
import bModel.ProcessManager;
import clasesAux.JTextFieldLimit;

import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.Panel;

public class PartidoPoliticoPanel extends JPanel implements ActionListener {
	private JTextField txtFieldNombre;
	private JTextField txtFieldNRep;
	private JTextField txtFieldTel;// JFormattedTextField txtFieldTel;//
	private JTextField txtFieldCorreo;
	private JTable tblPartPol;
	MyTableModel partPolModel;
	private int idRow = -1;
	private JButton btnAgregar;
	private JButton btnModificar;
	private JButton btnEliminar;

	/**
	 * Create the panel.
	 */
	public PartidoPoliticoPanel() {

		setLayout(null);

		JLabel lblNombre = new JLabel("Nombre");
		lblNombre.setBounds(62, 62, 46, 14);
		add(lblNombre);

		JLabel lblNombreRep = new JLabel("Nombre de representante");
		lblNombreRep.setBounds(62, 120, 143, 14);
		add(lblNombreRep);

		txtFieldNombre = new JTextField();
		txtFieldNombre.setBounds(215, 59, 329, 20);
		add(txtFieldNombre);
		txtFieldNombre.setColumns(10);
		txtFieldNombre.setDocument(new JTextFieldLimit(60));

		txtFieldNRep = new JTextField();
		txtFieldNRep.setBounds(215, 117, 328, 20);
		add(txtFieldNRep);
		txtFieldNRep.setColumns(10);
		txtFieldNRep.setDocument(new JTextFieldLimit(80));

		btnAgregar = new JButton("Agregar");

		btnAgregar.setBounds(80, 336, 89, 23);
		add(btnAgregar);

		btnModificar = new JButton("Modificar");
		btnModificar.setBounds(259, 336, 89, 23);
		add(btnModificar);

		JLabel lblTelefono = new JLabel("Telefono Representante");
		lblTelefono.setBounds(62, 182, 129, 14);
		add(lblTelefono);

		/*
		 * NumberFormat format = NumberFormat.getNumberInstance();
		 * NumberFormatter formatter = new NumberFormatter(format);
		 * formatter.setMinimum(0); formatter.setMaximum(Integer.MAX_VALUE);
		 * formatter.setValueClass(Integer.class);
		 * formatter.setCommitsOnValidEdit(true);
		 */

		txtFieldTel = new JTextField();// new JFormattedTextField(format);//
		txtFieldTel.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				char c = e.getKeyChar();
				if (!Character.isDigit(c) && !e.isAltDown())
					e.consume();

			}
		});
		txtFieldTel.setBounds(214, 179, 329, 20);
		add(txtFieldTel);
		txtFieldTel.setColumns(10);
		txtFieldTel.setDocument(new JTextFieldLimit(18));

		JLabel lblCorreo = new JLabel("Correo");
		lblCorreo.setBounds(62, 250, 46, 14);
		add(lblCorreo);

		txtFieldCorreo = new JTextField();
		txtFieldCorreo.setBounds(216, 247, 327, 20);
		add(txtFieldCorreo);
		txtFieldCorreo.setColumns(10);
		txtFieldCorreo.setDocument(new JTextFieldLimit(50));

		btnEliminar = new JButton("Eliminar");
		btnEliminar.setBounds(430, 336, 89, 23);
		add(btnEliminar);

		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(null, "Datos de Partidos Politicos", TitledBorder.LEADING, TitledBorder.TOP,
				null, null));
		panel.setBounds(28, 405, 579, 308);
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
				idRow = id;
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

		JPanel panelReg = new JPanel();
		panelReg.setBorder(
				new TitledBorder(null, "PARTIDOS POLITICOS", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panelReg.setBounds(28, 30, 573, 286);
		add(panelReg);
		panelReg.setLayout(new GridLayout(1, 1, 0, 0));

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
		String res;
		if (e.getSource() == btnAgregar) {
			res = camposNull();
			if (!res.equals("")) {
				JOptionPane.showMessageDialog(null, "Rellene los campos:\n" + res);
			} else {
				PartidoPolitico p = new PartidoPolitico();
				p.setCorreo(txtFieldCorreo.getText());
				p.setNombre(txtFieldNombre.getText());
				p.setNombreRep(txtFieldNRep.getText());
				p.setTelefono(txtFieldTel.getText());

				try {
					ProcessManager.addPartPolitico(p);
					refreshTblPartPol();
				} catch (Exception ex) {
					JOptionPane.showMessageDialog(null, "Fallo al insertar datos");
				}

			}

		}
		if (e.getSource() == btnModificar) {
			if (idRow == -1)
				JOptionPane.showMessageDialog(null, "Escoja una fila");
			else {
				res = camposNull();
				if (!res.equals("")) {
					JOptionPane.showMessageDialog(null, "Rellene los campos:\n" + res);
				} else {
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

					try {
						ProcessManager.updatePartPol(p);
						refreshTblPartPol();
					} catch (Exception ex) {
						JOptionPane.showMessageDialog(null, "Fallo al actualizar los datos");
					}

				}
			}

		}
		if (e.getSource() == btnEliminar) {
			if (idRow == -1)
				JOptionPane.showMessageDialog(null, "Escoja una fila");
			else {
				int result = JOptionPane.showConfirmDialog(null, "¿Está seguro?");
				if (result == JOptionPane.OK_OPTION) {
					try {
						if (!ProcessManager.deletePartPol(idRow)) {
							JOptionPane.showMessageDialog(null, "Existen cargas relacionadas a este proceso.\n"
									+ "No se puede realizar la eliminación");
						}
						refreshTblPartPol();
					} catch (Exception ex) {
						JOptionPane.showMessageDialog(null, "Fallo al borrar datos");
					}
				}
			}
		}
	}

	private String camposNull() {
		boolean v = false;
		String resultado = "";
		if (txtFieldNombre.getText().equals("")) {
			resultado += "- Nombre\n";
			v = true;
		}

		if (txtFieldNRep.getText().equals("")) {
			resultado += "- Representante\n";
			v = true;
		}

		if (txtFieldTel.getText().equals("")) {
			resultado += "- Telefono\n";
			v = true;
		}

		if (txtFieldCorreo.getText().equals("")) {
			resultado += "- Correo\n";
			v = true;
		}

		return resultado;
	}
}
