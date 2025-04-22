import java.util.ArrayList;
import java.util.Hashtable;


/**
 *  A campus object
 * 
 * @author Nathan Holmquist
 */
public class Campus {


    /**
     * Name of the campus object.
     */
    private String campusName;
    /**
     * Hashtable of location names (key) and locations (value).
     */
    private Hashtable<String,Location> locations;
    /**
     * Starting location for my program.
     */
    private Location startingLocation;

    private int locationCount;
    

    /**
     * No param constructor for campus object.
     */
    Campus(){
        this.locations = new Hashtable<String,Location>();
        locationCount = 0;
    }
    
    /**
     * 2 param constructor for campus object
     * 
     * @param entry starting location
     * @param name campus name
     */
    Campus(Location entry, String name){
        this.startingLocation = entry;
        this.campusName = name;
        this.locations = new Hashtable<String,Location>();
        locationCount = 0;
    }

    /**
     * Used as a debugging tool to print all of the data from the campus object at any time.
     */
    void printEverything() {
        int count = 1; // human readability
        for (Location location : this.locations.values()) {
            System.out.println("Location #" + count + ": " + location.getName());
            System.out.println(location.getDescription());
            System.out.println(location.describeDoors());
            count++;
        }
    }


    
    /**
     * Add's location to hashtable with the key being the location.getName()
     * 
     * @param location
     */
    void addLocation(Location location){


        this.locations.put(location.getName(), location);
        if (this.locationCount == 0){setStartLocation(location);} // If this is the first time we see this, make it the starting location also.
        

        this.locationCount++;
    }

    /**
     * Given string as input, search hashtable for values with string as their key, if there is one, return it
     * 
     * @param name
     * @return location
     */
    public Location getLocation(String name) {
        if (name == null || name.trim().isEmpty()) { 
            System.out.println("Invalid input: name is null or empty.");
            return null;
        }
    
        name = name.trim();
        return locations.get(name); 
    }
    
    /**
     * Sets campusName
     * 
     * @param campusName
     */
    void setCampusName(String campusName){
        this.campusName = campusName;
    }

    /**
     * Gets campusName
     * 
     * @return campusName
     */
    String getCampusName(){
        return this.campusName;
    }
    /**
     * Sets startingLocation
     * @param start
     */
    void setStartLocation(Location start){
        this.startingLocation = start;
    }
    /**
     * Gets startingLocation
     * @return location
     */
    Location getStartLocation(){
        return this.startingLocation;
    }

    
}
