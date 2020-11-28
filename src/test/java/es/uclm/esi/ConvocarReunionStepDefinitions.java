package es.uclm.esi;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

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
import es.uclm.esi.security.jwt.JwtUtils;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class ConvocarReunionStepDefinitions extends SpringIntegrationTest{

	ResponseEntity<String> response;
	String url = DEFAULT_URL + "reunion/convocar/";
	Map<String, Object> params = new HashMap<String, Object>();
	Integer codigo;
	HttpHeaders headers = new HttpHeaders();
	Reunion reu = new Reunion();
	ArrayList<Asistente> arrayAsistentes;
	@Autowired
	AuthenticationManager authenticationManager;
	@Autowired
	JwtUtils jwtUtils;

@When("titulo es {string}")
public void titulo_es(String titulo) {
    reu.setTitulo(titulo);

}


@When("dia es {int}")
public void dia_es(Integer dia) {
	reu.setDia(dia);

}

@When("mes es {int}")
public void mes_es(Integer mes) {
	reu.setMes(mes);

}

@When("ano es {int}")
public void ano_es(Integer ano) {
	reu.setAno(ano);

}

@When("hora es {string}")
public void hora_es(String hora) {
	reu.setHora(hora);

}

@When("descripcion es {string}")
public void descripcion_es(String descripcion) {
	reu.setDescripcion(descripcion);

}

@When("asistentes son {string}")
public void asistentes_son(String cadena) {
	if (!cadena.equals("")) {
		String[] arrayNombresAsistentes = cadena.split(",");

		arrayAsistentes = new ArrayList<Asistente>();
		for (int i = 0; i < arrayAsistentes.size(); i++) {
			String[] asistente = arrayNombresAsistentes[i].split(":");
			arrayAsistentes.set(i, new Asistente(asistente[0],asistente[1]));
		}
	}
	
	reu.setAsistentes(arrayAsistentes);

}

@Then("convoco la reunion")
public void convoco_la_reunion() {
	
	Authentication authentication = authenticationManager.authenticate(
			new UsernamePasswordAuthenticationToken("admin","Admin123"));

	SecurityContextHolder.getContext().setAuthentication(authentication);
	String jwt = jwtUtils.generateJwtToken(authentication);
	
	headers.set("Authorization", "Bearer " + jwt);
	params.put("titulo", reu.getTitulo());
	params.put("estado", reu.getEstado());
	params.put("fecha", reu.getAno()+"-"+reu.getMes()+"-"+reu.getDia());
	params.put("hora", reu.getHora());
	params.put("descripcion", reu.getDescripcion());
	params.put("organizador", reu.getOrganizador());
	params.put("asistentes",reu.getAsistentes());
	HttpEntity<Map<String, Object>> request = new HttpEntity<Map<String, Object>>(params, headers);
	
	try {
		response = restTemplate.postForEntity(url, request, String.class);
		codigo = response.getStatusCodeValue();
	} catch (HttpClientErrorException e) {
		codigo = e.getRawStatusCode();
	}

}

@Then("la respuesta a convocar sera {int}")
public void la_respuesta_a_convocar_sera(Integer res) {
	assertEquals(res, codigo);

}

}
