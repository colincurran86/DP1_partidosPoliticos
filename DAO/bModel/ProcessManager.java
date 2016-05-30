package bModel;


import models.Usuario;

import java.util.ArrayList;

import models.PartidoPolitico;
import models.TipoProceso;

public class ProcessManager {

	private static Login log = new Login() ;
	private static PartPoliticoDB partPoliticoDB = new PartPoliticoDB();
	private static TipoProcDB tProcesoDB=new TipoProcDB();
	
	//LOGIN!
	public static boolean search (Usuario u   ) {
				return  log.chequear(  u); 
				
	};
	
	//PARTIDO POLITICOS
	public static void addPartPolitico(PartidoPolitico p)
    {
        partPoliticoDB.add(p);
    };
	
	public static ArrayList<PartidoPolitico> queryAllPartPol(){
		return partPoliticoDB.queryAllPartPol();
	};
	
	public static void updatePartPol(PartidoPolitico p){
		partPoliticoDB.updatePartPol(p);
	};
	
	public static void deletePartPol(int id){
		partPoliticoDB.deletePartPol(id);
	}
	
	//TIPO DE PROCESOS
	public static void addTProc(TipoProceso tp)
    {
		tProcesoDB.add(tp);
    };
	
	public static ArrayList<TipoProceso> queryAllTProc(){
		return tProcesoDB.queryAllTProc();
	};
	
	public static void updateTProc(TipoProceso tp){
		tProcesoDB.updateTProc(tp);
	};
	
	public static void deleteTProc(int id){
		tProcesoDB.deleteTProc(id);
	}
	
	
}
