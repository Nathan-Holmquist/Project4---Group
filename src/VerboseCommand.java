public class VerboseCommand implements UserInputCommand {
    
    private TourStatus tourStatus;


    VerboseCommand() {
        this.tourStatus = TourStatus.getInstance();
        System.out.println(carryOut());
        this.tourStatus.toggleVerboseMode();
    }

    /**
     * Returns a string of all the items in the backpack
     */
    @Override
    public String carryOut() {
        return tourStatus.getIsVerboseModeMessage();
    }
}
