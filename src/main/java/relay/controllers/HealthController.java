package relay.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import relay.RelayDAO;

@RestController
@RequestMapping(path ="/health")
public class HealthController {
    @GetMapping(path="", produces = "application/json")
    public ResponseEntity <Object> getRelays() {
        if (RelayDAO.healthCheck()) {
            return new ResponseEntity<Object>("Service is Responsive and healthy", HttpStatus.OK);
        } else {
            return new ResponseEntity<Object>("Service is unhealthy", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}