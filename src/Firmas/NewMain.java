/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Firmas;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
        AlgoritmoFirmas af = new AlgoritmoFirmas("C:\\Users\\LUIS S\\Desktop\\Nueva carpeta\\Firmas Java\\Otras_Resoluciones", true, false, false, false,55);
        int [] indicePersonasFallaron=null;
        //System.out.println("Inicio");
        //indicePersonaFallaron= af.procesar(false, true, false, false, false, 55);
        if(indicePersonaFallaron!=null)
        for (int i = 0; i < indicePersonaFallaron.length; i++) {
            System.out.println("Persona fallo firma con id: "+(indicePersonaFallaron[i]+1));
        }       
       //
        
      //af.procesar2();
        
        
        
        //Posibles candidatos
        
        ArrayList<Integer> idPersonas = new ArrayList<>();
        idPersonas.add(2);
        idPersonas.add(3);
        idPersonas.add(4);
        idPersonas.add(5);
        idPersonas.add(6);
        idPersonas.add(7);
        idPersonas.add(8);
       
        //Firmas de esos posibles candidatos
        ArrayList<Integer> idFirmas = new ArrayList<>();
        idFirmas.add(36);
        idFirmas.add(37);
        idFirmas.add(38);
        idFirmas.add(39);
        idFirmas.add(40);
        idFirmas.add(41);
        idFirmas.add(42);
        
        String url1 = new String("C:\\Users\\LUIS S\\git\\Firmas Pruebas");
        String url2 = new String("C:\\Users\\LUIS S\\Desktop\\Nueva carpeta\\Firmas Java\\Imagenes\\firmas.jpg");
        
        /*
        List<List<Resultado>> listaTemporalPersona = af.verificarFirmas2(idPersonas, idFirmas, url1, url2);
        
 
        for (int i = 0; i < listaTemporalPersona.size(); i++) {	
        	for (int k = 0; k < listaTemporalPersona.get(i).size(); k++) {
				System.out.println(listaTemporalPersona.get(i).get(k).indFirmaMatch+" "+listaTemporalPersona.get(i).get(k).porcentaje);
			}
		}    	
        
       
    */
        
        
 //       List<Resultado> listaTemporalPersona = af.verificarFirmas5(idPersonas, idFirmas, url1, url2);
   /*     
        
        for (int i = 0; i < listaTemporalPersona.size(); i++) {	
        //	for (int k = 0; k < listaTemporalPersona.get(i).size(); k++) {
        	System.out.println(listaTemporalPersona.get(i).indFirmaMatch+" "+listaTemporalPersona.get(i).porcentaje+" "+listaTemporalPersona.get(i).idPersona);
		//	}
		}  
     */   
        
    }
    
}
