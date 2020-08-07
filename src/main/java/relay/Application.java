package relay;

import java.util.concurrent.TimeUnit;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
        
        try {
            TimeUnit.SECONDS.sleep(5);
        }
        catch (InterruptedException e) {
            
        }

        RelayDAO.init();

        // Handle keyboard interrupt gracefully
        Runtime.getRuntime().addShutdownHook(new Thread() 
        {
            @Override
            public void run() 
            {
                System.out.println("Shutting down gracefully");
                // Enter other shutdown logic here
            }
        });
        while(true) {
            RelayDAO.relayScheduleCheck();
            try {
                TimeUnit.SECONDS.sleep(5);
            }
            catch (InterruptedException e) {
                
            }
        }
    }
}