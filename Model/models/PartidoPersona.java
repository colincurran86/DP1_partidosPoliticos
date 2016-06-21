package models;

public class PartidoPersona {
	private PersonaReniec persona = new PersonaReniec();
	private PartidoPolitico partido = new PartidoPolitico();
	private String observacion = new String();
	private int condicionRepetido = 0;
	private Participante participando = new Participante();
	public PersonaReniec getPersona() {
		return persona;
	}
	public void setPersona(PersonaReniec persona) {
		this.persona = persona;
	}
	public PartidoPolitico getPartido() {
		return partido;
	}
	public void setPartido(PartidoPolitico partido) {
		this.partido = partido;
	}
	public String getObservacion() {
		return observacion;
	}
	public void setObservacion(String observacion) {
		this.observacion = observacion;
	}
	public int getCondicionRepetido() {
		return condicionRepetido;
	}
	public void setCondicionRepetido(int condicionRepetido) {
		this.condicionRepetido = condicionRepetido;
	}
	public Participante getParticipando() {
		return participando;
	}
	public void setParticipando(Participante participando) {
		this.participando = participando;
	}
	
	
	
	

}
