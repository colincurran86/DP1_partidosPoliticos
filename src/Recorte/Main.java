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

import models.Participante;
import models.PartidoPersona;
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
	public static List <PartidoPersona> participantesPreDuplicidad = new ArrayList<PartidoPersona> ();
		
	
    public  void  main(String rutaPadrones  , PartidoPolitico pp, String rutaFirma, String rutaHuella) {
    	Procesando.escribirLabelPartidoPolitico( "Iniciando recursos   :)   ");
    	Procesando.escribirLabelPadron( "Espere por favor . . . ");
    	Procesando.aumentarPorcentaje1(0);
    	Util u = new Util(); 
    	PrimeraFase primeraFase = new PrimeraFase();
 
    	
    	
    		String formatearRutaBD = u.formatearRuta(primeraFase.rutaExcel);
    		llenarBDReniec(formatearRutaBD);
    		
    		
        	String workingDir = System.getProperty("user.dir"); // nos evitamos el problema de las rutas :'v
            int personasxPadron = 8;
            recorteFunctions rf = new recorteFunctions();
            RecogChar recogChar = new RecogChar();
            recogChar.init();

            rf.guardarImagenes(rutaPadrones);
            rf.tamanhoEstandar(rutaPadrones);
    		File folder = new File(rutaPadrones);
    		int contPadrones = 0;
    		int auxiliarBarraProgreso = 0 ;
    		for (final File fileEntry : folder.listFiles()) { // abre cada planillon
    			
    			auxiliarBarraProgreso = auxiliarBarraProgreso +1 ;
    			
    			
    			Procesando.escribirLabelPartidoPolitico( "Partido politico: " + pp.getNombre());
    			Procesando.escribirLabelPadron ("Padron #"+ auxiliarBarraProgreso + " de " +  (folder.listFiles().length   ));
    			
    			int auxiliarBarraProgresoPlanillon  = 100 / (folder.listFiles().length + 1 ) ;
    			Procesando.aumentarPorcentaje1 (auxiliarBarraProgreso  * auxiliarBarraProgresoPlanillon);
    			
    			String ruta0 = rutaPadrones + "/" + fileEntry.getName();
    			String ruta1 = workingDir + "/Copia/" + fileEntry.getName();
            	String ruta2 =  workingDir + "/Auxiliar/recorteCostado.jpg";
            	String ruta3 = workingDir + "/Auxiliar/recorteBN.jpg";
            	
            	//System.out.println( ruta1);
          	    rutaPlanillonEjecutandose = ruta0 ;
             
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
                
                // Obtenemos la altura de las cuadriculas para cada DNI
                
                int distanceBetweenSquaresH = 0;
                for (int i = 0; i<1000; i++){
                	int resta = (alturaX+i) - alturaX ;
                	int r = padronJ.getPixel(yDNI+2, alturaX+i)[0];
                	if (resta >= 80 && resta <= 90 && r!=0){
                		distanceBetweenSquaresH = resta + 1;
                		break;
                	}
                }
                
                
                
                int distanceBetweenSquares = 14, widthSquare = 14, heightSquare = 84;
                int alturaFirma2 = alturaX + 4;	

                int contador = 0;
                for (int n  = 0; n < 8; n++){ // iterando en las filas     
                
                	int auxiliarBarraProgresoPlanillonFilas  =  (n+1)*auxiliarBarraProgresoPlanillon / 8 ;
                	
                	Procesando.aumentarPorcentaje1( (auxiliarBarraProgreso  * auxiliarBarraProgresoPlanillon ) +auxiliarBarraProgresoPlanillonFilas  );
                	
                    ImagePlus Copia1;
                    String dni = "";    yDNI = valorOriginal;
                    for (int h = 0; h<8 ; h++) { // NUMEROS DEL DNI DEL 0 A 7 ( 1 AL 8 )
                
                        //Detectamos el proximo espacio en blanco
                        Copia1 = IJ.openImage(ruta2);  
                        int valor = rf.getAnchoDNI(yDNI+1, (alturaX+7) + distanceBetweenSquaresH * n);
                        if (valor < 10 ) valor = 11;          
	                    Copia1.setRoi(yDNI+2, (alturaX+7)  + distanceBetweenSquaresH * n  , valor-3 , 54);
                        IJ.run(Copia1, "Crop", ""); 
                        
                        
                        for (int i = 0; i < 54 ; i++){
                 		   int r = Copia1.getPixel(0, i)[0];
                 		   if (r != 0){
                 			   Copia1.setRoi(1, 0  , 10 , 54);
                               IJ.run(Copia1, "Crop", ""); 
                 			   break;
                 		   }
                        }	
                        
                        
                        //new FileSaver(Copia1).saveAsPng("C:\\Users\\lenovo\\Desktop\\ENTRENAMIENTO\\RESULTADO\\" + contador + ".jpg");
                        contador++;
                        //Prefs.blackBackground = false;
  
                        if (h != 7 ) yDNI = rf.obtenerSiguienteEspacioDNI(yDNI+2,alturaX+7); 
                        numero =  recogChar.recognize_actionPerformed(Copia1.getImage());
                        if (dni == "") dni = ""+ numero;	             
    	                else dni=dni + numero;    
    	              }
                    
                    
     
                    int cuenta = 0;
                    String dniAux="";
                    //if (dni.length() == 9){
                    	for (int i = 0; i<dni.length() ; i++){
                    		if (dni.charAt(i) != '-'){
                    			dniAux+=dni.charAt(i);
                    			//cuenta = i;
                    			//dniAux+=
                    			//break;
                    		}
                    	}
                    	//dni = dni.replaceFirst(String.valueOf(dni.charAt(cuenta)), "");
                    //}
                    dni=dniAux;
                    
                    Procesando.escribirTextArea("============================================");
                    Procesando.escribirTextArea("Se esta procesando el dni :"  + dni );
                         
                    
                     List<PersonaReniec>  listaPersonasReniec = new ArrayList<PersonaReniec>();
                     //int indice = encontrarDNI(dni);
                     List<Double> listaPorcentajeFirma = new ArrayList<Double>();
                     List<Double> listaPorcentajeHuella = new ArrayList<Double>();
                   
                     PersonaReniec elElegidoSegunOCR = new PersonaReniec();
                     PersonaReniec elElegidoSegunFirma = new PersonaReniec();
                     PersonaReniec elElegidoFinal = null;
                     /*
                     if (indice != -1 ) {
                    	 Procesando.escribirTextArea("lo encontre al dni !!" + dni + " - " +  ReniecBD.lista.get(indice).getDni() );
                    	 listaPersonasReniec.add(  ReniecBD.lista.get(indice));
                    	 elElegidoFinal =  ReniecBD.lista.get(indice) ; 
                    	 
                    	 
                     }                    
                     else {                    	 
                    	 listaPersonasReniec =  Util.ocrMasReniec2(dni);
                    	 //    if (listaPersonasReniec.size() == 0 ) continue ;
                   
                   
                     };
                     */
                	 listaPersonasReniec =  Util.ocrMasReniec2(dni);
                                
                     Procesando.escribirTextArea("Cantidad de candidatos: " + listaPersonasReniec.size() );
                     for (int i = 0 ; i < listaPersonasReniec.size()  ; i++ )
                         Procesando.escribirTextArea("La lista de candidatos es la siguente: "  + listaPersonasReniec.get(i).getDni() );
                  
                   
                    if(listaPersonasReniec.size() != 0 ) 
                    {
                    	try {                           
                    		//firmas
                    	listaPorcentajeFirma= 	AlgoritmoFirmas.procesarNuevo(listaPersonasReniec, n , rutaPlanillonEjecutandose, rutaFirma); 
                        int indiceCandidatoFirmas = candidatoFirmas(listaPorcentajeFirma);  
                     	for ( int i = 0 ; i < listaPersonasReniec.size() ; i++) 
                     		Procesando.escribirTextArea("Para el candiato: " + listaPersonasReniec.get(i).getDni() + " Porcentaje de firmas es: "+ listaPorcentajeFirma.get(i) );     
                     	Procesando.escribirTextArea( "De todos los candidatos el mejor según firmas es: " + listaPersonasReniec.get(indiceCandidatoFirmas).getDni());
                     	//if(  listaPorcentajeFirma.get(  indiceCandidatoFirmas )    >   listaPorcentajeFirma.get(  listaPorcentajeFirma.size()-1  )    )   {
                     	 if(  elElegidoFinal ==null     )   elElegidoFinal = listaPersonasReniec.get(indiceCandidatoFirmas) ;
						 						
                
                     	rf.recortesHuellasOficial(ruta0);
                        List<Integer>coordX = rf.coordX;
                        List<Integer>coordY = rf.coordY;
                     	ImagePlus Copia2 = IJ.openImage(ruta0);
                        Copia2.setRoi(coordX.get(n), coordY.get(n) , rf.ancho , rf.alto);
                        IJ.run(Copia2, "Crop", ""); 
                        listaPorcentajeHuella = AlgoritmoHuellas.procesarNuevo(listaPersonasReniec,Copia2,rutaHuella); 
                       	int indiceCandidatoHuellas = candidatoHuellas(listaPorcentajeHuella);
                      	for ( int i = 0 ; i < listaPersonasReniec.size() ; i++) 
                      		Procesando.escribirTextArea("Para el candiato: " + listaPersonasReniec.get(i).getDni() + " Porcentaje de huellas es: "+ listaPorcentajeHuella.get(i) );     
                      	Procesando.escribirTextArea( "De todos los candidatos el mejor según Huellas es: " + listaPersonasReniec.get(indiceCandidatoHuellas).getDni());

                      	
                      	
                      	
                    	List<Double> sumaPorcentajes = new ArrayList<Double>();
						double valor = 0.0;
						for (int i = 0 ;  i< listaPersonasReniec.size() ; i++){
							valor = listaPorcentajeFirma.get(i)*8 + listaPorcentajeHuella.get(i)*2 ;
							sumaPorcentajes.add(valor);
						}
						
						
						 int indice = candidatoHuellas(sumaPorcentajes);
						 
						 elElegidoFinal= listaPersonasReniec.get(indice);
                      	
                      	
                      	if (elElegidoFinal != null ) {
	  
                      		//voy a llenar partido persona   
                      		PartidoPersona   auxElegidoPartidoPersona  = new PartidoPersona() ; 
                      		auxElegidoPartidoPersona.setPersona(elElegidoFinal);
                      		auxElegidoPartidoPersona.setPartido(pp);
                      		Participante par = new Participante () ;
                      		par.setApellidos( elElegidoFinal.getApellidos() );
                      		par.setDni(elElegidoFinal.getDni());
                      		par.setIdFirma( elElegidoFinal.getIdFirma());
							par.setIdHuella(   "" +  elElegidoFinal.getIdHuella()       );
							par.setNombres(   elElegidoFinal.getNombre()    );
							//  aca ira duplicidad par.setObservacion(                                                                          );
							par.setObservacion(  "No hay observacion"                                                                         );
							par.setPorcentajeFirma(listaPorcentajeFirma.get(  indiceCandidatoFirmas )          );
							par.setPorcentajeHuella(  listaPorcentajeHuella.get(indiceCandidatoHuellas) );
							auxElegidoPartidoPersona.setParticipando(  par );
							
						
							participantesPreDuplicidad.add( auxElegidoPartidoPersona) ; 
                      	}
                    	} catch (IOException e) {
                    		// TODO Auto-generated catch block
                    		e.printStackTrace();
                    	} 
                    }  
                }    

                //System.out.println("Padron numero " + (contPadrones+1) + " Procesado");
                contPadrones++;
                rf.coordX = new ArrayList<Integer> ();
                rf.coordY = new ArrayList<Integer> ();

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
				//if (row == null) break;
				// Index of column D is 3 (A->0, B->1, etc)
				
				nombre = row.getCell(0);
				if(nombre==null || nombre.getCellType() == Cell.CELL_TYPE_BLANK )break;
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
				
				//System.out.println(pr.getIdFirma());
				
				pr.setIdHuella( idHuella.getStringCellValue());
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
	
	for (int i = 0 ; i < lista.size() -1  ; i++){
		
		if (lista.get(i) >= mayor) {
			indice= i ;
			mayor = lista.get(i);
			
			
		}
		
	}
	
	
	return indice ;
	
	
	
}


public int candidatoHuellas ( List<Double > lista)  {
	
	int indice = -1 ; 
	Double mayor = 0.0;
	
	for (int i = 0 ; i < lista.size()  ; i++){
		
		if (lista.get(i) >= mayor) {
			indice= i ;
			mayor = lista.get(i);
			
			
		}
		
	}
	
	
	return indice ;
	
	
}



}