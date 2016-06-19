package pantallas;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.sql.Date;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.TitledBorder;
import javax.swing.table.AbstractTableModel;
import javax.swing.text.DefaultFormatter;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.JButton;

import businessModel.Dao.MySQLDAOProceso;
import clasesAux.JTextFieldLimit;
import models.PartidoPolitico;
import models.ProcesoElectoral;
import models.TipoProceso;
import pantallas.PartidoPoliticoPanel.MyTableModel;

import com.toedter.calendar.JDateChooser;

import bModel.ProcessManager;

public class ProcesoElectoralPanel extends JPanel implements ActionListener {
	private JTextField txtFieldNombre;
	private JTable tblProcesos;
	private JSpinner spinner;
	private JSpinner spinnerCant;
	private JDateChooser dateIni;
	private JDateChooser dateFin;
	private JTable tblProc;
	MyTableModel procModel;
	private int idRow=-1;
	private JComboBox comboBox;
	private JButton btnAgregar;
	private JButton btnModificar;
	private JButton btnEliminar;
	private List<TipoProceso> listaTProc=ProcessManager.queryAllTProc();

	/**
	 * Create the panel.
	 */
	public ProcesoElectoralPanel() {
		setLayout(null);

		JLabel lblNombre = new JLabel("Nombre");
		lblNombre.setBounds(70, 60, 46, 14);
		add(lblNombre);

		JLabel lblTipoProceso = new JLabel("Tipo Proceso");
		lblTipoProceso.setBounds(70, 102, 75, 14);
		add(lblTipoProceso);

		txtFieldNombre = new JTextField();
		txtFieldNombre.setBounds(184, 57, 299, 20);
		add(txtFieldNombre);
		txtFieldNombre.setColumns(10);
		txtFieldNombre.setDocument(new JTextFieldLimit(60));

		comboBox = new JComboBox();
		comboBox.setBounds(184, 99, 299, 20);
		add(comboBox);

		JLabel lblPorcentaje = new JLabel("Porcentaje");
		lblPorcentaje.setBounds(70, 146, 64, 14);
		add(lblPorcentaje);
		
		SpinnerModel sm = new SpinnerNumberModel(0, 0, 100, 1); //default value,lower bound,upper bound,increment by 
		spinner = new JSpinner(sm);
		spinner.setBounds(184, 143, 123, 20);
		JSpinner.NumberEditor jsEditor = (JSpinner.NumberEditor)spinner.getEditor();
		DefaultFormatter formatter = (DefaultFormatter) jsEditor.getTextField().getFormatter();
		formatter.setAllowsInvalid(false);
		//JFormattedTextField txt = ((JSpinner.NumberEditor) spinner.getEditor()).getTextField();		
        //((NumberFormatter)txt.getFormatter()).setAllowsInvalid(false);
		add(spinner);
		if(listaTProc.size()!=0) spinner.setValue(listaTProc.get(0).getPorcentaje());

		btnAgregar = new JButton("Agregar");
		btnAgregar.setBounds(80, 336, 89, 23);
		add(btnAgregar);

		btnModificar = new JButton("Modificar");
		btnModificar.setBounds(259, 336, 89, 23);
		add(btnModificar);

		dateIni = new JDateChooser();
		dateIni.setBounds(184, 189, 145, 20);
		add(dateIni);
		// dateIni.setCalendar(Calendar.getInstance());

		dateFin = new JDateChooser();
		dateFin.setBounds(182, 233, 147, 20);
		add(dateFin);

		JLabel lblFechaInicial = new JLabel("Fecha Inicial");
		lblFechaInicial.setBounds(70, 189, 75, 14);
		add(lblFechaInicial);

		JLabel lblFechaFin = new JLabel("Fecha Fin");
		lblFechaFin.setBounds(70, 233, 64, 14);
		add(lblFechaFin);
		// dateFin.setCalendar(Calendar.getInstance());

		btnEliminar = new JButton("Eliminar");
		btnEliminar.setBounds(430, 336, 89, 23);
		add(btnEliminar);
		
		SpinnerModel sm2 = new SpinnerNumberModel(0, 0, 999999999, 1); //default value,lower bound,upper bound,increment by				
		spinnerCant = new JSpinner(sm2);
		spinnerCant.setBounds(184, 276, 145, 20);
		JSpinner.NumberEditor jsEditor2 = (JSpinner.NumberEditor)spinner.getEditor();
		DefaultFormatter formatter2 = (DefaultFormatter) jsEditor2.getTextField().getFormatter();
		formatter2.setAllowsInvalid(false);
		add(spinnerCant);
		
		JLabel lblCantidadDePersonas = new JLabel("Cantidad de personas");
		lblCantidadDePersonas.setBounds(70, 279, 152, 14);
		add(lblCantidadDePersonas);

		JPanel panel = new JPanel();
		panel.setBorder(
				new TitledBorder(null, "Datos de Procesos", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel.setBounds(28, 385, 579, 213);
		add(panel);
		panel.setLayout(new GridLayout(1, 1, 0, 0));

		JScrollPane scrollPane = new JScrollPane();
		panel.add(scrollPane);

		tblProc = new JTable();
		scrollPane.setViewportView(tblProc);

		procModel = new MyTableModel();
		tblProc.setModel(procModel);
		
		for (int i = 0; i < listaTProc.size(); i++)
			comboBox.addItem(listaTProc.get(i).getDescripcion());

		btnAgregar.addActionListener(this);
		btnModificar.addActionListener(this);
		btnEliminar.addActionListener(this);
		comboBox.addActionListener(this);

		tblProc.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {

				int selRow = tblProc.getSelectedRow();
				int id = Integer.parseInt(tblProc.getValueAt(selRow, 0).toString());
				idRow = id;
				String nombre = tblProc.getValueAt(selRow, 1).toString();
				String tp = tblProc.getValueAt(selRow, 2).toString();
				String porc = tblProc.getValueAt(selRow, 3).toString();
				String fIni = tblProc.getValueAt(selRow, 4).toString();
				String fFin = tblProc.getValueAt(selRow, 5).toString();

				DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");

				txtFieldNombre.setText(nombre);
				comboBox.setSelectedItem(tp);
				
				spinner.setValue(Integer.parseInt(porc));
				try {
					dateIni.setDate(formatter.parse(fIni));
					dateFin.setDate(formatter.parse(fFin));
				} catch (ParseException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

			}
		});
		
		JPanel panelReg = new JPanel();
		panelReg.setBorder(new TitledBorder(null, "PROCESOS ELECTORALES", TitledBorder.LEADING, TitledBorder.TOP,
				null, null));
		panelReg.setBounds(28, 30, 573, 283);
		add(panelReg);
		panelReg.setLayout(new GridLayout(1, 1, 0, 0));
		
		
		
		JLabel label = new JLabel("%");
		label.setBounds(312, 146, 46, 14);
		add(label);
		
		
	}

	class MyTableModel extends AbstractTableModel {
		ArrayList<ProcesoElectoral> procLst = ProcessManager.queryAllProc();
		String[] titles = { "Código", "Nombre", "Tipo de Proc.", " % ", "Inicio", "Fin", "Cant. personas" };

		@Override
		public int getColumnCount() {
			// TODO Auto-generated method stub
			return 7;
		}

		@Override
		public int getRowCount() {
			// TODO Auto-generated method stub
			return procLst.size();

		}

		@Override
		public Object getValueAt(int row, int col) {
			// TODO Auto-generated method stub
			String value = "";
			DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");

			switch (col) {
			case 0:
				value = "" + procLst.get(row).getId();
				break;
			case 1:
				value = procLst.get(row).getNombre();
				break;
			case 2:
				value = "" + procLst.get(row).getTipoProceso();
				break;
			case 3:
				value = "" + procLst.get(row).getPorcentaje();
				break;
			case 4:
				value = "" + formatter.format(procLst.get(row).getFechaIni());
				break;

			case 5:
				value = "" + formatter.format(procLst.get(row).getFechaFin());
				break;
			
			case 6:
				value = "" + procLst.get(row).getTotalPersonas();
				break;
			}
			return value;
		}

		public String getColumnName(int col) {
			return titles[col];
		}

	}

	public void refreshTblProc() {
		procModel.procLst = ProcessManager.queryAllProc();
		procModel.fireTableChanged(null);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub			
		String res;
		if (e.getSource() == btnAgregar) {
			res=camposNull();
			if (!res.equals("")) {
				JOptionPane.showMessageDialog(null, "Rellene los campos:\n"+res);
			} else {
				ProcesoElectoral p = new ProcesoElectoral();
				p.setNombre(txtFieldNombre.getText());
				p.setPorcentaje((int) spinner.getValue());
				p.setTotalPersonas((int)spinnerCant.getValue());
				TipoProceso tp = listaTProc.get(comboBox.getSelectedIndex());
				p.setIdTipoProceso(tp.getId());
				p.setTipoProceso(tp.getDescripcion());

				SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");

				String startDateString = formatter.format(dateIni.getDate());
				Date d;
				try {
					d = new Date(formatter.parse(startDateString).getTime());
					p.setFechaIni(d);

					startDateString = formatter.format(dateFin.getDate());
					d = new Date(formatter.parse(startDateString).getTime());
					p.setFechaFin(d);

				} catch (ParseException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				try{
					ProcessManager.addProc(p);
					refreshTblProc();
				}catch(Exception ex){
					JOptionPane.showMessageDialog(null, "Fallo al insertar datos");
				}				
			}						
		}
		if (e.getSource() == btnModificar) {
			if(idRow==-1)
				JOptionPane.showMessageDialog(null, "Escoja una fila");
			else{
				res=camposNull();
				if (!res.equals("")) {
					JOptionPane.showMessageDialog(null, "Rellene los campos:\n"+res);
				} else {
					ProcesoElectoral p = new ProcesoElectoral();
					p.setNombre(txtFieldNombre.getText());
					p.setPorcentaje((int) spinner.getValue());
					p.setTotalPersonas((int)spinnerCant.getValue());
					TipoProceso tp = listaTProc.get(comboBox.getSelectedIndex());
					p.setIdTipoProceso(tp.getId());
					p.setTipoProceso(tp.getDescripcion());
					
					p.setId(idRow);
					
					SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");

					String startDateString = formatter.format(dateIni.getDate());
					Date d;
					try {
						d = new Date(formatter.parse(startDateString).getTime());
						p.setFechaIni(d);

						startDateString = formatter.format(dateFin.getDate());
						d = new Date(formatter.parse(startDateString).getTime());
						p.setFechaFin(d);

					} catch (ParseException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					
					try{
						ProcessManager.updateProc(p);
						refreshTblProc();
					}catch(Exception ex){
						JOptionPane.showMessageDialog(null, "Fallo al actualizar los datos");
					}					
				}			

			}
						
		}
		if (e.getSource() == btnEliminar) {
			if(idRow==-1)
				JOptionPane.showMessageDialog(null, "Escoja una fila");
			else{
				int result = JOptionPane.showConfirmDialog(null, "¿Está seguro?");
				if (result == JOptionPane.OK_OPTION) {
					try{
						if(!ProcessManager.deleteProc(idRow))
							JOptionPane.showMessageDialog(null, "Existen cargas relacionadas a este proceso.\n"
									+ "No se puede realizar la eliminación");
						refreshTblProc();
					}catch(Exception ex){
						JOptionPane.showMessageDialog(null, "Fallo al borrar datos");
					}
					
				}
			}			
		}
		if(e.getSource() == comboBox)
			if(listaTProc.size()!=0)	spinner.setValue(listaTProc.get(comboBox.getSelectedIndex()).getPorcentaje());
		
	}
	
	private String camposNull() {															
		boolean v = false;
		String resultado="";
		
		if (txtFieldNombre.getText().equals("")){
			resultado+="- Nombre\n";
			v = true;
		}
		if (dateIni.getDate()==null){
			resultado+="- Fecha Inicial\n";
			v = true;			
		}
		if (dateFin.getDate()==null){
			resultado+="- Fecha Final\n";
			v = true;			
		}		
		
		return resultado;
	}
}
