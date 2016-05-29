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
import java.util.Date;

import models.Rol;
import models.Usuario;

public class MySQLDaoLogin implements DaoLogin {

	private Connection connect = null;
	private Statement statement = null;
	private ResultSet resultSet = null;

	@Override
	public boolean select(Usuario usuario) {

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
			resultSet = statement.executeQuery("select count(*) from inf226ed.UsuarioLogin where Nombre = '"
					+ usuario.getNombre() + "'" + "and Password= '" + usuario.getPassword() + "'");
			resultSet.next();
			int resultado = resultSet.getInt(1);

			if (resultado == 1)
				return true;
			else
				return false;
			// if (resultSet.wasNull() ) return false; else return true ;
			// while (resultSet.next()) {
			// String nombre = resultSet.getString("Descripcion");
			// int edad = resultSet.getStri(2);
			// System.out.println(nombre );
			// }
			// int des = resultSet.getInt(1);
			// System.out.println( des );
			// connect.close();

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
		return true;
	}

}
