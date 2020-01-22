package demo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class EmployeeTest {

    @Test
    void employeeObjectTest() {
        Employee emp = new Employee(1, "test", "subject", "test.subject@test.com");
        
        assertEquals(1, emp.getId(), "The output should be the supplied firstName at object creation");
        assertTrue("test".equals(emp.getFirstName()),"The output should be the supplied firstName at object creation");
        assertTrue("subject".equals(emp.getLastName()),"The output should be the supplied lastName at object creation");
        assertTrue("test.subject@test.com".equals(emp.getEmail()),"The output should be the supplied email at object creation");
    }

    @Test
    void employeeFirstNameTest() {
        Employee emp = new Employee(1, "test", "subject", "test.subject@test.com");
        assertEquals("test", emp.getFirstName());
        emp.setFirstName("application");
        assertEquals("application", emp.getFirstName());
    }

    @Test
    void employeeLastNameTest() {
        Employee emp = new Employee(1, "test", "subject", "test.subject@test.com");
        assertEquals("subject", emp.getLastName());
        emp.setLastName("object");
        assertEquals("object", emp.getLastName());
    }

    @Test
    void employeeEmailTest() {
        Employee emp = new Employee(1, "test", "subject", "test.subject@test.com");
        assertEquals("test.subject@test.com", emp.getEmail());
        emp.setEmail("testsubject@test.com");
        assertEquals("testsubject@test.com", emp.getEmail());
    }
}