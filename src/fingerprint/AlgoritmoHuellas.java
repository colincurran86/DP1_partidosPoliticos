package fingerprint;

import ij.ImagePlus;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;

import models.PersonaReniec;

public class AlgoritmoHuellas {
	
	public static void procesarNuevo(List<PersonaReniec> listaPersonasReniec, ImagePlus imageInput, String rutaHuella){
		
		
		mainHuellas mhuellas = new mainHuellas();
		for (int i = 0; i < listaPersonasReniec.size(); i ++){
			
			int idHuella = listaPersonasReniec.get(i).getIdHuella();
			try {		
				
				BufferedImage imageBase = buscarImageBase(idHuella, rutaHuella);
				mhuellas.principal(imageInput.getBufferedImage(), imageBase);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		
	}
	
	
	public static BufferedImage buscarImageBase (int idHuella, String rutaHuella) throws IOException{

		String strI = Integer.toString(idHuella);
		String numeroFinal = "";
		if (strI.length() == 1)  numeroFinal = "00" + strI;
		else if (strI.length() == 2)  numeroFinal = "0" + strI;
		else numeroFinal = strI;
		String rutaFinal = rutaHuella  + "/" + numeroFinal + ".jpg";
		System.out.println(rutaFinal);
		BufferedImage imageBase = ImageIO.read(new File(rutaFinal));
		return imageBase;
	
	}
}
