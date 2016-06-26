package clasesAux;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import businessModel.Dao.DBConnection;

public class SegFase {	

	public static Connection connect = null;
	public static Statement statement = null;
	public static ResultSet resultSet = null;
	public static List<String> values =  new ArrayList<String>();
	
	public static int validarRechazados(){
		
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			connect = DriverManager.getConnection(DBConnection.URL_JDBC_MySQL, DBConnection.user,
					DBConnection.password);
			statement = connect.createStatement();

			resultSet = statement.executeQuery(
					"SELECT COUNT(*) " + "FROM ProcesoxFasexPartidoPolitico WHERE Resultado= '" + "Rechazado" + "'");
			resultSet.next();
			
			int val = resultSet.getInt(1);
			return val;

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
		
		
		return 0;
	}

	
}
