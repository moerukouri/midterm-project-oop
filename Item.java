public class Item{
    private final String category;
    private final String id;
    private final String name;
    private int quantity;
    private double price;

    public Item(String category, String id, String name, int quantity, double price){
        this.category = category;
        this.id = id;
        this.name = name;
        this.quantity = quantity;
        this.price = price;
    }

    public String getCategory() {
        return category;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String toTableRow(int idWidth, int nameWidth, int quantityWidth, int priceWidth, int categoryWidth) {
        String format = " %-" + idWidth + "s | %-" + nameWidth + "s | %-" + quantityWidth + "s | %-" + priceWidth + "d | %-" + categoryWidth + "s";
        return String.format(format, id, name, quantity, price, category);
    }

    public String toTableRowByCategory(int idWidth, int nameWidth, int quantityWidth, int priceWidth) {
        String format = " %-" + idWidth + "s | %-" + nameWidth + "s | %-" + quantityWidth + "s | %-" + priceWidth + "d";
        return String.format(format, id, name, quantity, price);
    }
}