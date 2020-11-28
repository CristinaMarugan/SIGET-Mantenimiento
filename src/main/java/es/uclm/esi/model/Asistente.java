package es.uclm.esi.model;

public class Asistente {

	private String usuario;
	private String estado;

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public Asistente(String usuario, String estado) {
		super();
		this.usuario = usuario;
		this.estado = estado;
	}

	@Override
	public String toString() {
		return "{\"usuario\": \"" + usuario + "\", \"estado\": \"" + estado + "\"}";
	}

}
