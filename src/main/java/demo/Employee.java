package demo;

public class Employee {
    
    private int id;
    private String firstName;
    private String lastName;
    private String email;
    
    public Employee() {

    }

    public Employee(int id, String firstName, String lastName, String email) {
        super();
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }

    @Override
    public String toString() {
        return "{id:" + id + ",firstName:" + firstName + ",lastName:" + lastName + ",email:" + email +"}";
    }

	public int getId() {
		return id;
	}

	public void setId(int id) {
        this.id = id;
    }
    
    public String getFirstName() {
        return this.firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return this.firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getemail() {
        return this.email;
    }

    public void setemail(String email) {
        this.email = email;
    }
}