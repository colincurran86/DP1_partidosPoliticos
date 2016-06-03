package businessModel.Dao;

import java.text.ParseException;
import java.util.ArrayList;

import models.PartidoPolitico;
import models.ProcesoElectoral;

public interface DAOProceso {
	void add(ProcesoElectoral e);
	ArrayList<ProcesoElectoral> queryAllProceso();
	void updateProceso(ProcesoElectoral p);
	boolean deleteProceso(int id);

}
