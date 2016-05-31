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
import javax.swing.border.TitledBorder;
import javax.swing.table.AbstractTableModel;
import javax.swing.JComboBox;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.JButton;

import businessModel.Dao.MySQLDAOProceso;
import models.PartidoPolitico;
import models.ProcesoElectoral;
import models.TipoProceso;
import pantallas.PartidoPoliticoPanel.MyTableModel;

import com.toedter.calendar.JDateChooser;

import bModel.ProcessManager;

public class ProcesoElectoralPanel extends JPanel implements ActionListener{
	private JTextField txtFieldNombre;
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
	private List<TipoProceso> listaTProc;
	

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
		
		txtFieldNombre = new JTextField();
		txtFieldNombre.setBounds(142, 43, 172, 20);
		add(txtFieldNombre);
		txtFieldNombre.setColumns(10);
		
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
		dateIni.setBounds(142, 141, 87, 20);
		add(dateIni);
		//dateIni.setCalendar(Calendar.getInstance());
		
		dateFin = new JDateChooser();
		dateFin.setBounds(142, 172, 87, 20);
		add(dateFin);
		
		JLabel lblFechaInicial = new JLabel("Fecha Inicial");
		lblFechaInicial.setBounds(45, 147, 75, 14);
		add(lblFechaInicial);
		
		JLabel lblFechaFin = new JLabel("Fecha Fin");
		lblFechaFin.setBounds(45, 178, 64, 14);
		add(lblFechaFin);
		//dateFin.setCalendar(Calendar.getInstance());
		
		btnEliminar = new JButton("Eliminar");
		btnEliminar.setBounds(265, 211, 89, 23);
		add(btnEliminar);
		
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(null, "Datos de Procesos", TitledBorder.LEADING, TitledBorder.TOP,
				null, null));
		panel.setBounds(45, 245, 327, 198);
		add(panel);
		panel.setLayout(new GridLayout(1, 1, 0, 0));

		JScrollPane scrollPane = new JScrollPane();
		panel.add(scrollPane);
		
		tblProc = new JTable();
		scrollPane.setViewportView(tblProc);

		procModel = new MyTableModel();
		tblProc.setModel(procModel);
		
		listaTProc=ProcessManager.queryAllTProc();
		for(int i=0;i<listaTProc.size();i++) comboBox.addItem(listaTProc.get(i).getDescripcion());
				    		
  
    	btnAgregar.addActionListener(this);
    	btnModificar.addActionListener(this);
    	btnEliminar.addActionListener(this);
    	
    	tblProc.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				
				int selRow = tblProc.getSelectedRow();
				int id = Integer.parseInt(tblProc.getValueAt(selRow, 0).toString());
				idRow=id;
				String nombre = tblProc.getValueAt(selRow, 1).toString();
				String tp = tblProc.getValueAt(selRow, 2).toString();			
				String porc = tblProc.getValueAt(selRow, 3).toString();
				String fIni = tblProc.getValueAt(selRow, 4).toString();
				String fFin = tblProc.getValueAt(selRow, 5).toString();
				
				DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
				
				txtFieldNombre.setText(nombre);				
				comboBox.setSelectedItem(tp);;				
				spinner.setValue(porc);				
				try {
					dateIni.setDate(formatter.parse(fIni));
					dateFin.setDate(formatter.parse(fFin));
				} catch (ParseException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
			}
		});

	}
	class MyTableModel extends AbstractTableModel {
		ArrayList<ProcesoElectoral> procLst = ProcessManager.queryAllProc();
		String[] titles = { "Código", "Nombre", "Tipo de Proc.", " % ", "Inicio", "Fin" };
		
		@Override
		public int getColumnCount() {
			// TODO Auto-generated method stub
			return 6;
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
		if(e.getSource() == btnAgregar){	
			ProcesoElectoral p=new ProcesoElectoral();
			p.setNombre(txtFieldNombre.getText());
			p.setPorcentaje((int)spinner.getValue());
			TipoProceso tp=listaTProc.get(comboBox.getSelectedIndex());
			p.setIdTipoProceso(tp.getId());
			p.setTipoProceso(tp.getDescripcion());									
			//p.setFechaIni(dateIni.getDate());
			//p.setFechaFin(dateFin.getDate());			
			
			
			

			
			ProcessManager.addProc(p);			
			refreshTblProc();
		}
		if(e.getSource() == btnModificar){	
			ProcesoElectoral p=new ProcesoElectoral();
			p.setNombre(txtFieldNombre.getText());
			p.setPorcentaje((int)spinner.getValue());
			TipoProceso tp=listaTProc.get(comboBox.getSelectedIndex());
			p.setIdTipoProceso(tp.getId());
			p.setTipoProceso(tp.getDescripcion());	
			
			SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
			
			String startDateString = formatter.format(dateIni.getDate());			
			Date d;
			try {
				d = new Date(formatter.parse(startDateString).getTime());
				p.setFechaIni(d);
				
				startDateString=formatter.format(dateFin.getDate());
				d=new Date(formatter.parse(startDateString).getTime());
				p.setFechaFin(d);					
				
			} catch (ParseException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			ProcessManager.updateProc(p);
			refreshTblProc();
		}
		if(e.getSource() == btnEliminar){	
			int res = JOptionPane.showConfirmDialog(null,"¿Está seguro?"); 
			if (res == JOptionPane.OK_OPTION) {					
				ProcessManager.deleteProc(idRow);
				refreshTblProc();
			}
		}
	}
}

