
package Firmas;

import java.awt.image.BufferedImage;
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
	
    public static void main(String[] args) throws IOException {
       
       //Inicio
       AlgoritmoFirmas af = new AlgoritmoFirmas();
       ArrayList<FirmaRecortada>lbi = new ArrayList<FirmaRecortada>();
       String urlPlanillonesOriginales = new String("C:\\Users\\LUIS S\\Desktop\\Nueva carpeta\\Firmas Java\\Otras_Resoluciones\\objs\\planillon\\");
       //String urlPlanillonesOriginales = new String("C:\\Users\\LUIS S\\git\\DP1_partidosPoliticos44\\src\\Recorte\\kappa\\padron.rayas.firmado.");
       String urlBaseDeDatos = new String("C:\\Users\\LUIS S\\Desktop\\Nueva carpeta\\Firmas Java\\Imagenes\\firmas.jpg");
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
       
       lper = af.llenarDatosPruebaListaDeLista(); 
       lista = af.procesar(lper, urlPlanillonesOriginales, urlBaseDeDatos);
       System.out.println("Resultados finales: ");
       for (int i = 0; i < lista.size(); i++) {
    	   //if(lista.get(i).getDni().equals("00000000")==false)
		System.out.println("dni :"+lista.get(i).getDni());
       }
    }
    
}

