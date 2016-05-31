package businessModel.Dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.sql.Date;
import java.util.List;

import models.Calendario;

public class MySQLDAOCalendario implements DAOCalendario{
	private Connection connect = null;
	private Statement statement = null;
	private ResultSet resultSet = null;
	
	
	@Override
	public void updateCalendario(Calendario c) {
		// TODO Auto-generated method stub
		try {

			Class.forName("com.mysql.jdbc.Driver").newInstance();
			connect = DriverManager.getConnection(DBConnection.URL_JDBC_MySQL, DBConnection.user,
					DBConnection.password);
			statement = connect.createStatement();			
			statement.executeUpdate("UPDATE Calendario " + 
					"SET FechaInicio= " + c.getFechaIni() + ", FechaFin= "
					+ c.getFechaFin() + " WHERE idCalendario= " + c.getId());			
			
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
	public int add(Calendario c) {
		int numero=0;
	    int risultato=-1;
		// TODO Auto-generated method stub
		try {
			// Class.forName("mysql-connector-java-5.1.35-bin.jar");
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			connect = DriverManager.getConnection(DBConnection.URL_JDBC_MySQL, DBConnection.user,
					DBConnection.password);
			statement = connect.createStatement();
			
			
			
			numero=statement.executeUpdate("INSERT INTO Calendario " + "(FechaInicio , FechaFin) " 
					+ "VALUES (" + c.getFechaIni() + ", " + c.getFechaFin() + ")",
					Statement.RETURN_GENERATED_KEYS);
			ResultSet rs = statement.getGeneratedKeys();
	        if (rs.next()){
	            risultato=rs.getInt(1);
	        }
	        rs.close();

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
		return risultato;
	}

}
