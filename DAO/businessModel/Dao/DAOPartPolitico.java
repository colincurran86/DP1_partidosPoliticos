package businessModel.Dao;

import java.util.ArrayList;

import models.PartidoPolitico;


public interface DAOPartPolitico {
	void add(PartidoPolitico p);
	ArrayList<PartidoPolitico> queryAllPartPol();
	void updatePartPol(PartidoPolitico p);
	void deletePartPol(int id);
}
