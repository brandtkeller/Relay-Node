package relay.controllers;

import java.util.ArrayList;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path ="/relay")
public class RelayController {

    @GetMapping(path="/", produces = "application/json")
    public ResponseEntity <Object> getRelays() {

        return new ResponseEntity<Object>("insert text", HttpStatus.OK);
    }
    
    @GetMapping(path="/{id}", produces = "application/json")
    public ResponseEntity<Object> getEmployee(@PathVariable("id") String id) { 
        
        return new ResponseEntity<>("Employee was not found", HttpStatus.NOT_FOUND);
    }  

    @PutMapping(path="/{id}", produces = "application/json")
    public ResponseEntity<Object> putEmployee(@PathVariable("id") String id, @RequestBody Employee employee) { 

        return new ResponseEntity<>("Employee was not found", HttpStatus.NOT_FOUND);
    }

}