package pantallas;

import javax.swing.JPanel;
import javax.swing.JToggleButton;
import javax.swing.UIManager;

import org.imgscalr.Scalr;
import org.imgscalr.Scalr.Method;

import Firmas.FastBitmap;
import clasesAux.SegFase;
import models.PartidoPolitico;
import models.TipoProceso;

import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
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
import javax.swing.SwingConstants;

public class Carga extends JPanel implements ActionListener{
	private JButton btnPrimCarga;
	private JButton btnSegCarga;
	private JButton btnConf;
	private JButton  btnConfSeg;
	public static int idPE=0;
	public static int idPP=0;
	public static int porc=-1;
	public static int choiceCM=0;
	public static int choiceCI=0;
	public static List<PartidoPolitico> ppEscogidos = new ArrayList<PartidoPolitico>() ;
	
	public static int idPESeg=0;
	public static int idPPSeg=0;
	public static int porcSeg=-1;
	public static int choiceCMSeg=0;
	public static int choiceCISeg=0;
	public static List<PartidoPolitico> ppEscogidosSeg = new ArrayList<PartidoPolitico>() ;
	
	private JLabel lblNfiguracion;
	
	
	/**
	 * Create the panel.
	 */
	public Carga() {
		setLayout(null);

		btnPrimCarga = new JButton("Realizar Primera Fase");		
		btnPrimCarga.setBounds(188, 210, 237, 115);
		add(btnPrimCarga);

		btnSegCarga = new JButton("Realizar Segunda Fase");
		btnSegCarga.setBounds(188, 385, 237, 115);
		add(btnSegCarga);

		btnConf = new JButton("");
		btnConf.setBounds(68, 77, 75, 60);
		/*btnConf.setIcon(new ImageIcon(getClass().getResource("/Pantallas/pantallas/conf.png")));
		try {
			Image img = ImageIO.read(getClass().getResource("/Pantallas/pantallas/conf.png"));
			btnConf.setIcon(new ImageIcon(img));
		} catch (IOException ex) {
		}*/
		//Icon i = new ImageIcon("lib/conf.png");
		Icon i = new ImageIcon(this.getClass().getResource("/conf.png"));
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
		
		lblNfiguracion = new JLabel("PARAMETROS DE \r\nCONFIGURACI\u00D3N");
		lblNfiguracion.setBounds(24, 22, 255, 34);
		add(lblNfiguracion);
		
		
		
	//	BufferedImage scaledImg = Scalr.resize(imgA, Method.AUTOMATIC, 203,202, Scalr.OP_BRIGHTER);
	//	FastBitmap imagenPlanillon = new FastBitmap(scaledImg); 
	//	ImageIcon ii = new ImageIcon ();
	//	ii = imagenPlanillon.toIcon(); 
	//	labelImage.setIcon( ii);
		
		
		JLabel lblImage = new JLabel("");
		ImageIcon img2 = new ImageIcon("lib/echeck.png");
		lblImage.setBounds(385, 11, 249, 170);
		lblImage.setIcon(img2);
		add(lblImage);
		
		JLabel lblPrimeraFase = new JLabel("Primera Fase");
		lblPrimeraFase.setBounds(78, 148, 89, 14);
		add(lblPrimeraFase);
		
		JLabel label = new JLabel("PARAMETROS DE \r\nCONFIGURACI\u00D3N");
		label.setBounds(24, 489, 255, 34);
		add(label);
		
		btnConfSeg = new JButton("");
		btnConfSeg.setBounds(68, 544, 75, 60);
		btnConfSeg.setIcon(i);
		
		add(btnConfSeg);
		
		JLabel lblSegundaFase = new JLabel("Segunda Fase");
		lblSegundaFase.setBounds(78, 615, 89, 14);
		add(lblSegundaFase);
		
		btnConfSeg.addActionListener(this);
		
		
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
			if(porcSeg==-1)
				JOptionPane.showMessageDialog(null, "No se puede realizar la 2da fase");
		}
		
		if(e.getSource()==btnConfSeg){
			if(SegFase.validarRechazados()>0){
				ConfiguracionSeg c= new ConfiguracionSeg();
				c.setVisible(true);
			}else
				JOptionPane.showMessageDialog(null, "No existen partidos rechazados para poder realizar la 2da fase");
		}
	}
}
