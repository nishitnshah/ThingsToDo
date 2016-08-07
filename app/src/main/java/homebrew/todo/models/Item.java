package homebrew.todo.models;

/**
 * Created by Nishit on 7/30/16.
 */
public class Item {
    private int id;
    private String itemName;

    public Item () {
        id = 0;
        itemName = "";
    }

    public Item(int id, String itemName) {
        this.id = id;
        this.itemName = itemName;
    }

    public void set (int id, String itemName) {
        this.id = id;
        this.itemName = itemName;
    }

    public void setId (int id) {
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