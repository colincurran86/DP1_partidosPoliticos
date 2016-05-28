package bModel;

import models.PartidoPolitico;
import businessModel.Dao.DAOFactory;
import businessModel.Dao.DAOPartPolitico;



public class PartPoliticoDB {
	DAOFactory daoFactory = DAOFactory.getDAOFactory();
    DAOPartPolitico daoPartPolitico = daoFactory.getDAOPartPolitico(); // POLIMORFISMO
    
    public void add(PartidoPolitico p) {
    	daoPartPolitico.add(p);
    }
}
