package businessModel.Dao;






public abstract class DAOFactory {
	public static DAOFactory getDAOFactory(){
		return new MySQLDAOFactory();
	}
	public abstract DAOPartPolitico getDAOPartPolitico();
	public abstract DaoLogin getDAOLogin();
	public abstract DAOTipoProceso getDAOTProceso();
	public abstract DAOProceso getDAOProceso();
	public abstract DAOCalendario getDAOCalendario();
	
}
