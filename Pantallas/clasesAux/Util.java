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
		// txtFieldPlan.setText("D:\\Users\\jemarroquin\\git\\DP1_partidosPoliticos\\src\\Recorte\\Padrones");
		// String formatearRutaPlan = u.formatearRuta(txtFieldPlan.getText());
		m.main("C:\\Users\\User\\Desktop\\9no\\DP1\\DP1_partidosPoliticos\\src\\Recorte\\Padrones");

		// txtFieldBDRNV.setText("D:\\Users\\jemarroquin\\git\\DP1_partidosPoliticos\\src");
		// String formatearRutaBD = u.formatearRuta(txtFieldBDRNV.getText());
		llenarBDReniec("C:\\Users\\User\\Desktop\\9no\\DP1\\Entrega de padrones"
				+ "/registro.nacional.v.1.xlsx");

		// escribirTextArea("****************************");
		// System.out.println("**************************************************");
		// System.out.println("DNIasdasdsd");
		// System.out.println("**************************************************");

		// escribirTextArea("DNI");

		// escribirTextArea("******************************************");

		// List<PersonaReniec> pr1 = u.ocrMasReniec();
		List<List<PersonaReniec>> pr1 = u.ocrMasReniec();
		String cadenaFinal = new String();

		//System.out.println(pr1.size());
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

		// Firmas

		// Main mainRecorte = new Main();

		/*
		 * descomentar aquiii2222 List<String> idFirmasLst = new
		 * ArrayList<String>(); List<Integer> idRegistroLst = new
		 * ArrayList<Integer>();
		 * 
		 * // si no encuentra el dni, no considera la firma :v for (int i = 0; i
		 * < pr1.size(); i++) { if (pr1.get(i) != null) {
		 * idFirmasLst.add(pr1.get(i).getIdFirma()); idRegistroLst.add(i + 1); }
		 * else { idFirmasLst.add("-1"); idRegistroLst.add(i + 1); } }
		 * 
		 * List<Resultado> listaTemporalPersona = null; System.out.println(
		 * "Inicio firmas:"); AlgoritmoFirmas algoritmoFrimas = new
		 * AlgoritmoFirmas();
		 * 
		 * try { listaTemporalPersona =
		 * algoritmoFrimas.verificarFirmas6(idRegistroLst, idFirmasLst,
		 * Main.listaBImage, u.formatearRuta2(formatearRutaBD +
		 * "/firmas.jpg/")); System.out.println("Porcentaje de Firmas "); for
		 * (int i = 0; i < listaTemporalPersona.size(); i++) {
		 * System.out.println("% " + listaTemporalPersona.get(i).porcentaje +
		 * " IDPersona:  " + listaTemporalPersona.get(i).idPersona); }
		 * 
		 * System.out.println("Fin firmas:"); System.out.println(
		 * "**************************************************");
		 * 
		 * } catch (IOException e1) { // TODO Auto-generated catch block
		 * e1.printStackTrace(); }
		 */ // descomentar aquiii222
		
		long endTime = System.currentTimeMillis();
		double totalTime = (endTime - startTime) / 1000.0;
		String almacenarMensaje = new String();
		almacenarMensaje = "Finalizado \n" + "El tiempo total de ejecucion del programa fue " + totalTime
				+ " segundos \n";
		this.mensajeFinal = this.mensajeFinal + almacenarMensaje;
		
		//System.out.println(this.mensajeFinal);
		
		// System.out.println("Finalizado");
		// System.out.println("El tiempo total de ejecucion del programa fue " +
		// totalTime + " segundos");

		// System.out.println("El tiempo total de ejecucion del programa fue " +
		// totalTime + " segundos");

		// aumentarPorcentaje(100);

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

	public void llenarBDReniec(String rutaBD) {
		try {
			InputStream file = new FileInputStream(new File(rutaBD));

			// Get the workbook instance for XLS file
			XSSFWorkbook wb = new XSSFWorkbook(file); // (2)

			// Get third(numbering starts from 0) sheet from the workbook
			XSSFSheet sheet = wb.getSheetAt(0);

			// Get iterator to all the rows in current sheet
			Iterator<Row> rowIterator = sheet.iterator();

			Row row = rowIterator.next();
			// CABECERAS!
			Cell nombre = row.getCell(0);
			Cell apellido = row.getCell(1);
			Cell dni = row.getCell(2);
			Cell ubigeo = row.getCell(3);
			Cell idHuella = row.getCell(4);
			Cell idFirma = row.getCell(5);

			ReniecBD.lista = null;
			ReniecBD.lista = new ArrayList<PersonaReniec>();

			// Iterate through rows
			while (rowIterator.hasNext()) {
				row = rowIterator.next();
				// Index of column D is 3 (A->0, B->1, etc)
				nombre = row.getCell(0);
				apellido = row.getCell(1);
				dni = row.getCell(2);
				ubigeo = row.getCell(3);
				idHuella = row.getCell(4);
				idFirma = row.getCell(5);

				PersonaReniec pr = new PersonaReniec();
				pr.setApellidos(apellido.getStringCellValue());
				int valor = (int) dni.getNumericCellValue();
				String val = "" + valor;
				if (val.length() != 8)
					for (int i = 0; i < 8 - val.length(); i++)
						val = "0" + val;
				pr.setDni(val);
				pr.setIdFirma(idFirma.getStringCellValue());
				pr.setIdHuella((int) idHuella.getNumericCellValue());
				pr.setNombre(nombre.getStringCellValue());
				pr.setUbigeo((int) ubigeo.getNumericCellValue());

				ReniecBD.lista.add(pr);
				// System.out.println(cellA.getStringCellValue());
				// System.out.println(cellB.getStringCellValue());

				// Your business logic continues....
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public List<List<PersonaReniec>> ocrMasReniec() {
		// List<PersonaReniec> pr = new ArrayList<PersonaReniec>();
		List<List<PersonaReniec>> candidatos = new ArrayList<List<PersonaReniec>>();
		/*
		for (int i = 0; i < Main.lista.size(); i++) {
			List<PersonaReniec> xd = new ArrayList<PersonaReniec>();
			candidatos.add(xd);
		}*/

		boolean entro = false;

		List<PersonaReniec> a = new ArrayList<PersonaReniec>();
		/*for (int i = 0; i < Main.lista.size(); i++) 
			System.out.println(Main.lista.get(i));*/
		
		
		for (int i = 0; i < Main.lista.size(); i++) {
			String dni = Main.lista.get(i);
			entro = false;
			// System.out.println("DNI RECONOCIDO DE LAS IMAGENES: " + dni);
			// mensajeFinal = mensajeFinal + "DNI RECONOCIDO DE LAS IMAGENES: "
			// + dni + "\n";
			
			for (int j = 0; j < ReniecBD.lista.size(); j++)
				if (dni != null || dni.length() != 0) {
					if (ReniecBD.lista.get(j).getDni().equals(dni)) {
						
						entro = true;
						a = sacaListaCandidatos(dni);
						//System.out.println(a.size());
						a.add(ReniecBD.lista.get(j));
					}
				}
			if (!entro){
				a = sacaListaCandidatos(dni);
				//System.out.println(a.size());
			}
			candidatos.add(a);
		}

		return candidatos;
	}

	public List<PersonaReniec> sacaListaCandidatos(String dni) {
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
