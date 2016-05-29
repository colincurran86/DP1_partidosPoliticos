package bModel;


import models.Usuario;

import java.util.ArrayList;

import models.PartidoPolitico;

public class ProcessManager {



	private static Login log = new Login() ;
	private static PartPoliticoDB partPoliticoDB = new PartPoliticoDB();
	
	public static boolean search (Usuario u   ) {
				return  log.chequear(  u); 
				
	};
	
	public static void addPartPolitico(PartidoPolitico p)
    {
        partPoliticoDB.add(p);
    };
	
	public static ArrayList<PartidoPolitico> queryAllPartPol(){
		return partPoliticoDB.queryAllPartPol();
	};
	
	public static void updatePartPol(PartidoPolitico p){
		partPoliticoDB.updatePartPol(p);
	};
}
