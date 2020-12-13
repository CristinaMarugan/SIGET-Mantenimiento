package es.uclm.esi;

import static org.junit.Assert.assertEquals;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.client.HttpClientErrorException;

import es.uclm.esi.security.jwt.JwtUtils;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class RolesStepDefinitions extends SpringIntegrationTest {

	ResponseEntity<String> response;
	String url;
	Map<String, String> params = new HashMap<String, String>();
	Integer codigo;
	HttpHeaders headers = new HttpHeaders();
	@Autowired
	JwtUtils jwtUtils;
	@Autowired
	AuthenticationManager authenticationManager;

	@When("accedo con el token de usuario {string}")
	public void accedo_con_el_token_de_usuario(String string) {
		Authentication authentication;
		String token;
		if (string.equals("admin")) {
			authentication = authenticationManager
			        .authenticate(new UsernamePasswordAuthenticationToken("admin", "admin"));
			SecurityContextHolder.getContext().setAuthentication(authentication);
			token = jwtUtils.generateJwtToken(authentication);
		} else if (string.equals("user")) {
			authentication = authenticationManager
			        .authenticate(new UsernamePasswordAuthenticationToken("manu", "manu"));
			SecurityContextHolder.getContext().setAuthentication(authentication);
			token = jwtUtils.generateJwtToken(authentication);
		} else {
			token = string;
		}

		headers.set("Authorization", "Bearer " + token);
	}

	@When("a recursos del rol {string}")
	public void a_recursos_del_rol(String string) {

		if (string.toUpperCase().equals("ADMIN")) {
			url = DEFAULT_URL + "/admin";
		} else if (string.toUpperCase().equals("USER")) {
			url = DEFAULT_URL + "/user";
		} else {
			url = DEFAULT_URL + "/all";
		}

		try {
			HttpEntity request = new HttpEntity(headers);
			response = restTemplate.exchange(url, HttpMethod.GET, request, String.class);
			codigo = response.getStatusCode().value();
		} catch (HttpClientErrorException e) {
			codigo = e.getRawStatusCode();
		}
	}

	@Then("muestro el codigo {int}")
	public void muestro_el_codigo(Integer int1) {
		assertEquals(int1, codigo);
	}

}
