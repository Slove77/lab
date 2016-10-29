package RPIS41.Fartushnov.wdad.learn.xml.rmi;

import java.util.List;

/**
 * Created by никита on 29.10.2016.
 */
public class Order {
    private final Officiant officiant;
    private List<Item> items;

    public Order(Officiant officiant, List<Item> items) {
        this.officiant = officiant;
        this.items = items;
    }

    public Officiant getOfficiant() {
        return officiant;
    }

    public List<Item> getItems() {
        return items;
    }
}
