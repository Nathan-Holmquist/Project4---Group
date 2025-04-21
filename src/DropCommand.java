

/**
 * This class is used to drop a item from the backpack to the currentLocation
 * @author Nathan Holmquist
 */
public class DropCommand implements UserInputCommand {

    private TourStatus tourStatus;
    private String itemName;
    private String carryOutMessage;


    /**
     * 
     * @param itemName the name of the item (user input minus "drop ")
     * Constructor
     */
    public DropCommand(String itemName){
        this.itemName = itemName;
        this.tourStatus = TourStatus.getInstance();

        System.out.println(carryOut());
        
    }



    /**
     * This method is used to drop an item from the backpack to the currentLocation
     * It returns a string with a message either indicating the drop was succsessful or a failure
     */
    @Override
    public String carryOut() {
        carryOutMessage = "";
        if (tourStatus.dropItemFromBackpack(itemName) != null){
            carryOutMessage = "You dropped " + itemName + " from your backpack. And left it at " + tourStatus.getCurrentLocation().getName();
        } else {
            carryOutMessage = "You do not have that item in your backpack";
        }
        return carryOutMessage;
        
    }
}
