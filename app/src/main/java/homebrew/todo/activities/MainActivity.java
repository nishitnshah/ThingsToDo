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
    int mId;
    String mString;

    ThingsToDoDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("Things To Do");

        database  = ThingsToDoDatabase.getInstance(this);

        populateArrayItems();
        lvItems = (ListView) findViewById(R.id.lvItems);
        lvItems.setAdapter(aToDoAdapter);
        lvItems.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id){
                Item item = new Item(database.getIdFromName(todoItems.get(position).getItemName()),todoItems.get(position).getItemName());
                todoItems.remove(position);
                aToDoAdapter.notifyDataSetChanged();
                database.deleteItem(item);
                return false;
            }
        });
        lvItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                clkPosition = position;
                mId = todoItems.get(position).getId();//database.getIdFromName(clickItem);
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

    private long writeDatabase (String addString) {
        Item tItem = new Item (0,addString);
        long id = database.addItem(tItem);
        return id;
    }

    private void updateDatabase () {
        Item item = new Item(mId, mString);
        database.updateItem(item);
    }

    private void showEditActivity(int position) {
        String clickItem = todoItems.get(position).getItemName();
        Intent i = new Intent(MainActivity.this, EditItemActivity.class);
        i.putExtra("clItem", clickItem);
        startActivityForResult(i, 1);
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
        item.setId(mId);
        todoItems.set(clkPosition, item);
        mString = item.getItemName();
        aToDoAdapter.notifyDataSetInvalidated();
        updateDatabase();
    }

    public void onFinishAddDialog (Item item) {
        String addString = item.getItemName();
        long id = writeDatabase(addString);
        item.setId((int)id);
        todoItems.add(item);
        aToDoAdapter.notifyDataSetChanged();
    }
}
