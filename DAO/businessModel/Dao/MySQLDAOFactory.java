package businessModel.Dao;



public class MySQLDAOFactory extends DAOFactory{

	@Override
	public DAOPartPolitico getDAOPartPolitico() {
		// TODO Auto-generated method stub
		return new MySQLDAOPartPolitico();
	}

	@Override
	public DaoLogin getDAOLogin() {
		// TODO Auto-generated method stub
		return new MySQLDaoLogin();
	}

	@Override
	public DAOTipoProceso getDAOTProceso() {
		// TODO Auto-generated method stub
		return new MySQLDAOTipoProceso();
	}

	@Override
	public DAOProceso getDAOProceso() {
		// TODO Auto-generated method stub
		return new MySQLDAOProceso();
	}

	@Override
	public DAOCalendario getDAOCalendario() {
		// TODO Auto-generated method stub
		return new MySQLDAOCalendario();
	}

}
