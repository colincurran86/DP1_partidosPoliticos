package bModel;

import businessModel.Dao.DAOFactory;
import businessModel.Dao.DaoLogin;
import models.Usuario;


public class Login {

	DAOFactory daofactory = DAOFactory.getDAOFactory();
	DaoLogin daologin = daofactory.getDAOLogin();
	
	
	
	
	public boolean chequear (Usuario u ){
		
		
		
		return daologin.select(u) ;
		
		
		
		
	}
	
	
}
