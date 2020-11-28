package es.uclm.esi.payload.request;



public class CalendarioDiaRequest {
	private int diaR;
	private int mesR;
	private int anoR;
	
	public int getDia() {
		return diaR;
	}
	public void setDia(int dia) {
		this.diaR = dia;
	}
	public int getMes() {
		return mesR;
	}
	public void setMes(int mes) {
		this.mesR = mes;
	}
	public int getAno() {
		return anoR;
	}
	public void setAno(int ano) {
		this.anoR = ano;
	}
	public CalendarioDiaRequest(int dia, int mes, int ano) {
		super();
		this.diaR = dia;
		this.mesR = mes;
		this.anoR = ano;
	}
}
