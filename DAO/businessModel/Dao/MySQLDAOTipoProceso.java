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

public class MySQLDAOTipoProceso{

	private static Connection connect = null;
	private static Statement statement = null;
	private static ResultSet resultSet = null;
	private static int id = 0;
	private static List<String> values =  new ArrayList<String>();

	public void add(String descripcion, int porcentaje) {

		// This will load the MySQL driver, each DB has its own driver
		try {
			// Class.forName("mysql-connector-java-5.1.35-bin.jar");
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			connect = DriverManager.getConnection("jdbc:mysql://quilla.lab.inf.pucp.edu.pe:3306/inf226ed?"
					+ "user=inf226ed&password=44RxmAP9qTa4V24L");
			statement = connect.createStatement();
			resultSet = statement.executeQuery("select max(idTipoProceso) from inf226ed.TipoProceso");
			if(resultSet.next()) {
				id = resultSet.getInt(1);
				id = id+1;
			}
			String sql = "INSERT INTO TipoProceso "+
					"(idTipoProceso, Descripcion , Porcentaje) " +
					"VALUES ('" + id + "', '" + descripcion + "', '" +porcentaje + "')";
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
	
	
	public void modificar(String descripcion, int porcentaje, int id) {

		// This will load the MySQL driver, each DB has its own driver
		try {
			// Class.forName("mysql-connector-java-5.1.35-bin.jar");
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			connect = DriverManager.getConnection("jdbc:mysql://quilla.lab.inf.pucp.edu.pe:3306/inf226ed?"
					+ "user=inf226ed&password=44RxmAP9qTa4V24L");
			statement = connect.createStatement();
			String sql = "UPDATE TipoProceso " + "SET Descripcion= " + "'descripcion'" + ", Porcentaje= "
					+ "'porcentaje'" + " WHERE idTipoProceso= " + id;
			String sql2 = "UPDATE TipoProceso " + "SET Descripcion= '" + descripcion + "' , Porcentaje= "
					+ porcentaje + " WHERE idTipoProceso= " + id;

			statement.executeUpdate(sql2);
	
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
	
	
	public void eliminar(int id) {

		// This will load the MySQL driver, each DB has its own driver
		try {
			// Class.forName("mysql-connector-java-5.1.35-bin.jar");
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			connect = DriverManager.getConnection("jdbc:mysql://quilla.lab.inf.pucp.edu.pe:3306/inf226ed?"
					+ "user=inf226ed&password=44RxmAP9qTa4V24L");
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
	
	public static ArrayList<TipoProceso> queryAllPartPol() {
		ArrayList<TipoProceso> arr = new ArrayList<TipoProceso>();
		// TODO Auto-generated method stub
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			connect = DriverManager.getConnection("jdbc:mysql://quilla.lab.inf.pucp.edu.pe:3306/inf226ed?"
					+ "user=inf226ed&password=44RxmAP9qTa4V24L");

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

}


