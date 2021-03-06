package relay;

import java.util.Hashtable;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.util.Set;

import org.springframework.stereotype.Repository;
import org.apache.commons.lang3.StringUtils;

import relay.models.Relay;

@Repository
public class RelayDAO {
    private static Hashtable<Integer, Relay> relayInstances = null;
    
    public static boolean init() {
        relayInstances = new Hashtable<Integer, Relay>();
        String relays = System.getProperty("relays");
        String pins = System.getProperty("pins");
        String type = System.getProperty("types");

        try {
            String[] names = relays.split(",");
            String[] relayPins = pins.split(",");
            String[] types = type.split(",");

            // initialize the relay objects
            for (int i = 0; i < names.length; i++) {
                
                // This can probaly be changed to a single constructor
                if (types[i].contains("timer")) {
                    System.out.println(names[i] + " is a timer relay");
                    relayInstances.put(i + 1, new Relay(i + 1, names[i], relayPins[i], types[i], readScheduleFromFile(names[i], types[i])));
                } else {
                    System.out.println(names[i] + " is NOT a timer relay");
                    relayInstances.put(i + 1, new Relay(i + 1, names[i], relayPins[i], types[i]));
                }
                
            }

        } catch (Exception e) {
            System.out.println(e);
            System.out.println("Unable to parse the necessary parameters (relays, pins & types)");
            System.exit(1);
        }
        return true;
    }

    public static String getAllrelays() {
        String response = "{'data':[";
        for (int i = 0; i < relayInstances.size(); i++) {
            Relay temp = relayInstances.get(i + 1);
            response += temp.toString();
        }
        if (relayInstances.size() > 0) {
            response = StringUtils.chop(response);
        }
        response += "]}";
        return response;
    }

    public static String getRelay( int id) {
        String response = "{'data':[";
        Relay temp = relayInstances.get(id);
        response += temp.toString();
        response = StringUtils.chop(response);
        response += "]}";
        return response;
    }

    public static boolean checkRelayId( int id) {
        Relay temp = relayInstances.get(id);
        if (temp != null) {
            return true;
        }
        return false;
    }

    // Add changes for modifying schedule and writing to file
    public static String modifyRelay(Relay reqRelay) {
        // Identify the relay to be modified
        int reqId = reqRelay.getId();
        boolean reqState = reqRelay.getState();
        // State (boolean) is the expected variable to change frequently
        Relay exRelay = relayInstances.get(reqId);
        exRelay.executeState(reqState);

        // If the existing relay is a timer relay then check for schedule changes
        if ( exRelay.getType().equals("timer")) {
            String reqSchedule = reqRelay.getSchedule();
            if ( reqSchedule!= null  && !reqSchedule.equals(exRelay.getSchedule()) && !exRelay.getType().equals("suntimer") ) {
                System.out.println("We need to update the schedule of this timer");
                // Modify the objects saved in memory
                exRelay.setSchedule(reqSchedule);
                // Write the new schedule to file for persistence
                writeScheduleToFile(exRelay.getTitle(), exRelay.getSchedule());
            }
        }
        return getRelay(reqId);
    }

    public static boolean healthCheck() {
        if (relayInstances != null && relayInstances.size() > 0) {
            return true;
        } else {
            return false;
        }
    }

    public static void relayScheduleCheck() {
        Set<Integer> keys = relayInstances.keySet();
        for(Integer key: keys){
            relayInstances.get(key).checkSchedule();
        }
    }

    private static String readScheduleFromFile(String name, String type) {
        if (type.equals("timer")) {
            try {
                // Read schedule from a file here, if not present then set a default
                // https://www.w3schools.com/java/java_files_read.asp
                File myObj = new File(name + ".txt");
                Scanner myReader = new Scanner(myObj);
                String schedule = myReader.nextLine();
                System.out.println(schedule);
                myReader.close();
                return schedule;
            } catch (FileNotFoundException e) {
                System.out.println("An error occurred while opening the " + name + ".txt file.");
                System.out.println("Assigning a generic schedule");
                return "06:00:00/08:00:00,11:00:00/14:00:00";
            }
        } else {
            return "";
        }
        
    }

    private static void writeScheduleToFile(String name, String schedule) {
        try {
            FileWriter myWriter = new FileWriter(name + ".txt");
            myWriter.write(schedule);
            myWriter.close();
            System.out.println("Successfully wrote new schedule to the file.");
          } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
          }
    }

}