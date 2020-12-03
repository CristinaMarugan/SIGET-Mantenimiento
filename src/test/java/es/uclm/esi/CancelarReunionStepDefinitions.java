package es.uclm.esi;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.client.HttpClientErrorException;

import es.uclm.esi.controller.ControllerConvocarReunion;
import es.uclm.esi.model.Asistente;
import es.uclm.esi.model.Reunion;
import es.uclm.esi.repository.RepositoryReuniones;
import es.uclm.esi.security.jwt.JwtUtils;
import io.cucumber.java.Before;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class CancelarReunionStepDefinitions extends SpringIntegrationTest {

	ResponseEntity<String> response;
	String url = DEFAULT_URL + "reunion/cancelar/";
	Map<String, Integer> params = new HashMap<String, Integer>();
	Integer codigo;
	HttpHeaders headers = new HttpHeaders();
	Integer idReunion;
	@Autowired
	JwtUtils jwtUtils;
	@Autowired
	AuthenticationManager authenticationManager;
	@Autowired
	RepositoryReuniones rReuniones;

	ControllerConvocarReunion controller;
	Reunion reunion = new Reunion();

	@Before
	public void init() {
		ArrayList<Asistente> asistentesR = new ArrayList<>();
		Asistente org = new Asistente("Elisa", "aceptada");
		Authentication authentication = authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken("Elisa", "Seguridad2020"));
		SecurityContextHolder.getContext().setAuthentication(authentication);
		String token = jwtUtils.generateJwtToken(authentication);
		headers.set("Authorization", "Bearer " + token);
		
		reunion.setId(1111);
		reunion.setOrganizador(authentication.getName());
		reunion.setTitulo("TestCancelar");
		reunion.setDescripcion("TestCancelar");
		asistentesR.add(org);
		reunion.setAsistentes(asistentesR);
		reunion.setDia(23);
		reunion.setMes(12);
		reunion.setAno(2020);
		reunion.setHora("11:00");
		
		rReuniones.save(reunion);
	}
	

	@When("cancelo la reunion")
	public void cancelo_la_reunion() {

		Reunion reun;
		reun = rReuniones.findFirstByOrderByIdDesc();
		idReunion = reun.getId();
		params.put("id", idReunion);
		HttpEntity<Map<String, Integer>> request = new HttpEntity<>(params, headers);
		try {
			response = restTemplate.postForEntity(url, request, String.class);
			codigo = response.getStatusCode().value();
		} catch (HttpClientErrorException e) {
			e.getStackTrace();
			codigo = e.getRawStatusCode();
		}

	}

	@Then("la respuesta debe ser {int}")
	public void la_respuesta_debe_ser(Integer res) {
		assertEquals(res, codigo);
	}

}
