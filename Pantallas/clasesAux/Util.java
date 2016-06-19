package clasesAux;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import pantallas.PrimeraFase;
import pantallas.Procesando;
import Recorte.Main;
import models.PersonaReniec;
import models.ReniecBD;

public class Util {

	public static String mensajeFinal = new String();

	public String formatearRuta(String ruta) {
		String nuevaRuta = "";
		for (int i = 0; i < ruta.length(); i++)
			if (ruta.charAt(i) == '\\')
				nuevaRuta += "/";
			else
				nuevaRuta += ruta.charAt(i);
		return nuevaRuta;
	}

	public void gerardoRecortesWarning() {

		long startTime = System.currentTimeMillis();

		Util u = new Util();
		Main m = new Main();
		PrimeraFase primeraFase = new PrimeraFase();
		String formatearRutaPlan = "D:\\Users\\jemarroquin\\git\\DP1_partidosPoliticos\\src\\Recorte\\Padrones";
	
	//	String formatearRutaPlan = u.formatearRuta(primeraFase.rutaPadrones);
		m.main(formatearRutaPlan);

	
		
		
		
		/*
		
		List<List<PersonaReniec>> pr1 = u.ocrMasReniec();
		String cadenaFinal = new String();

	
		for (int i = 0; i < pr1.size(); i++) {
			cadenaFinal = "DNI Reconocido es el siguiente: ";
			for (int j = 0; j < pr1.get(i).size(); j++){
				//System.out.println(pr1.get(i).size());
				cadenaFinal = cadenaFinal + pr1.get(i).get(j).getDni() + "  ";
			}
			cadenaFinal = cadenaFinal + "\n";
		}
		
		this.mensajeFinal = cadenaFinal;
		

		System.out.println("**************************************************");
		System.out.println("FIRMAS Reconocidas : ");
		System.out.println("**************************************************");

		
		long endTime = System.currentTimeMillis();
		double totalTime = (endTime - startTime) / 1000.0;
		String almacenarMensaje = new String();
		almacenarMensaje = "Finalizado \n" + "El tiempo total de ejecucion del programa fue " + totalTime
				+ " segundos \n";
		this.mensajeFinal = this.mensajeFinal + almacenarMensaje;
		
	
*/
	}

	public String formatearRuta2(String ruta) {
		String nuevaRuta = "";
		for (int i = 0; i < ruta.length(); i++)
			if (ruta.charAt(i) == '/')
				nuevaRuta += "\\";
			else
				nuevaRuta += ruta.charAt(i);
		return nuevaRuta;
	}

		
	public static List<PersonaReniec> ocrMasReniec2(String dni){
		List<PersonaReniec> candidatos = new ArrayList<PersonaReniec>();
		
		boolean entro = false;
		
		for (int j = 0; j < ReniecBD.lista.size(); j++)
			if (dni != null || dni.length() != 0) {
				if (ReniecBD.lista.get(j).getDni().compareTo(dni)==0) {
					//System.out.println(ReniecBD.lista.size());
					entro = true;
					candidatos = sacaListaCandidatos(dni);
					//System.out.println(a.size());
					candidatos.add(ReniecBD.lista.get(j));
				}
			}
		if (!entro){
			candidatos = sacaListaCandidatos(dni);
			//System.out.println(a.size());
		}				
		
		return candidatos;
	}
	
	public static List<PersonaReniec> sacaListaCandidatos(String dni) {
		List<PersonaReniec> candidatos = new ArrayList<PersonaReniec>();
		// String dniCad="" + dni;

		int posCad1 = 0, posCad2 = 0, posCad3 = 0;
		String cad1 = "", cad2 = "", cad3 = "";

		for (int i = 0; i < 7; i++) {

			for (int j = i + 1; j < 8; j++) {
				cad1 = "";
				cad2 = "";
				cad3 = "";

				posCad1 = 0;
				posCad2 = i + 1;
				posCad3 = j + 1;

				if (i == 0)
					cad1 = "";
				else
					for (int m = posCad1; m < i; m++)
						cad1 += dni.charAt(m);

				if (i == 6 || (j - 1 == 1))
					cad2 = "";
				else
					for (int m = posCad2; m < j; m++)
						cad2 += dni.charAt(m);

				if (j == 7)
					cad3 = "";
				else
					for (int m = posCad3; m < 8; m++)
						cad3 += dni.charAt(m);

				for (int k = 0; k < ReniecBD.lista.size(); k++) {
					String reniecDni = "" + ReniecBD.lista.get(k).getDni();
					if (cad1.compareTo("")!=0)
						for (int m = posCad1; m < cad1.length() + posCad1; m++)
							if (dni.charAt(m) != reniecDni.charAt(m))
								break;

					if (cad2.compareTo("")!=0)
						for (int m = posCad2; m < cad2.length() + posCad2; m++)
							if (dni.charAt(m) != reniecDni.charAt(m))
								break;

					if (cad3.compareTo("")!=0)
						for (int m = posCad3; m < cad3.length() + posCad3; m++) {							
							if (dni.charAt(m) != reniecDni.charAt(m))
								break;
						}
					// si no llego a ningun break, anhade
					for (int m = 0; m < candidatos.size(); m++)// para que no se
																// repita						
						if (candidatos.get(m).getDni().compareTo(ReniecBD.lista.get(k).getDni())!=0)
							candidatos.add(ReniecBD.lista.get(k));
				}
			}
		}
		/*
		 * String dniRecortado=""; int size;
		 * 
		 * for(size=7;size>3;size--){//cadenas de tamanho 7 que coincidan
		 * for(int i=0;i<8-size+1;i++){//posicion dniRecortado=""; for(int
		 * j=i;j<size;j++) //recorto dniRecortado+=dniCad.charAt(j); int
		 * dniRec=Integer.parseInt(dniRecortado);
		 * 
		 * for(int k=0;k<ReniecBD.lista.size();k++){//comparo esa parte de la
		 * cadena con la bd String reniecDni=""+ReniecBD.lista.get(k).getDni();
		 * String reniecRecorte=""; for(int l=i;l<size;l++)//recorte de la bd
		 * reniecRecorte+=reniecDni.charAt(l); int
		 * reniecRec=Integer.parseInt(reniecRecorte); if(reniecRec==dniRec) {
		 * for(int m=0;m<candidatos.size();m++)//para que no se repita
		 * if(candidatos.get(m).getDni()!=ReniecBD.lista.get(k).getDni())
		 * candidatos.add(ReniecBD.lista.get(k)); } } } }
		 */

		return candidatos;
	}

}
