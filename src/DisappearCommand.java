public class DisappearCommand implements UserInputCommand {

    Item item;
    TourStatus tourStatus;
    String message;

    public DisappearCommand(String itemName) {
        this.tourStatus = TourStatus.getInstance();
        this.item = tourStatus.getItemFromBackpack(itemName);

        
        System.out.println(carryOut());

    }
    

    @Override
    public String carryOut() {

        if (this.item != null) {
            tourStatus.removeItemFromBackpack(item);
            message = item.getName() + " has disappeared from your backpack.";
        } else {
            message = "Item not found in backpack.";
        }

        return message;
    }
}
