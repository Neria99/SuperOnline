package com.example.shop;
import java.util.Comparator;

public class PriceComparator  implements Comparator<Item>{
    private boolean ascendingOrder;

    public PriceComparator(boolean ascendingOrder) {
        this.ascendingOrder = ascendingOrder;
    }

    @Override
    public int compare(Item item1, Item item2) {
        if (ascendingOrder) {
            return Double.compare(item1.getPrice(), item2.getPrice());
        } else {
            return Double.compare(item2.getPrice(), item1.getPrice());
        }
    }
}
