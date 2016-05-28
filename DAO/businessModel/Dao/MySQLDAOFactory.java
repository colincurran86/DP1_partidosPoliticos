package businessModel.Dao;



public class MySQLDAOFactory extends DAOFactory{

	@Override
	public DAOPartPolitico getDAOPartPolitico() {
		// TODO Auto-generated method stub
		return new MySQLDAOPartPolitico();
	}

}
