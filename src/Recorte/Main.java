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
            rf.tamanhoEstandar(rutaPadrones, totalPadrones);
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

                int contador = 0;
                for (int n  = 0; n < 8; n++){ // iterando en las filas     
                	
                    ImagePlus Copia1;
                    String dni = "";    yDNI = valorOriginal;
                    
                    
                    for (int h = 0; h<8 ; h++) {
                
                        //Detectamos el proximo espacio en blanco
                        Copia1 = IJ.openImage(ruta2);  
                        int valor = rf.getAnchoDNI(yDNI+1, (alturaX+5) + distanceBetweenSquaresH * n);
                        if (valor == 0) valor = 10;          
	                        Copia1.setRoi(yDNI+2, (alturaX+5)  + distanceBetweenSquaresH * n  , 10 , 70);
                        IJ.run(Copia1, "Crop", ""); int k = h+1;
                        //new FileSaver(Copia1).saveAsPng("D:\\Users\\jemarroquin\\Desktop\\Nueva carpeta\\" + contador + ".jpg");
                        contador++;
                        //Prefs.blackBackground = false;
                        if (h != 7 ) yDNI = rf.obtenerSiguienteEspacioDNI(yDNI,alturaX+5);
                        numero =  recogChar.recognize_actionPerformed(Copia1.getImage());
                        if (dni == "") dni = ""+ numero;	             
    	                else dni=dni + numero;    
    	              }
    	
    	            //   lista.add(dni);
     
                    int cuenta = 0;
                    if (dni.length() == 9){
                    	for (int i = 0; i<9 ; i++){
                    		if (dni.charAt(i) == '-'){
                    			cuenta = i;
                    			break;
                    		}
                    	}
                    	dni = dni.replaceFirst(String.valueOf(dni.charAt(cuenta)), "");
                    }
                    
                    Procesando.escribirTextArea("============================================");
                    Procesando.escribirTextArea("Se esta procesando el dni :"  + dni );
                         
                    
                    List<PersonaReniec>  listaPersonasReniec = new ArrayList<PersonaReniec>();
                     int indice = encontrarDNI(dni);
                     List<Double> listaPorcentajeFirma = new ArrayList<Double>();
                     List<Double> listaPorcentajeHuella = new ArrayList<Double>();
                     
                     if (indice != -1 ) {
                    	 Procesando.escribirTextArea("lo encontre al dni !!" + dni + " - " +  ReniecBD.lista.get(indice).getDni() );
                    	 listaPersonasReniec.add(  ReniecBD.lista.get(indice));
                     }                    
                     else {                    	 
                   listaPersonasReniec =  Util.ocrMasReniec2(dni);
                                         };
                                
                     Procesando.escribirTextArea("Cantidad de candidatos: " + listaPersonasReniec.size() );
                     for (int i = 0 ; i < listaPersonasReniec.size()  ; i++ )
                         Procesando.escribirTextArea("La lista de candidatos es la siguente: "  + listaPersonasReniec.get(i).getDni() );
                  
                   
                    if(listaPersonasReniec.size() != 0 ) 
                    {
                    	try {                           
                    		//firmas
                    	listaPorcentajeFirma= 	AlgoritmoFirmas.procesarNuevo(listaPersonasReniec, n , rutaPlanillonEjecutandose, rutaFirma); 
                     	  int indiceCandidatoFirmas = candidatoFirmas(listaPorcentajeFirma);
                    	for ( int i = 0 ; i < listaPersonasReniec.size() ; i++) Procesando.escribirTextArea("Para el candiato: " + listaPersonasReniec.get(i).getDni() + " Porcentaje de firmas es: "+ listaPorcentajeFirma.get(i) );     
Procesando.escribirTextArea( "De todos los candidatos el mejor según firmas es: " + listaPersonasReniec.get(indiceCandidatoFirmas).getDni());



                    		//huellas
                    		distanceBetweenSquares = 86; widthSquare = 150;  heightSquare = 75;        
                    		ImagePlus Copia2 = IJ.openImage(ruta3);
                            Copia2.setRoi(yHuellas+2, alturaFirma2+2 , 150 , 77);
                            if (n != 7 ) alturaFirma2 = rf.obtenerSiguienteEspacioFirmas(yFirmas+5, alturaFirma2+2);
                            IJ.run(Copia2, "Crop", ""); 
                        	listaPorcentajeHuella = 	 AlgoritmoHuellas.procesarNuevo(listaPersonasReniec,Copia2,rutaHuella); 
                       	  int indiceCandidatoHuellas = candidatoFirmas(listaPorcentajeHuella);
                      	for ( int i = 0 ; i < listaPersonasReniec.size() ; i++) Procesando.escribirTextArea("Para el candiato: " + listaPersonasReniec.get(i).getDni() + " Porcentaje de huellas es: "+ listaPorcentajeHuella.get(i) );     
  Procesando.escribirTextArea( "De todos los candidatos el mejor según Huellas es: " + listaPersonasReniec.get(indiceCandidatoHuellas).getDni());


  
  
                    	
                    	} catch (IOException e) {
                    		// TODO Auto-generated catch block
                    		e.printStackTrace();
                    	} 
                    }  
                }    

                System.out.println("Padron numero " + (contPadrones+1) + " Procesado");
                contPadrones++;
                
            }
               
        
     
        
    }
    
    
    public void llenarBDReniec(String rutaBD) {
		try {
			
			System.out.println("ESTA ES UNA MIERDA XDD JAJA " + rutaBD);
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


public int encontrarDNI( String dni ){
	
	int indice = -1 ; 
	
	for (int j = 0; j < ReniecBD.lista.size(); j++)
		if (dni != null || dni.length() != 0) {
			if (ReniecBD.lista.get(j).getDni().compareTo(dni) == 0) {
				// System.out.println(ReniecBD.lista.size());
		
				indice = j ; 
			return indice ;  
				// System.out.println(a.size());
				
			}
		}
	return indice   ; 
}


public int candidatoFirmas ( List<Double > lista)  {
	
	int indice = -1 ; 
	Double mayor = 0.0;
	
	for (int i = 0 ; i < lista.size() ; i++){
		
		if (lista.get(i) >= mayor) {
			indice= i ;
			mayor = lista.get(i);
			
			
		}
		
	}
	
	
	return indice ;
	
	
}


}


