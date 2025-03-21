

/**
 * @author Nathan Holmquist
 * This class is used to list the items in your backpack
 */
public class BackpackCommand implements UserInputCommand {


    private TourStatus tourStatus;
    private String carryOutMessage;


    /**
     * This constructor is used to create a new instance of the BackpackCommand class
     * It will clear the screen and then call carryOut
     */
    BackpackCommand(){
        TourUMW.clearScreen();
        this.tourStatus = TourStatus.getInstance();
        carryOutMessage = "";
        System.out.println(carryOut());
        
    }

    /**
     * Returns a string of all the items in the backpack
     */
    @Override
    public String carryOut() {
        carryOutMessage = this.tourStatus.listBackpackItems();
        return carryOutMessage;
    }

}