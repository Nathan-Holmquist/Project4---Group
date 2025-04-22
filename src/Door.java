/**
 * keeps data about a door
 * 
 * @author Nathan Holmquist
 */
public class Door {
    private String direction;
    private Location leavingLocation;
    private Location enteringLocation;
    private boolean isLocked = false;

    /**
     * No arg constructor
     */
    Door(){
        this.direction = "";
    }
    /**
     * Three arg constructor 
     * 
     * @param dir direction
     * @param leave leavingLocation (where you are now)
     * @param enter enteringLocation (where the door leads to)
     */
    Door(String dir, Location leave, Location enter){
        this.direction = dir;
        this.leavingLocation = leave;
        this.enteringLocation = enter;
    }
    /**
     * describes a door object
     * @return string 
     */
    String describe(){
        String response = "This door goes from " + this.leavingLocation.getName() +" "+ this.direction +" to " + this.enteringLocation.getName();
        return response;
    }
    /**
     * Gets leavingLocation
     * @return leavingLocation
     */
    Location getLeaving(){
        return leavingLocation;
    }
    /**
     * Gets enteringLocation
     * @return enteringLocation
     */
    Location getEntering(){
        return enteringLocation;
    }
    /**
     * Gets direction
     * @return direction
     */
    String getDirection(){
        return direction;
    }

    /**
     * Gets lockedState
     * @return locked
     */
    boolean getLocked(){
        return isLocked;
    }

    /**
     * Sets leavingLocation
     * @param leave leavingLocation
     */
    void setLeaving(Location leave){
        this.leavingLocation = leave;
    }
    /**
     * Sets enteringLocation
     * @param enter enteringLocation
     */
    void setEntering(Location enter){
        this.enteringLocation = enter;
    }

    /**
     * Sets direction
     * @param dir direction
     */
    void setDirection(String dir){
        this.direction = dir;
    }

    /**
     * Sets locked
     * @param enteredLocked locked
     */
    void setLockedState(boolean enteredLocked){
        this.isLocked = enteredLocked;
    }
}
