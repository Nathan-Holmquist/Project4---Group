
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * 
 * Main class for my program 
 * @author Nathan Holmquist
 * @version 1.2.0
 */
public class TourUMW {
    public static void main(String[] args) throws FileNotFoundException{
        Scanner scanner = new Scanner(System.in);
        File file;

        // File input loop
        while (true) {


            //file = new File("..\\data\\newInputData.txt"); // WINDOWS VERSION
             file = new File("data/newInputData.txt"); // MAC/LINUX VERSION


            if (!file.exists()){
                System.out.println("File not found, please try again");
                System.out.println("Current working directory: " + System.getProperty("user.dir"));
                System.exit(-1);
            } else {
                break;
            }
        }
        

        // Object Creation
        Scanner fileScanner = new Scanner(file);
        TourStatus tourStatus = TourStatus.getInstance();
        TourUMW tourUmw = new TourUMW();
        boolean firstRun = true;

        // Setting up campus
        Campus campus = tourUmw.setUpCampus(fileScanner);

        tourStatus.setCampus(campus);

        tourStatus.setCurrentLocation(campus.getStartLocation());


        System.out.println("Would you like to restore a previous save file? (yes/no)");
        String response = scanner.nextLine().trim();
        if (response.equals("yes") || response.equals("y")) {
            firstRun = false;
            TourStatus.getInstance().loadTour(campus);
            System.out.println("Save file loaded! [Press Enter]");
            scanner.nextLine(); 
            clearScreen();
        }
        
        // Main user input loop
        clearScreen(); // To get the file input text out of the terminal and start the tour.
        while (true){
        
            UserInputCommand command = TourUMW.promptUser(scanner, firstRun);
            firstRun = false;

        }

        
    }


    /**
     * This method parses the input file and creates a new "Campus" object complete with the information from the file
     * 
     * @param fileScanner
     * @return campus
     * @throws FileNotFoundException
     */
    
    public Campus setUpCampus(Scanner fileScanner) throws FileNotFoundException{


        Campus campus = new Campus();
        String line;
        int itemStepCount = 0;
        int starCount = 0;
        boolean discription = false;
        String locationName = "";
        String locationDesc = "";
        ArrayList<Location> locations = new ArrayList<Location>();
        int locationCount = 0;
        int doorStepCount = 0;
        Location currentLocation = null;
        Door door = null;
        Item item = null;
        String doorLocationName = ""; // This is to keep track of the Location object that a door belongs to while in the door data in the input file
        TourStatus tourStatus = TourStatus.getInstance(); 
        boolean outsideCheck = false;
        

        // I need the array list to dynamically add Location objects. 

        while (fileScanner.hasNextLine()){
            line = fileScanner.nextLine().toLowerCase();
            // main logic loop, checks the amount of times ***** has come acround and switches modes accordingly

            if (line.contains("*****")) {  // starCount increment
               
                starCount++;
            } else if (starCount == 0){ // Campus Name
                campus.setCampusName(line);
                
                
            } else if (starCount == 1){ // Locations

                if (line.contains("locations:")){ // If line is Locations: it isn't needed and the rest of the loop can get skipped
                    ;
                } else if (line.contains("+++")){ // If line contains +++ it adds to the location count and skips rest of loop
                    locationCount++; 
                    discription = false;
                } else {
                    
                    if (!discription){ // if we are in NAME OF LOCATION mode
                        locationDesc = "";
                        locationName = line;
                        
                        // Make new location object and fill it with a name and description. Then add it to the ArrayList of locations
                        Location location = new Location();
                        location.setName(locationName);
                        

                        //make key

                        if(locationName.equalsIgnoreCase("katora coffee")){
                            item = new Item();
                            item.setName("monroe hall key");
                            item.setMessage("Unlocks Monroe Hall");
                            currentLocation.addItem(item); // Directly add to the location
                        }

                        campus.addLocation(location);
                        locations.add(location);
                        
                        outsideCheck = true;
                        discription = true;
                        

                    } else if (outsideCheck){
                        currentLocation = locations.get(locations.size()-1);
                        
                        if (line.equalsIgnoreCase("indoor")){
                            
                            currentLocation.setIsOutside(false);
                        } else if (line.equalsIgnoreCase("outdoor")){
                            
                            currentLocation.setIsOutside(true);
                        }
                        
                        outsideCheck = false;
                        
                        
                        
                    } else { // if we are in DESCRIPTION mode
                        // Get the location and then assign it the description
                        currentLocation = locations.get(locations.size() -1);
                        locationDesc += line + "\n"; 
                        // Location can be multiple lines so we need to concatinate them 
                        // this will result in more calls to the object but I don't think thats really a problem rn
                        currentLocation.setDescription(locationDesc);
                        
                    }

                    
                }
            } else if (starCount == 2){ // Doors

                if (line.contains("doors:")){ // If line is Doors: it can be ignored
                    ;
                } else if (line.contains("+++")){ // if line is +++ this means there is a new door object coming
                    continue;
                } else { // There are three steps, entering location, the direction the door faces, exiting location
                    
                    if (doorStepCount == 0){ // ENTERING LOCATION
                        currentLocation = campus.getLocation(line); // Location that the door should belong to
                        doorLocationName = line;

                        //if(doorLocationName.equalsIgnoreCase())

                        door  = new Door(); // create new door object
                        door.setLeaving(currentLocation); // Set the entering location of the door
                        doorStepCount++;
                    } else if (doorStepCount == 1) { // DIRECTION
                        door.setDirection(line);
                        doorStepCount++;
                    } else if (doorStepCount == 2) { // EXITING LOCATION (is this the place
                        currentLocation = campus.getLocation(doorLocationName); // THE LOCATION THAT THE DOOR BELONGS TO (ENTERING LOCATION)
                        door.setEntering(campus.getLocation(line));

                        //lock monroe hall door to
                        if(door.getDirection().equals("n")){
                            if(door.getEntering().getName().equalsIgnoreCase("Monroe Hall")){
                                door.setLockedState(true);
                            }
                        }


                        currentLocation.addDoor(door);
                        doorStepCount = 0;
                    }
                }

                //Get monroe hall door, and set it to LOCKED!




                //Get combs hall door and set it to Locked




            } else if (starCount == 3) { // Items
                
                if (line.contains("items:")) { // If line is "Items:", ignore it
                    continue;
                } else if (line.contains("+++")) { // If line is "+++", a new item object is coming
                    itemStepCount = 0;
                    continue;
                } else { // Three steps: item name, item location, item description

                    if (itemStepCount == 0) { // ENTERING NAME
                        item = new Item();
                        item.setName(line);
                        itemStepCount++;

                    } else if (itemStepCount == 1) { // ADDING IT TO LOCATION

                        if (line.contains("none")){
                            tourStatus.setUnplacedItem(item);
                            itemStepCount = 5; // Skip to the end of the loop the number is arbratrary
                            continue;
                        } else {
                            currentLocation = campus.getLocation(line); // Location the item belongs to
                            itemStepCount++;
                            continue;
                        }
                        
                    } else if (itemStepCount == 2) { // ADDING DESCRIPTION
                        item.setMessage(line);
                        currentLocation.addItem(item); // Directly add to the location
                        itemStepCount++;

                    } else {
                        if (line.contains("[")) {
                            // This means the item is a command
                            ItemCommand command = parseItemCommand(line);
                            if (command != null) {
                                item.addCommand(command);
                            } else {
                                System.out.println("Invalid command format: " + line);
                            }
                            continue;
                        }
                    }
                }

            }
        }
        return campus;
    }
    

    /**
     * This method prompts the user for input and returns a command based on the input
     * 
     * @param input Scanner used for input
     * @return Returns the correct command for what to do based on user input
     */
    public static UserInputCommand promptUser(Scanner input, boolean firstRun) {
        TourStatus tourStatus = TourStatus.getInstance();
        
        

        if (firstRun){
            System.out.println("Welcome to the UMW tour!\n");

            System.out.println("You came with a backpack! If you are at a location with an item, you can pick it up with 'pickup [item name]'");
            System.out.println("You can drop an item at a location with 'drop [item name]'\n");

            System.out.println("You can also just press the first letter of the command, so 'p [item name]' works too");
            System.out.println("If at any point you want to no longer see what doors are available to you, type 'v' or 'verbose' to turn on verbose mode\n");
            System.out.println("You can also type 'distance' to see the total amount of locations you have been to\n");

            System.out.println("You can move to a new location with n/s/e/w ");
            System.out.println("\n\n");

            System.out.println("You are starting at " + tourStatus.getCurrentLocation().getName()+ "\n");
            System.out.println(tourStatus.getCurrentLocation().describeDoors());
            System.out.println("Items: " + tourStatus.getCurrentLocation().getItemsInLocation());
            System.out.println();
            System.out.println("--------------------------------");



        } else {
            System.out.println("\n");
            System.out.println("Name: " + tourStatus.getCurrentLocation().getName() +"\n");
            
            if (tourStatus.getCurrentLocation().isOutside()){
                System.out.println("It is raining.");
            } else {
                System.out.println("It is not raining");
            }
                
            System.out.println(tourStatus.getCurrentLocation().getDescription());
            System.out.println();

            // Check if verbose mode is on
            if ((tourStatus.getIsVerboseMode() == false)){
                System.out.println("Doors: ");
                System.out.println(tourStatus.getCurrentLocation().describeDoors());
            }
            
            System.out.println(tourStatus.getCurrentLocation().getItemsInLocation() + "\n");
            System.out.println("Enter a command (n/s/e/w for movement, drop [item], pickup [item], backpack, q to quit): ");
            System.out.println();
            System.out.println("--------------------------------");
        }


        
    

        String itemName = null;
        
        String userLine = input.nextLine().trim().toLowerCase();
        

        
        // Handle movement commands
        if (userLine.equals("n") || userLine.equals("s") || userLine.equals("e") || userLine.equals("w")) {
            clearScreen();
            tourStatus.addToDistance();
            return new MovementCommand(userLine);
        }
    
        // Handle dropping an item
        if (userLine.startsWith("drop ") || userLine.startsWith("d ")) {

            if (userLine.startsWith("drop ")) {
                itemName = userLine.substring(5);
            }

            if (userLine.startsWith("d ")){
                itemName = userLine.substring(2);
            }
            
            clearScreen();
            return new DropCommand(itemName);
        }
    
        // Handle picking up an item
        if (userLine.startsWith("pickup ") || userLine.startsWith("p ")) {

            if (userLine.startsWith("pickup ")) {
                itemName = userLine.substring(7);
            }

            if (userLine.startsWith("p ")){
                itemName = userLine.substring(2);
            }
            
            clearScreen();
            return new PickupCommand(itemName);
        }
    
        // Handle checking backpack contents
        if (userLine.equals("backpack") || userLine.equals("b")) {
            clearScreen();
            return new BackpackCommand();
        }
    
        // Handle quitting
        if (userLine.equals("q") || userLine.equals("quit") || userLine.equals("exit")) {
            
            System.out.println("Would you like to save before quitting? (yes/no): ");
        	String response = input.nextLine().trim();
        	
        	if (response.equals("yes")) {
        		TourStatus.getInstance().saveTour();
        	}
            input.close();
            System.out.println("Quitting the tour. Thanks for coming!");
            System.exit(0);
        }

        if (userLine.equals("verbose ") || userLine.equals("v")) {
            clearScreen();
            return new VerboseCommand();
        }

        if (userLine.equals("d") || userLine.equals("distance")){
            clearScreen();
            return new DistanceCommand();
        }

        for (Item item : tourStatus.getBackpack()) {
            for (ItemCommand command : item.getCommands()) {
                if (userLine.equalsIgnoreCase(command.getTrigger())) {
                    clearScreen();
                    return new ItemInputCommand(userLine);
                }
            }
        }


        
        // If input is invalid, return an InvalidCommand
        clearScreen();
        return new InvalidCommand(userLine);
    }

    /**
     * Clears the terminal so the user experience is better. 
     * This code was heavily addapted from a StackOverflow post I saw.
     * It is linked in a comment within the method
     * 
     */
    public static void clearScreen() {
        String os = System.getProperty("os.name").toLowerCase();
        // I found this code on a website when I looked up how to clear the console since it makes
        // the user not have to change where they are looking at on the screen.
        // https://stackoverflow.com/questions/2979383/how-to-clear-the-console-using-java
        // It just makes the viewing much better when using the program

        try {
            if (os.contains("win")) {
                // Windows
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else {
                // Unix/Linux/Mac
                System.out.print("\033[H\033[2J");
                System.out.flush();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    

    public ItemCommand parseItemCommand(String line) {
        String trigger;
        String action = null;
        String target = null;
        String message;
    
        // Split the line at the first colon
        if (!line.contains(":")) return null; // Invalid format
    
        String[] parts = line.split(":", 2); // Only split into 2 parts
        trigger = parts[0].trim(); // Might be "drink" or "drink[Transform(emptyCoffee)]"
        message = parts[1].trim();
    
        // Check if the trigger contains brackets, meaning it has an action and maybe a target
        if (trigger.contains("[")) {
            int openBracket = trigger.indexOf("[");
            int closeBracket = trigger.indexOf("]");
            String insideBrackets = trigger.substring(openBracket + 1, closeBracket);
    
            trigger = trigger.substring(0, openBracket); // Strip off the action bracket part
    
            if (insideBrackets.contains("(")) {
                // Example: Transform(emptyCoffee)
                int openParen = insideBrackets.indexOf("(");
                int closeParen = insideBrackets.indexOf(")");
                action = insideBrackets.substring(0, openParen);
                target = insideBrackets.substring(openParen + 1, closeParen);
            } else {
                // Example: Disappear
                action = insideBrackets;
            }
        }
    
        return new ItemCommand(trigger, action, target, message);
    }
}
