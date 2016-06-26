package pantallas;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.EmptyBorder;
import javax.swing.text.DefaultFormatter;

import bModel.ProcessManager;
import clasesAux.partidosProcesos;
import models.PartidoPolitico;
import models.ProcesoElectoral;

public class ConfiguracionSeg extends JDialog implements ActionListener{

	private JComboBox cmbBoxPE;
	private JButton okButton;
	private JButton cancelButton;
	private JSpinner spinner;
	private ButtonGroup group;
	private List<ProcesoElectoral> listaPE=partidosProcesos.procElecRechazados();//ProcessManager.queryAllProc();
	public static List<PartidoPolitico> listaPP;//= ProcessManager.queryAllPartPol();
	public static String [] listaStrPP;
	
	
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
	public ConfiguracionSeg() {
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
		
		JLabel lblNewLabel = new JLabel("Escoger Partido(s) Politico(s)");
		lblNewLabel.setBounds(51, 235, 251, 14);
		contentPanel.add(lblNewLabel);
		
		JLabel lblPorcentajeDeAceptacin = new JLabel("Porcentaje de aceptaci\u00F3n");
		lblPorcentajeDeAceptacin.setBounds(51, 143, 167, 14);
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
		
		/*for (int i = 0; i < listaPP.size(); i++)
			cmbBoxPP.addItem(listaPP.get(i).getNombre());*/
	//	cmbBoxPP.disable();
		
		
		
		
		
		/*if(Carga.idPP!=0){
			int i;
			for(i=0;i<listaPP.size();i++)
				if(listaPP.get(i).getId()==Carga.idPP) break;
			cmbBoxPP.setSelectedIndex(i);
		}*/	
		
		SpinnerModel sm = new SpinnerNumberModel(0, 0, 100, 1); //default value,lower bound,upper bound,increment by 
		spinner = new JSpinner(sm);
		spinner.setBounds(247, 140, 55, 20);
		JSpinner.NumberEditor jsEditor = (JSpinner.NumberEditor)spinner.getEditor();
		DefaultFormatter formatter = (DefaultFormatter) jsEditor.getTextField().getFormatter();
		formatter.setAllowsInvalid(false);
		if(listaPE.size()!=0) spinner.setValue(listaPE.get(0).getPorcentaje());
		if(Carga.porc!=-1) spinner.setValue(Carga.porc);
		
		contentPanel.add(spinner);
		
		/*if(Carga.choiceCI==0 && Carga.choiceCI==0){
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
			}		*/
		
		//group=new ButtonGroup();
		
		JButton btnCheckListPartidos = new JButton("Multiples Partidos");
		btnCheckListPartidos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				listaPP=partidosProcesos.queryPPRechazado(listaPE.get(cmbBoxPE.getSelectedIndex()).getId());
				listaStrPP = new String  [ listaPP.size() ]  ; // listaPP.get(i).getNombre());
				
				for ( int i = 0 ;  i < listaPP.size()  ; i++ ) {
					listaStrPP[i] = new String();
					listaStrPP[i] = listaPP.get(i).getNombre() ;
					
				}
												
				 //CheckListPartPol frame = new CheckListPartPol( listaStrPP);
				CheckListPPSeg frame = new CheckListPPSeg( listaStrPP);
				 
				    frame.setSize(300, 200);
				    frame.setVisible(true);
				    
			}
		});
		btnCheckListPartidos.setBounds(247, 231, 167, 23);
		contentPanel.add(btnCheckListPartidos);
				
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
			//if(listaPP.size()!=0) pp=listaPP.get(cmbBoxPP.getSelectedIndex());
			int porcentaje=(int)spinner.getValue();
			
			if (pe!=null) {
				Carga.idPE=pe.getId();
				Carga.porc=porcentaje;
			}
			if (pp!=null) Carga.idPP=pp.getId();
			/*if(rdbtnCMasiva.isSelected()){ 
				Carga.choiceCM=1;
				Carga.choiceCI=0;
			}
			else{ 
				Carga.choiceCI=1;
				Carga.choiceCM=0;
			}*/
			this.dispose();
		}
		
		if(e.getSource()==cmbBoxPE){
			if(listaPE.size()!=0)	spinner.setValue(listaPE.get(cmbBoxPE.getSelectedIndex()).getPorcentaje());
			Carga.ppEscogidosSeg=null;
		}
		
		//if(e.getSource()==rdbtnCMasiva)
			//cmbBoxPP.disable();					
	}
}
