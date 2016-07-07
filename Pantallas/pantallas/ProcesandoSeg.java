package pantallas;

import java.awt.Font;
import java.awt.TextArea;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;

import org.imgscalr.Scalr;
import org.imgscalr.Scalr.Method;

import Firmas.FastBitmap;
import Reportes.Reportes;
import clasesAux.Util;
import pantallas.Procesando.ProcesoBar;
import pantallas.Procesando.ProcesoRecorte;

public class ProcesandoSeg extends JPanel {

	/**
	 * Create the panel.
	 */
	
	// static JProgressBar progressBar = new JProgressBar();
	static public JProgressBar progressBar = new JProgressBar();
	static public TextArea textArea = new TextArea();
	static public JLabel labelImage = new JLabel();
	
	static int porcentaje = 0;
	private static JButton btnGenerarReporte;
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

	public ProcesandoSeg( ) {
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
		btnGenerarReporte = new JButton("Generar Reporte");
		btnGenerarReporte.setBounds(270, 552, 129, 23);
		add(btnGenerarReporte);
		labelPartidoPolitico.setFont(new Font("Tahoma", Font.PLAIN, 12));
		labelPartidoPolitico.setBounds(10, 24, 234, 14);
		
		add(labelPartidoPolitico);
		labelPadron.setFont(new Font("Tahoma", Font.PLAIN, 12));
		labelPadron.setBounds(20, 48, 146, 14);
		
		add(labelPadron);
		btnGenerarReporte.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Reportes pruebaReport = new Reportes();
				 
				 //HACEMOS REPORTE AUTOMATICAMENTE FINALIZADO LA PANTALLA PARA LAS PERSONAS DUPLICADAS EN PARTDOS PLITICOS	
				 try {
					pruebaReport.generarReporte(Util.adSinPrimFase , Util.partDup , SegundaFase.ppescogidosSeg);
					//pruebaReport.generarReporteRepetido(Util.partDup);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		
		btnGenerarReporte.hide();
		//btnGenerarReporte.disable();
		//if(Util.mostrarReporte){
			
		//}			
	}

	static public void mostrarBoton(){
		btnGenerarReporte.show();
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
				System.out.println();
				u.gerardoRecortesWarningSeg();
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
