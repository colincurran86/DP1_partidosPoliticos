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

	public String formatearRuta(String ruta) {
		String nuevaRuta = "";
		for (int i = 0; i < ruta.length(); i++)
			if (ruta.charAt(i) == '\\')
				nuevaRuta += "/";
			else
				nuevaRuta += ruta.charAt(i);
		return nuevaRuta;
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
				pr.setDni((int) dni.getNumericCellValue());
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

	public List<PersonaReniec> ocrMasReniec() {
		List<PersonaReniec> pr = new ArrayList<PersonaReniec>();
		boolean entro = false;

		for (int i = 0; i < Main.lista.size(); i++) {
			String dni = Main.lista.get(i);
			entro = false;
			System.out.println("DNI RECONOCIDO DE LAS IMAGENES: " + dni);
			for (int j = 0; j < ReniecBD.lista.size(); j++)
				if (dni != null) {
					if (ReniecBD.lista.get(j).getDni() == Integer.parseInt(dni)) {
						entro = true;
						pr.add(ReniecBD.lista.get(j));
					}
				}
			if (!entro)
				pr.add(null);
		}
		return pr;
	}
	
	public List<PersonaReniec> sacaListaCandidatos(int dni){
		List<PersonaReniec> candidatos=new ArrayList<PersonaReniec>();
		String dniCad="" + dni;
		String dniRecortado="";
		int size;				
		
		for(size=7;size>3;size--){//cadenas de tamanho 7 que coincidan
			for(int i=0;i<8-size+1;i++){//posicion
				dniRecortado="";
				for(int j=i;j<size;j++) //recorto
					dniRecortado+=dniCad.charAt(j);
				int dniRec=Integer.parseInt(dniRecortado);
				
				for(int k=0;k<ReniecBD.lista.size();k++){//comparo esa parte de la cadena con la bd
					String reniecDni=""+ReniecBD.lista.get(k).getDni();
					String reniecRecorte="";
					for(int l=i;l<size;l++)//recorte de la bd
						reniecRecorte+=reniecDni.charAt(l);
					int reniecRec=Integer.parseInt(reniecRecorte);
					if(reniecRec==dniRec) {
						for(int m=0;m<candidatos.size();m++)//para que no se repita 
							if(candidatos.get(m).getDni()!=ReniecBD.lista.get(k).getDni())
								candidatos.add(ReniecBD.lista.get(k));
					}
				}
			}
		}
		
		return candidatos;
	}
	
}
