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

import relay.RelayDAO;
import relay.models.Relay;

@RestController
@RequestMapping(path ="/relays")
public class RelayController {

    @GetMapping(path="", produces = "application/json")
    public ResponseEntity <Object> getRelays() {

        return new ResponseEntity<Object>(RelayDAO.getAllrelays(), HttpStatus.OK);
    }
    
    @GetMapping(path="/{id}", produces = "application/json")
    public ResponseEntity<Object> getEmployee(@PathVariable("id") String id) { 
        if (RelayDAO.checkRelayId(Integer.parseInt(id))) {
            return new ResponseEntity<Object>(RelayDAO.getRelay(Integer.parseInt(id)), HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Relay was not found", HttpStatus.NOT_FOUND);
        }
    }  

    @PutMapping(path="/{id}", produces = "application/json")
    public ResponseEntity<Object> putEmployee(@PathVariable("id") String id, @RequestBody Relay relay) { 

        return new ResponseEntity<>("Relay was not found", HttpStatus.NOT_FOUND);
    }

}