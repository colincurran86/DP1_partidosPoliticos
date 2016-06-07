package clasesAux;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Iterator;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class Util {
	public void llenarBDReniec(){
		try {
			InputStream file = new FileInputStream(new File("C:/Users/inf250/Desktop/dp1/registro.nacional.v.1.xlsx"));

			// Get the workbook instance for XLS file
			XSSFWorkbook wb = new XSSFWorkbook(file); // (2)

			// Get third(numbering starts from 0) sheet from the workbook
			XSSFSheet sheet = wb.getSheetAt(0);

			// Get iterator to all the rows in current sheet
			Iterator<Row> rowIterator = sheet.iterator();

			// Iterate through rows
			while (rowIterator.hasNext()) {
				Row row = rowIterator.next();

				// Index of column D is 3 (A->0, B->1, etc)
				Cell cellA = row.getCell(0);
				Cell cellB = row.getCell(1);
				System.out.println(cellA.getStringCellValue());
				System.out.println(cellB.getStringCellValue());

				// Your business logic continues....
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
