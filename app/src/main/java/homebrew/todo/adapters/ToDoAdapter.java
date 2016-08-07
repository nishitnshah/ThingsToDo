package homebrew.todo.adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

import homebrew.todo.models.Item;
import homebrew.todo.R;

/**
 * Created by Nishit on 8/1/16.
 */
public class ToDoAdapter extends ArrayAdapter<Item> {
    //int [] color_arry = {0xAF3F51B5, 0xAF2196F3, 0xAF4CAF50, 0xAFFFEB3B, 0xAFFF9800, 0xAFF44336};
    public ToDoAdapter(Context context, ArrayList<Item> items) {
        super(context, 0, items);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Item item = getItem(position);

        if(convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.to_do_adapter, parent, false);
        }
        //convertView.setBackgroundColor(color_arry[(position%6)]);
        //convertView.setAlpha(0.5f);
        //TextView itemId = (TextView) convertView.findViewById(R.id.itemId);
        TextView itemString = (TextView) convertView.findViewById(R.id.itemString);

        //itemId.setText(toString().valueOf(item.getId()));
        itemString.setText(item.getItemName());

        return convertView;
    }
}
