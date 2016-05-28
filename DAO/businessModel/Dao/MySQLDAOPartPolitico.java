package businessModel.Dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import models.PartidoPolitico;


public class MySQLDAOPartPolitico implements DAOPartPolitico{

	@Override
	public void add(PartidoPolitico p) {
		// TODO Auto-generated method stub
		/*Connection conn = null;
		PreparedStatement pstmt = null;
		
		try{
			DriverManager.registerDriver(new com.mysql.jdbc.Driver());
			
		}catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			//Paso 6(OJO): Cerrar la conexión
			try { if (pstmt!= null) pstmt.close();} 
				catch (Exception e){e.printStackTrace();};
			try { if (conn!= null) conn.close();} 
				catch (Exception e){e.printStackTrace();};						
		}*/
	}

}
