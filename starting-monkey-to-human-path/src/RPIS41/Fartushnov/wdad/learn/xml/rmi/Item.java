package RPIS41.Fartushnov.wdad.learn.xml.rmi;


import java.io.Serializable;

/**
 * Created by никита on 29.10.2016.
 */
public class Item implements Serializable{
    private final String name;
    private final int cost;

    public Item (String name, int cost) {
        this.name = name;
        this.cost = cost;
    }

    public String getName() {
        return name;
    }

    public int getCost() {
        return cost;
    }
}
