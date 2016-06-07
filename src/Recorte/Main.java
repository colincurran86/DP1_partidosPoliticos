package Recorte;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;

import OCR.RecogChar;
import ij.IJ;
import ij.ImagePlus;
import ij.Prefs;
import ij.io.FileSaver;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String workingDir = System.getProperty("user.dir"); // nos evitamos el problema de las rutas :'v
		System.out.println(workingDir);

		int personasxPadron = 8;
		long startTime = System.currentTimeMillis();
		System.out.println("Procesando");
		recorteFunctions rf = new recorteFunctions();
		//verificamos cuantos padrones existen
		int totalPadrones = rf.contarPadrones();	

		System.out.println(totalPadrones);
		
		for (int contPadrones = 0; contPadrones<totalPadrones; contPadrones++){
			
			String ruta1 = workingDir + "/src/Recorte/padrones/padron.rayas.firmado." + (contPadrones+1) +".jpg";
			String ruta2 = workingDir + "/src/Recorte/padrones/recorteCostado.jpg";
			rf.recortarCostadosProcesarPadron(ruta1,ruta2);
			ImagePlus padronJ =  rf.getPadron();
			int width1=padronJ.getWidth();
			int height1=padronJ.getHeight();
			int cont = 0, alturaX = 0 , yHuellas = 0, yFirmas = 0, yDNI = 0, yNombre = 0, yApellido =0;
			
			rf.coordenadasHuella(padronJ); 
			

			alturaX = rf.getX(); yHuellas = rf.getYHuellas();

			
			rf.coordenadasFirma(padronJ); 
			yFirmas = rf.getYFirmas();
		
			rf.coordenadaDNIyNombre(padronJ);
			yDNI = rf.getYDNI(); yApellido = rf.getYApellido(); yNombre = rf.getYNombre();

			//cropeamos los digitos del DNI
		//	System.out.println(alturaX + " " + yDNI);
			int distanceBetweenSquaresH = 87 ,distanceBetweenSquares = 14, widthSquare = 14, heightSquare = 84;
			
		
			//RecogChar.recognize_actionPerformedDNI();
	
			System.out.println(yDNI);
			System.out.println(alturaX);
			for (int n  = 0; n < 8; n++){
				ImagePlus Copia1;
				String rutaAlmacenar = workingDir + "/src/Recorte/Resultado/Persona"
						+ String.valueOf(n+1+(8*contPadrones))  + "/DNI/";
				
				File file2 = new File(rutaAlmacenar); 	
				file2.mkdirs();
						
				
				for (int h = 0; h<8 ; h++) {
					Copia1 = IJ.openImage(ruta2);
					Copia1.setRoi(yDNI + distanceBetweenSquares*h, alturaX  + distanceBetweenSquaresH * n  , 11 , 79);
					//if (n<4) Copia1.setRoi(yDNI + distanceBetweenSquares*h, alturaX  + 2 + distanceBetweenSquaresH * n  , 12 , 84);
					//else Copia1.setRoi(yDNI + distanceBetweenSquares*h, alturaX + 5 + distanceBetweenSquaresH * n  , 12 , 84);
				   // Copia1.show();
					IJ.run(Copia1, "Crop", ""); int k = h+1;
				    String rutaDNI = workingDir + "/src/Recorte/Resultado/Persona"
				    + String.valueOf(n+1+(8*contPadrones)) + "/DNI/" + k + ".jpg";
				    //Copia1.show();
				    
				   
				    //Ejecutamos 
				 	RecogChar recogChar = new RecogChar();
				 recogChar.setVisible(false);
				 	recogChar.init();
					recogChar.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				  recogChar.setSize(820, 580);
				  	recogChar.recognize_actionPerformedDNI(Copia1.getImage());
				  	
				  	
				    // Copia1.show();
				    new FileSaver(Copia1).saveAsPng(rutaDNI);
					//Prefs.blackBackground = false;
				}
				
				System.out.println();
			}	
	
			int apellidoEspacios = 25, nombreEspacios = 23, espacioLetras = 15, alturaLetras = 85;
			
			for (int n = 0; n < personasxPadron; n++){
				ImagePlus Copia1;  
				String rutaAlmacenar = workingDir + "/src/Recorte/Resultado/Persona"
						+ String.valueOf(n+1+(8*contPadrones))  + "/Apellido/";
				String rutaAlmacenar2 = workingDir + "/src/Recorte/Resultado/Persona"
						+ String.valueOf(n+1+(8*contPadrones))  + "/Nombre/";
				File file = new File(rutaAlmacenar);  file.mkdirs();
				File file2 = new File(rutaAlmacenar2);  file2.mkdirs();

			//Cropeamos el Apellido
				for (int contApellido = 0; contApellido < apellidoEspacios; contApellido ++){
					ImagePlus Copi41 = IJ.openImage(ruta2);
				    String rutaNombre = workingDir + "/src/Recorte/Resultado/Persona"
				    + String.valueOf(n+1+(8*contPadrones)) + "/Apellido/" + contApellido + ".jpg";
				    if (n<3)Copi41.setRoi(yApellido + contApellido*espacioLetras, (alturaX + 1) + alturaLetras*n, widthSquare - 2 , heightSquare-3);
				    else Copi41.setRoi(yApellido + 2 + contApellido*espacioLetras, (alturaX + 5) + alturaLetras*n, widthSquare - 4 , heightSquare-3);
				    IJ.run(Copi41, "Crop", ""); 
				    new FileSaver(Copi41).saveAsPng(rutaNombre);
					//Prefs.blackBackground = false;
				}
				
			//Cropeamos el Nombre
	
				for (int contNombre = 0; contNombre < nombreEspacios; contNombre ++){
					ImagePlus Copi41 = IJ.openImage(ruta2);
				    String rutaNombre = workingDir + "/src/Recorte/Resultado/Persona"
				    + String.valueOf(n+1+(8*contPadrones)) + "/Nombre/" + contNombre + ".jpg";
				    if (n<3)Copi41.setRoi(yNombre + contNombre*espacioLetras, (alturaX + 1) + alturaLetras*n, widthSquare - 6 , heightSquare-3);
				    else Copi41.setRoi(yNombre+ 2 + contNombre*espacioLetras, (alturaX + 5) + alturaLetras*n, widthSquare - 6 , heightSquare-3);
				    IJ.run(Copi41, "Crop", ""); 
				    new FileSaver(Copi41).saveAsPng(rutaNombre);
					//Prefs.blackBackground = false;
				}
			}

	
			//Cropeamos las firmas y las huellas para cada persona
			List<ImagePlus>usuariosFirma = new ArrayList<ImagePlus>();
			List<ImagePlus>usuariosHuella = new ArrayList<ImagePlus>();
	
			for (int n  = 0; n < personasxPadron; n++){
				ImagePlus firmaUser,huellasUser = new ImagePlus();
				String rutaAlmacenar = workingDir + "/src/Recorte/Resultado/Persona"
						+ String.valueOf(n+1+(8*contPadrones))  + "/Huella/";
				String rutaAlmacenar2 = workingDir + "/src/Recorte/Resultado/Persona"
						+ String.valueOf(n+1+(8*contPadrones))  + "/Firma/";		
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
						+ String.valueOf(n+1+(8*contPadrones))  + "/Firma/firma.jpg");
					Prefs.blackBackground = false;
				   // Copia1.show();
				//huella
					Copia2.setRoi(yHuellas, alturaX+ distanceBetweenSquares*n , widthSquare + 12, heightSquare+4);
					IJ.run(Copia2, "Crop", ""); 
					//new FileSaver(padronJ).saveAsPng("D:/Users/a20101616/git/DP1_partidosPoliticos/src/Recorte/recorteCostado.jpg");
					new FileSaver(Copia2).saveAsPng(workingDir + "/src/Recorte/Resultado/Persona"
							+ String.valueOf(n+1+(8*contPadrones))  + "/Huella/Huella.jpg");
					Prefs.blackBackground = false;
					//Copia2.show();		
			}	
			


			System.out.println("Padron numero " + (contPadrones+1) + " Procesado");
			
			
		}
			
		long endTime   = System.currentTimeMillis();
		double totalTime = (endTime - startTime) / 1000.0;
		System.out.println("Finalizado");
		System.out.println("El tiempo total de ejecucion del programa fue " + totalTime + " segundos");
		
	}

}