package homebrew.todo;

/**
 * Created by Nishit on 7/30/16.
 */
public class Item {
    private int id;
    private String itemName;

    public Item () {

    }

    public Item(int id, String itemName) {
        this.id = id;
        this.itemName = itemName;
    }

    public void set (int id, String itemName) {
        this.id = id;
        this.itemName = itemName;
    }

    public void sedId (int id) {
        this.id = id;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public int getId () {
        return id;
    }

    public String getItemName () {
        return itemName;
    }
}