package relay;

import org.springframework.stereotype.Repository;

import relay.models.GpioRunner;
import relay.models.Relay;

@Repository
public class RelayDAO {
    private static GpioRunner gInstance = null;
    private static Relay[] relayInstances = null;
    
    public boolean init() {
        String relays = System.getProperty("relays");
        String pins = System.getProperty("pins");
        System.out.println(relays);
        System.out.println(pins);

        try {
            String[] names = relays.split(",");
            String[] relayPins = relays.split(",");
            gInstance = new GpioRunner(names, relayPins);

            // initialize the relay objects
            

        } catch (Exception e) {
            System.out.println("Unable to parse the necessary parameters (relays & pins)");
            System.exit(1);
        }

        
        
        return true;
    }
}