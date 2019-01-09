public class Item {
    private long uniqueID;
    private String type;
    private int quantity;
    private String manufacturer;
    private double price;
    private String note;

    public Item(long uniqueID,
                String type,
                int quantity,
                String manufacturer,
                double price,
                String note) {
        this(type, quantity, manufacturer, price, note);
        this.uniqueID = uniqueID;
    }

    public Item(String type,
                int quantity,
                String manufacturer,
                double price,
                String note) {
        this.type = type;
        this.quantity = quantity;
        this.manufacturer = manufacturer;
        this.price = price;
        this.note = note;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public long getUniqueID() {
        return uniqueID;
    }
    public String getType() {
        return type;
    }
    public int getQuantity() {
        return quantity;
    }
    public String getManufacturer() {
        return manufacturer;
    }
    public double getPrice() {
        return price;
    }
    public String getNote() {
        return note;
    }

    public String toString() {
        return uniqueID + "";
    }
}
