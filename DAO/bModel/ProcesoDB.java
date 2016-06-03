package bModel;

import java.text.ParseException;
import java.util.ArrayList;

import businessModel.Dao.DAOFactory;
import businessModel.Dao.DAOProceso;
import businessModel.Dao.DAOTipoProceso;
import models.ProcesoElectoral;
import models.TipoProceso;

public class ProcesoDB {
	DAOFactory daoFactory = DAOFactory.getDAOFactory();
    DAOProceso daoProc = daoFactory.getDAOProceso(); // POLIMORFISMO
    
    public void add(ProcesoElectoral p) {
    	daoProc.add(p);
    }
    
    public ArrayList<ProcesoElectoral> queryAllProc(){
    	return daoProc.queryAllProceso();
    }
    
    public void updateTProc(ProcesoElectoral p){
    	daoProc.updateProceso(p);
    }
    
    public boolean deleteTProc(int id){
    	return daoProc.deleteProceso(id);
    }
    
    
}
