package homebrew.todo.activities;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
import homebrew.todo.fragments.EditDialog;
import homebrew.todo.models.Item;

public class MainActivity extends AppCompatActivity implements EditDialog.EditDialogListener {

    ArrayList<Item> todoItems;
    ToDoAdapter aToDoAdapter;
    ListView lvItems;
    EditText etEditText;
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
        etEditText = (EditText) findViewById(R.id.etEditText);
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
                //showEditActivity(position);
                showEditDialog(position);
            }
        });
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

    public void onAddItem(View view) {
        String addString = etEditText.getText().toString();
        long id = writeDatabase(addString);
        Item aItem = new Item((int) id, addString);
        todoItems.add(aItem);
        aToDoAdapter.notifyDataSetChanged();
        etEditText.setText("");
        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(etEditText.getWindowToken(), 0);

    }

    private void showEditActivity(int position) {
        String clickItem = todoItems.get(position).getItemName();
        Intent i = new Intent(MainActivity.this, EditItemActivity.class);
        i.putExtra("clItem", clickItem);
        startActivityForResult(i, 1);
    }

    protected void onActivityResult(int request_code, int result_code, Intent data) {
        if((request_code == 1) && (result_code == 1)) {
            mString = data.getExtras().getString("newText").toString();
            Item tItem = new Item(mId,mString);
            todoItems.set(clkPosition, tItem);
            aToDoAdapter.notifyDataSetChanged();
            updateDatabase();
        }
    }

    private void showEditDialog (int position) {
        EditDialog dialog = EditDialog.newInstance(todoItems.get(position));
        dialog.show(getSupportFragmentManager(), "editDialog");
    }

    public void onFinishEditDialog (Item item) {
        item.setId(mId);
        todoItems.set(clkPosition, item);
        mString = item.getItemName();
        aToDoAdapter.notifyDataSetInvalidated();
        updateDatabase();
    }
}
