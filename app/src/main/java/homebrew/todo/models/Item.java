package homebrew.todo.models;

/**
 * Created by Nishit on 7/30/16.
 */
public class Item {
    private int id;
    private String itemName;
    private String itemDate;

    public Item () {
        id = 0;
        itemName = "";
        itemDate = "";
    }

    public Item(int id, String itemName, String itemDate) {
        this.id = id;
        this.itemName = itemName;
        this.itemDate = itemDate;
    }

    public void set (int id, String itemName, String itemDate) {
        this.id = id;
        this.itemName = itemName;
        this.itemDate = itemDate;
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

    public int getId () {
        return id;
    }

    public String getItemName () {
        return itemName;
    }

    public String getItemDate () {
        return itemDate;
    }
}