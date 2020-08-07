package relay.models;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

import com.pi4j.io.gpio.Pin;
import com.pi4j.io.gpio.RaspiPin;

public class Relay {
    private int id;
    private String title;
    private String pin;
    private boolean state;
    private String type;
    private String schedule;
    private Pin rpiPin;

    public Relay(int id, String title, String pin) {
        this.id =  id;
        this.title = title;
        this.pin = pin;
        this.state = false;
        this.type = "static";
        this.schedule = null;
        this.rpiPin = RaspiPin.getPinByName("GPIO " + pin);
    }

    public Relay(int id, String title, String pin, String type, String schedule) {
        this.id =  id;
        this.title = title;
        this.pin = pin;
        this.state = false;
        this.type = type;
        this.schedule = schedule;
        this.rpiPin = RaspiPin.getPinByName("GPIO " + pin);
    }

    @Override
    public String toString() { 
        return String.format("{'type':'relay','id':'" + id + "','attributes':{'title':'" + title + "','state':'" + state +"'}},"); 
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

    public void setState( boolean state ) {
        this.state = state;
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
    }

    public boolean checkSchedule() {
        ZoneId currentZone = ZoneId.of("America/Los_Angeles");
        DateTimeFormatter format = DateTimeFormatter.ofPattern("E");
        ZonedDateTime currentTime = ZonedDateTime.now(currentZone);
        // Here will be the function that is called every loop
        // It should return whether change in state is necessary
        return false;
    }

    // Write the relay pin execution function here (non-toggle)
    // This will be executed from the setState() function and can be private
    private void setGpioState(boolean state) {

    }
}