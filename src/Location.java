
import java.util.ArrayList;
/**
 * Keeps data about a location
 * 
 * @author Nathan Holmquist
 */
public class Location {
    
    private String name;
    private String desc;
    private String discription;
    private Boolean haveVisited;
    private ArrayList<Door> doors;
    private ArrayList<Item> items;
    
    /**
     * No arg constructor
     */
    Location(){
        this.name = "DEFAULT LOCATION NAME VALUE";
        this.desc = "DEFAULT LOCATION DISCRIPTION VALUE";
        this.doors = new ArrayList<Door>();
        this.items = new ArrayList<Item>();
        this.haveVisited = false;
        
    }
    /**
     * Two arg constructor
     * 
     * @param name name
     * @param desc discription
     */
    Location(String name, String desc){
        this.name = name;
        this.desc = desc;
        this.doors = new ArrayList<Door>();
        this.items = new ArrayList<Item>();
        this.haveVisited = false;
        
    }

    /**
     * Gets name
     * @return name
     */
    String getName(){
        return this.name;
    }
    /**
     * Gets discription
     * @return discription
     */
    String getDescription(){

        if (!this.haveVisited){
            return "Discription: " + this.desc;
        }
        return "";
    }
    /**
     * Sets name
     * 
     * @param name name
     */
    void setName(String name){
        this.name = name;
    }
    /**
     * Sets discription
     * @param desc discription
     */
    void setDescription(String desc){
        this.desc = desc;
    }
    /**
     * Returns true if there is a door that's direction is the same as the passed string
     * 
     * @param dir direction 
     * @return true if it is possible to go the direction the user specified
     */
    boolean canYouGoThisWay(String dir){ // True if there is a door in the ArrayList that is pointing in the direction
        if (this.doors.isEmpty()){
            System.out.println("SHITS ALSO EMPTY");
        }
        for(Door door:this.doors){
            if (door.getDirection().contains(dir)){
                return true;
            }
        }

        return false;
    }
    /**
     * Returns a location object that is the enteringLocation of the door. 
     * It gives you the location that the user is going to next (because at this point they have already typed a direction they wish to go)
     * 
     * @param dir
     * @return
     */
    Location goThisWay(String dir){ // returns you the location that your cardinal direction points to
        this.haveVisited = true;
        for (Door door:this.doors){
            if (door.getDirection().equals(dir)){
                return door.getEntering();
            }
        }
        return null; // THERE WAS NO DOOR THAT COULD BE FOUND GOING IN THAT DIRECTION
    }
    /**
     * Returns a string that describes all doors connected to this location object in the format "This door goes from (leavingLocation) (direction) to (enteringLocation)
     * "This door goes from Double Drive n to Bell Tower" 
     * @return string describing the doors
     */
    String describeDoors(){
        
        discription = "";
        if (this.doors.isEmpty()){
            discription = "SHITS EMPTY";
        } else {
            for(Door door:this.doors){
                discription += door.describe() + "\n";
            }
        }

        return discription;
    }
    /**
     * adds door to ArrayList
     * @param door door
     */
    void addDoor(Door door){
        this.doors.add(door);
    }

    // Adds item to items array
    void addItem(Item item){
        this.items.add(item);
    }
    // removes item from items array
    void removeItem(Item item){
        this.items.remove(item);
    }
    // returns an item from the items array that matches the name passed in
    Item getItemNamed(String name){
        for (Item item:this.items){
            if (item.getName().equals(name)){
                return item;
            } 
        }
         return null;
    }
    // returns a string that lists all items in the items array
    String getItemsInLocation(){
        String itemList = "";
        for (Item item:this.items){
            itemList += item.getName() + "\n";
        }
        if (itemList.equals("")){
            return "There are no items in this location";
        }
        return "\nItems in this location:\n" + itemList;
    }
    // returns the items array
    ArrayList<Item> getItems(){
        if (this.items.isEmpty()){
            System.out.println("SHITS EMPTY");
        }
        return this.items;
    }


}
