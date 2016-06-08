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
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JTextField;

import Firmas.AlgoritmoFirmas;
import Firmas.Resultado;
import Recorte.Main;
import clasesAux.Testing;
import clasesAux.Util;
import models.PersonaReniec;

public class PrimeraFase extends JPanel implements ActionListener {
	private JTextField txtFieldPlan;
	private JTextField txtFieldBD;
	private JButton btnProcesar;
	private JButton btnCancelar;
	private JButton btnBuscarBD;
	private JButton btnBuscarPlan;
	public static int idPE = 0;
	public static int idPP = 0;
	public static int porc = -1;
	public static int choiceCM = 0;
	public static int choiceCI = 0;

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
			Util u = new Util();

			Main m = new Main();
			String formatearRutaPlan = u.formatearRuta(txtFieldPlan.getText());
			m.main(formatearRutaPlan);
			System.out.println("Lista de DNIS " + Main.lista.size());
			System.out.println("Lista de Imagenes" + Main.listaBImage.size());
			
			String formatearRutaBD = u.formatearRuta(txtFieldBD.getText());
			u.llenarBDReniec(formatearRutaBD + "/registro.nacional.v.1.xlsx");

			List<PersonaReniec> pr = u.ocrMasReniec();
			// rico pe
			
	        
	    	Main mainRecorte = new Main();
	        mainRecorte.main("kaka");
	        Util kaka2 = new Util();
	       
	        for (int i = 0; i< mainRecorte.lista.size(); i++) System.out.println( mainRecorte.lista.get(i)) ;
	        List<PersonaReniec> pr1 = kaka2.ocrMasReniec();
	       // System.out.println("cuantos ha encontrado: " + pr.size());

			
			
			
			List<String> idFirmasLst = new ArrayList<String>();
			List<Integer> idRegistroLst = new ArrayList<Integer>();
			System.out.println("ts:" +pr1.size());
			for (int i = 0; i < pr1.size(); i++) {
				if(pr1.get(i)!=null){
					idFirmasLst.add(pr1.get(i).getIdFirma());					
					idRegistroLst.add(i + 1);
				}
				else
				{
					idFirmasLst.add("-1");					
					idRegistroLst.add(i + 1);		
				}
				
			}
			System.out.println("ts:" +idRegistroLst.size());
			List<Resultado> listaTemporalPersona = null;
			System.out.println("Inicio firmas:");
			AlgoritmoFirmas algoritmoFrimas = new AlgoritmoFirmas();
	
			try {
	listaTemporalPersona = algoritmoFrimas.verificarFirmas6(idRegistroLst, idFirmasLst,Main.listaBImage , u.formatearRuta2(formatearRutaBD + "/firmas.jpg/"));
	
	for (int i = 0; i < listaTemporalPersona.size(); i++) {
		// for (int k = 0; k < listaTemporalPersona.get(i).size(); k++)
		// {
		System.out.println("por "
				+ listaTemporalPersona.get(i).porcentaje + " " + listaTemporalPersona.get(i).idPersona);
		// }
	}
	System.out.println("Fin firmas:");

	
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}



			
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
		if (e.getSource() == btnBuscarPlan) {
			JFileChooser jFileChooser = new JFileChooser();
			jFileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
			jFileChooser.setAcceptAllFileFilterUsed(false);

			int result = jFileChooser.showOpenDialog(this);
			StringBuffer buffer = new StringBuffer();

			if (result == JFileChooser.APPROVE_OPTION) {// abrir
				File file = jFileChooser.getSelectedFile();
				/*
				 * if (!file.isDirectory()) file = file.getParentFile(); file=
				 * file.getCurrentDirectory();
				 */
				txtFieldPlan.setText(file.getAbsolutePath());
			}
		}
	}
}
