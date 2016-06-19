package clasesAux;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import models.Calendario;
import models.PartidoPersona;
import models.ProcesoXFase;
import bModel.ProcessManager;
import businessModel.Dao.DBConnection;

public class partidosProcesos {
	
	public static Connection connect = null;
	public static Statement statement = null;
	public static ResultSet resultSet = null;
	public static List<String> values =  new ArrayList<String>();
	
	public static void almacenarBD(List<ProcesoXFase>resultados){
		
		ProcesoXFase p = new ProcesoXFase();

		for (int i = 0; i < resultados.size(); i++ ){
			
			// TODO Auto-generated method stub
			int numero=0;
			try {

				Class.forName("com.mysql.jdbc.Driver").newInstance();
				connect = DriverManager.getConnection(DBConnection.URL_JDBC_MySQL, DBConnection.user,
						DBConnection.password);
				statement = connect.createStatement();
				numero =statement.executeUpdate("INSERT INTO ProcesoxFasexPartidoPolitico "
						+ "(IdPartidosPoliticos , idFase, Resultado, Observacion, IdProceso, FechaInicioProceso) " 
						+ "VALUES ('" + p.getIdPartPol() + "', " + p.getIdFase() + " '," + p.getResultado() + "', "  + p.getObservacion() 
						+ "', " + p.getIdProceso() + ", " + p.getFechaInicioProc() + ")",Statement.RETURN_GENERATED_KEYS);
				
				ResultSet rs = statement.getGeneratedKeys();
				connect.close();

				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InstantiationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
				
		}
		
		
	}
	
	
	public static void verificarDuplicados(List<PartidoPersona>personaReniec){
		
		for (int i = 0; i<personaReniec.size(); i++){
			
			
			
		}
		
		
		
		
	}
	
}
