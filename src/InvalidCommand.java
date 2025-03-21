
/**
 * This class is used as a catch all for all user input that doesn't conform to the accepted input.
 * It will print an error message and ask the user for input once again
 * @author Nathan Holmquist
 */
// FULLY DONE
public class InvalidCommand implements UserInputCommand {

    private String invalidCommand;
    private String carryOutMessage;


    /**
     * 
     * @param invalidCommand the user input that doesn't conform to the accepted input
     * constructor
     */
    public InvalidCommand(String invalidCommand){
        this.invalidCommand = invalidCommand;
        this.carryOutMessage = "";
        System.out.println(carryOut());
        
    }

    /**
     * This method is used to return a string of the error message
     * It will return a string with a message indicating the input was invalid
     * and ask the user for input once again
     */
    @Override
    public String carryOut() {
        
        this.carryOutMessage = "Invalid command: " + this.invalidCommand + ". Please try again.";
        return this.carryOutMessage;
    }
    
}
