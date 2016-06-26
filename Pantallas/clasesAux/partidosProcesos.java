package clasesAux;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import models.Calendario;
import models.Participante;
import models.PartidoPersona;
import models.ProcesoXFase;
import bModel.ProcessManager;
import businessModel.Dao.DBConnection;

public class partidosProcesos {
	
	public static Connection connect = null;
	public static Statement statement = null;
	public static ResultSet resultSet = null;
	public static List<String> values =  new ArrayList<String>();
	
	public static void almacenarBD(ProcesoXFase p){
		

			// TODO Auto-generated method stub
			int numero=0;
			try {

				Class.forName("com.mysql.jdbc.Driver").newInstance();
				connect = DriverManager.getConnection(DBConnection.URL_JDBC_MySQL, DBConnection.user,
						DBConnection.password);
				statement = connect.createStatement();
	
				System.out.println("FECHAAA :" +  p.getFechaInicioProc());
				statement.executeUpdate("INSERT INTO ProcesoxFasexPartidoPolitico " + "(IdPartidosPoliticos , idFase, Resultado, Observacion, IdProceso, FechaInicioProceso)" 
				+ "VALUES ('"
						+ p.getIdPartPol() + "', '" +  p.getIdFase() + "' , '" + p.getResultado() + "' , '" + p.getObservacion() + "' , '" + p.getIdProceso() +  "' , '" + p.getFechaInicioProc() +  "'   )" );
				
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
	
	
	public static List<PartidoPersona> verificarDuplicados(List<PartidoPersona>partidoPersona ){
		List<PartidoPersona> nuevaLista = new ArrayList<PartidoPersona>();
		//Verificamos los duplicados para cada partido politico
		for (int i = 0; i<partidoPersona.size() -1 ; i++){
				if (partidoPersona.get(i).getCondicionRepetido() == 0){
					for (int j = i+1; j<partidoPersona.size();j++){
						if (partidoPersona.get(i).getPersona().getDni().compareTo(  
								partidoPersona.get(j).getPersona().getDni()
								) == 0){
							nuevaLista.add(partidoPersona.get(j));
							partidoPersona.get(i).setCondicionRepetido(1);
							partidoPersona.get(i).setObservacion( "Repetido entre los partidos politicos " +  partidoPersona.get(i).getPartido().getNombre() + " - "  +    partidoPersona.get(j).getPartido().getNombre() );
							partidoPersona.get(j).setCondicionRepetido(1);
							partidoPersona.get(j).setObservacion( "Repetido entre los partidos politicos " +  partidoPersona.get(i).getPartido().getNombre() + " - "  +    partidoPersona.get(j).getPartido().getNombre() );
							break;
						}
						
					}
				}
		}
		
		return nuevaLista;
		
	}
	
	
	public static List<PartidoPersona>  traerSinDuplicados (List<PartidoPersona>partidoPersona ){
		List<PartidoPersona> nuevaLista = new ArrayList<PartidoPersona>();
		//Verificamos los duplicados para cada partido politico
		for (int i = 0; i<partidoPersona.size() -1 ; i++){
				if (partidoPersona.get(i).getCondicionRepetido() == 0){
					for (int j = i+1; j<partidoPersona.size();j++){
						if (partidoPersona.get(i).getPersona().getDni().compareTo(  
								partidoPersona.get(j).getPersona().getDni()
								) != 0){
							nuevaLista.add(partidoPersona.get(j));
								break;
						}
						
					}
				}
		}
		
		return nuevaLista;
		
	}
	
	
	public static void llenarParticipante(Participante p, int idPP,int idFase, int idPE){		
		try {

			Class.forName("com.mysql.jdbc.Driver").newInstance();
			connect = DriverManager.getConnection(DBConnection.URL_JDBC_MySQL, DBConnection.user,
					DBConnection.password);
			statement = connect.createStatement();

			//System.out.println("FECHAAA :" +  p.getFechaInicioProc());
			statement.executeUpdate("INSERT INTO Participantes " + 
						"(IdPartidosPoliticos , idFase, IdProceso, IdUbigeo, Nombres, Apellidos, Aceptado, DNI, Firma,"
						+ " Huella, Observacion, PorcentajeFirma, PorcentajeHuella)" 
						+ "VALUES (" + idPP + ", " +  idFase + " , " 
						+ idPE + " , " + 1 + " , '" 
						+ p.getNombres() +  "' , '" + p.getApellidos() +  "' , '" + p.getAceptado() + "' , '"
						+ p.getDni() + "' , '" + p.getIdFirma() + "' , '" + p.getIdHuella() + "' , '"+ p.getObservacion() 
						+ "' , " + p.getPorcentajeFirma() + " , " + p.getPorcentajeHuella() + " )" );
			
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
