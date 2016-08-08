package homebrew.todo.fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.DatePicker;
import android.widget.EditText;

import homebrew.todo.R;
import homebrew.todo.models.Item;

/**
 * Created by Nishit on 8/6/16.
 */
public class EditDialog extends DialogFragment {
    private EditText mEditText;
    private DatePicker mItemDate;

    public EditDialog () {

    }

    public static EditDialog newInstance(Item item) {
        EditDialog frag = new EditDialog();
        Bundle args = new Bundle();
        args.putInt("id", item.getId());
        args.putString("text", item.getItemName());
        args.putString("date",item.getItemDate());
        frag.setArguments(args);
        return frag;
    }

    public interface EditDialogListener {
        void onFinishEditDialog(Item item);
    }

    @Override
    public Dialog onCreateDialog(Bundle SavedInstance) {
        String text = getArguments().getString("text");
        String date = getArguments().getString("date");
        String mdy[]  = date.split("-");
        int month = Integer.parseInt(mdy[0]);
        int day = Integer.parseInt(mdy[1]);
        int year = Integer.parseInt(mdy[2]);
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.item_edit_dialog, null);
        mEditText = (EditText) view.findViewById(R.id.diaEditText);
        mItemDate = (DatePicker) view.findViewById(R.id.diaItemDate);
        mItemDate.updateDate(year,month,day);
        mEditText.setText(text);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Edit Item");
        builder.setView(view);
        mEditText.requestFocus();
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                EditDialogListener listener = (EditDialogListener) getActivity();
                String itemDate = mItemDate.getMonth() + "-" + mItemDate.getDayOfMonth() + "-" + mItemDate.getYear();
                Item item = new Item(getArguments().getInt("id"), mEditText.getText().toString(), itemDate);
                listener.onFinishEditDialog(item);
                InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(mEditText.getWindowToken(), 0);
            }

        });
        builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        return builder.create();
    }
}
