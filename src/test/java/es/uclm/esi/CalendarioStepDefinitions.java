package es.uclm.esi;

import static org.junit.Assert.assertEquals;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.client.HttpClientErrorException;

import es.uclm.esi.payload.request.CalendarioDiaRequest;
import es.uclm.esi.payload.request.CalendarioMesRequest;
import es.uclm.esi.security.jwt.JwtUtils;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class CalendarioStepDefinitions extends SpringIntegrationTest {

	ResponseEntity<String> response;

	Map<String, String> params = new HashMap<String, String>();
	Integer codigo;
	HttpHeaders headers = new HttpHeaders();
	@Autowired
	JwtUtils jwtUtils;
	@Autowired
	AuthenticationManager authenticationManager;
	@When("consulto con el usuario {string}")
	public void consulto_con_el_usuario(String string) {
		Authentication authentication;
		String token;
		if(string.equals("manu")) {
			 authentication = authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken("manu","manu"));
			SecurityContextHolder.getContext().setAuthentication(authentication);
			 token = jwtUtils.generateJwtToken(authentication);		
		}else if(string.equals("Elisa")) {
			authentication = authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken("Elisa","Seguridad2020"));
			SecurityContextHolder.getContext().setAuthentication(authentication);
			 token = jwtUtils.generateJwtToken(authentication);	
		}else {
			token = string;
		}
		
		headers.set("Authorization", "Bearer " + token);
	}

	@When("el calendario mes con mes {int} y anio {int}")
	public void el_calendario_mes_con_mes_y_anio(Integer int1, Integer int2) {
		CalendarioMesRequest fecha = new CalendarioMesRequest(int1, int2);
		HttpEntity<CalendarioMesRequest> request = new HttpEntity<>(fecha, headers);
		String url = DEFAULT_URL + "getCalendarioPersonalMes/";
		try {
			response = restTemplate.postForEntity(url, request, String.class);
			codigo = response.getStatusCode().value();
		} catch (HttpClientErrorException e) {
			codigo = e.getRawStatusCode();
		}

	}

	@When("el calendario dia con dia {int} y mes {int} y anio {int}")
	public void el_calendario_dia_con_dia_y_mes_y_anio(Integer int1, Integer int2, Integer int3) {
		CalendarioDiaRequest fecha = new CalendarioDiaRequest(int1, int2, int3);
		HttpEntity<CalendarioDiaRequest> request = new HttpEntity<>(fecha, headers);
		String url = DEFAULT_URL + "getDetallesReunion/";
		try {
			response = restTemplate.postForEntity(url, request, String.class);
			codigo = response.getStatusCode().value();
		} catch (HttpClientErrorException e) {
			codigo = e.getRawStatusCode();
		}

	}

	@Then("obtengo el codigo {int}")
	public void obtengo_el_codigo(Integer int1) {
		assertEquals(int1, codigo);
	}

	@Then("obtengo los dias {string}")
	public void obtengo_los_dias(String string) {
		if (codigo == 200) {
			String[] arrayDias = string.split(",");

			try {
				JSONObject jso = new JSONObject(response.getBody());
				JSONArray jsa = jso.getJSONArray("reuniones");

				for (int i = 0; i < jsa.length(); i++) {

					assertEquals(arrayDias[i], jsa.get(i).toString());
				}
			} catch (JSONException e) {

			}

		}
	}

	@Then("obtengo las reuniones {string}")
	public void obtengo_las_reuniones(String string) {
		if (codigo == 200) {
			String[] arrayReuniones = string.split(",");

			try {
				JSONObject jso = new JSONObject(response.getBody());
				JSONArray jsa = jso.getJSONArray("reuniones");

				for (int i = 0; i < jsa.length(); i++) {

					assertEquals(arrayReuniones[i], new JSONObject(jsa.get(i).toString()).get("titulo"));
				}
			} catch (JSONException e) {

			}
		}
	}

}