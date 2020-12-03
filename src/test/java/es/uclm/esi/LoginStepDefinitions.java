/*package es.uclm.esi;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class LoginStepDefinitions extends SpringIntegrationTest {
	 
    ResponseEntity<String> response ;
    String url = DEFAULT_URL + "api/auth/signin/";
    Map<String, String> params = new HashMap<String, String>();
    Integer codigo;
    
    @When("Hago login con {string} y {string}")
    public void hago_login_con_y(String string, String string2) {
    	params.put("username", string);
    	params.put("password", string2);
    	try {
        	response = restTemplate.postForEntity(url, params, String.class);
        	codigo = response.getStatusCode().value();
        	}catch(HttpClientErrorException e) {
        		codigo = e.getRawStatusCode();
        	}
    	//System.out.println(string+" y "+string2+" = "+codigo.toString());
    }
    @Then("el codigo de respuesta debe ser {int}")
    public void el_codigo_de_respuesta_debe_ser(Integer int1) {
    	assertEquals(int1, codigo);
    }
    @Then("el codigo de respuesta no debe ser {int}")
    public void el_codigo_de_respuesta_no_debe_ser(Integer int1) {
    	assertNotEquals(int1, codigo);
    }
    
}*/