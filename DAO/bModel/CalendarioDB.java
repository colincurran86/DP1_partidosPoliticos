package bModel;

import java.text.ParseException;

import businessModel.Dao.DAOCalendario;
import businessModel.Dao.DAOFactory;
import businessModel.Dao.DAOPartPolitico;
import models.Calendario;
import models.PartidoPolitico;

public class CalendarioDB {
	DAOFactory daoFactory = DAOFactory.getDAOFactory();
    DAOCalendario daoCalendario = daoFactory.getDAOCalendario(); // POLIMORFISMO
    
    public int add(Calendario c){
    	return daoCalendario.add(c);
    }
    
    public void updateCal(Calendario c){
    	daoCalendario.updateCalendario(c);
    }
}
