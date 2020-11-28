package es.uclm.esi.controller;

import org.springframework.web.bind.annotation.*;

import java.util.Map;

import org.json.JSONObject;

@RestController
public class PruebaController {

	@PostMapping("/pruebaConexion")
    public String pruebaConexion(@RequestBody Map<String, Object> prueba){
		JSONObject jso=new JSONObject(prueba);
		System.out.println("Pues mira me ha llegao esto: "+ jso.getInt("id"));
		JSONObject jsoret = new JSONObject();
		jsoret.put("hey", "hola");
        return jsoret.toString();
    }
}
