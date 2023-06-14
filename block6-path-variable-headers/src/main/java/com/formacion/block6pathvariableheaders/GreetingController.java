package com.formacion.block6pathvariableheaders;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class GreetingController {

    private static final String template = "Hello, %s!";
    private final AtomicLong counter = new AtomicLong();

    //Este método recibe un parámetro opcional 'name' en la URL y devuelve objeto tipo 'Greeting' con mensaje
    @GetMapping("/greeting")
    public Greeting greeting(@RequestParam(value = "name", defaultValue = "World") String name) {
        return new Greeting(counter.incrementAndGet(), String.format(template, name));
    }

    //Este método recibe un objeto JSON en el cuerpo de la solicitud y luego lo devuelve
    @PostMapping("/postGreeting")
    public ResponseEntity<?> postGreeting(@RequestBody String requestBody) {
        return ResponseEntity.ok(requestBody);
    }

    //Este método recibe parámetro 'id' en la ruta y lo devuelve en la respuesta
    @GetMapping("/user/{id}")
    public ResponseEntity<Long> getId(@PathVariable("id") long id) {
        return(ResponseEntity.ok(id));
    }

    //Este método recibe por parámetros de solicitud 'var1' y 'var2' en la URL y los devuelve con un HaspMap
    @PutMapping("/post")
    public ResponseEntity<?> updateData(@RequestParam String var1, @RequestParam String var2) {
        HashMap<String, String> resultMap = new HashMap<>();
        resultMap.put("var1", var1);
        resultMap.put("var2", var2);
        return ResponseEntity.ok(resultMap);
    }
}