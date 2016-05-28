package pantallas;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JLabel;

import models.Rol;
import models.Usuario;
import bModel.ProcessManager;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Login {

	private JFrame frame;
	private JTextField txtUsuario;
	private JTextField txtPassword;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Login window = new Login();
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
	public Login() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 381, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JButton btnIngresar = new JButton("Ingresar");
		btnIngresar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				
				Usuario u = new Usuario () ;
				Rol r = new Rol () ;
				r.setDescripcion( "admin");
				
				u.setNombre( txtUsuario.getText());
				u.setPassword(txtPassword.getText());
				u.setRol(r);
				
		if (	ProcessManager.search(u ) ) 
			
		{
			
			
			
			
			
			
			Principal window = new Principal();
	 
			
			
			Principal carga =new Principal();
			carga.frame.setVisible(true);
		
			
			
			
			
		}
		else {System.out.println("No ingreso");
		
		
		};
		
	
			
			//System.out.println( "Ingreso"); else  System.out.println("STOP");
						
						
						
				
				
			}
		});
		btnIngresar.setBounds(71, 151, 89, 23);
		frame.getContentPane().add(btnIngresar);
		
		txtUsuario = new JTextField();
		txtUsuario.setBounds(113, 61, 172, 20);
		frame.getContentPane().add(txtUsuario);
		txtUsuario.setColumns(10);
		
		JButton btnSalir = new JButton("Salir");
		btnSalir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				
				
			}
		});
		btnSalir.setBounds(196, 151, 89, 23);
		frame.getContentPane().add(btnSalir);
		
		txtPassword = new JTextField();
		txtPassword.setBounds(113, 92, 172, 20);
		frame.getContentPane().add(txtPassword);
		txtPassword.setColumns(10);
		
		JLabel lblUsuario = new JLabel("Usuario");
		lblUsuario.setBounds(34, 64, 56, 14);
		frame.getContentPane().add(lblUsuario);
		
		JLabel lblContrasea = new JLabel("Contrase\u00F1a");
		lblContrasea.setBounds(34, 95, 69, 14);
		frame.getContentPane().add(lblContrasea);
	}
}
