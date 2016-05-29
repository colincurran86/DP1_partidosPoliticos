/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Firmas;

/**
 *
 * @author LUIS S
 */
public class NewMain {

    /**
     * @param args the command line arguments
     */
	
    public static void main(String[] args) {
        AlgoritmoFirmas af = new AlgoritmoFirmas("C:\\Users\\LUIS S\\Desktop\\Firmas Java", true, false, false, false,55);
        int [] indicePersonasFallaron = af.procesar(false, false, false, false, false, 55);
        for (int i = 0; i < indicePersonasFallaron.length; i++) {
            System.out.println("Persona fallo firma con id: "+(indicePersonasFallaron[i]+1));
        }
        // TODO code application logic here
    }
    
}
