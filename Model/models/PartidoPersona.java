package models;

public class PartidoPersona
{
	private PersonaReniec persona = new PersonaReniec();
	private PartidoPolitico partido = new PartidoPolitico();
	private String observacion = new String();
	private int condicionRepetido ;
	
	public PersonaReniec getPersonaReniec() {
		return persona;
	}

	public PartidoPolitico getPartidoPolitico() {
		return partido;
	}
	
	public void setObservacion (String observacion){
		this.observacion = observacion;
	}
	
	public String getObservacion (){
		return observacion;
	}
	
	public int getCondicion(){
		return condicionRepetido;
	}
	
	public void setCondicion(){
		this.condicionRepetido = condicionRepetido;
	}
	
}
