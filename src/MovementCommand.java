


/**
 * This class is used to move the user into a new loacation. It takes in a input of either n,s,e,w and then moves the user accordingly.
 * @author Nathan Holmquist
 */
// DONE FULLY I THINK
public class MovementCommand implements UserInputCommand {
    private String dir;
    private TourStatus tourStatus;
    private String response;


    /**
     * Constructor
     * @param dir direction, n,s,e,w
     */ 
    public MovementCommand(String dir) {
        this.dir = dir;
        this.tourStatus = TourStatus.getInstance();
        response = "";
        System.out.println(carryOut());
    }


    /**
     * Handels and moves the user into a new location depending on what the input was
     */
    @Override
    public String carryOut() {

        if (dir.equals("n")){
            tourStatus.updateTourLocation(dir);
            response = "you moved north";

        } else if (dir.equals("s")){
            tourStatus.updateTourLocation(dir);
            response = "you moved south";

        } else if (dir.equals("e")){
            tourStatus.updateTourLocation(dir);
            response = "you moved east";

        } else if (dir.equals("w")){
            tourStatus.updateTourLocation(dir);
            response = "you moved west";

        } else {
            response = "Data entered was incorrect. Please only use n, s, e, w as input.";
        }

        return response;


    }

}
