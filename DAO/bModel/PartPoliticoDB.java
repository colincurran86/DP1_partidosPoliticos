package bModel;

import models.PartidoPolitico;

import java.util.ArrayList;

import businessModel.Dao.DAOFactory;
import businessModel.Dao.DAOPartPolitico;



public class PartPoliticoDB {
	DAOFactory daoFactory = DAOFactory.getDAOFactory();
    DAOPartPolitico daoPartPolitico = daoFactory.getDAOPartPolitico(); // POLIMORFISMO
    
    public void add(PartidoPolitico p) {
    	daoPartPolitico.add(p);
    }
    
    public ArrayList<PartidoPolitico> queryAllPartPol(){
    	return daoPartPolitico.queryAllPartPol();
    }
    
    public void updatePartPol(PartidoPolitico p){
    	daoPartPolitico.updatePartPol(p);
    }
    
    public void deletePartPol(int id){
    	daoPartPolitico.deletePartPol(id);
    }
}
