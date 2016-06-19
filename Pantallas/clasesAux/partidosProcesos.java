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
	
	
	public static List<PartidoPersona> verificarDuplicados(List<PartidoPersona>partidoPersona){
		
		
		List<PartidoPersona> nuevaLista = new ArrayList<PartidoPersona>();
		//Verificamos los duplicados para cada partido politico
		for (int i = 0; i<partidoPersona.size(); i++){
				if (partidoPersona.get(i).getCondicion() == 0){
					for (int j = i+1; j<partidoPersona.size();j++){
						if (partidoPersona.get(i).getPersonaReniec().getDni().compareTo(partidoPersona.get(j).getPersonaReniec().getDni()) == 0){
							nuevaLista.add(partidoPersona.get(j));
							partidoPersona.get(j).setCondicion(1);
							break;
						}
					}
				}
		}
		
		return nuevaLista;
		
	}
	
}
