package Reportes;

import java.awt.Font;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.hwpf.usermodel.DateAndTime;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;

import models.PartidoPersona;
import models.PartidoPolitico;
import models.ProcesoXFase;

public class Reportes {


	public void generarReporte(ArrayList<ProcesoXFase>proceFaseBd ,ArrayList<PartidoPolitico> listaPPoliticos,String rutaGuardaReporte) throws IOException
	{
		int totalAceptados=0;
		int totalRechazados=0;
		FileOutputStream fileOut = new FileOutputStream(rutaGuardaReporte);
		
		
		try {
			
			HSSFWorkbook workbook = new HSSFWorkbook();
			HSSFSheet worksheet = workbook.createSheet("Reporte");
			worksheet.setColumnWidth(0, 5100);
			worksheet.setColumnWidth(1, 5100);
			worksheet.setColumnWidth(2, 5100);
			worksheet.setColumnWidth(3, 5100);
		
			
		    HSSFFont hSSFFont = workbook.createFont();
	        

			Row row1 = worksheet.createRow((short) 0);
			Cell cellA1 = row1.createCell((short) 2);
			cellA1.setCellValue("REPORTE DEL PROCESO DE VALIDACIÓN DE PADRONES");
			CellStyle cellStyle = workbook.createCellStyle();
			cellStyle.setFillForegroundColor(HSSFColor.WHITE.index);
			cellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
			cellStyle.setFont(hSSFFont);
	        cellA1.setCellStyle(cellStyle);
	        
	        
	  
	        hSSFFont.setFontName(HSSFFont.FONT_ARIAL);
	        hSSFFont.setFontHeightInPoints((short) 16);
	        cellStyle.setFont(hSSFFont);    
			row1 = worksheet.createRow((short) 2);
			cellA1 = row1.createCell((short) 0);
			cellA1.setCellValue("FASE");
			cellStyle = workbook.createCellStyle();
			cellStyle.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);
			cellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
			cellStyle.setBorderLeft((short)2);
			cellStyle.setBorderBottom((short) 2);
			cellStyle.setBorderRight((short)2);
			cellStyle.setBorderTop((short)2);
			cellStyle.setAlignment(cellStyle.ALIGN_CENTER);
			cellA1.setCellStyle(cellStyle);

			cellA1 = row1.createCell((short) 1);
			cellA1.setCellValue("PARTIDO POLÍTICO");
			cellStyle = workbook.createCellStyle();
			cellStyle.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);
			cellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
			cellStyle.setBorderLeft((short)2);
			cellStyle.setBorderBottom((short) 2);
			cellStyle.setBorderRight((short)2);
			cellStyle.setBorderTop((short)2);
			cellStyle.setAlignment(cellStyle.ALIGN_CENTER);
			cellA1.setCellStyle(cellStyle);
			
			
			cellA1 = row1.createCell((short) 2);
			cellA1.setCellValue("ID PROCESO");
			cellStyle = workbook.createCellStyle();
			cellStyle.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);
			cellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
			cellStyle.setBorderLeft((short)2);
			cellStyle.setBorderBottom((short) 2);
			cellStyle.setBorderRight((short)2);
			cellStyle.setBorderTop((short)2);
			cellStyle.setAlignment(cellStyle.ALIGN_CENTER);
			cellA1.setCellStyle(cellStyle);
			
			cellA1 = row1.createCell((short) 3);
			cellA1.setCellValue("RESULTADO");
			cellStyle = workbook.createCellStyle();
			cellStyle.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);
			cellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
			cellStyle.setBorderLeft((short)2);
			cellStyle.setBorderBottom((short) 2);
			cellStyle.setBorderRight((short)2);
			cellStyle.setBorderTop((short)2);
			cellStyle.setAlignment(cellStyle.ALIGN_CENTER);
			cellA1.setCellStyle(cellStyle);
			
			cellA1 = row1.createCell((short) 4);
			cellA1.setCellValue("OBSERVACIÓN");
			cellStyle = workbook.createCellStyle();
			cellStyle.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);
			cellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
			cellStyle.setBorderLeft((short)2);
			cellStyle.setBorderBottom((short) 2);
			cellStyle.setBorderRight((short)2);
			cellStyle.setBorderTop((short)2);
			cellStyle.setAlignment(cellStyle.ALIGN_CENTER);
			cellA1.setCellStyle(cellStyle);

			int i;
			for (i = 0; i < proceFaseBd.size(); i++) {

				
			row1 = worksheet.createRow((short) i+4);
			cellA1 = row1.createCell((short) 0);
			cellA1.setCellValue(proceFaseBd.get(i).getIdFase());
			cellStyle = workbook.createCellStyle();
			cellStyle.setFillForegroundColor(HSSFColor.WHITE.index);
			//		cellStyle.setFillForegroundColor(HSSFColor.GOLD.index);
			cellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
			cellStyle.setAlignment(cellStyle.ALIGN_CENTER);
			cellA1.setCellStyle(cellStyle);
			
			String nombrePartido = null;
			Cell cellB1 = row1.createCell((short)1);
			for (int j = 0; j < listaPPoliticos.size(); j++) {
				if(listaPPoliticos.get(j).getId()==proceFaseBd.get(i).getIdPartPol())
					nombrePartido = listaPPoliticos.get(j).getNombre();
			}
			
			cellB1.setCellValue(nombrePartido);
			cellStyle = workbook.createCellStyle();
			cellStyle.setFillForegroundColor(HSSFColor.WHITE.index);
			//	cellStyle.setFillForegroundColor(HSSFColor.LIGHT_CORNFLOWER_BLUE.index);
			cellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
			cellStyle.setAlignment(cellStyle.ALIGN_CENTER);
			cellB1.setCellStyle(cellStyle);

			Cell cellC1 = row1.createCell((short)2);
			cellC1.setCellValue(proceFaseBd.get(i).getIdProceso());
			cellStyle = workbook.createCellStyle();
			cellStyle.setFillForegroundColor(HSSFColor.WHITE.index);
			//	cellStyle.setFillForegroundColor(HSSFColor.LIGHT_CORNFLOWER_BLUE.index);
			cellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
			cellStyle.setAlignment(cellStyle.ALIGN_CENTER);
			cellC1.setCellStyle(cellStyle);
			
			Cell cellD1 = row1.createCell((short)3);
			cellD1.setCellValue(proceFaseBd.get(i).getResultado());
			cellStyle = workbook.createCellStyle();
			cellStyle.setFillForegroundColor(HSSFColor.WHITE.index);
			//	cellStyle.setFillForegroundColor(HSSFColor.LIGHT_CORNFLOWER_BLUE.index);
			cellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
			cellStyle.setAlignment(cellStyle.ALIGN_CENTER);
			cellD1.setCellStyle(cellStyle);
		
			
			Cell cellE1 = row1.createCell((short)4);
			cellE1.setCellValue(proceFaseBd.get(i).getObservacion());
			cellStyle = workbook.createCellStyle();
			if(proceFaseBd.get(i).getObservacion().equals("Aceptado")){
				totalAceptados++;
				cellStyle.setFillForegroundColor(HSSFColor.GOLD.index);
			}
			else{
				totalRechazados++;
				cellStyle.setFillForegroundColor(HSSFColor.WHITE.index);
				}
			
			//	cellStyle.setFillForegroundColor(HSSFColor.LIGHT_CORNFLOWER_BLUE.index);
			cellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
			cellStyle.setAlignment(cellStyle.ALIGN_CENTER);
			cellE1.setCellStyle(cellStyle);
			
			
			}
			int tmp =i+6;
			row1 = worksheet.createRow((short)tmp);
			Cell cellF1 = row1.createCell((short)3);
			cellF1.setCellValue("TOTAL ACEPTADOS: ");
			cellStyle = workbook.createCellStyle();
			cellStyle.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);
			//	cellStyle.setFillForegroundColor(HSSFColor.LIGHT_CORNFLOWER_BLUE.index);
			cellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
			cellStyle.setBorderLeft((short)2);
			cellStyle.setBorderBottom((short) 2);
			cellStyle.setBorderRight((short)2);
			cellStyle.setBorderTop((short)2);
			cellStyle.setAlignment(cellStyle.ALIGN_CENTER);
			cellF1.setCellStyle(cellStyle);
	
			
			Cell cellG1 = row1.createCell((short)4);
			cellG1.setCellValue(totalAceptados);
			cellStyle = workbook.createCellStyle();
			cellStyle.setFillForegroundColor(HSSFColor.RED.index);	
			//	cellStyle.setFillForegroundColor(HSSFColor.LIGHT_CORNFLOWER_BLUE.index);
			cellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
			cellStyle.setAlignment(cellStyle.ALIGN_CENTER);
			cellG1.setCellStyle(cellStyle);
			

			row1 = worksheet.createRow((short)tmp+1);
			Cell cellH1 = row1.createCell((short)3);
			cellH1.setCellValue("TOTAL RECHAZADOS: ");
			cellStyle = workbook.createCellStyle();
			cellStyle.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);
			//	cellStyle.setFillForegroundColor(HSSFColor.LIGHT_CORNFLOWER_BLUE.index);
			cellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
			cellStyle.setBorderLeft((short)2);
			cellStyle.setBorderBottom((short) 2);
			cellStyle.setBorderRight((short)2);
			cellStyle.setBorderTop((short)2);
			cellStyle.setAlignment(cellStyle.ALIGN_CENTER);
			cellH1.setCellStyle(cellStyle);
			i++;
			
			Cell cellI1 = row1.createCell((short)4);
			cellI1.setCellValue(totalRechazados);
			cellStyle = workbook.createCellStyle();
			cellStyle.setFillForegroundColor(HSSFColor.RED.index);	
			//	cellStyle.setFillForegroundColor(HSSFColor.LIGHT_CORNFLOWER_BLUE.index);
			cellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
			cellStyle.setAlignment(cellStyle.ALIGN_CENTER);
			cellI1.setCellStyle(cellStyle);
			
			
			workbook.write(fileOut);
						
	
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		
		fileOut.flush();
		fileOut.close();	
	}
	
	
	public void generarReporteRepetido(List<PartidoPersona> practicantesDuplicados) throws IOException
	{
		int totalAceptados=0;
		int totalRechazados=0; 
		String workingDir = System.getProperty("user.dir");
		String rutaReporte = workingDir + "/ReporteDuplicado.xls";
		System.out.println(rutaReporte);
		FileOutputStream fileOut = new FileOutputStream(rutaReporte);
		
		
		try {
			
			HSSFWorkbook workbook = new HSSFWorkbook();
			HSSFSheet worksheet = workbook.createSheet("Reporte");
			worksheet.setColumnWidth(0, 5100);
			worksheet.setColumnWidth(1, 5100);
			worksheet.setColumnWidth(2, 5100);
			worksheet.setColumnWidth(3, 5100);
		
			
		    HSSFFont hSSFFont = workbook.createFont();
	        

			Row row1 = worksheet.createRow((short) 0);
			Cell cellA1 = row1.createCell((short) 2);
			cellA1.setCellValue("REPORTE DE PERSONAS REPETIDAS ENTRE PARTIDOS ");
			CellStyle cellStyle = workbook.createCellStyle();
			cellStyle.setFillForegroundColor(HSSFColor.WHITE.index);
			cellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
			cellStyle.setFont(hSSFFont);
	        cellA1.setCellStyle(cellStyle);
	        
	        
	  
	        hSSFFont.setFontName(HSSFFont.FONT_ARIAL);
	        hSSFFont.setFontHeightInPoints((short) 16);
	        cellStyle.setFont(hSSFFont);    
			row1 = worksheet.createRow((short) 2);
			cellA1 = row1.createCell((short) 0);
			cellA1.setCellValue("DNI");
			cellStyle = workbook.createCellStyle();
			cellStyle.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);
			cellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
			cellStyle.setBorderLeft((short)2);
			cellStyle.setBorderBottom((short) 2);
			cellStyle.setBorderRight((short)2);
			cellStyle.setBorderTop((short)2);
			cellStyle.setAlignment(cellStyle.ALIGN_CENTER);
			cellA1.setCellStyle(cellStyle);

			cellA1 = row1.createCell((short) 1);
			cellA1.setCellValue("NOMBRE");
			cellStyle = workbook.createCellStyle();
			cellStyle.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);
			cellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
			cellStyle.setBorderLeft((short)2);
			cellStyle.setBorderBottom((short) 2);
			cellStyle.setBorderRight((short)2);
			cellStyle.setBorderTop((short)2);
			cellStyle.setAlignment(cellStyle.ALIGN_CENTER);
			cellA1.setCellStyle(cellStyle);
			
			
			cellA1 = row1.createCell((short) 2);
			cellA1.setCellValue("PARTIDO REPETIDO 1");
			cellStyle = workbook.createCellStyle();
			cellStyle.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);
			cellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
			cellStyle.setBorderLeft((short)2);
			cellStyle.setBorderBottom((short) 2);
			cellStyle.setBorderRight((short)2);
			cellStyle.setBorderTop((short)2);
			cellStyle.setAlignment(cellStyle.ALIGN_CENTER);
			cellA1.setCellStyle(cellStyle);
			
			cellA1 = row1.createCell((short) 3);
			cellA1.setCellValue("PARTIDO REPETIDO 2");
			cellStyle = workbook.createCellStyle();
			cellStyle.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);
			cellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
			cellStyle.setBorderLeft((short)2);
			cellStyle.setBorderBottom((short) 2);
			cellStyle.setBorderRight((short)2);
			cellStyle.setBorderTop((short)2);
			cellStyle.setAlignment(cellStyle.ALIGN_CENTER);
			cellA1.setCellStyle(cellStyle);
			


			int i;
			for (i = 0; i < practicantesDuplicados.size(); i++) {

				
			row1 = worksheet.createRow((short) i+4);
			cellA1 = row1.createCell((short) 0);
			

			cellA1.setCellValue(practicantesDuplicados.get(i).getPersona().getDni());
			cellStyle = workbook.createCellStyle();
			cellStyle.setFillForegroundColor(HSSFColor.WHITE.index);
			//		cellStyle.setFillForegroundColor(HSSFColor.GOLD.index);
			cellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
			cellStyle.setAlignment(cellStyle.ALIGN_CENTER);
			cellA1.setCellStyle(cellStyle);
			
			
			Cell cellB1 = row1.createCell((short)1);

			/*
			String nombrePartido = null;
			for (int j = 0; j < listaPPoliticos.size(); j++) {
				if(listaPPoliticos.get(j).getId()==proceFaseBd.get(i).getIdPartPol())
					nombrePartido = listaPPoliticos.get(j).getNombre();
			}
			*/
			String apellido = practicantesDuplicados.get(i).getPersona().getApellidos();
			String nombre = practicantesDuplicados.get(i).getPersona().getNombre();
			cellB1.setCellValue(apellido + " " + nombre);
			cellStyle = workbook.createCellStyle();
			cellStyle.setFillForegroundColor(HSSFColor.WHITE.index);
			//	cellStyle.setFillForegroundColor(HSSFColor.LIGHT_CORNFLOWER_BLUE.index);
			cellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
			cellStyle.setAlignment(cellStyle.ALIGN_CENTER);
			cellB1.setCellStyle(cellStyle);
			
			
			String observacion = practicantesDuplicados.get(i).getObservacion();
			String a = observacion.substring(38);
			int valorGuion = 0;
			for (int M = 0; M<a.length(); M++){
				if (a.charAt(M)== '-') {
					valorGuion = M; 
					break;
				}	
			}
			
			String nombrePP1 = a.substring(0, valorGuion - 1);
			String nombrePP2 = a.substring(valorGuion+2);
			

			Cell cellC1 = row1.createCell((short)2);
			cellC1.setCellValue(nombrePP1);
			
			cellStyle = workbook.createCellStyle();
			cellStyle.setFillForegroundColor(HSSFColor.WHITE.index);
			//	cellStyle.setFillForegroundColor(HSSFColor.LIGHT_CORNFLOWER_BLUE.index);
			cellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
			cellStyle.setAlignment(cellStyle.ALIGN_CENTER);
			cellC1.setCellStyle(cellStyle);
			
			Cell cellD1 = row1.createCell((short)3);
			cellD1.setCellValue(nombrePP2);
			cellStyle = workbook.createCellStyle();
			cellStyle.setFillForegroundColor(HSSFColor.WHITE.index);
			//	cellStyle.setFillForegroundColor(HSSFColor.LIGHT_CORNFLOWER_BLUE.index);
			cellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
			cellStyle.setAlignment(cellStyle.ALIGN_CENTER);
			cellD1.setCellStyle(cellStyle);	
		}
			workbook.write(fileOut);
	
		
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		System.out.println("ANTEEES");
		fileOut.flush();
		fileOut.close();	
		System.out.println("DESPUES");
	}
	
	
}
