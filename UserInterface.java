import java.util.List;
import java.util.Scanner;

public class UserInterface {
    private static final Scanner sc = new Scanner(System.in);
    private static final Inventory inventory = new Inventory();
    private static final Validator validator = new Validator();

    // Menu handler
    public void printMenu() {
        System.out.println("Inventory Management System");
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
        String input = readNonEmptyString("Enter your choice: ", "Menu Choice");
        return validator.parseMenuChoice(input);
    }

    // Item management methods

    // 1 Add Item
    public void addItem() {
        String category = readCategory("Enter item category (Clothing, Electronics, Entertainment): ");
        if (category == null) {
            System.out.println("Category " + category + " does not exist!");
            return;
        }
        String id = readUniqueId("Enter item ID: ");
        String name = readNonEmptyString("Enter item name: ", "Name");
        int quantity = readPositiveInt("Enter item quantity: ", "Quantity");
        double price = readPositiveDouble("Enter item price: ", "Price");

        Item newItem = new Item(category, id, name, quantity, price);
        inventory.addItem(newItem);
        System.out.println("Item added successfully!");
    }

    // 2 Update Item
    public void updateItem() {
        String id = readNonEmptyString("Enter item ID to update: ", "ID");
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
        int newQuantity = readPositiveInt("Enter new quantity: ", "Quantity");
        try {
            inventory.updateQuantity(id, newQuantity);
            System.out.println("Quantity of item " + item.getName() + " is updated from " + oldQuantity + " to " + newQuantity + ".");
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    public void updatePrice(String id, Item item) {
        double oldPrice = item.getPrice();
        double newPrice = readPositiveDouble("Enter new price: ", "Price");
        try {
            inventory.updatePrice(id, newPrice);
            System.out.println("Price of item " + item.getName() + " is updated from " + oldPrice + " to " + newPrice + ".");
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    // 3 Remove Item
    public void removeItem() {
        String id = readNonEmptyString("Enter item ID to remove: ", "ID");
        try {
            Item removed = inventory.removeItem(id);
            System.out.println("Item " + removed.getName() + " with ID " + id + " has been removed from the inventory.");
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    // 4 Display Items by Category
    public void displayItemsByCategory() {
        String category = readCategory("Enter category to display (Clothing, Electronics, Entertainment): ");
        if (category == null) {
            System.out.println("Category " + category + " does not exist!");
            return;
        }
        List<Item> itemsByCategory = inventory.getItemsByCategory(category);
        if (itemsByCategory.isEmpty()) {
            System.out.println("No items found in " + category + " category!");
            return;
        }

        printItems(itemsByCategory, false);
    }

    // 5 Display All Items
    public void displayAllItems() {
        List<Item> allItems = inventory.getAllItems();
        if (allItems.isEmpty()) {
            System.out.println("No items in the inventory!");
            return;
        }

        printItems(allItems, true);
    }

    // 6 Search Item
    public void searchItem() {
        String id = readNonEmptyString("Enter item ID to search: ", "ID");
        Item item = inventory.findItemById(id);
        if (item == null) {
            System.out.println("Item not found!");
        } else {
            System.out.println(item);
        }
    }

    // 7 Sort Items
    public void sortItems() {
        if (inventory.getAllItems().isEmpty()) {
            System.out.println("No items in the inventory to sort!");
            return;
        }

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
        if (includeCategory) {
            System.out.printf(" %-" + idWidth + "s | %-" + nameWidth + "s | %-" + quantityWidth + "s | %-" + priceWidth + "s | %-" + categoryWidth + "s%n", "ID", "Name", "Quantity", "Price", "Category");
            System.out.println("-------------------------------------------------------------------------------------");
        } else {
            System.out.printf(" %-" + idWidth + "s | %-" + nameWidth + "s | %-" + quantityWidth + "s | %-" + priceWidth + "s%n", "ID", "Name", "Quantity", "Price");
            System.out.println("---------------------------------------------------------------");
        }
    }

    // Input reading methods
    private static String readNonEmptyString(String prompt, String fieldName) {
        String input;
        boolean valid = false;
        do {
            System.out.print(prompt);
            input = sc.nextLine().trim();
            try {
                validator.validateNonEmpty(input, fieldName);
                valid = true;
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        } while (!valid);
        return input;
    }

    private static String readCategory(String prompt) {
        String category;
        boolean valid = false;
        do {
            System.out.print(prompt);
            category = sc.nextLine().trim();
            if (validator.isValidCategory(category)) {
                valid = true;
            } else {
                System.out.println("Invalid category. Please enter a valid category (Clothing, Electronics, Entertainment).");
            }
        } while (!valid);
        return validator.normalizeCategory(category);
    }

    private static String readUniqueId(String prompt) {
        String id;
        boolean valid = false;
        do {
            id = readNonEmptyString(prompt, "ID");
            if (inventory.findItemById(id) != null) {
                System.out.println("Item with ID " + id + " already exists. Please enter a unique ID.");
            } else {
                valid = true;
            }
        } while (!valid);
        return id;
    }

    private static int readPositiveInt(String prompt, String fieldName) {
        int value = 0;
        boolean valid = false;
        do {
            System.out.print(prompt);
            String input = sc.nextLine().trim();
            try {
                value = validator.parseInt(input, fieldName);
                validator.validatePositiveInt(value, fieldName);
                valid = true;
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        } while (!valid);
        return value;
    }

    private static double readPositiveDouble(String prompt, String fieldName) {
        double value = 0;
        boolean valid = false;
        do {
            System.out.print(prompt);
            String input = sc.nextLine().trim();
            try {
                value = validator.parseDouble(input, fieldName);
                validator.validatePositiveDouble(value, fieldName);
                valid = true;
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        } while (!valid);
        return value;
    }
}
