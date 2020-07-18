package relay;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.springframework.stereotype.Repository;

@Repository
public class RelayDAO {
    InputStream inputStream;
    
    public boolean init() {

        
        // Properties prop = new Properties();
        // inputStream = Object.class.getResourceAsStream(".//main//resources//config.properties");


        // try {
        //     prop.load(inputStream);
        // } catch (IOException e) {
        //     System.out.println(e);
        // }

        // // get the property value and print it out
        // String relays = prop.getProperty("relays");
        // System.out.println(relays);
        return true;
    }
}