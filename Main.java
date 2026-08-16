public class Main {
    public static void main(String[] args) {
        boolean running = true;
        System.out.println("Welcome to the Inventory Management System!");
        UserInterface ui = new UserInterface();
        do {
            ui.printMenu();
            int choice = ui.getMenuChoice();
            switch (choice) {
            case 1 -> ui.addItem();
            case 2 -> ui.updateItem();
            case 3 -> ui.removeItem();
            case 4 -> ui.displayItemsByCategory();
            case 5 -> ui.displayAllItems();
            case 6 -> ui.searchItem();
            case 7 -> ui.sortItems();
            case 8 -> ui.displayLowStockItems();
            case 9 -> {
                System.out.println("Exiting the program.");
                running = false;
            }
            default -> System.out.println("Invalid choice. Please try again.");
        }
        } while (running);
    }
}