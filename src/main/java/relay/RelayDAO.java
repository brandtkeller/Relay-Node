package relay;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;
import org.apache.commons.lang3.StringUtils;

import relay.models.GpioRunner;
import relay.models.Relay;

@Repository
public class RelayDAO {
    private static GpioRunner gInstance = null;
    private static List<Relay> relayInstances = null;
    
    public static boolean init() {
        relayInstances = new ArrayList<>();
        String relays = System.getProperty("relays");
        String pins = System.getProperty("pins");
        System.out.println(relays);
        System.out.println(pins);

        try {
            String[] names = relays.split(",");
            String[] relayPins = pins.split(",");
            gInstance = new GpioRunner(names, relayPins);

            // initialize the relay objects
            for (int i = 0; i < names.length; i++) {
                relayInstances.add(new Relay(i, names[i], relayPins[i]));
            }

        } catch (Exception e) {
            System.out.println(e);
            System.out.println("Unable to parse the necessary parameters (relays & pins)");
            System.exit(1);
        }
        return true;
    }

    public static String getAllrelays() {
        System.out.println(relayInstances.size() + " is the current size of the relay list");
        String response = "{'data':[";
        for (Relay temp : relayInstances) {
            response += temp.toString();
        }
        response = StringUtils.chop(response);
        // End of payload
        response += "]}";
        return response;
    }
}