package clasesAux;

import java.io.File;

public class LecturaFolder {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		File folder = new File("C:/Users/Administrador/Desktop");
		
		for (final File fileEntry : folder.listFiles()) {
	        if (fileEntry.isDirectory()) {
	        	System.out.println("Directorio:" + fileEntry.getName());
	        } else {
	            System.out.println(fileEntry.getName());
	        }
	    }
	}

}
