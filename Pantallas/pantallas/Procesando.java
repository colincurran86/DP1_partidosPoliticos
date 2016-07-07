package pantallas;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;

import org.apache.poi.hssf.record.chart.BarRecord;
import org.imgscalr.Scalr;
import org.imgscalr.Scalr.Method;

import models.PartidoPolitico;
import models.PersonaReniec;
import Firmas.FastBitmap;
import Recorte.Main;
import Reportes.Reportes;
import clasesAux.Util;

import java.awt.Image;
import java.awt.TextArea;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Label;
import java.awt.Font;

public class Procesando extends JPanel {

	// static JProgressBar progressBar = new JProgressBar();
	static public JProgressBar progressBar = new JProgressBar();
	static public TextArea textArea = new TextArea();
static public JLabel labelImage = new JLabel();


	static int porcentaje = 0;
	private static JButton btnGenerarReporteDuplicados;
	private static JButton btnGenerarReporteProceso;

	static final JLabel labelPartidoPolitico = new JLabel("Iniciando . . .");
	static final JLabel labelPadron = new JLabel("Espere por favor . . . ");

	/**
	 * Create the panel.
	 * 
	 * @throws InterruptedException
	 */

	public void run() {
		//aumentarPorcentaje(2);
	}

	public Procesando( ) {
		setLayout(null);

		// JProgressBar progressBar = new JProgressBar();
		progressBar.setBounds(173, 44, 296, 26);
		add(progressBar);
		
	
	//	JLabel labelImage = new JLabel();
//		labelImage.setIcon( ii);
		labelImage.setBounds(88, 309, 203, 202);
		add(labelImage);
		
		

		// JFrame f = new JFrame("JProgressBar Sample");
		// f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// Container content = f.getContentPane();
		// JProgressBar progressBar = new JProgressBar();
		progressBar.setValue(0);
		progressBar.setStringPainted(true);

		textArea.setBounds(137, 95, 380, 160);
		add(textArea);
		
		JLabel lblHuella = new JLabel("Firma procesada: ");
		lblHuella.setBounds(78, 284, 122, 14);
		add(lblHuella);
		
		

		// gerardoRecortesWarning();

		System.out.println("------Inicio del proceso de calculo-----");

		ProcesoBar hilo1 = new ProcesoBar("Hilo 1"); // Proceso de la barrera 
		ProcesoRecorte hilo2 = new ProcesoRecorte("Hilo 2"); // Proceso Principal de recorte
	//	hilo1.setMensaje("Este es el mensaje del hilo 1"); //
	//	hilo2.setMensaje("Mensaje hilo 2"); //
		hilo1.start(); // Proceso de la barrera 
		hilo2.start(); // Proceso Principal de recorte
		/*
		try {
			hilo2.wait();
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}*/
		btnGenerarReporteDuplicados = new JButton("Generar Reporte Duplicados");
		btnGenerarReporteDuplicados.setBounds(324, 572, 193, 23);
		add(btnGenerarReporteDuplicados);
		labelPartidoPolitico.setFont(new Font("Tahoma", Font.PLAIN, 12));
		labelPartidoPolitico.setBounds(10, 24, 234, 14);
		
		add(labelPartidoPolitico);
		labelPadron.setFont(new Font("Tahoma", Font.PLAIN, 12));
		labelPadron.setBounds(20, 48, 146, 14);
		
		add(labelPadron);
		
		 btnGenerarReporteProceso = new JButton("Generar Reporte Proceso");
		btnGenerarReporteProceso.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				
				Reportes pruebaReport = new Reportes(); 
				 
				 //HACEMOS REPORTE AUTOMATICAMENTE FINALIZADO LA PANTALLA PARA LAS PERSONAS DUPLICADAS EN PARTDOS PLITICOS	
				 try {
					pruebaReport.generarReporte(Util.adherentes , Util.partDup , PrimeraFase.ppescogidos);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
		});
		btnGenerarReporteProceso.setBounds(51, 572, 193, 23);
		add(btnGenerarReporteProceso);
		btnGenerarReporteDuplicados.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Reportes pruebaReport = new Reportes();
				 
				 //HACEMOS REPORTE AUTOMATICAMENTE FINALIZADO LA PANTALLA PARA LAS PERSONAS DUPLICADAS EN PARTDOS PLITICOS	
				 try {
					 
					pruebaReport.generarReporteRepetido(Util.partDup);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		
		btnGenerarReporteDuplicados.hide();
		btnGenerarReporteProceso.hide();
		//btnGenerarReporte.disable();
		//if(Util.mostrarReporte){
			
		//}			
	}

	static public void mostrarBoton(){
		btnGenerarReporteDuplicados.show();
		btnGenerarReporteProceso.show();
		//btnGenerarReporte.enable();
	}

	static public void aumentarPorcentaje1(int count) {

	//	count = count + progressBar.getValue();

		progressBar.setValue(count);

	}

	static public void escribirTextArea(String cadena) {

		textArea.append(cadena + "\n");
	}
	
	static public void escribirLabelPartidoPolitico ( String intro) {

		labelPartidoPolitico.setText(   intro );
	}
	
	
	static public void escribirLabelPadron ( String intro) {

		labelPadron.setText(   intro );
	}
	
	
	static public void setearImagenFirma ( BufferedImage imgA  ) {
		
				
		
		//FastBitmap imagenPlanillon = new FastBitmap("C:\\Users\\LUIS S\\Desktop\\Nueva carpeta\\Firmas Java\\bd\\firmas.jpg\\f001.jpg");
	//	BufferedImage imgA = imagenPlanillon.toBufferedImage(); 
		BufferedImage scaledImg = Scalr.resize(imgA, Method.AUTOMATIC, 203,202, Scalr.OP_BRIGHTER);
		FastBitmap imagenPlanillon = new FastBitmap(scaledImg); 
		ImageIcon ii = new ImageIcon ();
		ii = imagenPlanillon.toIcon(); 
		labelImage.setIcon( ii);
		

		
		
	}
	
static public void setearImagenHuella ( BufferedImage imgA  ) {
		
				
		
		//FastBitmap imagenPlanillon = new FastBitmap("C:\\Users\\LUIS S\\Desktop\\Nueva carpeta\\Firmas Java\\bd\\firmas.jpg\\f001.jpg");
	//	BufferedImage imgA = imagenPlanillon.toBufferedImage(); 
		BufferedImage scaledImg = Scalr.resize(imgA, Method.AUTOMATIC, 203,202, Scalr.OP_BRIGHTER);
		FastBitmap imagenPlanillon = new FastBitmap(scaledImg); 
		ImageIcon ii = new ImageIcon ();
		ii = imagenPlanillon.toIcon(); 
		//labelHuella.setIcon( ii);
		

		
		
	}


	public class ProcesoBar extends Thread {

		String mensaje;

		public ProcesoBar(String msg) {
			super(msg);
		}

		public void run() {

//			String workingDir = System.getProperty("user.dir"); // nos evitamos
//																// el problema
//																// de las rutas
//																// :'v
//			int num = new File(workingDir + "/src/Recorte/padrones/").list().length;

			new ProgressMonitorExample();
		}

		public void setMensaje(String msj) {
			this.mensaje = msj;
		}
	}

	public class ProcesoRecorte extends Thread {
		
		String mensaje;

		public ProcesoRecorte(String msg) {
			super(msg);
		}

		public void run() {
			Util u = new Util();
			try {
				u.gerardoRecortesWarning();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		public void setMensaje(String msj) {
			this.mensaje = msj;
		}
	}
}
