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
}