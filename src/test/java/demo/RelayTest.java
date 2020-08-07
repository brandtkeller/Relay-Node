package demo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import relay.models.Relay;

class RelayTest {

    @Test
    void relayObjectTest() {
        Relay relay = new Relay(1, "test", "22", "static");
        
        assertEquals(1, relay.getId(), "The output should be the supplied id at object creation");
        assertTrue("test".equals(relay.getTitle()),"The output should be the supplied title at object creation");
        assertEquals("22", relay.getPin(), "The output should be the supplied Pin at object creation");
    }

    @Test
    void relayTitleTest() {
        Relay relay = new Relay(1, "test", "22", "static");
        assertEquals("test", relay.getTitle());
        relay.setTitle("application");
        assertEquals("application", relay.getTitle());
    }

    @Test
    void relayPinTest() {
        Relay relay = new Relay(1, "test", "22", "static");
        assertEquals("22", relay.getPin());
        relay.setPin("23");
        assertEquals("23", relay.getPin());
    }

    @Test
    void relayStateTest() {
        Relay relay = new Relay(1, "test", "22", "static");
        assertEquals(false, relay.getState());
        relay.setState(true);
        assertEquals(true, relay.getState());
    }
}