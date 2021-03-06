package RPIS41.Fartushnov.wdad.learn.xml.rmi;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by никита on 29.10.2016.
 */
public class Order implements Serializable {
    private final Officiant officiant;
    private List<Item> items;

    public Order(Officiant officiant, List<Item> items) {
        this.officiant = officiant;
        this.items = items;
    }

    public Order(Officiant officiant){
        this(officiant, new LinkedList<Item>());
    }

    public Officiant getOfficiant() {
        return officiant;
    }

    public List<Item> getItems() {
        return items;
    }

    public void addItem(Item item){items.add(item);}
    public Item getItem(int index){return items.get(index);}
    public int getCountItems(){return items.size();}
}
