/**
 * This class represents an item that can be placed at a location, picked up, dropped off, or looked at from inside the backpack
 * it keeps track of the name and the added message for each item
 * @author Nathan Holmquist
 */
public class Item {

    private String name;
    private String message;

    /** 
     * Item constructor indicating the item class was made but has not had any data added to it
     */
    public Item() {
        this.name = "DEFAULT NAME";
        this.message = "DEFAULT MESSAGE";
    }

    /** 
     * Item constructor with name and message values as parameters
     * @param n the name of the item
     * @param m the message for the item
     */
    public Item(String n, String m) {
        this.name = n;
        this.message = m;
    }
    


    // GETTER/SETTER
    String getName() {return this.name;}
    String getMessage() {return this.message;}
    void setName(String name) {this.name = name;}
    void setMessage(String message) {this.message = message;}


    
}
