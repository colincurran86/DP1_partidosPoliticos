package Recorte;

import java.awt.FlowLayout;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

import pantallas.ProgressMonitorExample;
import Catalano.Imaging.FastBitmap;
import Catalano.Imaging.Filters.Resize;

import OCR.RecogChar;
import ij.IJ;
import ij.ImagePlus;
import ij.Prefs;
import ij.io.FileSaver;

public class Main {

	public static List<String> lista = new ArrayList<String>()  ; 
	public static List<BufferedImage> listaBImage = new ArrayList<BufferedImage>()  ; 
	public static int totalPadrones;

    public  void  main(String rutaPadrones) {

    	String workingDir = System.getProperty("user.dir"); // nos evitamos el problema de las rutas :'v
        int personasxPadron = 8;
        recorteFunctions rf = new recorteFunctions();
        RecogChar recogChar = new RecogChar();
        recogChar.init();

        //verificamos cuantos padrones existen	
        totalPadrones = rf.contarPadrones(rutaPadrones);    
       // rf.tamanhoEstandar(rutaPadrones, totalPadrones);
		File folder = new File(rutaPadrones);
		int contPadrones = 0;

		for (final File fileEntry : folder.listFiles()) {

			String ruta1 = rutaPadrones + "/" + fileEntry.getName();
        	String ruta2 =  workingDir + "/src/Recorte/Auxiliar/recorteCostado.jpg";
        	String ruta3 = workingDir + "/src/Recorte/Auxiliar/recorteBN.jpg";
        	
        
            rf.recortarCostadosProcesarPadron(ruta1,ruta2,ruta3);
            
            
            ImagePlus padronJ =  rf.getPadron();
            int width1=padronJ.getWidth();
            int height1=padronJ.getHeight();
            int cont = 0, alturaX = 0 , yHuellas = 0, yFirmas = 0, yDNI = 0, yNombre = 0, yApellido =0;
            
            rf.coordenadasHuella(padronJ);  alturaX = rf.getX(); yHuellas = rf.getYHuellas();
            rf.coordenadasFirma(padronJ);  yFirmas = rf.getYFirmas();
            rf.coordenadaDNIyNombre(padronJ);
            yDNI = rf.getYDNI(); yApellido = rf.getYApellido(); yNombre = rf.getYNombre();
            
            //cropeamos los digitos del DNI    
            List <String> dniLista  = new ArrayList <String>() ;
            int numero, valorOriginal = yDNI,   valorOriginalFirma = yFirmas, valorOriginalApellido = yApellido, valorOriginalNombre = yNombre;
            int distanceBetweenSquaresH = 87 ,distanceBetweenSquares = 14, widthSquare = 14, heightSquare = 84;

            for (int n  = 0; n < 8; n++){    
            	
                ImagePlus Copia1;
                String rutaAlmacenar = workingDir + "/src/Recorte/Resultado/Persona"
                        + String.valueOf(n+1+(8*contPadrones))  + "/DNI/";
            
                File file2 = new File(rutaAlmacenar);      file2.mkdirs();
                String dni = "";    yDNI = valorOriginal;
                
                
                for (int h = 0; h<8 ; h++) {
                    
                    //Detectamos el proximo espacio en blanco
                    Copia1 = IJ.openImage(ruta2);
            
                    int valor = rf.getAnchoDNI(yDNI+1, (alturaX+5) + distanceBetweenSquaresH * n);
                    if (valor == 0) valor = 13;
           
                    Copia1.setRoi(yDNI+2, (alturaX+5)  + distanceBetweenSquaresH * n  , valor-1 , 70);
                    IJ.run(Copia1, "Crop", ""); int k = h+1;
                    String rutaDNI = workingDir + "/src/Recorte/Resultado/Persona" + String.valueOf(n+1+(8*contPadrones))     + "/DNI/" + k + ".jpg";
                    if (h != 7 ) yDNI = rf.obtenerSiguienteEspacioDNI(yDNI,alturaX+5);
                                 
	                           
                    numero =  recogChar.recognize_actionPerformed(Copia1.getImage());
            
                    new FileSaver(Copia1).saveAsPng(rutaDNI);
	                
                    
                    
                    if (dni == "") dni = ""+ numero;	             
	                else dni=dni + numero;    

	              }
	
	               lista.add(dni);
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
                yApellido     = valorOriginalApellido;
                for (int contApellido = 0; contApellido < apellidoEspacios; contApellido ++){
                    ImagePlus Copi41 = IJ.openImage(ruta2);
                    String rutaNombre = workingDir + "/src/Recorte/Resultado/Persona"
                    + String.valueOf(n+1+(8*contPadrones)) + "/Apellido/" + contApellido + ".jpg";
                    int valor = rf.getAnchoDNI(yApellido+1, (alturaX+5) + alturaLetras * n);
                    if (valor == 0 || valor == 1) valor = 13;
                    Copi41.setRoi(yApellido+1 , (alturaX + 6) + alturaLetras*n,  valor-1  , heightSquare-7);
                    if (contApellido != 24 ) yApellido = rf.obtenerSiguienteEspacioDNI(yApellido,alturaX+5);
                    IJ.run(Copi41, "Crop", "");
                 
                    new FileSaver(Copi41).saveAsPng(rutaNombre);
                    //Prefs.blackBackground = false;
                }
               
                
            }

            for (int n  = 0; n < personasxPadron; n++){
              //  ImagePlus firmaUser,huellasUser = new ImagePlus();
                String rutaAlmacenar = workingDir + "/src/Recorte/Resultado/Persona"
                        + String.valueOf(n+1+(8*contPadrones))  + "/Huella/";
                String rutaAlmacenar2 = workingDir + "/src/Recorte/Resultado/Persona"
                        + String.valueOf(n+1+(8*contPadrones))  + "/Firma/";        
                File file = new File(rutaAlmacenar);  file.mkdirs();
                File file2 = new File(rutaAlmacenar2);  file2.mkdirs();
                
             //   firmaUser = IJ.openImage(ruta2);
             //   huellasUser = IJ.openImage(ruta2);
              //  usuariosFirma.add(firmaUser);     usuariosHuella.add(huellasUser);
            }    
            
             distanceBetweenSquares = 86; widthSquare = 150;  heightSquare = 75;        
            //para cada imagen le�da de la carpeta de imagenes realizar lo de abajo. 

            int alturaFirma =  alturaX + 4;
            int alturaFirma2 = alturaX + 4;
            for (int n = 0; n< 8; n++){
                //firma
                    ImagePlus Copia1,Copia2;
                    // ******BLANCOYNEGRO*****
                      Copia1 = IJ.openImage(ruta3);
                      Copia1.setRoi(yFirmas+3, alturaFirma+2 , widthSquare , heightSquare);
                    // ******BLANCOYNEGRO*****
                    // ******COLOOOOOOOOOOOOOOOOOR*****
                    //  Copia1 = IJ.openImage(ruta1);
                    //  Copia1.setRoi(yFirmas+39, alturaFirma+2 , widthSquare , heightSquare);
                    // ******COLOOOOOOOOOOOOOOOOOR*****

                    if (n != 7 ) alturaFirma = rf.obtenerSiguienteEspacioFirmas(yFirmas+4, alturaFirma);
                    IJ.run(Copia1, "Crop", ""); 
                   // new FileSaver(Copia1).saveAsPng(workingDir + "/src/Recorte/Resultado/Persona"
                   //     + String.valueOf(n+1+(8*contPadrones))  + "/Firma/firma.jpg");
                    Prefs.blackBackground = false;

                    FastBitmap fb = new FastBitmap(Copia1.getBufferedImage());
                    
                   // fb.saveAsPNG(workingDir + "/src/Recorte/Resultado/Persona"  + String.valueOf(n+1+(8*contPadrones))  + "/Firma/firma.jpg");
                   // Copia1.show();
   
                    // ==listaBImage.add(Copia1.getBufferedImage());
                    
                  
                    
                //huella
                    Copia2 = IJ.openImage(ruta3);
                   // Copia2.setRoi(yHuellas, alturaX+ distanceBetweenSquares*n , widthSquare + 12, heightSquare+4);
                    Copia2.setRoi(yHuellas+2, alturaFirma2+2 , widthSquare , heightSquare+2);
                  //  System.out.println("Valor nuevo " + alturaFirma2);
                    if (n != 7 ) alturaFirma2 = rf.obtenerSiguienteEspacioFirmas(yFirmas+5, alturaFirma2+2);
                    IJ.run(Copia2, "Crop", ""); 
                    //new FileSaver(padronJ).saveAsPng("D:/Users/a20101616/git/DP1_partidosPoliticos/src/Recorte/recorteCostado.jpg");
                    new FileSaver(Copia2).saveAsPng(workingDir + "/src/Recorte/Resultado/Persona"
                            + String.valueOf(n+1+(8*contPadrones))  + "/Huella/Huella.jpg");
                    Prefs.blackBackground = false;
                    //Copia2.show();        
                   
            }    
            	


            System.out.println("Padron numero " + (contPadrones+1) + " Procesado");
            System.out.println("yDNI " + (contPadrones+1) + " " +  yDNI);  
            System.out.println("alturaX " + (contPadrones+1) + " " +	 alturaX);  
            contPadrones++;
            
        }
            

        
    }
    
    

}
