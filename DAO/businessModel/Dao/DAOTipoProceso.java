package businessModel.Dao;

import java.util.ArrayList;

import models.TipoProceso;

public interface DAOTipoProceso {
	void add(TipoProceso tp);
	void updateTProc(TipoProceso tp);
	void deleteTProc(int id);	
	ArrayList<TipoProceso> queryAllTProc();
	TipoProceso queryTPById(int id);
}
