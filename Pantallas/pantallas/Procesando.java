package pantallas;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;

import org.apache.poi.hssf.record.chart.BarRecord;

import models.PersonaReniec;
import Recorte.Main;
import clasesAux.Util;

import java.awt.TextArea;
import java.io.File;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class Procesando extends JPanel {

	// static JProgressBar progressBar = new JProgressBar();
	static public JProgressBar progressBar = new JProgressBar();
	static public TextArea textArea = new TextArea();

	static int porcentaje = 0;

	/**
	 * Create the panel.
	 * 
	 * @throws InterruptedException
	 */

	public void run() {
		aumentarPorcentaje(2);
	}

	public Procesando() {
		setLayout(null);

		// JProgressBar progressBar = new JProgressBar();
		progressBar.setBounds(118, 46, 296, 26);
		add(progressBar);

		// JFrame f = new JFrame("JProgressBar Sample");
		// f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// Container content = f.getContentPane();
		// JProgressBar progressBar = new JProgressBar();
		progressBar.setValue(0);
		progressBar.setStringPainted(true);

		textArea.setBounds(83, 100, 380, 160);
		add(textArea);

		// gerardoRecortesWarning();

		System.out.println("aentre hilo util hilo bar");

		ProcesoBar hilo1 = new ProcesoBar("Hilo 1");
		ProcesoRecorte hilo2 = new ProcesoRecorte("Hilo 2");
		hilo1.setMensaje("Este es el mensaje del hilo 1");
		hilo2.setMensaje("Mensaje hilo 2");
		hilo1.start();
		hilo2.start();

	}

	public void gerardoRecortesWarning() {
		// ir a util
	}

	static public void aumentarPorcentaje(int count) {

		count = count + progressBar.getValue();

		progressBar.setValue(count);

	}

	static public void escribirTextArea(String cadena) {

		textArea.append(cadena + "\n");

	}

	public class ProcesoBar extends Thread {

		String mensaje;

		public ProcesoBar(String msg) {
			super(msg);
		}

		public void run() {

			String workingDir = System.getProperty("user.dir"); // nos evitamos
																// el problema
																// de las rutas
																// :'v
			int num = new File(workingDir + "/src/Recorte/padrones/").list().length;

			new ProgressMonitorExample(num);
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
