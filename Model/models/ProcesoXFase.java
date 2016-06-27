package models;

import java.sql.Date;

public class ProcesoXFase {
	private int idPartPol;
	private int idProceso;
	private int idFase;
	private String resultado;
	private String observacion;
	private Date fechaInicioProc;
	private int totalAd;
	private int totalDup;
		
	public int getTotalAd() {
		return totalAd;
	}
	public void setTotalAd(int totalAd) {
		this.totalAd = totalAd;
	}
	public int getTotalDup() {
		return totalDup;
	}
	public void setTotalDup(int totalDup) {
		this.totalDup = totalDup;
	}
	public int getIdPartPol() {
		return idPartPol;
	}
	public void setIdPartPol(int idPartPol) {
		this.idPartPol = idPartPol;
	}
	public int getIdProceso() {
		return idProceso;
	}
	public void setIdProceso(int idProceso) {
		this.idProceso = idProceso;
	}
	public int getIdFase() {
		return idFase;
	}
	public void setIdFase(int idFase) {
		this.idFase = idFase;
	}
	public String getResultado() {
		return resultado;
	}
	public void setResultado(String resultado) {
		this.resultado = resultado;
	}
	public String getObservacion() {
		return observacion;
	}
	public void setObservacion(String observacion) {
		this.observacion = observacion;
	}
	public Date getFechaInicioProc() {
		return fechaInicioProc;
	}
	public void setFechaInicioProc(Date fechaInicioProc) {
		this.fechaInicioProc = fechaInicioProc;
	}
	
	
	
}
