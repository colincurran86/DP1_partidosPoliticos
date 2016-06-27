
package fingerprint;

import ij.ImagePlus;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
	


import pantallas.Procesando;
import models.PersonaReniec;

public class AlgoritmoHuellas {
	
	public static List<Double> procesarNuevo(List<PersonaReniec> listaPersonasReniec, ImagePlus imageInput, String rutaHuella){
		
		List<Double> resultado= new ArrayList<Double>();
		
		mainHuellas mhuellas = new mainHuellas();
		for (int i = 0; i < listaPersonasReniec.size(); i ++){
			
			//int idHuella = listaPersonasReniec.get(i).getIdHuella();
			String idHuella = listaPersonasReniec.get(i).getIdHuella();
			try {		
				
				BufferedImage imageBase = buscarImageBase(idHuella, rutaHuella);
				Procesando.setearImagenHuella(imageInput.getBufferedImage());
				double Porcentaje = mhuellas.principal(imageInput.getBufferedImage(), imageBase);
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

		/*String strI = Integer.toString(idHuella);
		String numeroFinal = "";
		if (strI.length() == 1)  numeroFinal = "00" + strI;
		else if (strI.length() == 2)  numeroFinal = "0" + strI;
		else numeroFinal = strI;*/
		String rutaFinal = rutaHuella  + "/" + idHuella + ".png";
		System.out.println(rutaFinal);
		BufferedImage imageBase = ImageIO.read(new File(rutaFinal));
		return imageBase;
	
	}
}
