package pantallas;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JProgressBar;
import javax.swing.Timer;

import java.awt.Container;
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
	private JTextField txtFieldBDRNV;
	private JTextField txtFieldBDHuellas;
	private JTextField txtFieldBDFirmas;
	private JButton btnProcesar;
	private JButton btnCancelar;
	private JButton btnBuscarBDRNV;
	private JButton btnBuscarPlan;
	private JButton btnBuscarBDHuellas;
	private JButton btnBuscarBDFirmas;
	public static int idPE = 0;
	public static int idPP = 0;
	public static int porc = -1;
	public static int choiceCM = 0;
	public static int choiceCI = 0;
	public static String rutaPadrones = new String();
	public static String rutaExcel = new String();
	private JTextField txtFieldRecortes;
	private JButton btnBuscarRecortes;
	private JLabel lblRutaDondeSe;

	/**
	 * Create the panel.
	 */
	public PrimeraFase() {
		setLayout(null);

		JLabel lblRutaDeLos = new JLabel("Ruta de los planillones");
		lblRutaDeLos.setBounds(90, 48, 291, 14);
		add(lblRutaDeLos);

		JLabel lblRutaDeLa = new JLabel("Ruta de la Base de Datos de la RNV");
		lblRutaDeLa.setBounds(90, 107, 302, 14);
		add(lblRutaDeLa);

		btnProcesar = new JButton("Procesar");
		btnProcesar.setBounds(153, 496, 103, 38);
		add(btnProcesar);

		btnCancelar = new JButton("Cancelar");
		btnCancelar.setBounds(407, 496, 103, 38);
		add(btnCancelar);

		txtFieldPlan = new JTextField();
		txtFieldPlan.setBounds(90, 74, 339, 20);
		add(txtFieldPlan);
		txtFieldPlan.setColumns(10);

		txtFieldBDRNV = new JTextField();
		txtFieldBDRNV.setBounds(90, 132, 339, 20);
		add(txtFieldBDRNV);
		txtFieldBDRNV.setColumns(10);

		btnBuscarBDRNV = new JButton("Buscar");
		btnBuscarBDRNV.setBounds(432, 131, 89, 23);
		add(btnBuscarBDRNV);

		btnBuscarPlan = new JButton("Buscar ");
		btnBuscarPlan.setBounds(432, 73, 89, 23);
		add(btnBuscarPlan);

		JLabel lblRutaDeLa_1 = new JLabel("Ruta de la Base de Datos de Huellas");
		lblRutaDeLa_1.setBounds(90, 163, 302, 14);
		add(lblRutaDeLa_1);

		txtFieldBDHuellas = new JTextField();
		txtFieldBDHuellas.setColumns(10);
		txtFieldBDHuellas.setBounds(90, 188, 339, 20);
		add(txtFieldBDHuellas);

		btnBuscarBDHuellas = new JButton("Buscar");
		btnBuscarBDHuellas.setBounds(432, 187, 89, 23);
		add(btnBuscarBDHuellas);

		JLabel lblRutaDeLa_2 = new JLabel("Ruta de la Base de Datos de Firmas");
		lblRutaDeLa_2.setBounds(90, 219, 302, 14);
		add(lblRutaDeLa_2);

		txtFieldBDFirmas = new JTextField();
		txtFieldBDFirmas.setColumns(10);
		txtFieldBDFirmas.setBounds(90, 244, 339, 20);
		add(txtFieldBDFirmas);

		btnBuscarBDFirmas = new JButton("Buscar");
		btnBuscarBDFirmas.setBounds(432, 243, 89, 23);
		add(btnBuscarBDFirmas);

		
		
		txtFieldRecortes = new JTextField();
		txtFieldRecortes.setBounds(90, 338, 339, 20);
		add(txtFieldRecortes);
		txtFieldRecortes.setColumns(10);
		
		btnBuscarRecortes = new JButton("Buscar");
		btnBuscarRecortes.setBounds(432, 337, 89, 23);
		add(btnBuscarRecortes);
		
		lblRutaDondeSe = new JLabel("Ruta donde se almacenar\u00E1n los recortes");
		lblRutaDondeSe.setBounds(90, 313, 317, 14);
		add(lblRutaDondeSe);
		
		
		btnProcesar.addActionListener(this);
		btnCancelar.addActionListener(this);
		btnBuscarBDRNV.addActionListener(this);
		btnBuscarPlan.addActionListener(this);
		btnBuscarBDHuellas.addActionListener(this);
		btnBuscarBDFirmas.addActionListener(this);
		btnBuscarRecortes.addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		String res;
		if (e.getSource() == btnCancelar) {
			Carga carga = new Carga();
			carga.setVisible(true);
			Principal.getFrame().getContentPane().setVisible(false);
			Principal.getFrame().setContentPane(carga);
		}
		if (e.getSource() == btnProcesar) {

			Procesando p1;
			p1 = new Procesando();
			p1.setVisible(true);
			rutaPadrones = txtFieldPlan.getText();
			rutaExcel = txtFieldBDRNV.getText();
			Principal.getFrame().getContentPane().setVisible(false);
			Principal.getFrame().setContentPane(p1);

		}

		if (e.getSource() == btnBuscarBDRNV) {
			JFileChooser jFileChooser = new JFileChooser();
			jFileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
			jFileChooser.setAcceptAllFileFilterUsed(false);

			int result = jFileChooser.showOpenDialog(this);
			StringBuffer buffer = new StringBuffer();

			if (result == JFileChooser.APPROVE_OPTION) {// abrir
				File file = jFileChooser.getSelectedFile();
				txtFieldBDRNV.setText(file.getAbsolutePath());

			}
		}
		if (e.getSource() == btnBuscarPlan) {
			JFileChooser jFileChooser = new JFileChooser();
			jFileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
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
		if (e.getSource() == btnBuscarBDHuellas) {
			JFileChooser jFileChooser = new JFileChooser();
			jFileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
			jFileChooser.setAcceptAllFileFilterUsed(false);

			int result = jFileChooser.showOpenDialog(this);
			StringBuffer buffer = new StringBuffer();

			if (result == JFileChooser.APPROVE_OPTION) {// abrir
				File file = jFileChooser.getSelectedFile();
				/*
				 * if (!file.isDirectory()) file = file.getParentFile(); file=
				 * file.getCurrentDirectory();
				 */
				txtFieldBDHuellas.setText(file.getAbsolutePath());
			}
		}
		if (e.getSource() == btnBuscarBDFirmas) {
			JFileChooser jFileChooser = new JFileChooser();
			jFileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
			jFileChooser.setAcceptAllFileFilterUsed(false);

			int result = jFileChooser.showOpenDialog(this);
			StringBuffer buffer = new StringBuffer();

			if (result == JFileChooser.APPROVE_OPTION) {// abrir
				File file = jFileChooser.getSelectedFile();
				/*
				 * if (!file.isDirectory()) file = file.getParentFile(); file=
				 * file.getCurrentDirectory();
				 */
				txtFieldBDFirmas.setText(file.getAbsolutePath());
			}
		}
		if(e.getSource()==btnBuscarRecortes){
			JFileChooser jFileChooser = new JFileChooser();
			jFileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
			jFileChooser.setAcceptAllFileFilterUsed(false);

			int result = jFileChooser.showOpenDialog(this);
			StringBuffer buffer = new StringBuffer();

			if (result == JFileChooser.APPROVE_OPTION) {// abrir
				File file = jFileChooser.getSelectedFile();
				/*
				 * if (!file.isDirectory()) file = file.getParentFile(); file=
				 * file.getCurrentDirectory();
				 */
				txtFieldRecortes.setText(file.getAbsolutePath());
			}
		}
	}

	private String camposNull() {
		boolean v = false;
		String resultado = "";
		if (txtFieldPlan.getText().equals("")) {
			resultado += "- Planillon\n";
			v = true;
		}

		if (txtFieldBDRNV.getText().equals("")) {
			resultado += "- Base de datos de la RNV\n";
			v = true;
		}

		if (txtFieldBDHuellas.getText().equals("")) {
			resultado += "- Base de datos de Huellas\n";
			v = true;
		}

		if (txtFieldBDFirmas.getText().equals("")) {
			resultado += "- Base de datos de Firmas\n";
			v = true;
		}

		return resultado;
	}
}
