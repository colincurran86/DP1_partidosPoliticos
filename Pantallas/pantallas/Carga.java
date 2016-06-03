package pantallas;

import javax.swing.JPanel;
import javax.swing.JToggleButton;
import javax.swing.UIManager;

import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;

public class Carga extends JPanel implements ActionListener{
	private JButton btnPrimCarga;
	private JButton btnSegCarga;
	private JButton btnConf;
	/**
	 * Create the panel.
	 */
	public Carga() {
		setLayout(null);

		JButton btnPrimCarga = new JButton("Primera fase");
		btnPrimCarga.setBounds(162, 44, 120, 23);
		add(btnPrimCarga);

		JButton btnSegCarga = new JButton("Segunda fase");
		btnSegCarga.setBounds(162, 123, 120, 23);
		add(btnSegCarga);

		JButton btnConf = new JButton("");
		btnConf.setBounds(21, 21, 50, 49);
		/*btnConf.setIcon(new ImageIcon(getClass().getResource("/Pantallas/pantallas/conf.png")));
		try {
			Image img = ImageIO.read(getClass().getResource("/Pantallas/pantallas/conf.png"));
			btnConf.setIcon(new ImageIcon(img));
		} catch (IOException ex) {
		}
		Icon i = new ImageIcon("/Pantallas/pantallas/conf.png");
		btnConf.setIcon(i);*/
		

		/*
		 * JToggleButton button = new
		 * JToggleButton(UIManager.getIcon("OptionPane.informationIcon"));
		 * button.setSelectedIcon(UIManager.getIcon("OptionPane.errorIcon"));
		 * button.setBounds(21, 21, 50, 49); add(button);
		 */
		add(btnConf);
		btnConf.addActionListener(this);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == btnConf) {
			
		}
	}
}
