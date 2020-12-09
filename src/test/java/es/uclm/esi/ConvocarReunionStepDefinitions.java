package es.uclm.esi;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.client.HttpClientErrorException;

import es.uclm.esi.model.Asistente;
import es.uclm.esi.model.Reunion;
import es.uclm.esi.payload.request.LoginRequest;
import es.uclm.esi.security.jwt.JwtUtils;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class ConvocarReunionStepDefinitions extends SpringIntegrationTest{
	
	@Autowired
	AuthenticationManager authenticationManager;
	
	Integer codigo=200;
	Reunion r = new Reunion();
	
	@Given("Me autentico como usuario existente en el sistema y usuario es {string} y password es {string}")
	public void me_autentico_como_usuario_existente_en_el_sistema_y_usuario_es_y_password_es(String user, String pass) throws Throwable{
		try{
			Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user, pass));
			SecurityContextHolder.getContext().setAuthentication(authentication);
		}catch (Exception e) {
			fail("Se ha lanzado una excepcion inesperada: " + e);
		}
		
	}

	@When("convoco la reunion y titulo es {string}")
	public void convoco_la_reunion_y_titulo_es(String titulo) throws Throwable {
		try{
			r.setTitulo(titulo);
		}catch (Exception e) {
			fail("Se ha lanzado una excepcion inesperada: " + e);
		}
	}
	
	@When("dia es {int}")
	public void dia_es(Integer dia) throws Throwable {
		try{
			r.setDia(dia);
		}catch (Exception e) {
			fail("Se ha lanzado una excepcion inesperada: " + e);
		}
	}
	
	@When("mes es {int}")
	public void mes_es(Integer mes) throws Throwable {
		try{
			r.setMes(mes);
		}catch (Exception e) {
			fail("Se ha lanzado una excepcion inesperada: " + e);
		}
	}
	
	@When("ano es {int}")
	public void ano_es(Integer ano) throws Throwable {
		try{
			r.setAno(ano);
		}catch (Exception e) {
			fail("Se ha lanzado una excepcion inesperada: " + e);
		}
	}
	
	@When("hora es {string}")
	public void hora_es(String hora) throws Throwable {
		try{
			r.setHora(hora);
		}catch (Exception e) {
			fail("Se ha lanzado una excepcion inesperada: " + e);
		}
	}
	
	@When("descripcion es {string}")
	public void descripcion_es(String descripcion) throws Throwable {
		try{
			r.setDescripcion(descripcion);
		}catch (Exception e) {
			fail("Se ha lanzado una excepcion inesperada: " + e);
		}
	}
	
	@When("asistentes son {string}")
	public void asistentes_son(String asistentes) throws Throwable {
		try{
			ArrayList<Asistente> arrayAsistentes = new ArrayList<Asistente>();
			if (!asistentes.equals("")) {
				String[] arrayNombresAsistentes = asistentes.split(",");

				for (int i = 0; i < arrayAsistentes.size(); i++) {
					String[] asistente = arrayNombresAsistentes[i].split(":");
					arrayAsistentes.set(i, new Asistente(asistente[0],asistente[1]));
				}
			}
			r.setAsistentes(arrayAsistentes);
		}catch (Exception e) {
			fail("Se ha lanzado una excepcion inesperada: " + e);
		}
	}
	
	@Then("la respuesta a convocar sera {int}")
	public void la_respuesta_a_convocar_sera(Integer respuesta) throws Throwable {
		try{
			assertEquals(respuesta, codigo);
		}catch (Exception e) {
			fail("Se ha lanzado una excepcion inesperada: " + e);
		}
	}//hola
}