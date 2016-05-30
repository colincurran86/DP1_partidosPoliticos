package models;	

public class TipoProceso {
	
	private int id;
	private String descripcion;
	private int porcentaje ;

	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public int getPorcentaje() {
		return porcentaje;	
	}
	public void setPorcentaje(int Porcentaje) {
		this.porcentaje = Porcentaje;
	}
	
}
