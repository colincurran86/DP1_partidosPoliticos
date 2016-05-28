package bModel;

import models.PartidoPolitico;



public class ProcessManager {
	private static PartPoliticoDB partPoliticoDB = new PartPoliticoDB();
	
	public static void addPartPolitico(PartidoPolitico p)
    {
        partPoliticoDB.add(p);
    }
}
