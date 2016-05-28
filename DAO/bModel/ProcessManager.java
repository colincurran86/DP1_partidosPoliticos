package bModel;

import models.Usuario;

public class ProcessManager {

	
	private static Login log = new Login() ;
	
	
	public static boolean search (Usuario u   ) {
		
		
		
		return  log.chequear(  u); 
		
		
	};
	
	
}