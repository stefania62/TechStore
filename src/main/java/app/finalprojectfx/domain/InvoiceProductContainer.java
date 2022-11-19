package app.finalprojectfx.domain;

import app.finalprojectfx.db.model.Item;

public class InvoiceProductContainer {
    private final Item item;

    private final int quantity;

    private final double price;

    public InvoiceProductContainer(Item item, int quantity, double price) {
        this.item = item;
        this.quantity = quantity;
        this.price = price;
    }

    public Item getItem() {
        return item;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getPrice() {
        return this.price;
    }
}
