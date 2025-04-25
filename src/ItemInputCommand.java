import java.util.ArrayList;



/**
 * ItemInputCommand executes item commands. 
 * @author Nathan Holmquist
 * 
 */
public class ItemInputCommand implements UserInputCommand{

    private String line;
    private TourStatus tourStatus;
    private ArrayList<Item> itemList;
    private String response;
    private Location endLocation;

    public ItemInputCommand(String line){
        this.line = line;
        this.tourStatus = TourStatus.getInstance();
        System.out.println(carryOut());
    }

    @Override
    public String carryOut() {

        response= "";
        itemList = tourStatus.getBackpack();

        for (Item item : itemList) {
            for (ItemCommand command : item.getCommands()) {
                if (line.equalsIgnoreCase(command.getTrigger())) {

                    if (command.getAction().equalsIgnoreCase("Transform")){

                        Item unplacedItem = tourStatus.getUnplacedItem();
                        

                        tourStatus.addToBackpack(unplacedItem);
                        tourStatus.removeItemFromBackpack(item);
                        tourStatus.setUnplacedItem(item);
                        response = command.getMessage() + "\n" + unplacedItem.getName() + " has been added to your backpack.";

                    
                    } else if (command.getAction().equalsIgnoreCase("Disappear")){

                        if (item != null) {
                            tourStatus.removeItemFromBackpack(item);
                            response = item.getName() + " has disappeared from your backpack.";
                        } else {
                            response = "Item not found in backpack.";
                        }


                    } else if (command.getAction().equalsIgnoreCase("teleport")){

                        endLocation = tourStatus.getCampus().getRandomLocation();
                        tourStatus.setCurrentLocation(endLocation);
                        return "You have been teleported to " + endLocation.getName();
                    } else{
                        response = command.getMessage();
                    }


                    return response;
                }
            }
        }
        return null;
    }
    
}
