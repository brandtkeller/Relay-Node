package relay.models;

public class Relay {
    private int id;
    private String title;
    private String pin;
    private boolean state;
    private String relayType;
    private String schedule;

    public Relay(int id, String title, String pin) {
        this.id =  id;
        this.title = title;
        this.pin = pin;
        this.state = false;
        this.relayType = "static";
        this.schedule = null;
    }

    public Relay(int id, String title, String pin, String relayType, String S)

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

    public String getRelayType() {
        return this.relayType;
    }

    public void setRelayType(String relayType) {
        this.relayType = relayType;
    }

    public String getSchedule() {
        return this.schedule;
    }

    public void setSchedule(String schedule) {
        this.schedule = schedule;
    }
}