public abstract class Item{
    private final String id;
    private final String name;
    private int quantity;
    private double price;

    public Item(String id, String name, int quantity, double price){
        this.id = id;
        this.name = name;
        this.quantity = quantity;
        this.price = price;
    }

    public abstract String getCategory();

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
        String format = " %-" + idWidth + "s | %-" + nameWidth + "s | %,-" + quantityWidth + "d | P%," + priceWidth + ".2f | %-" + categoryWidth + "s";
        return String.format(format, id, name, quantity, price, getCategory());
    }

    public String toTableRowByCategory(int idWidth, int nameWidth, int quantityWidth, int priceWidth) {
        String format = " %-" + idWidth + "s | %-" + nameWidth + "s | %,-" + quantityWidth + "d | P%," + priceWidth + ".2f";
        return String.format(format, id, name, quantity, price);
    }

    @Override
    public String toString() {
        return "ID: " + id + " | Name: " + name + " | Quantity: " + String.format("%,d", quantity) + " | Price: " + String.format("P%,.2f", price) + " | Category: " + getCategory();
    }
}