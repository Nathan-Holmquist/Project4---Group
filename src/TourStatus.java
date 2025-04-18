import java.util.ArrayList;

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

    /**
     * No param constructor for TourStatus
     */
    private TourStatus(){
        this.backpack = new ArrayList<Item>();
        this.isVerboseMode = false;
        this.distance = 0;
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
     * Updates the tour location
     * 
     * Checks if user can go to a location that they specify, if they can't it tells them they can't
     * if they can, it changes this.currentLocation to the location at the end of the door that they "went through"
     * @param dir direction
     */
    void updateTourLocation (String dir){ // THIS IS WHAT TO WORK ON NEXT, Make sure this does what it's supposed to.
        
        if (this.campus.getLocation(this.currentLocationString) == null) {
            System.out.println("Error: Current location is not set.");
            return;
        }
        
        if (this.campus.getLocation(this.currentLocationString).canYouGoThisWay(dir)){ // If there is a door where you are that leads to the direction you entered
            
            // System.out.println("Changed Current Location in Tour Status to : " + this.campus.getLocation(this.currentLocationString).goThisWay(dir).getName() );
            setCurrentLocation(this.campus.getLocation(this.currentLocationString).goThisWay(dir)); // Sets current location to a location that is the end point of the door (moves you through the door)
        } else { // User must have entered data wrong
            System.out.println("You cannot go " + dir + " from here. Try a different direction.");
        }
    
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

    void addToDistance(){
        this.distance += 1;
    }
    
    int getDistance(){
        return this.distance;
    }
}