package relay.models;

public class Relay {
    private int id;
    private String title;
    private int pin;
    private boolean state;

    public Relay(int id, String title, int pin) {
        this.id =  id;
        this.title = title;
        this.pin = pin;
        this.state = false;
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

    public int getPin() {
        return this.pin;
    }

    public void setPin( int pin ) {
        this.pin = pin;
    }

    public boolean getState() {
        return this.state;
    }

    public void setState( boolean state ) {
        this.state = state;
    }
}