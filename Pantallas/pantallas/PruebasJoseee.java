package pantallas;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.ProgressMonitor;
import javax.swing.SwingUtilities;

import pantallas.Procesando.Update;

public class PruebasJoseee {

	private JFrame frame;
	static ProgressMonitor pbar;
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					PruebasJoseee window = new PruebasJoseee();
					window.frame.setVisible(true);
					
					
					
					
					
				} catch (Exception e) {
					e.printStackTrace();
				}
				
				
					
				 
			}
		});
		
	
		 
		 
	}

	/**
	 * Create the application.
	 */
	public PruebasJoseee() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	
	  class Update implements Runnable {
		    public void run() {
		      if (pbar.isCanceled()) {
		        pbar.close();
		        System.exit(1);
		      }
		      pbar.setProgress(counter);
		      pbar.setNote("Operation is " + counter + "% complete");
		   //   counter += 2;
		    }
		  }
	 
}
