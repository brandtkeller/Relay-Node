package relay.models;

import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.GpioPinDigitalOutput;
import com.pi4j.io.gpio.Pin;
import com.pi4j.io.gpio.PinState;
import com.pi4j.io.gpio.RaspiPin;
import com.pi4j.wiringpi.GpioUtil;

public class Relay {
    private int id;
    private String title;
    private String pin;
    private boolean state;
    private String type;

    // Scheduling Variables
    private String schedule;
    private Hashtable<Integer, String> onTimes;
    private Hashtable<Integer, String> offTimes;
    private ZonedDateTime nextTriggerTime;

    // GPIO variables
    private Pin rpiPin;
    private GpioPinDigitalOutput provRpiPin;

    public Relay() {
        // Initialize private variables for on-times and off-times
        this.onTimes = new Hashtable<Integer, String>();
        this.offTimes = new Hashtable<Integer, String>();
        this.nextTriggerTime = null;

    }

    public Relay(int id, String title, String pin) {
        this.id =  id;
        this.title = title;
        this.pin = pin;
        this.state = false;
        this.type = "static";
        this.schedule = null;
        if (!Boolean.parseBoolean(System.getProperty("testing"))) {
            this.rpiPin = RaspiPin.getPinByName("GPIO " + pin);
        }
        
    }

    public Relay(int id, String title, String pin, String type) {
        this.id =  id;
        this.title = title;
        this.pin = pin;
        this.state = false;
        this.type = type;
        this.schedule = null;
        if (!Boolean.parseBoolean(System.getProperty("testing"))) {
            this.rpiPin = RaspiPin.getPinByName("GPIO " + pin);
        }
        
    }

    public Relay(int id, String title, String pin, String type, String schedule) {
        this.id =  id;
        this.title = title;
        this.pin = pin;
        this.state = false;
        this.type = type;
        // Initialize private variables for on-times and off-times
        this.onTimes = new Hashtable<Integer, String>();
        this.offTimes = new Hashtable<Integer, String>();
        this.nextTriggerTime = null;

        setSchedule(schedule);
        
        if (!Boolean.parseBoolean(System.getProperty("testing"))) {
            this.rpiPin = RaspiPin.getPinByName("GPIO " + pin);
        }
    }

    @Override
    public String toString() { 

        return String.format("{'type':'relay','id':'" + id + "','attributes':{'title':'" + title + "','state':'" + state + "','schedule':'" + schedule + "'}},"); 
    }

    public int getId() {
        return this.id;
    }

    public void setId( int id ) {
        this.id = id;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle( String title ) {
        this.title = title;
    }

    public String getPin() {
        return this.pin;
    }

    public void setPin( String pin ) {
        this.pin = pin;
    }

    public boolean getState() {
        return this.state;
    }

    public void setState( boolean newState ) {
        this.state = newState;
    }

    public void executeState(boolean newState) {
        if (!java.util.Objects.equals(this.state, newState)) {
            this.state = newState;
            setGpioState(newState);
        } else {
            System.out.println("Attempted to change state to it's already existing state. IE " + this.state + " == " + newState);
        }
    }

    public String getType() {
        return this.type;
    }

    public void setRelayType(String type) {
        this.type = type;
    }

    public String getSchedule() {
        return this.schedule;
    }

    public void setSchedule(String schedule) {
        this.schedule = schedule;

        Hashtable<Integer, String> newOnTimes = new Hashtable<Integer, String>();
        Hashtable<Integer, String> newOffTimes = new Hashtable<Integer, String>();
        this.nextTriggerTime = null;

        // populate on/off times
        String[] scheduleSplit = schedule.split(",");
        for (int i = 0; i < scheduleSplit.length; i++) {
            String[] timeSplit = scheduleSplit[i].split("/");
            newOnTimes.put(i+1, timeSplit[0]);
            newOffTimes.put(i+1, timeSplit[1]);
        }

        this.onTimes = newOnTimes;
        this.offTimes = newOffTimes;
    }

    public void checkSchedule() {
        if (this.type.equals("timer")) {
            ZoneId currentZone = ZoneId.of("America/Los_Angeles");
            ZonedDateTime currentTime = ZonedDateTime.now(currentZone);
            System.out.println(currentTime + " = current time");

            // If we don't have a cached trigger time, we need to iterate through the on/off lists
            // We expect the times to be in sequential order (What if we wanted to run something over-night?)
            if (this.nextTriggerTime == null) {
                System.out.println("nextTriggerTime is null, executing setScheduleLogic");
                setScheduleLogic(currentTime);
            } else {
                // If we have a cached trigger-time, we can utilize it
                if ( currentTime.isAfter(this.nextTriggerTime) ) {
                    setScheduleLogic(currentTime);
                } else {
                    System.out.println("No change to state neccessary. Next state change at " + this.nextTriggerTime);
                }
            }

        } else {
            System.out.println("No schedule check needed. " + this.title +" is not a timer relay.");
        }
    }

    // Write the relay pin execution function here (non-toggle)
    // This will be executed from the setState() function and should be private
    private void setGpioState(boolean newState) {
        if (!Boolean.parseBoolean(System.getProperty("testing"))) {
            GpioUtil.enableNonPrivilegedAccess();
            final GpioController gpioRelay = GpioFactory.getInstance();

            // This logic will only occur if there is no provisioned pin already. Should be fine before checking state
            if (this.provRpiPin == null) {
                this.provRpiPin = gpioRelay.provisionDigitalOutputPin(rpiPin,"RelayLED",PinState.HIGH);
            }
            
            if (newState) {
                this.provRpiPin.low(); // ON
            } else {
                this.provRpiPin.high(); // OFF
            }

        } else {
            System.out.println("Testing mode - Changing gpio state to " + state);
        }
    }

    private void setScheduleLogic(ZonedDateTime currentTime) {
        System.out.println(this.onTimes.size() + " is the size of the onTimes list");
        for (int i = 0; i < this.onTimes.size(); i++) {
            String[] timeSplit = this.onTimes.get(i+1).split(":");
            ZonedDateTime onCompareTime = currentTime.with(LocalTime.of ( Integer.parseInt(timeSplit[0]) , Integer.parseInt(timeSplit[1]) ));
            if (currentTime.isAfter(onCompareTime)) {
                String[] offTimeSplit = this.offTimes.get(i+1).split(":");
                ZonedDateTime offCompareTime = currentTime.with(LocalTime.of ( Integer.parseInt(offTimeSplit[0]) , Integer.parseInt(offTimeSplit[1]) ));

                if (currentTime.isBefore(offCompareTime)) {
                    executeState(true);
                    this.nextTriggerTime = offCompareTime;
                    System.out.println("Setting nextTriggerTime for " + this.nextTriggerTime);
                    return;
                } else {
                    continue;
                }

            } else {
                executeState(false);
                this.nextTriggerTime = onCompareTime;
                System.out.println("Setting nextTriggerTime for " + this.nextTriggerTime);
            }
        }


    }
}