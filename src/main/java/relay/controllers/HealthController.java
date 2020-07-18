package relay.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path ="/relay")
public class HealthController {
    @GetMapping(path="/", produces = "application/json")
    public ResponseEntity <Object> getRelays() {
        // Check for some logic here then return http 200 or 400 accordingly
        return new ResponseEntity<Object>("Service is Responsive and healthy", HttpStatus.OK);
    }
}