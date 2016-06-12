package pantallas;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;

import models.PersonaReniec;
import Recorte.Main;
import clasesAux.Util;

import java.awt.TextArea;
import java.util.List;

public class Procesando extends JPanel {

	//static JProgressBar progressBar = new JProgressBar();
	static public JProgressBar progressBar = new JProgressBar();
	static public TextArea textArea = new TextArea();
    
	static int porcentaje = 0 ;
	/**
	 * Create the panel.
	 * @throws InterruptedException 
	 */
	public Procesando() throws InterruptedException {
		setLayout(null);
		
		//JProgressBar progressBar = new JProgressBar();
		progressBar.setBounds(118, 46, 296, 26);
		add(progressBar);
		
		
		//  JFrame f = new JFrame("JProgressBar Sample");
		  //  f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		   // Container content = f.getContentPane();
		    //JProgressBar progressBar = new JProgressBar();
		    progressBar.setValue(25);
		    progressBar.setStringPainted(true);
		    
	
		    textArea.setBounds(83, 100, 380, 160);
		    add(textArea);
		   
		    
		    

	
			    
			//    System.out.println("13213");
	
				
			long startTime = System.currentTimeMillis();
			
			Thread.sleep(22);

		    Thread.sleep(4000);
				
				
			
			//	System.out.println("Procesando");

				Util u = new Util();
				Main m = new Main();
				// txtFieldPlan.setText("D:\\Users\\jemarroquin\\git\\DP1_partidosPoliticos\\src\\Recorte\\Padrones");
				// String formatearRutaPlan = u.formatearRuta(txtFieldPlan.getText());
				m.main("D:\\Users\\jemarroquin\\git\\DP1_partidosPoliticos\\src\\Recorte\\Padrones");
				
			//	txtFieldBDRNV.setText("D:\\Users\\jemarroquin\\git\\DP1_partidosPoliticos\\src");
			//	String formatearRutaBD = u.formatearRuta(txtFieldBDRNV.getText());
				u.llenarBDReniec("D:\\Users\\jemarroquin\\git\\DP1_partidosPoliticos\\src" + "/registro.nacional.v.1.xlsx");

				
				escribirTextArea( "****************************");
				//System.out.println("**************************************************");
				//System.out.println("DNIasdasdsd");
				//System.out.println("**************************************************");


				escribirTextArea("DNI");
				
				escribirTextArea("******************************************");
				
				
				List<PersonaReniec> pr1 = u.ocrMasReniec();

				System.out.println("**************************************************");
				System.out.println("FIRMAS Reconocidas : ");
				System.out.println("**************************************************");


		    
		    
				// Firmas

				// Main mainRecorte = new Main();

				/*descomentar aquiii2222
				List<String> idFirmasLst = new ArrayList<String>();
				List<Integer> idRegistroLst = new ArrayList<Integer>();

				// si no encuentra el dni, no considera la firma :v
				for (int i = 0; i < pr1.size(); i++) {
					if (pr1.get(i) != null) {
						idFirmasLst.add(pr1.get(i).getIdFirma());
						idRegistroLst.add(i + 1);
					} else {
						idFirmasLst.add("-1");
						idRegistroLst.add(i + 1);
					}
				}

				List<Resultado> listaTemporalPersona = null;
				System.out.println("Inicio firmas:");
				AlgoritmoFirmas algoritmoFrimas = new AlgoritmoFirmas();

				try {
					listaTemporalPersona = algoritmoFrimas.verificarFirmas6(idRegistroLst, idFirmasLst, Main.listaBImage,
							u.formatearRuta2(formatearRutaBD + "/firmas.jpg/"));
					System.out.println("Porcentaje de Firmas ");
					for (int i = 0; i < listaTemporalPersona.size(); i++) {
						System.out.println("% " + listaTemporalPersona.get(i).porcentaje + " IDPersona:  "
								+ listaTemporalPersona.get(i).idPersona);
					}

					System.out.println("Fin firmas:");
					System.out.println("**************************************************");

				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
*/ // descomentar aquiii222
				long endTime = System.currentTimeMillis();
				double totalTime = (endTime - startTime) / 1000.0;
		//		System.out.println("Finalizado");
			//	System.out.println("El tiempo total de ejecucion del programa fue " + totalTime + " segundos");
				
				escribirTextArea(  "El tiempo total de ejecucion del programa fue " + totalTime + " segundos"   );
				
aumentarPorcentaje(100 );
			}
			
		    
		    
		    
		    
		    
		

	
	public void aumentarPorcentaje ( int count ) {
		
		porcentaje = count ; 
	    progressBar.setValue(porcentaje);
		
	} 
	
	public void escribirTextArea ( String cadena ) {
		
		
		textArea.append(cadena+ "\n");
		
		
	} 
}


