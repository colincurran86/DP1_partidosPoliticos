package Reportes;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.hwpf.usermodel.DateAndTime;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;

import models.PartidoPolitico;
import models.ProcesoXFase;

public class PruebaReportes {

	public static void main(String[] args) throws IOException {
	
		ArrayList<ProcesoXFase> proceFaseBd = new ArrayList<ProcesoXFase>();

		ProcesoXFase partidoPoliticoFase = new ProcesoXFase();
		partidoPoliticoFase.setIdFase(1);
		partidoPoliticoFase.setIdPartPol(0);
		partidoPoliticoFase.setIdProceso(0);
		partidoPoliticoFase.setResultado("95%");
		partidoPoliticoFase.setObservacion("Aceptado");
		proceFaseBd.add(partidoPoliticoFase);
		
		partidoPoliticoFase = new ProcesoXFase();
		partidoPoliticoFase.setIdFase(1);
		partidoPoliticoFase.setIdPartPol(1);
		partidoPoliticoFase.setIdProceso(0);
		partidoPoliticoFase.setResultado("30%");
		partidoPoliticoFase.setObservacion("Rechazado");
		proceFaseBd.add(partidoPoliticoFase);
		
		ArrayList<PartidoPolitico> listaPPoliticos = new ArrayList<PartidoPolitico>();
		PartidoPolitico politico1 = new PartidoPolitico();
		politico1.setNombre("Partido politico 1");
		politico1.setId(0);
		listaPPoliticos.add(politico1);
		
		PartidoPolitico politico2 = new PartidoPolitico();
		politico2.setNombre("Partido politico 2");
		politico2.setId(1);	
		listaPPoliticos.add(politico2);
		
	
		Reportes reporte = new Reportes();
		String rutaGuardaReporte = new String("C:\\Users\\LUIS S\\Desktop\\bdd\\reporte.xls");
		reporte.generarReporte2(proceFaseBd, listaPPoliticos,rutaGuardaReporte);
	
	
	}

}
