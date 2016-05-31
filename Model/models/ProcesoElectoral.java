package models;

import java.sql.Date;

public class ProcesoElectoral {
	private int id;
	private String nombre;
	private int idTipoProceso;
	private String tipoProceso;
	private int porcentaje;
	private Date fechaIni;
	private Date fechaFin;
	private int idCalendario;
	
	
	public String getNombre() {		
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getTipoProceso() {
		return tipoProceso;
	}
	public void setTipoProceso(String tipoProceso) {
		this.tipoProceso = tipoProceso;
	}
	public int getPorcentaje() {
		return porcentaje;
	}
	public void setPorcentaje(int porcentaje) {
		this.porcentaje = porcentaje;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getIdTipoProceso() {
		return idTipoProceso;
	}
	public void setIdTipoProceso(int idTipoProceso) {
		this.idTipoProceso = idTipoProceso;
	}
	public Date getFechaIni() {
		return fechaIni;
	}
	public void setFechaIni(Date fechaIni) {
		this.fechaIni = fechaIni;
	}
	public Date getFechaFin() {
		return fechaFin;
	}
	public void setFechaFin(Date fechaFin) {
		this.fechaFin = fechaFin;
	}
	public int getIdCalendario() {
		return idCalendario;
	}
	public void setIdCalendario(int idCalendario) {
		this.idCalendario = idCalendario;
	}

}
