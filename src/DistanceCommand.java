/**
 * @author Nathan Holmquist
 * 
 * implements UserInputCommand to print out the number of locations the user has been to so far in the tour
 */


public class DistanceCommand implements UserInputCommand {

    TourStatus tourStatus;

    public DistanceCommand(){
        this.tourStatus = TourStatus.getInstance();

        System.out.println(carryOut());
    }

    @Override
    public String carryOut() { 
        String distanceMessage = "You have traveled to " + tourStatus.getDistance() + " locations.";
        return distanceMessage;
    }

}
