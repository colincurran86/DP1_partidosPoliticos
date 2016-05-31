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
import models.Usuario;

public class MySQLDAOProceso implements DAOProceso{

	private Connection connect = null;
	private Statement statement = null;
	private ResultSet resultSet = null;
	private List<String> values =  new ArrayList<String>();


	@Override
	public void add(ProcesoElectoral p) {
		// TODO Auto-generated method stub
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			connect = DriverManager.getConnection(DBConnection.URL_JDBC_MySQL, DBConnection.user,
					DBConnection.password);
			statement = connect.createStatement();
			statement.executeUpdate("INSERT INTO Proceso "
					+ "(Nombre , PorcentajeAceptado , idTipoProceso) " 
					+ "VALUES ('" + p.getNombre() + "', " + p.getPorcentaje() 
					+ ", " + p.getIdTipoProceso() + ")");
			
			Calendario c=new Calendario();
			c.setFechaIni(p.getFechaIni());
			c.setFechaFin(p.getFechaFin());
			
			int id=ProcessManager.add(c);
			p.setIdCalendario(id);
			ProcessManager.updateProc(p);
			
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
			//falta actualizar calendario
			Calendario c=new Calendario();
			c.setId(p.getIdCalendario());
			c.setFechaIni(p.getFechaIni());
			c.setFechaFin(p.getFechaFin());
			ProcessManager.updateCal(c);
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
	public void deleteProceso(int id) {
		// TODO Auto-generated method stub
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			connect = DriverManager.getConnection(DBConnection.URL_JDBC_MySQL, DBConnection.user,
					DBConnection.password);
			statement = connect.createStatement();					
			statement.executeUpdate("DELETE FROM Proceso WHERE IdProceso = " + id);
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

	}

}


