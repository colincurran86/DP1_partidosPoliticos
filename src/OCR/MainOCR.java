package OCR;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JLabel;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class MainOCR {

	private JFrame frame;
	private JTextField textField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainOCR window = new MainOCR();
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
	public MainOCR() {
		initialize();
		
		
		
		
		
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		textField = new JTextField();
		textField.setBounds(137, 55, 86, 20);
		frame.getContentPane().add(textField);
		textField.setColumns(10);
		
		JButton btnOCRDNI = new JButton("Reconocer");
		btnOCRDNI.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
			//RecogChar rc = new RecogChar();
			
			String ruta  ;
			ruta =textField.getText();
			//RecogChar.recognize_actionPerformedDNI();
				RecogChar recogChar = new RecogChar();
		  		recogChar.setVisible(false);
		  		recogChar.init();
		  		recogChar.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		  		recogChar.setSize(820, 580);
		  //	recogChar.recognize_actionPerformedDNI(ruta); ESPERA UN STRING XD
			
			}
		});
		btnOCRDNI.setBounds(137, 100, 89, 23);
		frame.getContentPane().add(btnOCRDNI);
		
		JLabel lblRutaDni = new JLabel("Ruta DNI");
		lblRutaDni.setBounds(57, 58, 46, 14);
		frame.getContentPane().add(lblRutaDni);
	}
}
