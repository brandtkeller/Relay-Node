package relay.models;

import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.GpioPinDigitalOutput;
import com.pi4j.io.gpio.Pin;
import com.pi4j.io.gpio.PinState;
import com.pi4j.io.gpio.RaspiPin;
import com.pi4j.wiringpi.GpioUtil;

import java.util.Hashtable;

public class GpioRunner {
    private Hashtable<String, GpioPinDigitalOutput> output_dict = new Hashtable<String, GpioPinDigitalOutput>();
    private Hashtable<String, Pin> pin_dict = new Hashtable<String, Pin>();
    private Hashtable<String, Boolean> state_dict = new Hashtable<String, Boolean>();

    public GpioRunner(String[] names, String[] pins) {
        // Get two arrays of Relay Names and Pins and assign to pin/state Hashtables
        for (int i = 0; i < names.length; i++) {
            if (!Boolean.parseBoolean(System.getProperty("testing"))) {
                System.out.println("We are not in the testing mode.");
                pin_dict.put(names[i], RaspiPin.getPinByName("GPIO " + pins[i]));
            }
            state_dict.put(names[i], false);
        }
    }

    public void activateRelay(String relayName) {
        GpioUtil.enableNonPrivilegedAccess();
        final GpioController gpioRelay = GpioFactory.getInstance();
        GpioPinDigitalOutput relay = null;
        // Check if dictionary item for pin is null
        relay = output_dict.get(relayName);
        // If null, assign
        if (relay == null) {
            output_dict.put(relayName, gpioRelay.provisionDigitalOutputPin(pin_dict.get(relayName),"RelayLED",PinState.HIGH)); // OFF
            relay = output_dict.get(relayName);
        }
        relay.low(); // ON
        state_dict.put(relayName, true);
    }

    public void deactivateRelay(String relayName) {
        GpioUtil.enableNonPrivilegedAccess();
        final GpioController gpioRelay = GpioFactory.getInstance();
        GpioPinDigitalOutput relay = null;
        // Check if dictionary item for pin is null
        relay = output_dict.get(relayName);
        // If null, assign
        if (relay == null) {
            output_dict.put(relayName, gpioRelay.provisionDigitalOutputPin(pin_dict.get(relayName),"RelayLED",PinState.HIGH)); // OFF
            relay = output_dict.get(relayName);
        }
        relay.high(); // OFF
        state_dict.put(relayName, false);
    }
}