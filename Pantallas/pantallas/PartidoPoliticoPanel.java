package pantallas;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;

import models.PartidoPolitico;
import bModel.ProcessManager;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class PartidoPoliticoPanel extends JPanel {
	private JTextField txtFieldNombre;
	private JTextField txtFieldNRep;
	private JTextField txtFieldTel;
	private JTextField txtFieldCorreo;

	/**
	 * Create the panel.
	 */
	public PartidoPoliticoPanel() {
		
		setLayout(null);
		
		JLabel lblNombre = new JLabel("Nombre");
		lblNombre.setBounds(28, 24, 46, 14);
		add(lblNombre);
		
		JLabel lblNombreRep = new JLabel("Nombre de representante");
		lblNombreRep.setBounds(28, 55, 143, 14);
		add(lblNombreRep);
		
		txtFieldNombre = new JTextField();
		txtFieldNombre.setBounds(181, 18, 174, 20);
		add(txtFieldNombre);
		txtFieldNombre.setColumns(10);
		
		txtFieldNRep = new JTextField();
		txtFieldNRep.setBounds(181, 52, 173, 20);
		add(txtFieldNRep);
		txtFieldNRep.setColumns(10);
		
		JButton btnGuardar = new JButton("Guardar");
		btnGuardar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				PartidoPolitico p=new PartidoPolitico();
				p.setCorreo(txtFieldCorreo.getText());
				p.setNombre(txtFieldNombre.getText());
				p.setNombreRep(txtFieldNRep.getText());
				p.setTelefono(txtFieldTel.getText());
				ProcessManager.addPartPolitico(p);
			}
		});
		btnGuardar.setBounds(82, 193, 89, 23);
		add(btnGuardar);
		
		JButton btnCancelar = new JButton("Cancelar");
		btnCancelar.setBounds(229, 193, 89, 23);
		add(btnCancelar);
		
		JLabel lblTelefono = new JLabel("Telefono Representante");
		lblTelefono.setBounds(28, 86, 129, 14);
		add(lblTelefono);
		
		txtFieldTel = new JTextField();
		txtFieldTel.setBounds(180, 83, 175, 20);
		add(txtFieldTel);
		txtFieldTel.setColumns(10);
		
		JLabel lblCorreo = new JLabel("Correo");
		lblCorreo.setBounds(28, 117, 46, 14);
		add(lblCorreo);
		
		txtFieldCorreo = new JTextField();
		txtFieldCorreo.setBounds(181, 114, 173, 20);
		add(txtFieldCorreo);
		txtFieldCorreo.setColumns(10);

	}
}
