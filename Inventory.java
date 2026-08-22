import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Inventory {
    private final List<Item> items;
    private static final int THRESHOLD = 5;

    public Inventory() {
        items = new ArrayList<>();
    }

    public Item findItemById(String id) {
        for (Item item : items) {
            if (item.getId().equalsIgnoreCase(id)) {
                return item;
            }
        }
        return null;
    }

    public void addItem(Item item) {
        items.add(item);
    }

    public Item removeItem(String id) {
        Item item = findItemById(id);
        if (item == null) {
            throw new IllegalArgumentException("Item not found!");
        }
        items.remove(item);
        return item;
    }

    public void updateQuantity(String id, int newQuantity) {
        Item item = findItemById(id);
        if (item == null) {
            throw new IllegalArgumentException("Item not found!");
        }
        item.setQuantity(newQuantity);
    }

    public void updatePrice(String id, double newPrice) {
        Item item = findItemById(id);
        if (item == null) {
            throw new IllegalArgumentException("Item not found!");
        }
        item.setPrice(newPrice);
    }

    public List<Item> getItemsByCategory(String category) {
        List<Item> itemsByCategory = new ArrayList<>();
        for (Item item : items) {
            if (item.getCategory().equalsIgnoreCase(category)) {
                itemsByCategory.add(item);
            }
        }
        return itemsByCategory;
    }

    public List<Item> getAllItems() {
        return new ArrayList<>(items);
    }

    public List<Item> getLowStockItems() {
        List<Item> lowStockItems = new ArrayList<>();
        for (Item item : items) {
            if (item.getQuantity() < THRESHOLD) {
                lowStockItems.add(item);
            }
        }
        return lowStockItems;
    }

    public List<Item> getSortedItems(String sortBy, String order) {
        List<Item> sortedItems = new ArrayList<>(items);
        
        Comparator<Item> comparator;

        switch (sortBy.toLowerCase()) {
            case "quantity" -> comparator = Comparator.comparingInt(Item::getQuantity);
            case "price" -> comparator = Comparator.comparingDouble(Item::getPrice);
            default -> throw new IllegalArgumentException("Invalid sortBy parameter: " + sortBy);
        }

        if (order.equalsIgnoreCase("descending")) {
            comparator = comparator.reversed();
        }   

        sortedItems.sort(comparator);
        return sortedItems;
    }

    public boolean isEmpty() {
        return items.isEmpty();
    }
}
