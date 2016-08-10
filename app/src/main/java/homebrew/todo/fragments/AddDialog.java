package homebrew.todo.fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

import homebrew.todo.R;
import homebrew.todo.models.Item;

/**
 * Created by Nishit on 8/6/16.
 */
public class AddDialog extends DialogFragment {
    private EditText mEditText;
    private DatePicker mItemDate;
    private Spinner mPriority;
    private String [] mPriorityType;

    public AddDialog() {

    }

    public static AddDialog newInstance(Item item) {
        AddDialog frag = new AddDialog();
        Bundle args = new Bundle();
        args.putInt("id", item.getId());
        args.putString("text", item.getItemName());
        args.putString("text", item.getItemPriority());
        frag.setArguments(args);
        return frag;
    }

    public interface AddDialogListener {
        void onFinishAddDialog(Item item);
    }

    @Override
    public Dialog onCreateDialog(Bundle SavedInstance) {
        String text = getArguments().getString("text");
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.item_edit_dialog, null);

        mEditText = (EditText) view.findViewById(R.id.diaEditText);
        mEditText.setText(text);

        mItemDate = (DatePicker) view.findViewById(R.id.diaItemDate);

        mPriority = (Spinner) view.findViewById(R.id.diaPriority);
        mPriorityType = getResources().getStringArray(R.array.priority_type);
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, mPriorityType);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mPriority.setAdapter(spinnerAdapter);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Add New Item");
        builder.setView(view);
        mEditText.requestFocus();
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                AddDialogListener listener = (AddDialogListener) getActivity();
                String itemDate = mItemDate.getMonth() + "-" + mItemDate.getDayOfMonth() + "-" + mItemDate.getYear();
                Item item = new Item(getArguments().getInt("id"), mEditText.getText().toString(), itemDate, mPriority.getSelectedItem().toString());
                listener.onFinishAddDialog(item);
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
