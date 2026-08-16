public class Item{
    private String category;
    private String id;
    private String name;
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

    public String toTableRow(int categoryWidth, int idWidth, int nameWidth, int quantityWidth, int priceWidth) {
        String format = " %-" + categoryWidth + "s | %-" + idWidth + "s | %-" + nameWidth + "s | %-" + quantityWidth + "d | %-" + priceWidth + ".2f";
        return String.format(format, category, id, name, quantity, price);
    }
}