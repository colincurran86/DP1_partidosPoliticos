package models;

public class Participante {
	private String aceptado;
	private String nombres;
	private String apellidos;
	private String dni;
	private String idFirma;
	private String idHuella;
	private double porcentajeFirma;
	private double porcentajeHuella;
	private String observacion;
	public String getAceptado() {
		return aceptado;
	}
	public void setAceptado(String aceptado) {
		this.aceptado = aceptado;
	}
	public String getNombres() {
		return nombres;
	}
	public void setNombres(String nombres) {
		this.nombres = nombres;
	}
	public String getApellidos() {
		return apellidos;
	}
	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}
	public String getDni() {
		return dni;
	}
	public void setDni(String dni) {
		this.dni = dni;
	}
	public String getIdFirma() {
		return idFirma;
	}
	public void setIdFirma(String idFirma) {
		this.idFirma = idFirma;
	}
	public String getIdHuella() {
		return idHuella;
	}
	public void setIdHuella(String i) {
		this.idHuella = i;
	}
	public double getPorcentajeFirma() {
		return porcentajeFirma;
	}
	public void setPorcentajeFirma(double porcentajeFirma) {
		this.porcentajeFirma = porcentajeFirma;
	}
	public double getPorcentajeHuella() {
		return porcentajeHuella;
	}
	public void setPorcentajeHuella(double porcentajeHuella) {
		this.porcentajeHuella = porcentajeHuella;
	}
	public String getObservacion() {
		return observacion;
	}
	public void setObservacion(String observacion) {
		this.observacion = observacion;
	}
	
	
	
}
