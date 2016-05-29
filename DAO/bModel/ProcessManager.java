package bModel;


import models.Usuario;
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
	
	
	
}
