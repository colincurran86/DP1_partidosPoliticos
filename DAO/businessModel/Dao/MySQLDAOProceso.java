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

import models.Rol;
import models.Usuario;

public class MySQLDAOProceso{

	private Connection connect = null;
	private Statement statement = null;
	private ResultSet resultSet = null;
	private List<String> values =  new ArrayList<String>();

	public List<String> getProcesos() {

		// This will load the MySQL driver, each DB has its own driver
		try {
			// Class.forName("mysql-connector-java-5.1.35-bin.jar");
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			connect = DriverManager.getConnection("jdbc:mysql://quilla.lab.inf.pucp.edu.pe:3306/inf226ed?"
					+ "user=inf226ed&password=44RxmAP9qTa4V24L");
			

			// System.out.println("hola22222");
			// connect = DriverManager
			// .getConnection("jdbc:mysql://localhost/feedback?"
			// + "user=sqluser&password=sqluserpw");
			// System.out.println(usuario.getNombre() );
			statement = connect.createStatement();
			resultSet = statement.executeQuery("select * from inf226ed.TipoProceso");
			while (resultSet.next()){
				 String str1 = resultSet.getString("Descripcion");
				 values.add(str1);
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
		return values;
	}

}


