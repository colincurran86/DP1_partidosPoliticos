package businessModel.Dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import models.PartidoPolitico;
import models.Rol;
import models.TipoProceso;
import models.Usuario;

public class MySQLDAOTipoProceso implements DAOTipoProceso {

	private static Connection connect = null;
	private static Statement statement = null;
	private static ResultSet resultSet = null;
	private static int id = 0;
	private static List<String> values = new ArrayList<String>();	

	@Override
	public ArrayList<TipoProceso> queryAllTProc() {
		// TODO Auto-generated method stub
		ArrayList<TipoProceso> arr = new ArrayList<TipoProceso>();
		// TODO Auto-generated method stub
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			connect = DriverManager.getConnection(DBConnection.URL_JDBC_MySQL, DBConnection.user,
					DBConnection.password);

			statement = connect.createStatement();
			resultSet = statement.executeQuery("select * from inf226ed.TipoProceso");

			while (resultSet.next()) {
				int id = resultSet.getInt("idTipoProceso");
				String descripcion = resultSet.getString("Descripcion");
				int porcentaje = resultSet.getInt("Porcentaje");

				TipoProceso p = new TipoProceso();
				p.setId(id);
				p.setDescripcion(descripcion);
				p.setPorcentaje(porcentaje);
				arr.add(p);
			}

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
	public void add(TipoProceso tp) {
		// TODO Auto-generated method stub
		// This will load the MySQL driver, each DB has its own driver
		try {
			// Class.forName("mysql-connector-java-5.1.35-bin.jar");
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			connect = DriverManager.getConnection(DBConnection.URL_JDBC_MySQL, DBConnection.user,
					DBConnection.password);
			statement = connect.createStatement();
			statement.executeUpdate("INSERT INTO TipoProceso " + "(Porcentaje , Descripcion) " + "VALUES ('"
					+ tp.getPorcentaje() + "', '" + tp.getDescripcion() + "')");

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
	public void updateTProc(TipoProceso tp) {
		// TODO Auto-generated method stub
		// This will load the MySQL driver, each DB has its own driver
		try {
			// Class.forName("mysql-connector-java-5.1.35-bin.jar");
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			connect = DriverManager.getConnection(DBConnection.URL_JDBC_MySQL, DBConnection.user,
					DBConnection.password);
			statement = connect.createStatement();			
			String sql = "UPDATE TipoProceso " + "SET Descripcion= '" + tp.getDescripcion() 
					+ "' , Porcentaje= " + tp.getPorcentaje() + " WHERE idTipoProceso= " + tp.getId();

			statement.executeUpdate(sql);

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
	public void deleteTProc(int id) {
		// TODO Auto-generated method stub
		try {
			// Class.forName("mysql-connector-java-5.1.35-bin.jar");
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			connect = DriverManager.getConnection(DBConnection.URL_JDBC_MySQL, DBConnection.user,
					DBConnection.password);
			statement = connect.createStatement();
			statement.executeUpdate("DELETE FROM TipoProceso WHERE idTipoProceso=" + id);

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
