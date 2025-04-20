public class ItemCommand {
    private String trigger; // like "drink" or "refill"
    private String action;  // like "Transform", "Disappear", etc.
    private String target;  // like "emptyCoffee"
    private String message; // the text response

    // Constructor
    public ItemCommand(String trigger, String action, String target, String message) {
        this.trigger = trigger;
        this.action = action;
        this.target = target;
        this.message = message;
    }

    // Getters
    public String getTrigger() { return trigger; }
    public String getAction() { return action; }
    public String getTarget() { return target; }
    public String getMessage() { return message; }
}