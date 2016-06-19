package pantallas;

import javax.swing.JPanel;
import javax.swing.JToggleButton;
import javax.swing.UIManager;

import models.PartidoPolitico;
import models.TipoProceso;

import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JLabel;

public class Carga extends JPanel implements ActionListener{
	private JButton btnPrimCarga;
	private JButton btnSegCarga;
	private JButton btnConf;
	public static int idPE=0;
	public static int idPP=0;
	public static int porc=-1;
	public static int choiceCM=0;
	public static int choiceCI=0;

	public static List<PartidoPolitico> ppEscogidos = new ArrayList<PartidoPolitico>() ;
	private JLabel lblNfiguracion;
	
	
	/**
	 * Create the panel.
	 */
	public Carga() {
		setLayout(null);

		btnPrimCarga = new JButton("Primera fase");		
		btnPrimCarga.setBounds(235, 187, 162, 57);
		add(btnPrimCarga);

		btnSegCarga = new JButton("Segunda fase");
		btnSegCarga.setBounds(235, 379, 162, 57);
		add(btnSegCarga);

		btnConf = new JButton("");
		btnConf.setBounds(62, 127, 50, 49);
		/*btnConf.setIcon(new ImageIcon(getClass().getResource("/Pantallas/pantallas/conf.png")));
		try {
			Image img = ImageIO.read(getClass().getResource("/Pantallas/pantallas/conf.png"));
			btnConf.setIcon(new ImageIcon(img));
		} catch (IOException ex) {
		}*/
		Icon i = new ImageIcon("lib/conf.png");
		
		btnConf.setIcon(i);
		

		/*
		 * JToggleButton button = new
		 * JToggleButton(UIManager.getIcon("OptionPane.informationIcon"));
		 * button.setSelectedIcon(UIManager.getIcon("OptionPane.errorIcon"));
		 * button.setBounds(21, 21, 50, 49); add(button);
		 */
		add(btnConf);
		btnConf.addActionListener(this);
		btnPrimCarga.addActionListener(this);
		btnSegCarga.addActionListener(this);
		
		lblNfiguracion = new JLabel("CONFIGURACION");
		lblNfiguracion.setBounds(44, 187, 136, 14);
		add(lblNfiguracion);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == btnConf) {
			//try{
				Configuracion c= new Configuracion();
				c.setVisible(true);
				
//			}catch(Exception ex){
//				JOptionPane.showMessageDialog(null, "Hubo un error al abrir la ventana de configuracion	");
//			}			
//			
			
			

		}
		if(e.getSource()==btnPrimCarga){
			if(porc==-1)
				JOptionPane.showMessageDialog(null, "Escoja los parámetros en la tuerca de configuración");
			else{
				PrimeraFase pf =new PrimeraFase();
				pf.idPE=idPE;
				pf.idPP=idPP;
				pf.porc=porc;
				pf.choiceCI=choiceCI;
				pf.choiceCM=choiceCM;
				pf.ppescogidos = ppEscogidos;
				pf.setVisible(true);
				
				Principal.getFrame().getContentPane().setVisible(false);
				Principal.getFrame().setContentPane(pf);
				
				//System.out.println(idPP + " " + idPE);				
			}
			
		}
		if(e.getSource()==btnSegCarga){
			
		}
	}
}
