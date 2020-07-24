package relay;

import java.util.Hashtable;
import java.util.Set;

import org.springframework.stereotype.Repository;
import org.apache.commons.lang3.StringUtils;

import relay.models.GpioRunner;
import relay.models.Relay;

@Repository
public class RelayDAO {
    private static GpioRunner gInstance = null;
    private static Hashtable<Integer, Relay> relayInstances = null;
    
    public static boolean init() {
        relayInstances = new Hashtable<Integer, Relay>();
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
                relayInstances.put(i + 1, new Relay(i + 1, names[i], relayPins[i]));
            }

        } catch (Exception e) {
            System.out.println(e);
            System.out.println("Unable to parse the necessary parameters (relays & pins)");
            System.exit(1);
        }
        return true;
    }

    public static String getAllrelays() {
        String response = "{'data':[";
        for (int i = 0; i < relayInstances.size(); i++) {
            Relay temp = relayInstances.get(i + 1);
            response += temp.toString();
        }
        if (relayInstances.size() > 0) {
            response = StringUtils.chop(response);
        }
        response += "]}";
        return response;
    }

    public static String getRelay( int id) {
        String response = "{'data':[";
        Relay temp = relayInstances.get(id);
        response += temp.toString();
        response = StringUtils.chop(response);
        response += "]}";
        return response;
    }

    public static boolean checkRelayId( int id) {
        Relay temp = relayInstances.get(id);
        if (temp != null) {
            return true;
        }
        return false;
    }

    public static String modifyRelay(Relay reqRelay) {
        // Identify the relay to be modified
        int reqId = reqRelay.getId();
        boolean reqState = reqRelay.getState();
        // State (boolean) is the only attribute that should be modified currently
        Relay exRelay = relayInstances.get(reqId);
        if ( exRelay.getState() != reqState) {
            if (reqState == true) {
                gInstance.activateRelay(exRelay.getTitle());
                exRelay.setState(true);
            } else {
                gInstance.deactivateRelay(exRelay.getTitle());
                exRelay.setState(false);
            }
        }
        return getRelay(reqId);
    }

    public static boolean healthCheck() {
        if (relayInstances != null && relayInstances.size() > 0) {
            return true;
        } else {
            return false;
        }
    }
}