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
import clasesAux.Util;

import java.awt.Image;
import java.awt.TextArea;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class Procesando extends JPanel {

	// static JProgressBar progressBar = new JProgressBar();
	static public JProgressBar progressBar = new JProgressBar();
	static public TextArea textArea = new TextArea();
static public JLabel labelImage = new JLabel();
	static int porcentaje = 0;

	/**
	 * Create the panel.
	 * 
	 * @throws InterruptedException
	 */

	public void run() {
		aumentarPorcentaje(2);
	}

	public Procesando( ) {
		setLayout(null);

		// JProgressBar progressBar = new JProgressBar();
		progressBar.setBounds(118, 46, 296, 26);
		add(progressBar);
		
	
	//	JLabel labelImage = new JLabel();
//		labelImage.setIcon( ii);
		labelImage.setBounds(340, 309, 203, 202);
		add(labelImage);
		
		

		// JFrame f = new JFrame("JProgressBar Sample");
		// f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// Container content = f.getContentPane();
		// JProgressBar progressBar = new JProgressBar();
		progressBar.setValue(0);
		progressBar.setStringPainted(true);

		textArea.setBounds(83, 100, 380, 160);
		add(textArea);
		
		JLabel lblHuella = new JLabel("Firma procesada: ");
		lblHuella.setBounds(330, 284, 122, 14);
		add(lblHuella);

		// gerardoRecortesWarning();

		System.out.println("------Inicio del proceso de calculo-----");

		ProcesoBar hilo1 = new ProcesoBar("Hilo 1"); // Proceso de la barrera 
		ProcesoRecorte hilo2 = new ProcesoRecorte("Hilo 2"); // Proceso Principal de recorte
	//	hilo1.setMensaje("Este es el mensaje del hilo 1"); //
	//	hilo2.setMensaje("Mensaje hilo 2"); //
		hilo1.start(); // Proceso de la barrera 
		hilo2.start(); // Proceso Principal de recorte

	}



	static public void aumentarPorcentaje(int count) {

		count = count + progressBar.getValue();

		progressBar.setValue(count);

	}

	static public void escribirTextArea(String cadena) {

		textArea.append(cadena + "\n");
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
			u.gerardoRecortesWarning();
		}

		public void setMensaje(String msj) {
			this.mensaje = msj;
		}
	}
}
