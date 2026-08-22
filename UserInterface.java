import java.util.List;
import java.util.Scanner;

public class UserInterface {
    private static final Scanner sc = new Scanner(System.in);
    private static final Inventory inventory = new Inventory();
    private static final Validator validator = new Validator();
    private static final String BORDER_STRING = "=".repeat(50);

    // Menu handler
    public void printMenu() {
        System.out.println(BORDER_STRING);
        System.out.println("                  Inventory Management System");
        System.out.println(BORDER_STRING);
        System.out.println("1 - Add Item");
        System.out.println("2 - Update Item");
        System.out.println("3 - Remove Item");
        System.out.println("4 - Display Items by Category");
        System.out.println("5 - Display All Items");
        System.out.println("6 - Search Item");
        System.out.println("7 - Sort Items");
        System.out.println("8 - Display Low Stock Items");
        System.out.println("9 - Exit");
    }

    public int getMenuChoice() {
        return readMenuChoice(1, 9);
    }

    // Item management methods

    // 1 Add Item
    public void addItem() {
        System.out.println(BORDER_STRING);
        System.out.println("                  ADD NEW ITEM");
        System.out.println(BORDER_STRING);

        String category = readCategory();
        String id = readUniqueId();
        String name = readName();
        int quantity = readPositiveInt("Enter item quantity: ", "Quantity");
        double price = readPositiveDouble("Enter item price: ", "Price");

        Item newItem = new Item(category, id, name, quantity, price);
        inventory.addItem(newItem);
        System.out.println("Item added successfully!");
    }

    // 2 Update Item
    public void updateItem() {
        if (inventory.isEmpty()) {
            System.out.println("No items in the inventory to update!");
            return;
        }
        System.out.println(BORDER_STRING);
        System.out.println("                  UPDATE ITEM");
        System.out.println(BORDER_STRING);

        String id = readId("Enter item ID to update: ");
        Item item = inventory.findItemById(id);
        if (item == null) {
            System.out.println("Item with not found!");
            return;
        }

        String choice = readNonEmptyString("Update Quantity or Price? (Q/P): ", "Update Choice");
        if (choice.equalsIgnoreCase("Q")) {
            updateQuantity(id, item);
        } else if (choice.equalsIgnoreCase("P")) {
            updatePrice(id, item);
        } else {
            System.out.println("Invalid choice. Please enter 'Q' for Quantity or 'P' for Price.");
        }
    }

    // Update Quantity and Price methods
    public void updateQuantity(String id, Item item) {
        int oldQuantity = item.getQuantity();
        int newQuantity = readNonNegativeInt("Enter new quantity: ", "Quantity");
        inventory.updateQuantity(id, newQuantity);
        System.out.println("Quantity of item " + item.getName() + " is updated from " + oldQuantity + " to " + newQuantity + ".");
    }

    public void updatePrice(String id, Item item) {
        double oldPrice = item.getPrice();
        double newPrice = readPositiveDouble("Enter new price: ", "Price");
        inventory.updatePrice(id, newPrice);
        System.out.println("Price of item " + item.getName() + " is updated from " + oldPrice + " to " + newPrice + ".");
    }

    // 3 Remove Item
    public void removeItem() {
        if (inventory.isEmpty()) {
            System.out.println("No items in the inventory to remove!");
            return;
        }
        System.out.println(BORDER_STRING);
        System.out.println("                  REMOVE ITEM");
        System.out.println(BORDER_STRING);
        String id = readId("Enter item ID to remove: ");
        Item removed = inventory.removeItem(id);
        System.out.println("Item " + removed.getName() + " with ID " + id + " has been removed from the inventory.");
    }

    // 4 Display Items by Category
    public void displayItemsByCategory() {
        if (inventory.isEmpty()) {
            System.out.println("No items in the inventory to display!");
            return;
        }
        System.out.println(BORDER_STRING);
        System.out.println("                  DISPLAY ITEMS BY CATEGORY");
        System.out.println(BORDER_STRING);
        String category = readCategory();
        List<Item> itemsByCategory = inventory.getItemsByCategory(category);
        if (itemsByCategory.isEmpty()) {
            System.out.println("No items found in " + category + " category!");
            return;
        }

        printItems(itemsByCategory, false);
    }

    // 5 Display All Items
    public void displayAllItems() {
        if (inventory.isEmpty()) {
            System.out.println("No items in the inventory!");
            return;
        }

        System.out.println(BORDER_STRING);
        System.out.println("                  DISPLAY ALL ITEMS");
        System.out.println(BORDER_STRING);

        printItems(inventory.getAllItems(), true);
    }

    // 6 Search Item
    public void searchItem() {
        if (inventory.isEmpty()) {
            System.out.println("No items in the inventory to search!");
            return;
        }
        System.out.println(BORDER_STRING);
        System.out.println("                  SEARCH ITEM");
        System.out.println(BORDER_STRING);
        String id = readId("Enter item ID to search: ");
        Item item = inventory.findItemById(id);
        if (item == null) {
            System.out.println("Item not found!");
        } else {
            printItems(List.of(item), true);
        }
    }

    // 7 Sort Items
    public void sortItems() {
        if (inventory.isEmpty()) {
            System.out.println("No items in the inventory to sort!");
            return;
        }

        System.out.println(BORDER_STRING);
        System.out.println("                  SORT ITEMS");
        System.out.println(BORDER_STRING);

        String sortChoice = readNonEmptyString("Sort by Quantity or Price? (Q/P): ", "Sort Choice");
        String sortBy;
        if(sortChoice.equalsIgnoreCase("Q")){
            sortBy = "quantity";
        } else if(sortChoice.equalsIgnoreCase("P")){
            sortBy = "price";
        } else {
            System.out.println("Invalid choice. Please enter 'Q' for Quantity or 'P' for Price.");
            return;
        }
        
        String orderChoice = readNonEmptyString("Sort in Ascending or Descending order? (A/D): ", "Order Choice");
        String order;
        if (orderChoice.equalsIgnoreCase("A")) {
            order = "ascending";
        } else if (orderChoice.equalsIgnoreCase("D")) {
            order = "descending";
        } else {
            System.out.println("Invalid choice. Please enter 'A' for Ascending or 'D' for Descending.");
            return;
        }

        List<Item> sortedItems = inventory.getSortedItems(sortBy, order);
        printItems(sortedItems, true);
    }

    // 8 Display Low Stock Items
    public void displayLowStockItems() {
        if (inventory.isEmpty()) {
            System.out.println("No items in the inventory to display!");
            return;
        }
        System.out.println(BORDER_STRING);
        System.out.println("                  DISPLAY LOW STOCK ITEMS");
        System.out.println(BORDER_STRING);
        List<Item> lowStockItems = inventory.getLowStockItems();
        if (lowStockItems.isEmpty()) {
            System.out.println("No low stock items found!");
            return;
        }
        printItems(lowStockItems, true);
    }

    // Table helper methods
    private static void printItems(List<Item> items, boolean includeCategory) {
        int idWidth = "ID".length();
        int nameWidth = "Name".length();
        int quantityWidth = "Quantity".length();
        int priceWidth = "Price".length();
        int categoryWidth = "Category".length();

        for (Item item : items) {
            idWidth = Math.max(idWidth, item.getId().length());
            nameWidth = Math.max(nameWidth, item.getName().length());
            quantityWidth = Math.max(quantityWidth, String.valueOf(item.getQuantity()).length());
            priceWidth = Math.max(priceWidth, String.format("%,.2f", item.getPrice()).length());
            if (includeCategory) {
                categoryWidth = Math.max(categoryWidth, item.getCategory().length());
            }
        }

        printTableHeader(idWidth, nameWidth, quantityWidth, priceWidth, categoryWidth, includeCategory);
        for (Item item : items) {
            if (includeCategory) {
                System.out.println(item.toTableRow(idWidth, nameWidth, quantityWidth, priceWidth, categoryWidth));
            } else {
                System.out.println(item.toTableRowByCategory(idWidth, nameWidth, quantityWidth, priceWidth));
            }
        }
    }

    private static void printTableHeader(int idWidth, int nameWidth, int quantityWidth, int priceWidth, int categoryWidth, boolean includeCategory) {
        
        int borderWidth = idWidth + nameWidth + quantityWidth + priceWidth + (includeCategory ? categoryWidth : 0) + (includeCategory ? 13 : 9);
        if (includeCategory) {
            System.out.printf(" %-" + idWidth + "s | %-" + nameWidth + "s | %-" + quantityWidth + "s | %-" + priceWidth + "s | %-" + categoryWidth + "s%n", "ID", "Name", "Quantity", "Price", "Category");
            System.out.println("-".repeat(borderWidth));
        } else {
            System.out.printf(" %-" + idWidth + "s | %-" + nameWidth + "s | %-" + quantityWidth + "s | %-" + priceWidth + "s%n", "ID", "Name", "Quantity", "Price");
            System.out.println("-".repeat(borderWidth));
        }
    }

    // Input reading methods
    private static String readNonEmptyString(String prompt, String fieldName){
        System.out.print(prompt);
        String input = sc.nextLine().trim();

        while(input.isEmpty()){
            System.out.println(fieldName + " cannot be empty.");
            System.out.print(prompt);
            input = sc.nextLine().trim();
        }
        return input;
    }

    private static String readName(){
        System.out.print("Enter item name: ");
        String name = sc.nextLine().trim();

        while(!validator.isValidName(name)){
            System.out.println("Name must contain only letters, numbers, and spaces.");
            System.out.print("Enter item name: ");
            name = sc.nextLine().trim();
        }
        return name;
    }

    private static String readCategory(){
        System.out.print("Enter item category (Clothing, Electronics, Entertainment): ");
        String category = sc.nextLine().trim();

        while(!validator.isValidCategory(category)){
            System.out.println("Invalid category. Please enter a valid category (Clothing, Electronics, Entertainment).");
            System.out.print("Enter item category (Clothing, Electronics, Entertainment): ");
            category = sc.nextLine().trim();
        }
        return validator.normalizeCategory(category);
    }

    private static String readId(String prompt){
        System.out.print(prompt);
        String id = sc.nextLine().trim();

        while(!validator.isValidId(id)){
            System.out.println("Invalid ID. Please enter exactly 6 letters and/or numbers.");
            System.out.print(prompt);
            id = sc.nextLine().trim();
        }
        return id;
    }
    
    private static String readUniqueId(){
        String id = readId("Enter item ID (6 uppercase letters/numbers): ");
        while (inventory.findItemById(id) != null) {
            System.out.println("Item with ID " + id + " already exists. Please enter a unique ID.");
            id = readId("Enter item ID (6 uppercase letters/numbers): ");
        }
        return id;
    }

    private static int readPositiveInt(String prompt, String fieldName) {
        System.out.print(prompt);
        String input = sc.nextLine().trim();
 
        while (!validator.isValidPositiveInteger(input)) {
            System.out.println(fieldName + " must be a positive whole number.");
            System.out.print(prompt);
            input = sc.nextLine().trim();
        }
        return Integer.parseInt(input);
    }
 
    private static int readNonNegativeInt(String prompt, String fieldName) {
        System.out.print(prompt);
        String input = sc.nextLine().trim();
 
        while (!validator.isValidNonNegativeInteger(input)) {
            System.out.println(fieldName + " must be a whole number that is zero or greater.");
            System.out.print(prompt);
            input = sc.nextLine().trim();
        }
        return Integer.parseInt(input);
    }
 
    private static double readPositiveDouble(String prompt, String fieldName) {
        System.out.print(prompt);
        String input = sc.nextLine().trim();
 
        while (!validator.isValidPositiveDouble(input)) {
            System.out.println(fieldName + " must be a positive number with at most two decimal places.");
            System.out.print(prompt);
            input = sc.nextLine().trim();
        }
        return Double.parseDouble(input);
    }

    private static int readMenuChoice(int low, int high){
        System.out.print("Enter your choice: ");
        String choice = sc.nextLine().trim();

        while (!validator.isValidPositiveInteger(choice) || Integer.parseInt(choice) < low || Integer.parseInt(choice) > high){
            System.out.println("Invalid choice. Enter a whole number from " + low + "-" + high + ".");
            System.out.print("Enter your choice: ");
            choice = sc.nextLine().trim();
        }
        return Integer.parseInt(choice);
    }
}
