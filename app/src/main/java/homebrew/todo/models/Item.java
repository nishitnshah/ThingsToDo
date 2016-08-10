package homebrew.todo.models;

/**
 * Created by Nishit on 7/30/16.
 */
public class Item {
    private int id;
    private String itemName;
    private String itemDate;
    private String itemPriority;

    public Item () {
        id = 0;
        itemName = "";
        itemDate = "";
        itemPriority = "";
    }

    public Item(int id, String itemName, String itemDate, String itemPriority) {
        this.id = id;
        this.itemName = itemName;
        this.itemDate = itemDate;
        this.itemPriority = itemPriority;
    }

    public void set (int id, String itemName, String itemDate, String itemPriority) {
        this.id = id;
        this.itemName = itemName;
        this.itemDate = itemDate;
        this.itemPriority = itemPriority;
    }

    public void setId (int id) {
        this.id = id;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public void setItemDate(String itemDate) {
        this.itemDate = itemDate;
    }

    public void setItemPriority (String itemPriority) {
        this.itemPriority = itemPriority;
    }

    public int getId () {
        return id;
    }

    public String getItemName () {
        return itemName;
    }

    public String getItemDate () {
        return itemDate;
    }

    public String getItemPriority () {
        return itemPriority;
    }
}