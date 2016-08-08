package homebrew.todo.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import homebrew.todo.models.Item;

/**
 * Created by Nishit on 7/30/16.
 */
public class ThingsToDoDatabase extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "itemDatabase";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_LIST = "list";
    private static final String ID = "key";
    private static final String ITEM = "item";
    private static final String DATE = "date";
    private static ThingsToDoDatabase Instance;

    public static synchronized ThingsToDoDatabase getInstance(Context context) {
        if(Instance == null) {
            Instance = new ThingsToDoDatabase(context.getApplicationContext());
        }
        return Instance;
    }

    private ThingsToDoDatabase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_ITEM_DATABASE = "CREATE TABLE " + TABLE_LIST +
                "(" +
                ID + " INTEGER PRIMARY KEY," +
                ITEM + " TEXT," +
                DATE + " TEXT" +
                ")";
        db.execSQL(CREATE_ITEM_DATABASE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if(oldVersion != newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_LIST);
            onCreate(db);
        }
    }

    public long addItem(Item item) {
        SQLiteDatabase db = getWritableDatabase();
        db.beginTransaction();
        long id = -1;

        try {
            ContentValues values = new ContentValues();
            values.put(ITEM, item.getItemName());
            values.put(DATE, item.getItemDate());

            id = db.insertOrThrow(TABLE_LIST, null, values);
            db.setTransactionSuccessful();
        } catch (Exception e) {
            Log.d("addItem", "Error while trying to add item to database");
        } finally {
            db.endTransaction();
        }
        return id;
    }

    public void updateItem (Item item) {
        SQLiteDatabase db = getWritableDatabase();
        db.beginTransaction();

        try {
            ContentValues values = new ContentValues();
            values.put(ITEM, item.getItemName());
            values.put(DATE, item.getItemDate());
            db.update(TABLE_LIST, values, ID + " = ?", new String[] {Integer.toString(item.getId())});
            db.setTransactionSuccessful();
        }catch (Exception e) {
            Log.d("updateItem","Error while updating an item in database");
        } finally {
            db.endTransaction();
        }
    }

    public void deleteItem (Item item) {
        int id = item.getId();

        SQLiteDatabase db = getWritableDatabase();
        db.beginTransaction();

        try {
            db.delete(TABLE_LIST, ID + " = ?",new String[] {String.valueOf(id)});
            db.setTransactionSuccessful();
        } catch (Exception e) {
            Log.d("deleteItem","Error while deleting the itme");
        } finally{
            db.endTransaction();
        }
    }

    public void deleteAll () {
        SQLiteDatabase db = getWritableDatabase();
        db.beginTransaction();
        try {
            db.execSQL("DELETE FROM " + TABLE_LIST);
        }catch (Exception e) {
            Log.d("deleteAll", "Error while deleting all entries");
        } finally {
            db.endTransaction();
        }
    }

    public List<String> getAllItemNames() {
        List<String> readItems = new ArrayList<>();
        String readQuery = String.format("SELECT * FROM %s", TABLE_LIST);
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(readQuery, null);

        try {
            if(cursor.moveToFirst()) {
                do {
                    Item item = new Item();
                    int id = cursor.getInt(0); //gets the unique id
                    String text = cursor.getString(cursor.getColumnIndex(ITEM));
                    item.set(id,text,"");
                    readItems.add(item.getItemName());
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.d("getAllItems", "Error while reading all items from database");
        } finally{
            if(cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
        }
        return readItems;
    }

    public List<Item> getAllItems() {
        List<Item> readItems = new ArrayList<>();
        String readQuery = String.format("SELECT * FROM %s", TABLE_LIST);
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(readQuery, null);

        try {
            if(cursor.moveToFirst()) {
                do {
                    Item item = new Item();
                    int id = cursor.getInt(0); //gets the unique id
                    String text = cursor.getString(cursor.getColumnIndex(ITEM));
                    String date = cursor.getString(cursor.getColumnIndex(DATE));
                    item.set(id,text,date);
                    readItems.add(item);
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.d("getAllItems", "Error while reading all items from database");
        } finally{
            if(cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
        }
        return readItems;
    }

    public int getIdFromName(String name) {
        int retId = 0;
        String query = String.format("SELECT %s FROM %s WHERE %s = ?",
                ID, TABLE_LIST, ITEM);

        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(query, new String [] {String.valueOf(name)});

        try {
            if(cursor.moveToFirst()) {
                retId = cursor.getInt(0);
            }
        } catch (Exception e) {
            Log.d("getIdFromName", "Error while locating id in database");
        } finally {
            if(cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
        }
        return retId;
    }
}
