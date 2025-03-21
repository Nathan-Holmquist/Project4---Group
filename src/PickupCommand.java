

/**
 * @author Nathan Holmquist
 * This class is used to pick up an item from a location and place it in the backpack.
 */
public class PickupCommand implements UserInputCommand {

    private TourStatus tourStatus;
    private String itemName;
    private String carryOutMessage;

    /**
     * Constructor
     * @param itemName The name of the item
     */
    public PickupCommand(String itemName){
        this.itemName = itemName;
        this.tourStatus = TourStatus.getInstance();

        System.out.println(carryOut());
        
    }



    /**
     * Picks up an item from a location and places it in the backpack
     * Handels all errors gracefully.
     */
    @Override
    public String carryOut() {
        // IF ITEM DOESN'T EXIST
        if (tourStatus.getCurrentLocation().getItemNamed(itemName) == null){
            carryOutMessage = "You do not have that item in this location";
            return carryOutMessage;
        }


        tourStatus.addToBackpack(tourStatus.getCurrentLocation().getItemNamed(itemName));
        tourStatus.pickupItemFromLocation(itemName);

        if (tourStatus.getCurrentLocation().getItemNamed(itemName) == null){
            carryOutMessage = "You have picked up " + itemName + " and placed it in your backpack";
        } else {
            carryOutMessage = "You do not have that item in this location";
        }

        return carryOutMessage;
        
    }
}
