package es.uclm.esi;

import org.junit.runner.RunWith;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.client.RestTemplate;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(
        classes = SigetApplication.class,
        webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)

public abstract class SpringIntegrationTest {
	
    protected RestTemplate restTemplate = new RestTemplate();
    
//    protected final String DEFAULT_URL = "http://localhost:8080/";
    protected final String DEFAULT_URL = "https://siget-equipo3.herokuapp.com/";


}