package businessModel.Dao;

import java.text.ParseException;

import models.Calendario;
import models.ProcesoElectoral;

public interface DAOCalendario {
	int add(Calendario c) ;
	void updateCalendario(Calendario c);
	Calendario queryCalById(int id);
}
