package Recorte;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import ij.IJ;
import ij.ImagePlus;
import ij.Prefs;
import ij.io.FileSaver;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String workingDir = System.getProperty("user.dir");
		System.out.println(workingDir);

		int personasxPadron = 8;
		String ruta1 = workingDir + "/src/Recorte/padron.rayas.firmado.2.jpg";
		String ruta2 = workingDir + "/src/Recorte/recorteCostado.jpg";
		long startTime = System.currentTimeMillis();
		
		
		System.out.println("Procesando");
		recorteFunctions rf = new recorteFunctions();
		rf.recortarCostadosProcesarPadron(workingDir);
		ImagePlus padronJ =  rf.getPadron();
		
		int width1=padronJ.getWidth();
		int height1=padronJ.getHeight();
		int cont = 0, alturaX = 0 , yHuellas = 0, yFirmas = 0;
		
		//obtienes esquina izquierda superior de una huella imp.setRoi(1027,204,28,24); imp.setRoi(y,x,cuadrado1,cuadrado2);
		rf.coordenadasHuella(padronJ); 	alturaX = rf.getX(); yHuellas = rf.getYHuellas();
		rf.coordenadasFirma(padronJ); yFirmas = rf.getYFirmas();
		
		//cropeamos los digitos del DNI

		int distanceBetweenSquaresH = 85 ,distanceBetweenSquares = 15, widthSquare = 14, heightSquare = 84;

		for (int n  = 0; n < personasxPadron; n++){
			ImagePlus Copia1;
			String rutaAlmacenar = workingDir + "/src/Recorte/Resultado/Persona"
					+ String.valueOf(n+1)  + "/DNI/";
			
			File file2 = new File(rutaAlmacenar); 	
			file2.mkdirs();
					
			for (int h = 0; h<8 ; h++) {
				Copia1 = IJ.openImage(ruta2);
				if (n<4) Copia1.setRoi(26 + distanceBetweenSquares*h, 206 + distanceBetweenSquaresH * n  , 11 , 82);
				else Copia1.setRoi(26 + distanceBetweenSquares*h, 209 + distanceBetweenSquaresH * n  , 11 , 82);
			   // Copia1.show();
				IJ.run(Copia1, "Crop", ""); int k = h+1;
			    String rutaDNI = workingDir + "/src/Recorte/Resultado/Persona"
			    + String.valueOf(n+1) + "/DNI/" + k + ".jpg";
				new FileSaver(Copia1).saveAsPng(rutaDNI);
				Prefs.blackBackground = false;
			}
			
		}	
		

		
		//Cropeamos el Nombre
		
		
		//Cropeamos el Apellido


		//Cropeamos las firmas y las huellas para cada persona
		List<ImagePlus>usuariosFirma = new ArrayList<ImagePlus>();
		List<ImagePlus>usuariosHuella = new ArrayList<ImagePlus>();

		for (int n  = 0; n < personasxPadron; n++){
			ImagePlus firmaUser,huellasUser = new ImagePlus();
			String rutaAlmacenar = workingDir + "/src/Recorte/Resultado/Persona"
					+ String.valueOf(n+1)  + "/Huella/";
			String rutaAlmacenar2 = workingDir + "/src/Recorte/Resultado/Persona"
					+ String.valueOf(n+1)  + "/Firma/";		
			File file = new File(rutaAlmacenar);  file.mkdirs();
			File file2 = new File(rutaAlmacenar2);  file2.mkdirs();
			
			firmaUser = IJ.openImage(ruta2);
			huellasUser = IJ.openImage(ruta2);
			usuariosFirma.add(firmaUser); 	usuariosHuella.add(huellasUser);
		}	
		
	    distanceBetweenSquares = 86; widthSquare = 150; heightSquare = 75;		
		//para cada imagen leída de la carpeta de imagenes realizar lo de abajo. 
		for (int n = 0; n< personasxPadron; n++){
			//firma
				ImagePlus Copia1,Copia2;
				Copia1 = usuariosFirma.get(n); Copia2 = usuariosHuella.get(n);
				Copia1.setRoi(yFirmas, alturaX + distanceBetweenSquares*n , widthSquare , heightSquare);
			    IJ.run(Copia1, "Crop", ""); 
				new FileSaver(Copia1).saveAsPng(workingDir + "/src/Recorte/Resultado/Persona"
					+ String.valueOf(n+1)  + "/Firma/firma.jpg");
				Prefs.blackBackground = false;
			    //Copia1.show();
			//huella
				Copia2.setRoi(yHuellas, alturaX+ distanceBetweenSquares*n , widthSquare + 12, heightSquare+4);
				IJ.run(Copia2, "Crop", ""); 
				//new FileSaver(padronJ).saveAsPng("D:/Users/a20101616/git/DP1_partidosPoliticos/src/Recorte/recorteCostado.jpg");
				new FileSaver(Copia2).saveAsPng(workingDir + "/src/Recorte/Resultado/Persona"
						+ String.valueOf(n+1)  + "/Huella/Huella.jpg");
				Prefs.blackBackground = false;
				//Copia2.show();		
		}	
		
		long endTime   = System.currentTimeMillis();
		double totalTime = (endTime - startTime) / 1000.0;
		System.out.println("Finalizado");
		System.out.println("El tiempo total de ejecucion del programa fue " + totalTime + " segundos");
		

	}

}