import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;

/**
 *  This class keeps track of where you are on campus, it also keeps track of the backpack and the items inside it.
 * 
 * @author Nathan Holmquist
 */
public class TourStatus{

    private boolean isVerboseMode;
    private Campus campus;
    private Location  currentLocation;
    private String currentLocationString;
    private ArrayList<Item> backpack;
    private static TourStatus instance;     
    private int distance;
    private ArrayList<Item> unplacedItems;
    private int outdoorVisit;
    private boolean rain;


    /**
     * No param constructor for TourStatus
     */
    private TourStatus(){
        this.backpack = new ArrayList<Item>();
        this.isVerboseMode = false;
        this.distance = 0;
        this.unplacedItems = new ArrayList<Item>();
        this.outdoorVisit = 0;
        this.rain = false;
    }


    // This will be used for the transform command
    public void addUnplacedItem(Item item){
        this.unplacedItems.add(item);
    }

    public Item getUnplacedItem(String name){
        for (Item item : this.unplacedItems){
            if (item.getName().equals(name)){
                return item;
            }
        }
        
        return null;
    }


    public void printUnplacedItems(){
        if (this.unplacedItems.isEmpty()){
            System.out.println("There are no unplaced items.");
        } else {
            System.out.println("Unplaced items:");
            for (Item item : this.unplacedItems){
                System.out.println(item.getName());
            }
        }
    }
    /**
     * This method is used to add an item to the backpack
     * @param item the item to be added to the backpack
     */
    void addToBackpack(Item item){
        this.backpack.add(item);
    }
    /**
     * This method is used to drop an item from the backpack to the currentLocation
     * @param name the name of the item to be dropped
     * @return the item that was dropped
     */
    Item dropItemFromBackpack(String name){
        for (Item item : this.backpack){
            if (item.getName().equals(name)){
                this.backpack.remove(item);
                currentLocation.addItem(item);
                return item;
            }
        } 
        return null;
    }
    /**
     * This method is used to pick up an item from the currentLocation and add it to the backpack
     * @param name the name of the item to be picked up
     * @return the item that was picked up
     */
    Item pickupItemFromLocation(String name){
        for (Item item : this.currentLocation.getItems()){
            if (item.getName().equals(name)){
                this.currentLocation.removeItem(item);
                System.out.println("You picked up " + item.getName() + " from " + currentLocation.getName());
                return item;
            }
        } 
        return null;
    }
   
    void removeItemFromBackpack(Item item){
        this.backpack.remove(item);
    }
    
    
    void setCurrentLocation (Location loc){
        this.currentLocation = loc;
        this.currentLocationString = loc.getName();
    }
    
    Boolean getIsVerboseMode(){return this.isVerboseMode;}

    void toggleVerboseMode(){
        if (this.isVerboseMode == true){
            this.isVerboseMode = false;
        } else {
            this.isVerboseMode = true;
        }
    }

    void setVerboseMode(boolean v){
        this.isVerboseMode = v;
    }

    Campus getCampus(){return this.campus;}
    void setCampus(Campus campus){this.campus = campus;}

    String getIsVerboseModeMessage(){
        if (this.isVerboseMode){
            return "Verbose mode is on";
        } else {
            return "Verbose mode is off";
        }
    }

    Location  getCurrentLocation (){return this.currentLocation;}

    /**
     * This method is used to get the instance of the TourStatus class
     * @return the instance of the TourStatus
     */
    public static TourStatus getInstance() {    
        if (instance == null) {
            instance = new TourStatus();
        }  
        return instance;
    }


    

    /**
     * lists backpack items. I am not going into deeper detail. 
     * @return a string with a list of all the items in the backpack
     */
    String listBackpackItems(){
        String list = "";

        if (this.backpack.isEmpty()){
            return "Your backpack is empty.";
        }

        for (Item item : this.backpack){
            list += item.getName() + " - " + item.getMessage() + "\n";
        }

        return list;
    }

    public ArrayList<Item> getBackpack(){
        return this.backpack;
    }

    void addToDistance(){
        this.distance += 1;
    }
    
    int getDistance(){
        return this.distance;
    }

    void updateTourLocation (String dir){ // THIS IS WHAT TO WORK ON NEXT, Make sure this does what it's supposed to.
        
    	TourStatus status = TourStatus.getInstance();
        if (this.campus.getLocation(this.currentLocationString) == null) {
            System.out.println("Error: Current location is not set.");
            return;
        }
        
        if (this.campus.getLocation(this.currentLocationString).canYouGoThisWay(dir)) { // If there is a door where you are that leads to the direction you entered
        	Location newLoc = currentLocation.goThisWay(dir);
        	setCurrentLocation(newLoc);
        	
        	if (newLoc.isOutside()) {
        		status.outdoorVisit++;
        	} else {
        		status.outdoorVisit = 0;
        	}
        	
        	if (status.outdoorVisit > 2) {
        		status.rain = true;
        		System.out.println("It is currently raining!");
        	} else {
        		status.rain = false;
        	}
    
        } else {
        	System.out.println("You cannot go this way!");
        }
    }
    


    	
    public void saveTour() {
        try {
            PrintWriter write = new PrintWriter("data/save.txt");
            TourStatus status = TourStatus.getInstance();
                
                System.out.println("Saving...");
                write.println("Current Location:" + status.getCurrentLocation().getName());	//saves the current location and distance traveled
                write.println("Current Distance:" + status.getDistance());
                write.println("Rain status:" + status.rain);
                write.println("Outdoor visit:" + status.outdoorVisit);
    
                for (Item item : status.backpack) {			//this looks through the current backpack and saves everything in there
                    write.println("Item:" + item.getName());
                }

                System.out.println("Tour saved!");		//Message to make sure the user saved
                write.close();
            } catch (Exception e) {
                System.out.println("Couldn't save file");
            }
        }
    
    public void loadTour(Campus campus) {
        try {
            Scanner scan = new Scanner(new File("data/save.txt"));
            TourStatus status = TourStatus.getInstance();
            
            if (!scan.hasNextLine()) {	
                scan.close();
                return;
            }
            
            String locationLine = scan.nextLine();			//restores curr ent location and replaces and sets it to that.
            String currentLoc = locationLine.substring(17);
            System.out.println(locationLine.substring(17));
            Location loc = campus.getLocation(currentLoc);
                
            status.setCurrentLocation(loc);
            
            if (!scan.hasNextLine()) {			//Goes to the next line
                scan.close();
                return;
            }
            
            String distanceLine = scan.nextLine();
            int distance = Integer.parseInt(distanceLine.substring(17).trim());		//Reads in the distance BACK as an int and saves it now
            status.distance = distance;
            
            while (scan.hasNextLine()) {
                String line = scan.nextLine();			//Reads in all the items	
                
                if (line.startsWith("Item:")) {		//It skips the part where the file says the item prefix and 
                    String itemName = line.substring(5);	//saves the part after it says it (hence the substring)
                    
                    for (Item item : campus.getAllItems()){
                        if (item.getName().equals(itemName)) {	//checks if the item name is the same as the one in the backpack
                            status.backpack.add(item);			//adds it to the backpack
                            campus.getLocation(currentLoc).removeItem(item);	//removes it from the location
                            break;
                        }
                    }
                    
        
                }
                
                if (line.startsWith("Rain status: ")) {
                    status.rain = Boolean.parseBoolean(line.substring(13).trim());
                }
                
                if (line.startsWith("Outdoor visit: ")) {
                    status.outdoorVisit = Integer.parseInt(line.substring(15).trim());
            
            }
        }    
            
            System.out.println("Tour loaded");
        } catch (FileNotFoundException e) {
            System.out.println("An error occured");
        } catch (NumberFormatException e) {
            System.out.println("Error in number format while loading the tour.");
        }
    }
}