package homebrew.todo.activities;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;

import homebrew.todo.R;
import homebrew.todo.adapters.ToDoAdapter;
import homebrew.todo.database.ThingsToDoDatabase;
import homebrew.todo.fragments.AddDialog;
import homebrew.todo.fragments.EditDialog;
import homebrew.todo.models.Item;

public class MainActivity extends AppCompatActivity implements EditDialog.EditDialogListener, AddDialog.AddDialogListener {

    ArrayList<Item> todoItems;
    ToDoAdapter aToDoAdapter;
    ListView lvItems;
    int clkPosition;
    Item mItem;

    ThingsToDoDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("Things To Do");
        mItem = new Item();
        database  = ThingsToDoDatabase.getInstance(this);

        populateArrayItems();
        lvItems = (ListView) findViewById(R.id.lvItems);
        lvItems.setAdapter(aToDoAdapter);
        lvItems.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id){
                Item item = new Item();
                item = todoItems.get(position);
                todoItems.remove(position);
                aToDoAdapter.notifyDataSetChanged();
                database.deleteItem(item);
                return false;
            }
        });
        lvItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                clkPosition = position;
                mItem.setId(todoItems.get(position).getId());
                showEditDialog(position);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.title_bar_item, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        showAddDialog();
        return true;
    }

    public void populateArrayItems() {
        readDatabase();
        aToDoAdapter = new ToDoAdapter(this, todoItems);
    }

    private void readDatabase () {
        todoItems = new ArrayList<Item>(database.getAllItems());
    }

    private long writeDatabase (Item item) {
        long id = database.addItem(item);
        return id;
    }

    private void updateDatabase () {
        database.updateItem(mItem);
    }

    private void showEditDialog (int position) {
        EditDialog dialog = EditDialog.newInstance(todoItems.get(position));
        dialog.show(getSupportFragmentManager(), "editDialog");
    }

    private void showAddDialog () {
        Item item = new Item();
        AddDialog dialog = AddDialog.newInstance(item);
        dialog.show(getSupportFragmentManager(), "editDialog");
    }

    public void onFinishEditDialog (Item item) {
        item.setId(mItem.getId());
        mItem.setItemName(item.getItemName());
        mItem.setItemDate(item.getItemDate());
        mItem.setItemPriority(item.getItemPriority());
        todoItems.set(clkPosition, item);
        aToDoAdapter.notifyDataSetInvalidated();
        updateDatabase();
    }

    public void onFinishAddDialog (Item item) {
        long id = writeDatabase(item);
        item.setId((int)id);
        todoItems.add(item);
        aToDoAdapter.notifyDataSetChanged();
    }
}
