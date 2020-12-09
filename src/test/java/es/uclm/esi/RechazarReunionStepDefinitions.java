package es.uclm.esi;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.junit.AfterClass;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
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

public class RechazarReunionStepDefinitions extends SpringIntegrationTest {

 ResponseEntity<String> response;
 String url = DEFAULT_URL + "reunion/rechazar/";
 Map<String, Integer> params = new HashMap<String, Integer>();
 Integer codigo;
 HttpHeaders headers = new HttpHeaders();
 ControllerCancelarAceptarReunion controller = new ControllerCancelarAceptarReunion();
 @Autowired
 JwtUtils jwtUtils;
 @Autowired
 AuthenticationManager authenticationManager;
 @Autowired
 RepositoryReuniones rReuniones;

 @When("rechazo la reunion")
 public void rechazo_la_reunion() {
  Authentication authentication = authenticationManager
    .authenticate(new UsernamePasswordAuthenticationToken("Elisa", "Seguridad2020"));
  SecurityContextHolder.getContext().setAuthentication(authentication);
  String token = jwtUtils.generateJwtToken(authentication);
  headers.set("Authorization", "Bearer " + token);

  // Creo una reunion para rechazarla
  Reunion reunion = new Reunion();
  int id = 11119;
  ArrayList<Asistente> asistentes = new ArrayList<>();
  reunion.setId(id);
  reunion.setOrganizador("Elisa");
  reunion.setTitulo("TestRechazarReunion");
  reunion.setEstado("pendiente");
  reunion.setDia(25);
  reunion.setMes(12);
  reunion.setAno(2020);
  reunion.setHora("11:00");
  reunion.setDescripcion("TestRechazarReunion");
  asistentes.add(new Asistente("manu", "pendiente"));
  asistentes.add(new Asistente("jaime", "pendiente"));
  asistentes.add(new Asistente("elisa", "pendiente"));
  reunion.setAsistentes(asistentes);

  rReuniones.save(reunion);
  params.put("id", id);


  HttpEntity<Map<String, Integer>> request = new HttpEntity<>(params, headers);
  try {
      response = restTemplate.postForEntity(url, request, String.class);
      codigo = response.getStatusCode().value();
  } catch (HttpClientErrorException e) {
      codigo = e.getRawStatusCode();
  }
    rReuniones.delete(reunion); 
 }

 @Then("la respuesta a rechazar debe ser {int}")
 public void la_respuesta_a_rechazar_debe_ser(Integer res) {
  assertEquals(res, codigo);
 }

}
