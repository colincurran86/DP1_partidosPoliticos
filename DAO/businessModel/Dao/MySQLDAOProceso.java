package businessModel.Dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.sql.Date;
import java.util.List;

import bModel.ProcessManager;
import models.Calendario;
import models.PartidoPolitico;
import models.ProcesoElectoral;
import models.Rol;
import models.TipoProceso;
import models.Usuario;

public class MySQLDAOProceso implements DAOProceso{

	private Connection connect = null;
	private Statement statement = null;
	private ResultSet resultSet = null;
	private List<String> values =  new ArrayList<String>();


	@Override
	public void add(ProcesoElectoral p) {
		// TODO Auto-generated method stub
		int numero=0;
		int risultato=-1;
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			connect = DriverManager.getConnection(DBConnection.URL_JDBC_MySQL, DBConnection.user,
					DBConnection.password);
			statement = connect.createStatement();
			numero =statement.executeUpdate("INSERT INTO Proceso "
					+ "(Nombre , PorcentajeAceptado , idTipoProceso) " 
					+ "VALUES ('" + p.getNombre() + "', " + p.getPorcentaje() 
					+ ", " + p.getIdTipoProceso() + ")",Statement.RETURN_GENERATED_KEYS);
			
			ResultSet rs = statement.getGeneratedKeys();
	        if (rs.next()){
	        	//System.out.println(risultato);
	            risultato=rs.getInt(1);
	        }
	        rs.close();
			
			Calendario c=new Calendario();
			c.setFechaIni(p.getFechaIni());
			c.setFechaFin(p.getFechaFin());
			
			
			//System.out.println(p.getFechaIni());
			//System.out.println(p.getFechaFin());
			
			int id=ProcessManager.add(c);
									
			statement.executeUpdate("UPDATE Proceso " + 
					"SET idCalendario= " + id + " WHERE IdProceso= " + risultato);
			
			connect.close();
			//p.setIdCalendario(id);
			//ProcessManager.updateProc(p);
			
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

	@Override
	public ArrayList<ProcesoElectoral> queryAllProceso() {
		ArrayList<ProcesoElectoral> arr = new ArrayList<ProcesoElectoral>();
		// TODO Auto-generated method stub
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			connect = DriverManager.getConnection(DBConnection.URL_JDBC_MySQL, DBConnection.user,
					DBConnection.password);

			statement = connect.createStatement();
			resultSet = statement.executeQuery("select * from Proceso");

			while (resultSet.next()) {
				int id = resultSet.getInt("IdProceso");
				String nombre = resultSet.getString("Nombre");
				int porc = Integer.parseInt(resultSet.getString("PorcentajeAceptado"));
				int idTP = Integer.parseInt(resultSet.getString("idTipoProceso"));
				int idCal=Integer.parseInt(resultSet.getString("idCalendario"));
				
				
				ProcesoElectoral p = new ProcesoElectoral();
				p.setId(id);
				p.setNombre(nombre);
				p.setPorcentaje(porc);
				p.setIdTipoProceso(idTP);
				p.setIdCalendario(idCal);
				
				Calendario c=ProcessManager.queryCalById(idCal);
				p.setFechaIni(c.getFechaIni());
				p.setFechaFin(c.getFechaFin());
				
				TipoProceso tp=ProcessManager.queryTPById(idTP);
				p.setTipoProceso(tp.getDescripcion());
								
				arr.add(p);
			}
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
		return arr;

		
	}

	@Override
	public void updateProceso(ProcesoElectoral p) {
		// TODO Auto-generated method stub
		try {

			Class.forName("com.mysql.jdbc.Driver").newInstance();
			connect = DriverManager.getConnection(DBConnection.URL_JDBC_MySQL, DBConnection.user,
					DBConnection.password);
			statement = connect.createStatement();			
			statement.executeUpdate("UPDATE Proceso " + 
					"SET Nombre= '" + p.getNombre() + "', PorcentajeAceptado= "
					+ p.getPorcentaje() + ", IdTipoProceso= " + p.getIdTipoProceso() 
					+ " WHERE IdProceso= " + p.getId());
			
			resultSet = statement.executeQuery("SELECT * FROM Proceso " + "WHERE idProceso= " + p.getId());
			int idCal=1;
			if (resultSet.next()) 
				idCal = resultSet.getInt("idCalendario");			
			
			//falta actualizar calendario
			statement.executeUpdate("UPDATE Calendario " + "SET FechaInicio= '" 
							+ p.getFechaIni() + "', FechaFin= '"
							+ p.getFechaFin() + "' WHERE idCalendario= " + idCal);
			
			//ProcessManager.updateCal(c);
			
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

	@Override
	public boolean deleteProceso(int id) {
		// TODO Auto-generated method stub
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			connect = DriverManager.getConnection(DBConnection.URL_JDBC_MySQL, DBConnection.user,
					DBConnection.password);
			statement = connect.createStatement();				
			
			resultSet = statement.executeQuery(
					"SELECT COUNT(*) " + "FROM ProcesoxFasexPartidoPolitico WHERE IdProceso=" + id);
			resultSet.next();
			int val = resultSet.getInt(1);
			if (val == 0) {
				statement.executeUpdate("DELETE FROM Proceso WHERE IdProceso = " + id);
				connect.close();
				return true;
			} else {
				connect.close();
				return false;
			}
												
			//eliminar calendario ?
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
		return false;
	}

}


