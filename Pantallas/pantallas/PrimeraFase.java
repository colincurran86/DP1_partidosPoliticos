package pantallas;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JTextField;

public class PrimeraFase extends JPanel implements ActionListener {
	private JTextField txtFieldPlan;
	private JTextField txtFieldBD;
	private JButton btnProcesar;
	private JButton btnCancelar;
	private JButton btnBuscarBD;
	private JButton btnBuscarPlan;

	/**
	 * Create the panel.
	 */
	public PrimeraFase() {
		setLayout(null);

		JLabel lblRutaDeLos = new JLabel("Ruta de los planillones");
		lblRutaDeLos.setBounds(90, 177, 291, 14);
		add(lblRutaDeLos);

		JLabel lblRutaDeLa = new JLabel("Ruta de la Base de Datos");
		lblRutaDeLa.setBounds(90, 308, 302, 14);
		add(lblRutaDeLa);

		btnProcesar = new JButton("Procesar");
		btnProcesar.setBounds(153, 511, 89, 23);
		add(btnProcesar);

		btnCancelar = new JButton("Cancelar");
		btnCancelar.setBounds(421, 511, 89, 23);
		add(btnCancelar);

		txtFieldPlan = new JTextField();
		txtFieldPlan.setBounds(90, 202, 339, 20);
		add(txtFieldPlan);
		txtFieldPlan.setColumns(10);

		txtFieldBD = new JTextField();
		txtFieldBD.setBounds(90, 333, 339, 20);
		add(txtFieldBD);
		txtFieldBD.setColumns(10);

		btnBuscarBD = new JButton("Buscar");
		btnBuscarBD.setBounds(432, 332, 89, 23);
		add(btnBuscarBD);

		btnBuscarPlan = new JButton("Buscar ");
		btnBuscarPlan.setBounds(432, 201, 89, 23);
		add(btnBuscarPlan);

		btnProcesar.addActionListener(this);
		btnCancelar.addActionListener(this);
		btnBuscarBD.addActionListener(this);
		btnBuscarPlan.addActionListener(this);

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if (e.getSource() == btnCancelar) {
			Carga carga = new Carga();
			carga.setVisible(true);
			Principal.getFrame().getContentPane().setVisible(false);
			Principal.getFrame().setContentPane(carga);
		}
		if (e.getSource() == btnProcesar) {
			// rico pe
		}
		if (e.getSource() == btnBuscarBD) {
			JFileChooser jFileChooser = new JFileChooser();
			jFileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
			jFileChooser.setAcceptAllFileFilterUsed(false);
			
			int result = jFileChooser.showOpenDialog(this);
			StringBuffer buffer = new StringBuffer();					

			if (result == JFileChooser.APPROVE_OPTION) {// abrir				
				File file = jFileChooser.getSelectedFile();
				txtFieldBD.setText(file.getAbsolutePath());

			}
		}
		if (e.getSource() == btnBuscarPlan){
			JFileChooser jFileChooser = new JFileChooser();
			jFileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
			jFileChooser.setAcceptAllFileFilterUsed(false);
			
			int result = jFileChooser.showOpenDialog(this);
			StringBuffer buffer = new StringBuffer();			
			
			
			if (result == JFileChooser.APPROVE_OPTION) {// abrir				
				File file = jFileChooser.getSelectedFile();
				/*if (!file.isDirectory()) 
			        file = file.getParentFile();
			        file= file.getCurrentDirectory();*/			    
				txtFieldPlan.setText(file.getAbsolutePath());
			}
		}
	}
}
