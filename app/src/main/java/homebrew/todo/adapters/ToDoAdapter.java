package homebrew.todo.adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

import homebrew.todo.models.Item;
import homebrew.todo.R;

/**
 * Created by Nishit on 8/1/16.
 */
public class ToDoAdapter extends ArrayAdapter<Item> {
    public ToDoAdapter(Context context, ArrayList<Item> items) {
        super(context, 0, items);
    }
    String [] month_array = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};
    int Red = 0xFFB71C1C;
    int Green = 0xFF4CAF50;
    int Yellow = 0xFFFFAB91;

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Item item = getItem(position);

        if(convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.to_do_adapter, parent, false);
        }
        TextView itemDate = (TextView) convertView.findViewById(R.id.itemDate);
        TextView itemMonth = (TextView) convertView.findViewById(R.id.itemMonth);
        TextView itemString = (TextView) convertView.findViewById(R.id.itemString);
        //ImageView circle = (ImageView) convertView.findViewById(R.id.numberCircle);
        String day = item.getItemDate().split("-")[1];
        int month = Integer.parseInt(item.getItemDate().split("-")[0]);
        itemDate.setText(day);
        itemMonth.setText(month_array[month].toString());
        itemString.setText(item.getItemName());
        switch(item.getItemPriority()) {
            case "High" :
                //circle.setBackgroundColor(Red);
                itemDate.setTextColor(Red);
                itemMonth.setTextColor(Red);
                itemString.setTextColor(Red);
                break;

            case "Medium" :
                //circle.setBackgroundColor(Green);
                itemDate.setTextColor(Green);
                itemMonth.setTextColor(Green);
                itemString.setTextColor(Green);
                break;

            case "Low" :
                //circle.setBackgroundColor(Yellow);
                itemDate.setTextColor(Yellow);
                itemMonth.setTextColor(Yellow);
                itemString.setTextColor(Yellow);
                break;

            default :
                break;
        }

        return convertView;
    }
}
