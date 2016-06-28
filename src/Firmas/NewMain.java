package Firmas;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.Buffer;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import Catalano.Imaging.FastBitmap;
import models.PersonaReniec;



/**
 *
 * @author LUIS S
 */
public class NewMain {

    /**
     * @param args the command line arguments
     */
	private static int[] indicePersonaFallaron = null;
	
    /**
     * @param args
     * @throws IOException
     */
    /**
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
       
       //Inicio
       AlgoritmoFirmas af = new AlgoritmoFirmas();
       ArrayList<FirmaRecortada>lbi = new ArrayList<FirmaRecortada>();
       String urlPlanillonesOriginales = new String("C:\\Users\\LUIS S\\Desktop\\Nueva carpeta\\Firmas Java\\Otras_Resoluciones\\objs\\planillon");
       //String urlPlanillonesOriginales = new String("C:\\Users\\LUIS S\\git\\DP1_partidosPoliticos\\src\\Recorte\\kappa");
       
       String urlBaseDeDatos = new String("C:\\Users\\LUIS S\\Desktop\\Firmas Java\\1.jpg");
       List<PersonaReniec> lista;
       List<List<PersonaReniec>> lper = new ArrayList<List<PersonaReniec>>();
       /*
       lbi = af.cortarFirmas(urlPlanillonesOriginales); 
       lper = af.llenarDatosPruebaListaDeLista();   
       lista = af.procesarFirmas(lbi,lper,urlBaseDeDatos);      
       
       //Resultados
       System.out.println("Resultados finales: ");
       for (int i = 0; i < lista.size(); i++) {
    	   //if(lista.get(i).getDni().equals("00000000")==false)
		System.out.println("dni :"+lista.get(i).getDni());
       }
       */
       
       /*
       lper = af.llenarDatosPruebaListaDeLista(); 
       lista = af.procesar(lper, urlPlanillonesOriginales, urlBaseDeDatos);
       System.out.println("Resultados finales: ");
       for (int i = 0; i < lista.size(); i++) {
    	   //if(lista.get(i).getDni().equals("00000000")==false)
		System.out.println("dni :"+lista.get(i).getDni());
       }
      */
       
  
      int indice=2;
      List<PersonaReniec> lper2 = new ArrayList<PersonaReniec>();
      lper2 = af.llenarDatosPruebaListaDeLista2();   

     // lista = af.procesarNuevo(lper2, indice, urlPlanillonesOriginales, urlBaseDeDatos);
//     List<Double>  resupuesta = af.procesarNuevo(lper2, indice, urlPlanillonesOriginales, urlBaseDeDatos);
  // for (int i = 0; i < lista.size(); i++) {
     
      //urlBaseDeDatos = new String("C:\\Users\\LUIS S\\Desktop\\bd");
    
      
      //urlPlanillonesOriginales = new String("C:\\Users\\LUIS S\\Desktop\\Nueva carpeta\\Firmas Java\\Otras_Resoluciones\\objs\\planillon\\1.jpg");
    
      
      
      
      
      
      
      
      
      /*
      urlPlanillonesOriginales = new String("C:\\Users\\LUIS S\\Desktop\\part.d.original9.png");
      urlBaseDeDatos = new String("C:\\Users\\LUIS S\\Desktop\\Firmas Java\\1.jpg");
      //urlBaseDeDatos = new String("C:\\Users\\LUIS S\\Desktop\\Firmas.jpg\\dfi00"+(i+1)+".png");
      System.out.println("base datos: "+urlBaseDeDatos);
      indice=0;
      List<Double>  resupuesta = af.procesarNuevo(lper2, indice, urlPlanillonesOriginales, urlBaseDeDatos);     
      for (int i = 0; i < resupuesta.size(); i++) {
	//System.out.println("Indice: "+i+" DNI: "+lista.get(i).getDni());
	   System.out.println("Indice: "+i+" DNI: "+resupuesta.get(i));
    }
    */
     
      
      
      
      
      
      //af.procesar2();
      
      
      
      
      
      
      
      
       /*
     FastBitmap i3 = new FastBitmap("C:\\Users\\LUIS S\\Desktop\\Nueva carpeta\\Firmas Java\\Imagenes\\5.jpg");
     urlBaseDeDatos = new String("C:\\Users\\LUIS S\\Desktop\\Nueva carpeta\\Firmas Java\\Imagenes\\1otsu.png");
     FirmaRecortada ne = new FirmaRecortada();
     ne.alto = i3.getHeight();
     ne.ancho = i3.getHeight();
     ne.img  = i3.toBufferedImage();
     System.out.println("Alto: "+ne.alto);
     System.out.println("Ancho: "+ne.ancho);
     
     af.procesarFirmasNuevoNuevo(ne,urlBaseDeDatos);
   */
    
      
      //urlPlanillonesOriginales = new String("C:\\Users\\LUIS S\\Desktop\\part.d.original3.png");
     // urlPlanillonesOriginales = new String("C:\\Users\\LUIS S\\Desktop\\Nueva carpeta\\Firmas Java\\Otras_Resoluciones\\objs\\planillon\\8.jpg"); 
     
     //urlPlanillonesOriginales = new String("C:\\Users\\LUIS S\\Desktop\\part.d.original3.png"); 
   //  urlPlanillonesOriginales = new String("C:\\Users\\LUIS S\\Desktop\\Padron1MenosCalidad.png"); 
   //  urlPlanillonesOriginales = new String("C:\\Users\\LUIS S\\Desktop\\part.d.original22.png"); 


     
     
     /*
      urlBaseDeDatos = new String("C:\\Users\\LUIS S\\Desktop\\Firmas.jpg\\dfi003.png");
      System.out.println("ULR: "+urlBaseDeDatos);
      // urlBaseDeDatos = new String("C:\\Users\\LUIS S\\Desktop\\Nueva carpeta\\Firmas Java\\1.jpg");
       //urlPlanillonesOriginales = new String("C:\\Users\\LUIS S\\Desktop\\1.jpg");
       FirmaRecortada ne = new FirmaRecortada(); 
       ne = af.cortarFirma(urlPlanillonesOriginales,4);
       af.procesarFirmasNuevoNuevo(ne,urlBaseDeDatos);
     */
 
      
      
      
      
      
      
      
      
      
      
      
     // for (int i = 0; i < 8; i++) {
		
	
      urlBaseDeDatos = new String("C:\\Users\\LUIS S\\Desktop\\Firmas.jpg\\dfi065.png");
      urlPlanillonesOriginales = new String("C:\\Users\\LUIS S\\Desktop\\part.d.original9.png");
      System.out.println("ULR turno: ---------------------------------------------------"+urlBaseDeDatos);
      // urlBaseDeDatos = new String("C:\\Users\\LUIS S\\Desktop\\Nueva carpeta\\Firmas Java\\1.jpg");
       //urlPlanillonesOriginales = new String("C:\\Users\\LUIS S\\Desktop\\1.jpg");
       FirmaRecortada ne = new FirmaRecortada(); 
       ne = af.cortarFirma(urlPlanillonesOriginales,0);
       List<Double> dl = af.procesarFirmasNuevoNuevo(ne,urlBaseDeDatos);
       System.out.println("Umbral: "+dl.get(1)+" i: ");
      
   //   }  
      
     
    }
    
}



