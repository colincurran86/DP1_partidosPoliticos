package pantallas;

import java.awt.EventQueue;
import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;

import models.Rol;
import models.Usuario;
import bModel.ProcessManager;
import clasesAux.JTextFieldLimit;

import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.ActionEvent;
import javax.swing.JCheckBox;

public class Login {

	private JFrame frame;
	private JTextField txtUsuario;
	private JPasswordField txtPassword;

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
		frame.setTitle("Log In");
		frame.getContentPane().setLayout(null);

		ImageIcon img = new ImageIcon("lib/login.png");
		frame.setIconImage(img.getImage());
		
		JButton btnIngresar = new JButton("Ingresar");
		btnIngresar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				if (txtUsuario.getText().equals("") || txtPassword.getText().equals("")) {
					JOptionPane.showMessageDialog(null, "Rellene los campos");				
				} else {
					Usuario u = new Usuario();
					Rol r = new Rol();
					r.setDescripcion("admin");

					u.setNombre(txtUsuario.getText());
					u.setPassword(txtPassword.getText());
					u.setRol(r);
					
					try{
						if (ProcessManager.search(u)) {
							//Principal window = new Principal();
							Principal carga = new Principal();
							frame.setVisible(false);
							carga.frame.setVisible(true);
							
						} else {
							JOptionPane.showMessageDialog(null, "Usuario incorrecto");	
						};
					}catch(Exception ex){
						JOptionPane.showMessageDialog(null, "Fallo el proceso");
					}
					
					// System.out.println( "Ingreso"); else
					// System.out.println("STOP");
				}
			}
		});
		btnIngresar.setBounds(67, 178, 89, 23);
		frame.getContentPane().add(btnIngresar);

		txtUsuario = new JTextField();
		txtUsuario.setBounds(113, 61, 184, 20);
		frame.getContentPane().add(txtUsuario);
		txtUsuario.setColumns(10);
		txtUsuario.setDocument(new JTextFieldLimit(90));

		JButton btnSalir = new JButton("Salir");
		btnSalir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		btnSalir.setBounds(196, 178, 89, 23);
		frame.getContentPane().add(btnSalir);

		txtPassword = new JPasswordField();
		txtPassword.setBounds(113, 92, 184, 20);
		frame.getContentPane().add(txtPassword);
		txtPassword.setColumns(10);
		txtPassword.setEchoChar('*');
		txtPassword.setDocument(new JTextFieldLimit(20));

		JLabel lblUsuario = new JLabel("Usuario");
		lblUsuario.setBounds(34, 64, 56, 14);
		frame.getContentPane().add(lblUsuario);

		JLabel lblContrasea = new JLabel("Contrase\u00F1a");
		lblContrasea.setBounds(34, 95, 69, 14);
		frame.getContentPane().add(lblContrasea);

		JCheckBox chckbx = new JCheckBox("Mostrar contrase\u00F1a");
		chckbx.setBounds(154, 136, 172, 23);
		frame.getContentPane().add(chckbx);
		
		JPanel panelReg = new JPanel();
		panelReg.setBorder(
				new TitledBorder(null, "Sistema de verificacion de planillones", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panelReg.setBounds(21, 30, 318, 103);
		frame.getContentPane().add(panelReg);
		panelReg.setLayout(new GridLayout(1, 1, 0, 0));


		chckbx.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				// TODO Auto-generated method stub
				if (e.getStateChange() == ItemEvent.SELECTED) {// checkbox has
																// been selected
					txtPassword.setEchoChar((char) 0);
				} else {// checkbox has been deselected
					txtPassword.setEchoChar('*');
				}
				;
			}
		});
	}
}
