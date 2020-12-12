package es.uclm.esi.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.ArrayList;

/**
 * 
 * @author Víctor
 * Clase de dominio que usaremos para instanciar reuniones
 */
@Document(collection= "reuniones")
public class Reunion {
	
	@Id
	private int id;
	private String organizador;
	private String titulo;
	private String estado;
	private int dia;
	private int mes;
	private int ano;
	private String hora;
	private String horaFin;
	private String descripcion;
	private ArrayList<Asistente> asistentes;
	
	

	public Reunion(int id, String organizador, String titulo, String estado, int dia, int mes, int ano, String hora, String horaFin) {
		super();
		this.id = id;
		this.organizador = organizador;
		this.titulo = titulo;
		this.estado = estado;
		this.dia = dia;
		this.mes = mes;
		this.ano = ano;
		this.hora = hora;
		this.horaFin=horaFin;
		this.asistentes = new ArrayList<>();
	}

	public Reunion() {
		
	}

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

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public int getDia() {
		return dia;
	}

	public void setDia(int dia) {
		this.dia = dia;
	}

	public int getMes() {
		return mes;
	}

	public void setMes(int mes) {
		this.mes = mes;
	}

	public int getAno() {
		return ano;
	}

	public void setAno(int ano) {
		this.ano = ano;
	}

	public String getHora() {
		return hora;
	}

	public void setHora(String hora) {
		this.hora = hora;
	}
	public String getHoraFin() {
     return horaFin;
 }

 public void setHoraFin(String horaFin) {
     this.horaFin = horaFin;
 }

	public ArrayList<Asistente> getAsistentes() {
		return asistentes;
	}
	
	public Asistente getAsistente(int index) {
		return asistentes.get(index);
	}

	public void setAsistentes(ArrayList<Asistente> asistentes) {
		this.asistentes = asistentes;
	}

	public String getOrganizador() {
		return organizador;
	}

	public void setOrganizador(String organizador) {
		this.organizador = organizador;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	

}
