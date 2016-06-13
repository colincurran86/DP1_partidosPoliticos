package pantallas;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.util.List;

import javax.swing.ButtonGroup;
import javax.swing.ButtonModel;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.text.DefaultFormatter;

import bModel.ProcessManager;
import models.PartidoPolitico;
import models.ProcesoElectoral;
import models.TipoProceso;

import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.JSpinner;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.JRadioButton;

public class Configuracion extends JDialog implements ActionListener{
	private JComboBox cmbBoxPE;
	private JComboBox cmbBoxPP;
	private JButton okButton;
	private JButton cancelButton;
	private JSpinner spinner;
	private JRadioButton rdbtnCMasiva;
	private JRadioButton rdbtnCIndividual;
	private ButtonGroup group;
	private List<ProcesoElectoral> listaPE=ProcessManager.queryAllProc();
	private List<PartidoPolitico> listaPP= ProcessManager.queryAllPartPol();
	
	
	
	private final JPanel contentPanel = new JPanel();
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			Configuracion dialog = new Configuracion();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public Configuracion() {
		this.setTitle("Configuracion");
		setModal(true);
		setBounds(100, 100, 599, 407);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		ImageIcon img = new ImageIcon("lib/conf.png");
		this.setIconImage(img.getImage());
		
		JLabel lblProcesoElectoral = new JLabel("Proceso Electoral");
		lblProcesoElectoral.setBounds(51, 65, 167, 14);
		contentPanel.add(lblProcesoElectoral);
		
		JLabel lblNewLabel = new JLabel("Partido Politico");
		lblNewLabel.setBounds(51, 240, 154, 14);
		contentPanel.add(lblNewLabel);
		
		JLabel lblPorcentajeDeAceptacin = new JLabel("Porcentaje de aceptaci\u00F3n");
		lblPorcentajeDeAceptacin.setBounds(51, 131, 167, 14);
		contentPanel.add(lblPorcentajeDeAceptacin);
		
		cmbBoxPE = new JComboBox();
		cmbBoxPE.setBounds(247, 62, 253, 20);
		contentPanel.add(cmbBoxPE);
				
		for (int i = 0; i < listaPE.size(); i++)
			cmbBoxPE.addItem(listaPE.get(i).getNombre());
		
		if(Carga.idPE!=0){
			int i;
			for(i=0;i<listaPE.size();i++)
				if(listaPE.get(i).getId()==Carga.idPE) break;
			cmbBoxPE.setSelectedIndex(i);
		}	
		
		cmbBoxPP = new JComboBox();
		cmbBoxPP.setBounds(247, 237, 253, 20);
		contentPanel.add(cmbBoxPP);
		cmbBoxPP.addActionListener(this);		
		
		for (int i = 0; i < listaPP.size(); i++)
			cmbBoxPP.addItem(listaPP.get(i).getNombre());
		cmbBoxPP.disable();
		
		if(Carga.idPP!=0){
			int i;
			for(i=0;i<listaPP.size();i++)
				if(listaPP.get(i).getId()==Carga.idPP) break;
			cmbBoxPP.setSelectedIndex(i);
		}	
		
		SpinnerModel sm = new SpinnerNumberModel(0, 0, 100, 1); //default value,lower bound,upper bound,increment by 
		spinner = new JSpinner(sm);
		spinner.setBounds(247, 128, 55, 20);
		JSpinner.NumberEditor jsEditor = (JSpinner.NumberEditor)spinner.getEditor();
		DefaultFormatter formatter = (DefaultFormatter) jsEditor.getTextField().getFormatter();
		formatter.setAllowsInvalid(false);
		if(listaPE.size()!=0) spinner.setValue(listaPE.get(0).getPorcentaje());
		if(Carga.porc!=-1) spinner.setValue(Carga.porc);
		
		contentPanel.add(spinner);
		
		rdbtnCMasiva = new JRadioButton("Carga Masiva");
		rdbtnCMasiva.setBounds(109, 183, 147, 23);		
		contentPanel.add(rdbtnCMasiva);
		
		rdbtnCIndividual = new JRadioButton("Carga individual");
		rdbtnCIndividual.setBounds(358, 183, 142, 23);
		contentPanel.add(rdbtnCIndividual);
		
		if(Carga.choiceCI==0 && Carga.choiceCI==0){
			rdbtnCMasiva.setSelected(true);
			cmbBoxPP.disable();
		}
		else
			if(Carga.choiceCI==1) {
				rdbtnCIndividual.setSelected(true);
				cmbBoxPP.enable();
			}							
			else {
				rdbtnCMasiva.setSelected(true);
				cmbBoxPP.disable();
			}		
		
		group=new ButtonGroup();
		group.add(rdbtnCMasiva);
		group.add(rdbtnCIndividual);
				
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				okButton = new JButton("OK");
				//okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				cancelButton = new JButton("Cancel");
				//cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
		
		okButton.addActionListener(this);
		cancelButton.addActionListener(this);
		rdbtnCMasiva.addActionListener(this);
		rdbtnCIndividual.addActionListener(this);
		cmbBoxPE.addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource()==cancelButton){
			this.dispose();
		}
		
		if(e.getSource()==okButton){
			ProcesoElectoral pe=null;
			PartidoPolitico pp=null;
			if(listaPE.size()!=0)  pe=listaPE.get(cmbBoxPE.getSelectedIndex());
			if(listaPP.size()!=0) pp=listaPP.get(cmbBoxPP.getSelectedIndex());
			int porcentaje=(int)spinner.getValue();
			
			if (pe!=null) {
				Carga.idPE=pe.getId();
				Carga.porc=porcentaje;
			}
			if (pp!=null) Carga.idPP=pp.getId();
			if(rdbtnCMasiva.isSelected()){ 
				Carga.choiceCM=1;
				Carga.choiceCI=0;
			}
			else{ 
				Carga.choiceCI=1;
				Carga.choiceCM=0;
			}
			this.dispose();
		}
		
		if(e.getSource()==cmbBoxPE)
			if(listaPE.size()!=0)	spinner.setValue(listaPE.get(cmbBoxPE.getSelectedIndex()).getPorcentaje());
		
		if(e.getSource()==rdbtnCMasiva)
			cmbBoxPP.disable();
		if(e.getSource()==rdbtnCIndividual)
			cmbBoxPP.enable();
		
			
	}
}
