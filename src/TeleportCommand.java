

public class TeleportCommand implements UserInputCommand{


    private TourStatus tourStatus;
    private Location endLocation;

    public TeleportCommand(){
        this.tourStatus =  TourStatus.getInstance();
        System.out.println(carryOut());
    }

    @Override
    public String carryOut() {
        endLocation = tourStatus.getCampus().getRandomLocation();
        return "You have been teleported to " + endLocation.getName();
    }
}
