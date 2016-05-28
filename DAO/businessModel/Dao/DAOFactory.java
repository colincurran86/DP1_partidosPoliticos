package businessModel.Dao;






public abstract class DAOFactory {
	public static DAOFactory getDAOFactory(){
		return new MySQLDAOFactory();
	}
	public abstract DAOPartPolitico getDAOPartPolitico();
	public abstract DaoLogin getDAOLogin();
	
	
}
