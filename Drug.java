package Pharmacy;

public class Drug {
    private String name;
    private String id;
    private double price;
    private String category;
    private int availableQuantity;

    public Drug(String name, String id, double price, String category, int availableQuantity) {
        this.name = name;
        this.id = id;
        this.price = price;
        this.category = category;
        this.availableQuantity = availableQuantity;}

   
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public int getAvailableQuantity() { return availableQuantity; }
    public void setAvailableQuantity(int availableQuantity) { this.available }

   
    @Override
    public String toString() {
        return "Name: " + name + ", ID: " + id + ", Price: $" + price + ", Category: " + category
                + ", Available Quantity: " + availableQuantity;
    }
}
