package clasesAux;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import models.Calendario;
import models.Participante;
import models.PartidoPersona;
import models.PartidoPolitico;
import models.ProcesoElectoral;
import models.ProcesoXFase;
import models.TipoProceso;
import pantallas.SegundaFase;
import bModel.ProcessManager;
import businessModel.Dao.DBConnection;

public class partidosProcesos {

	public static Connection connect = null;
	public static Statement statement = null;
	public static ResultSet resultSet = null;
	public static List<String> values = new ArrayList<String>();

	public static void almacenarBD(ProcesoXFase p) {

		// TODO Auto-generated method stub
		int numero = 0;
		try {

			Class.forName("com.mysql.jdbc.Driver").newInstance();
			connect = DriverManager.getConnection(DBConnection.URL_JDBC_MySQL, DBConnection.user,
					DBConnection.password);
			statement = connect.createStatement();

			System.out.println("FECHAAA :" + p.getFechaInicioProc());
			try{
			statement.executeUpdate("INSERT INTO ProcesoxFasexPartidoPolitico "
					+ "(IdPartidosPoliticos , idFase, Resultado, Observacion, IdProceso, FechaInicioProceso)"
					+ "VALUES ('" + p.getIdPartPol() + "', '" + p.getIdFase() + "' , '" + p.getResultado() + "' , '"
					+ p.getObservacion() + "' , '" + p.getIdProceso() + "' , '" + p.getFechaInicioProc() + "'   )");
			}catch(Exception e){
				
			}
			//resultSet.close();
			connect.close();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static List<PartidoPersona>  traerSinDuplicados (List<PartidoPersona>partidoPersona ){
		 		List<PartidoPersona> nuevaLista = new ArrayList<PartidoPersona>();
		 		//Verificamos los duplicados para cada partido politico
		 		for (int i = 0; i<partidoPersona.size() -1 ; i++){
		 				if (partidoPersona.get(i).getCondicionRepetido() == 0){
		 					for (int j = i+1; j<partidoPersona.size();j++){
		 						if (partidoPersona.get(i).getPersona().getDni().compareTo(partidoPersona.get(j).getPersona().getDni()) == 0){
		 							if(partidoPersona.get(i).getPartido().getId()==partidoPersona.get(j).getPartido().getId()){
		 								partidoPersona.get(j).setCondicionRepetido(1);		 								
		 							}else{
		 								partidoPersona.get(i).setCondicionRepetido(1);
		 								partidoPersona.get(j).setCondicionRepetido(1);
		 							}
		 						}
		 						/*
		 						if (partidoPersona.get(i).getPersona().getDni().compareTo(  
		 								partidoPersona.get(j).getPersona().getDni()
		 								) != 0){
		 								nuevaLista.add(partidoPersona.get(j));
		 								break;
		 						}*/		 						
		 					}
		 				}
		 		}
		 		for(int i = 0; i<partidoPersona.size() ; i++)
		 			if(partidoPersona.get(i).getCondicionRepetido()==0) nuevaLista.add(partidoPersona.get(i));
		 				 		
		 		return nuevaLista;		 		
	}

	
	
	public static List<PartidoPersona> verificarDuplicados(List<PartidoPersona>partidoPersona ){
		List<PartidoPersona> nuevaLista = new ArrayList<PartidoPersona>();
		boolean repMismoPart=false;
		boolean repDifPart=false;
		// Verificamos los duplicados para cada partido politico
		for (int i = 0; i < partidoPersona.size() - 1; i++) {
			repMismoPart=false;
			repDifPart=false;
			if (partidoPersona.get(i).getCondicionRepetido() == 0) {
				for (int j = i + 1; j < partidoPersona.size(); j++) {
					if (partidoPersona.get(i).getPersona().getDni().compareTo(partidoPersona.get(j).getPersona().getDni()) == 0) {
						if(partidoPersona.get(i).getPartido().getId()==partidoPersona.get(j).getPartido().getId())
							repMismoPart=true;													
						else 
							repDifPart=true;
												
						partidoPersona.get(j).setCondicionRepetido(1);
						partidoPersona.get(i).setObservacion("Repetido entre los partidos politicos "
								+ partidoPersona.get(i).getPartido().getNombre() + " - "
								+ partidoPersona.get(j).getPartido().getNombre());
						partidoPersona.get(j).setObservacion("Repetido entre los partidos politicos "
								+ partidoPersona.get(i).getPartido().getNombre() + " - "
								+ partidoPersona.get(j).getPartido().getNombre());
						nuevaLista.add(partidoPersona.get(j));
						
						/*nuevaLista.add(partidoPersona.get(i));
						nuevaLista.add(partidoPersona.get(j));
						partidoPersona.get(i).setCondicionRepetido(1);
						
						
						partidoPersona.get(j).setCondicionRepetido(1);
						partidoPersona.get(j).setObservacion("Repetido entre los partidos politicos "
										+ partidoPersona.get(i).getPartido().getNombre() + " - "
										+ partidoPersona.get(j).getPartido().getNombre());
						break;*/
					}
				}
				if(repDifPart){
					partidoPersona.get(i).setCondicionRepetido(1);
					nuevaLista.add(partidoPersona.get(i));
				}else
					partidoPersona.get(i).setCondicionRepetido(0);				
			}
		}

		return nuevaLista;

	}
	
	
	public static List<PartidoPersona> validarPrimFase(List<PartidoPersona> adherentes,int idFase,int idPESeg){
		List<PartidoPersona> nuevaLista = new ArrayList<PartidoPersona>();
		List<PartidoPersona> listaAceptadosPrim = new ArrayList<PartidoPersona>();
		
		
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			connect = DriverManager.getConnection(DBConnection.URL_JDBC_MySQL, DBConnection.user,
					DBConnection.password);
			statement = connect.createStatement();
			
			ResultSet resultSet3 = statement.executeQuery("select * from ProcesoxFasexPartidoPolitico where idFase = " + 1 + " AND IdProceso= " + idPESeg);			

			while (resultSet3.next()) {
				
				int id = resultSet3.getInt("IdPartidosPoliticos");
				Statement statement2 = connect.createStatement();
				ResultSet resultSet2=statement2.executeQuery("select * from Participantes where idFase = " + 1 + " and IdProceso= " + idPESeg + " and IdPartidosPoliticos= " + id);
				
				while(resultSet2.next()){
					int idPar = resultSet2.getInt("IdParticipante");
					String dni= resultSet2.getString("DNI");
					PartidoPersona pp=new PartidoPersona();
					Participante p=new Participante();
					p.setDni(dni);
					pp.setParticipando(p);	
					listaAceptadosPrim.add(pp);
				}
				resultSet2.close();
			}
			resultSet3.close();
			connect.close();
			
			for(int i=0;i<adherentes.size();i++){
				boolean encontrado=false;
				String segDNI= adherentes.get(i).getParticipando().getDni();
				for(int j=0;j<listaAceptadosPrim.size();j++){
					String primDNI=listaAceptadosPrim.get(j).getParticipando().getDni();
					
					if(segDNI.compareTo(primDNI)==0){
						encontrado= true;
						break;
					} 
				}				
				if(!encontrado) nuevaLista.add(adherentes.get(i));
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return nuevaLista;
	}
	
	public static List<PartidoPersona> repetidosPrimFase(List<PartidoPersona> adherentes,List<PartidoPersona> adSinPrimFase,List<PartidoPersona> partDup){
		List<PartidoPersona> nuevaLista = new ArrayList<PartidoPersona>();
				
		for(int i=0;i<adherentes.size();i++){
			boolean encontrado=false;
			String segDNI= partDup.get(i).getParticipando().getDni();
			for(int j=0;j<adSinPrimFase.size();j++){
				String primDNI=adSinPrimFase.get(j).getParticipando().getDni();
				if(segDNI.compareTo(primDNI)==0){
					encontrado= true;
					break;
				} 
			}
			if(!encontrado) partDup.add(adherentes.get(i));
		}		
		nuevaLista=partDup;
		
		return nuevaLista;
	}
	
	
	
	public static void llenarParticipante(Participante p, int idPP, int idFase, int idPE) {
		try {

			Class.forName("com.mysql.jdbc.Driver").newInstance();
			connect = DriverManager.getConnection(DBConnection.URL_JDBC_MySQL, DBConnection.user,
					DBConnection.password);
			statement = connect.createStatement();

			// System.out.println("FECHAAA :" + p.getFechaInicioProc());									
			
			statement.executeUpdate("INSERT INTO Participantes "
					+ "(IdPartidosPoliticos , idFase, IdProceso, IdUbigeo, Nombres, Apellidos, Aceptado, DNI, Firma,"
					+ " Huella, Observacion, PorcentajeFirma, PorcentajeHuella)" + "VALUES (" + idPP + ", " + idFase
					+ " , " + idPE + " , " + 1 + " , '" + p.getNombres() + "' , '" + p.getApellidos() + "' , "
					+ p.getAceptado() + " , '" + p.getDni() + "' , '" + p.getIdFirma() + "' , '" + p.getIdHuella()
					+ "' , '" + p.getObservacion() + "' , " + p.getPorcentajeFirma() + " , " + p.getPorcentajeHuella()
					+ " )");
			//resultSet.close();
			connect.close();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void sacarPrimeraFase(int idFase, int idPE) {
		// TODO Auto-generated method stub
		/*int numero = 0;
		try {

			Class.forName("com.mysql.jdbc.Driver").newInstance();
			connect = DriverManager.getConnection(DBConnection.URL_JDBC_MySQL, DBConnection.user,
					DBConnection.password);
			statement = connect.createStatement();

			//System.out.println("FECHAAA :" + p.getFechaInicioProc());
			statement.executeUpdate("INSERT INTO ProcesoxFasexPartidoPolitico "
					+ "(IdPartidosPoliticos , idFase, Resultado, Observacion, IdProceso, FechaInicioProceso)"
					+ "VALUES ('" + p.getIdPartPol() + "', '" + p.getIdFase() + "' , '" + p.getResultado() + "' , '"
					+ p.getObservacion() + "' , '" + p.getIdProceso() + "' , '" + p.getFechaInicioProc() + "'   )");

			connect.close();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
	}
	

	public static ProcesoElectoral queryById(int id) {

		ProcesoElectoral pe = new ProcesoElectoral();
		// TODO Auto-generated method stub
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			connect = DriverManager.getConnection(DBConnection.URL_JDBC_MySQL, DBConnection.user,
					DBConnection.password);

			statement = connect.createStatement();
			ResultSet resultSet2 = statement.executeQuery("select * from Proceso where IdProceso = " + id);

			while (resultSet2.next()) {
				// int id = resultSet.getInt("IdProceso");
				int idTP = resultSet2.getInt("IdTipoProceso");
				String nombre = resultSet2.getString("Nombre");
				int idCal = resultSet2.getInt("idCalendario");
				int totalP = resultSet2.getInt("TotalPersonas");
				double porc = resultSet2.getDouble("PorcentajeAceptado");

				pe.setId(id);
				pe.setNombre(nombre);
				pe.setPorcentaje((int) porc);
				pe.setIdTipoProceso(idTP);
				pe.setIdCalendario(idCal);
				pe.setTotalPersonas(totalP);

			}
			resultSet2.close();
			connect.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return pe;

	}

	public static List<ProcesoElectoral> procElecRechazados() {

		ArrayList<ProcesoElectoral> arr = new ArrayList<ProcesoElectoral>();
		// TODO Auto-generated method stub
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			connect = DriverManager.getConnection(DBConnection.URL_JDBC_MySQL, DBConnection.user,
					DBConnection.password);

			statement = connect.createStatement();
			resultSet = statement
					.executeQuery("select * from ProcesoxFasexPartidoPolitico where Resultado= '" + "Rechazado" + "'");

			List<Integer> listPE = new ArrayList<Integer>();
			while (resultSet.next()) {
				
				int idProc = resultSet.getInt("IdProceso");
				ProcesoElectoral pe = queryById(idProc);
				if (pe != null)
					arr.add(pe);
			}
			resultSet.close();
			connect.close();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return arr;
	}
	
	public static PartidoPolitico queryByIdPP(int id) {

		PartidoPolitico pp = new PartidoPolitico();
		// TODO Auto-generated method stub
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			connect = DriverManager.getConnection(DBConnection.URL_JDBC_MySQL, DBConnection.user,
					DBConnection.password);

			statement = connect.createStatement();
			ResultSet resultSet2 = statement.executeQuery("select * from PartidosPoliticos where IdPartidosPoliticos = " + id);

			while (resultSet2.next()) {
				// int id = resultSet.getInt("IdProceso");
				int idPP = resultSet2.getInt("IdPartidosPoliticos");
				String nombre = resultSet2.getString("Nombre");
				String rep = resultSet2.getString("Representante");
				String telRep = resultSet2.getString("TelefonoRepre");
				String correo = resultSet2.getString("Correo");
				
				
				pp.setId(id);
				pp.setNombre(nombre);
				pp.setNombreRep(rep);
				pp.setTelefono(telRep);
				pp.setCorreo(correo);				

			}
			resultSet2.close();
			connect.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return pp;
	}

	public static List<PartidoPolitico> queryPPRechazado(int idPE) {
		ArrayList<PartidoPolitico> arr = new ArrayList<PartidoPolitico>();
		// TODO Auto-generated method stub
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			connect = DriverManager.getConnection(DBConnection.URL_JDBC_MySQL, DBConnection.user,
					DBConnection.password);

			statement = connect.createStatement();
			resultSet = statement.executeQuery("select * from ProcesoxFasexPartidoPolitico where IdProceso= " + idPE + " AND Resultado = '" + "Rechazado" + "'");

			List<Integer> listPE = new ArrayList<Integer>();
			while (resultSet.next()) {
				int idPP = resultSet.getInt("IdPartidosPoliticos");
				PartidoPolitico pp = queryByIdPP(idPP);
				if (pp != null)
					arr.add(pp);
			}
			resultSet.close();
			connect.close();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return arr;
	}
	
	
	public static void updatePFPP(ProcesoXFase pxf){
		
		try {

			Class.forName("com.mysql.jdbc.Driver").newInstance();
			connect = DriverManager.getConnection(DBConnection.URL_JDBC_MySQL, DBConnection.user,
					DBConnection.password);
			statement = connect.createStatement();
			statement.executeUpdate("UPDATE ProcesoxFasexPartidoPolitico " + "SET Resultado= '" + pxf.getResultado()
					+ "', TotalAdherentes= " + pxf.getTotalAd() + ", TotalDuplicados= " + pxf.getTotalDup() + " WHERE IdPartidosPoliticos= " + pxf.getIdPartPol() + 
					" AND IdProceso= "+ pxf.getIdProceso() + " AND idFase= "+ pxf.getIdFase());
			resultSet.close();
			connect.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public static int getCantPer(int idPE){
		ProcesoElectoral pe = new ProcesoElectoral();
		int val=0;
		// TODO Auto-generated method stub
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			connect = DriverManager.getConnection(DBConnection.URL_JDBC_MySQL, DBConnection.user,
					DBConnection.password);

			statement = connect.createStatement();
			resultSet = statement.executeQuery("select * from Proceso where IdProceso = " + idPE);

			while (resultSet.next()) {
				// int id = resultSet.getInt("IdProceso");
				//int idTP = resultSet.getInt("IdTipoProceso");
				//String nombre = resultSet.getString("Nombre");
				//int idCal = resultSet.getInt("idCalendario");
				val = resultSet.getInt("TotalPersonas");
				//double porc = resultSet.getDouble("PorcentajeAceptado");

				/*pe.setId(id);
				pe.setNombre(nombre);
				pe.setPorcentaje((int) porc);
				pe.setIdTipoProceso(idTP);
				pe.setIdCalendario(idCal);
				pe.setTotalPersonas(totalP);*/

			}
			resultSet.close();
			connect.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return val;

	}
}