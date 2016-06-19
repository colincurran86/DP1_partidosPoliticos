package clasesAux;

import java.util.List;

import Recorte.Main;
import models.PersonaReniec;

public class MaixAux2 {

    public static void main(String[] args) {
        // TODO Auto-generated method stub
        
    /*
    	Main mainRecorte = new Main();
        mainRecorte.main("kaka");
        Util kaka2 = new Util();
       
        for (int i = 0; i< mainRecorte.lista.size(); i++) System.out.println( mainRecorte.lista.get(i)) ;
        List<PersonaReniec> pr = kaka2.ocrMasReniec();
       // System.out.println("cuantos ha encontrado: " + pr.size());
*/
       
    	Util u = new Util();
    	u.llenarBDReniec("C:\\Users\\Administrador\\Desktop\\Christian\\9no\\DP1\\Entrega de padrones\\registro.nacional.v.1.xlsx");
    	List<PersonaReniec> l=u.sacaListaCandidatos("10132272");
    	System.out.println("LISTA CANDIDATOS: "+l.size());
    	for(int i=0;i<l.size();i++)
    		l.get(i).getDni();
    }

}