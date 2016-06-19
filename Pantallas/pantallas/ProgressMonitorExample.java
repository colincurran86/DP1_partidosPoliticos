package pantallas;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.ProgressMonitor;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import javax.swing.UIManager;

import clasesAux.Util;

public class ProgressMonitorExample  implements ActionListener {

  static ProgressMonitor pbar;
  static int porcentaje = 0 ;
  public static int counter = 0;
  Timer timer = new Timer(1000, this);
  
  public ProgressMonitorExample() {
   // super("Progress Monitor Demo");
	  
	  // UIManager.put("ProgressMonitor.progressText", "Cargando");
	   // UIManager.put("OptionPane.cancelButtonText", "Go Away");
	    //UIManager.
	    
	    
    //setSize(250, 1000);
    //this.setVisible(false);
   // setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

  //  pbar = new ProgressMonitor(null, "Monitoring Progress",  "Initializing . . .", 0, 100);
    

    // Fire a timer every once in a while to update the progress.
  //  System.out.println("nummmm " + num);
  
    
    porcentaje = 10 ;
    timer.start();
  //  setVisible(false);
    
  }

  public static void main(String args[]) {
 //   UIManager.put("ProgressMonitor.progressText", "Cargando");
  //  UIManager.put("OptionPane.cancelButtonText", "Go Away");
  //  new ProgressMonitorExample();
  }

  public void actionPerformed(ActionEvent e) {
    // Invoked by the timer every half second. Simply place
    // the progress monitor update on the event queue.
    SwingUtilities.invokeLater(new Update());
  }

  class Update implements Runnable {
    public void run() {
    /*
      if (pbar.isCanceled()) {
          pbar.close();
        System.exit(1);
      }
      pbar.setProgress(counter);
      pbar.setNote("Operation is " + counter + "% complete");
      */
     counter += porcentaje;
//     int contador2 = 1;
     
  	Procesando.escribirTextArea( "" + counter );
  	
	Procesando.aumentarPorcentaje( counter);
	
	
    if (counter >= 500 + porcentaje){
    	

    	
    	Procesando.escribirTextArea( Util.mensajeFinal );
    	
 
 timer.stop();
   }

    }
  }
}
           
         
    