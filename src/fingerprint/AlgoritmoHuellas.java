
package fingerprint;

import ij.ImagePlus;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.JOptionPane;

import Catalano.Imaging.FastBitmap;
import Firmas.AlgoritmoFirmas;
import pantallas.Procesando;
import pantallas.ProcesandoSeg;
import models.PersonaReniec;

public class AlgoritmoHuellas {
	
	//public static List<Double> procesarNuevo(List<PersonaReniec> listaPersonasReniec, ImagePlus imageInput, String rutaHuella){
	public static List<Double> procesarNuevo(List<PersonaReniec> listaPersonasReniec, BufferedImage imageInput, String rutaHuella){	
		List<Double> resultado= new ArrayList<Double>();
		
		mainHuellas mhuellas = new mainHuellas();
		for (int i = 0; i < listaPersonasReniec.size(); i ++){
			
			//int idHuella = listaPersonasReniec.get(i).getIdHuella();
			String idHuella = listaPersonasReniec.get(i).getIdHuella();
			try {		
				//System.out.println(rutaHuella + " " + idHuella);
				BufferedImage imageBase = buscarImageBase(idHuella, rutaHuella);
				//Procesando.setearImagenHuella(imageInput.getBufferedImage());
				//ProcesandoSeg.setearImagenHuella(imageInput.getBufferedImage());
				//Procesando.setearImagenHuella(imageInput);
				//ProcesandoSeg.setearImagenHuella(imageInput);
				//double Porcentaje = mhuellas.principal(imageInput.getBufferedImage(), imageBase);
				double Porcentaje = mhuellas.principal(imageInput, imageBase);
				if (Porcentaje == -1) Porcentaje = 0;
				resultado.add(Porcentaje);
				
				
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return resultado; 
		
	}
	
	
	public static BufferedImage buscarImageBase (String idHuella, String rutaHuella) throws IOException{
		String urlQueda;
		String rutaFinal = rutaHuella  + "/" + idHuella + ".png";
		File a=new File(rutaFinal);
		if(!a.exists()){
			rutaFinal=rutaHuella  + "/" + idHuella + ".jpg";			
		}
		
		//System.out.println(rutaFinal);
		
		//FastBitmap aa= new FastBitmap(rutaFinal);
		//JOptionPane.showMessageDialog(null, aa.toIcon(),"",JOptionPane.PLAIN_MESSAGE);
		
		BufferedImage imageBase = ImageIO.read(new File(rutaFinal));
		return imageBase;
	
	}
}
