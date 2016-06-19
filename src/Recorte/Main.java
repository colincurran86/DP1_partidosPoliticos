package Recorte;

import java.awt.FlowLayout;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

import models.PartidoPolitico;
import models.PersonaReniec;
import models.ReniecBD;

import org.apache.poi.hsmf.examples.Msg2txt;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import clasesAux.Util;
import fingerprint.AlgoritmoHuellas;
import pantallas.PrimeraFase;
import pantallas.Procesando;
import pantallas.ProgressMonitorExample;
import Catalano.Imaging.FastBitmap;
import Catalano.Imaging.Filters.Resize;
import Firmas.AlgoritmoFirmas;
import OCR.RecogChar;
import ij.IJ;
import ij.ImagePlus;
import ij.Prefs;
import ij.gui.MessageDialog;
import ij.io.FileSaver;

public class Main {

	public static List<String> lista = new ArrayList<String>()  ; 
	public static List<BufferedImage> listaBImage = new ArrayList<BufferedImage>()  ; 
	public static int totalPadrones;
	public static String rutaPlanillonEjecutandose ; 
	
    public  void  main(String rutaPadrones  , PartidoPolitico pp, String rutaFirma, String rutaHuella) {

    	Util u = new Util(); PrimeraFase primeraFase = new PrimeraFase();
    	//	String formatearRutaBD = "D:\\Users\\jemarroquin\\git\\DP1_partidosPoliticos\\src\\registro.nacional.v.1.xlsx" ;
    		String formatearRutaBD = u.formatearRuta(primeraFase.rutaExcel);
    		llenarBDReniec(formatearRutaBD);
    		
    		
        	String workingDir = System.getProperty("user.dir"); // nos evitamos el problema de las rutas :'v
            int personasxPadron = 8;
            recorteFunctions rf = new recorteFunctions();
            RecogChar recogChar = new RecogChar();
            recogChar.init();

            //verificamos cuantos padrones existen	
            //totalPadrones = rf.contarPadrones(rutaPadrones);    
           // rf.tamanhoEstandar(rutaPadrones, totalPadrones);
    		File folder = new File(rutaPadrones);
    		int contPadrones = 0;

    		for (final File fileEntry : folder.listFiles()) { // abre cada planillon

    			String ruta1 = rutaPadrones + "/" + fileEntry.getName();
            	String ruta2 =  workingDir + "/Auxiliar/recorteCostado.jpg";
            	String ruta3 = workingDir + "/Auxiliar/recorteBN.jpg";
            	
            	System.out.println( ruta1);
          	    rutaPlanillonEjecutandose = ruta1 ;
             
                rf.recortarCostadosProcesarPadron(ruta1,ruta2,ruta3,workingDir);
                
                
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
                int alturaFirma2 = alturaX + 4;

                for (int n  = 0; n < 8; n++){ // iterando en las filas     
                	
                    ImagePlus Copia1;
                    String dni = "";    yDNI = valorOriginal;
                    
                    
                    for (int h = 0; h<8 ; h++) {
                        
                        //Detectamos el proximo espacio en blanco
                        Copia1 = IJ.openImage(ruta2);  
                        int valor = rf.getAnchoDNI(yDNI+1, (alturaX+5) + distanceBetweenSquaresH * n);
                        if (valor == 0) valor = 13;          
	                        Copia1.setRoi(yDNI+2, (alturaX+5)  + distanceBetweenSquaresH * n  , valor-1 , 70);
                        IJ.run(Copia1, "Crop", ""); int k = h+1;
                        if (h != 7 ) yDNI = rf.obtenerSiguienteEspacioDNI(yDNI,alturaX+5);
                        numero =  recogChar.recognize_actionPerformed(Copia1.getImage());
                        if (dni == "") dni = ""+ numero;	             
    	                else dni=dni + numero;    

    	              }
    	
    	            //   lista.add(dni);
                    Procesando.escribirTextArea("============================================");
                    Procesando.escribirTextArea("Se esta procesando el dni :"  + dni );
                    
                    List<PersonaReniec>  listaPersonasReniec =  Util.ocrMasReniec2(dni); 
    	               
                    for (int i = 0 ; i < listaPersonasReniec.size()  ; i++ )
                    Procesando.escribirTextArea("La lista de candidatos es la siguente: "  + listaPersonasReniec.get(i).getDni() );
                    
                    if(listaPersonasReniec.size() != 0 ) 
                    {
                    	try {
                           
                    		//firmas
                    		AlgoritmoFirmas.procesarNuevo(listaPersonasReniec, n , rutaPlanillonEjecutandose, rutaFirma);  
                    		//huellas
                    		distanceBetweenSquares = 86; widthSquare = 150;  heightSquare = 75;        
                    		ImagePlus Copia2 = IJ.openImage(ruta3);
                            Copia2.setRoi(yHuellas+2, alturaFirma2+2 , widthSquare , heightSquare+2);
                            if (n != 7 ) alturaFirma2 = rf.obtenerSiguienteEspacioFirmas(yFirmas+5, alturaFirma2+2);
                            IJ.run(Copia2, "Crop", ""); 
                           //AlgoritmoHuellas.procesarNuevo(listaPersonasReniec,Copia2,rutaHuella);
                    	
                    	} catch (IOException e) {
                    		// TODO Auto-generated catch block
                    		e.printStackTrace();
                    	} 
                    }  
                }    
                /*
                
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
                */
                
               

                 distanceBetweenSquares = 86; widthSquare = 150;  heightSquare = 75;        
                //para cada imagen le�da de la carpeta de imagenes realizar lo de abajo. 

                 /*
                int alturaFirma =  alturaX + 4;
                int alturaFirma2 = alturaX + 4;
                for (int n = 0; n< 8; n++){
                    
                    ImagePlus Copia2 = IJ.openImage(ruta3);
                    Copia2.setRoi(yHuellas+2, alturaFirma2+2 , widthSquare , heightSquare+2);
                    if (n != 7 ) alturaFirma2 = rf.obtenerSiguienteEspacioFirmas(yFirmas+5, alturaFirma2+2);
                    IJ.run(Copia2, "Crop", ""); 
              
                }    
                  */
    			
                System.out.println("Padron numero " + (contPadrones+1) + " Procesado");
                contPadrones++;
                
            }
               
        
     
        
    }
    
    
    public void llenarBDReniec(String rutaBD) {
		try {
			InputStream file = new FileInputStream(new File(rutaBD));

			// Get the workbook instance for XLS file
			XSSFWorkbook wb = new XSSFWorkbook(file); // (2)

			// Get third(numbering starts from 0) sheet from the workbook
			XSSFSheet sheet = wb.getSheetAt(0);

			// Get iterator to all the rows in current sheet
			Iterator<Row> rowIterator = sheet.iterator();

			Row row = rowIterator.next();
			// CABECERAS!
			Cell nombre = row.getCell(0);
			Cell apellido = row.getCell(1);
			Cell dni = row.getCell(2);
			Cell ubigeo = row.getCell(3);
			Cell idHuella = row.getCell(4);
			Cell idFirma = row.getCell(5);

			ReniecBD.lista = null;
			ReniecBD.lista = new ArrayList<PersonaReniec>();

			// Iterate through rows
			while (rowIterator.hasNext()) {
				row = rowIterator.next();
				// Index of column D is 3 (A->0, B->1, etc)
				nombre = row.getCell(0);
				apellido = row.getCell(1);
				dni = row.getCell(2);
				ubigeo = row.getCell(3);
				idHuella = row.getCell(4);
				idFirma = row.getCell(5);

				PersonaReniec pr = new PersonaReniec();
				pr.setApellidos(apellido.getStringCellValue());
				int valor = (int) dni.getNumericCellValue();
				String val = "" + valor;
				if (val.length() != 8)
					for (int i = 0; i < 8 - val.length(); i++)
						val = "0" + val;
				pr.setDni(val);
				pr.setIdFirma(idFirma.getStringCellValue());
				pr.setIdHuella((int) idHuella.getNumericCellValue());
				pr.setNombre(nombre.getStringCellValue());
				pr.setUbigeo((int) ubigeo.getNumericCellValue());

				ReniecBD.lista.add(pr);
				// System.out.println(cellA.getStringCellValue());
				// System.out.println(cellB.getStringCellValue());

				// Your business logic continues....
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
