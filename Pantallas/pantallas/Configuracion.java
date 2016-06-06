package pantallas;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import bModel.ProcessManager;
import models.PartidoPolitico;
import models.ProcesoElectoral;
import models.TipoProceso;

import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.JSpinner;

public class Configuracion extends JDialog {
	private JComboBox cmbBoxPE;
	private JComboBox cmbBoxPP;
	private JButton okButton;
	private JButton cancelButton;
	private List<ProcesoElectoral> listaPE;
	private List<PartidoPolitico> listaPP;
	
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
		setModal(true);
		setBounds(100, 100, 599, 407);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		JLabel lblProcesoElectoral = new JLabel("Proceso Electoral");
		lblProcesoElectoral.setBounds(51, 65, 108, 14);
		contentPanel.add(lblProcesoElectoral);
		
		JLabel lblNewLabel = new JLabel("Partido Politico");
		lblNewLabel.setBounds(51, 131, 80, 14);
		contentPanel.add(lblNewLabel);
		
		JLabel lblPorcentajeDeAceptacin = new JLabel("Porcentaje de aceptaci\u00F3n");
		lblPorcentajeDeAceptacin.setBounds(51, 220, 130, 14);
		contentPanel.add(lblPorcentajeDeAceptacin);
		
		cmbBoxPE = new JComboBox();
		cmbBoxPE.setBounds(247, 62, 253, 20);
		contentPanel.add(cmbBoxPE);
		
		listaPE = ProcessManager.queryAllProc();
		for (int i = 0; i < listaPE.size(); i++)
			cmbBoxPE.addItem(listaPE.get(i).getNombre());
		
		
		cmbBoxPP = new JComboBox();
		cmbBoxPP.setBounds(247, 128, 253, 20);
		contentPanel.add(cmbBoxPP);
		
		listaPP = ProcessManager.queryAllPartPol();
		for (int i = 0; i < listaPE.size(); i++)
			cmbBoxPE.addItem(listaPE.get(i).getNombre());
		
		JSpinner spinner = new JSpinner();
		spinner.setBounds(247, 217, 29, 20);
		contentPanel.add(spinner);
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
	}
}
