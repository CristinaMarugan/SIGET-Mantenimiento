package es.uclm.esi;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.client.HttpClientErrorException;

import es.uclm.esi.controller.ControllerCancelarAceptarReunion;
import es.uclm.esi.model.Asistente;
import es.uclm.esi.model.Reunion;
import es.uclm.esi.repository.RepositoryReuniones;
import es.uclm.esi.security.jwt.JwtUtils;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class CancelarReunionStepDefinitions extends SpringIntegrationTest {

	String url = DEFAULT_URL + "reunion/cancelar/";
	Map<String, Integer> req = new HashMap<String, Integer>();
	ControllerCancelarAceptarReunion controller = new ControllerCancelarAceptarReunion();
	Integer codigo;
	HttpHeaders headers = new HttpHeaders();

	@Value("${siget.app.jwtSecret}")
	private String jwtSecret;

	@Autowired
	private JwtUtils jwtUtils;
	@Autowired
	private AuthenticationManager authenticationManager;
	@Autowired
	private RepositoryReuniones rReuniones;

	@When("cancelo la reunion")
	public void cancelo_la_reunion() {

		Authentication authentication = authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken("Elisa", "Seguridad2020"));
		SecurityContextHolder.getContext().setAuthentication(authentication);
		String token = jwtUtils.generateJwtToken(authentication);
		headers.set("Authorization", "Bearer " + token);

		// Creo siempre una reunion para eliminar
		Reunion reunion = new Reunion();
		int id = 11111;
		ArrayList<Asistente> asistentes = new ArrayList<>();
		reunion.setId(id);
		reunion.setOrganizador("Elisa");
		reunion.setTitulo("TestCancelarReunion");
		reunion.setEstado("aceptada");
		reunion.setDia(26);
		reunion.setMes(12);
		reunion.setAno(2020);
		reunion.setHora("11:00");
		reunion.setDescripcion("TestCancelarReunion");
		asistentes.add(new Asistente("manu", "pendiente"));
		asistentes.add(new Asistente("jaime", "pendiente"));
		reunion.setAsistentes(asistentes);

		rReuniones.save(reunion);

		// Cancelo la reunion creada

		req.put("id", id);
		try {
			ResponseEntity<HttpStatus> respuesta = controller.cancelarReunion(req, token);
			Integer codigo = respuesta.getStatusCode().value();
		} catch (HttpClientErrorException e) {
			codigo = e.getRawStatusCode();

		}

	}

	@Then("la respuesta debe ser {int}")
	public void la_respuesta_debe_ser(Integer res) {
		assertEquals(res, codigo);
	}

}