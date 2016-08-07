package homebrew.todo.activities;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.EditText;

import homebrew.todo.R;

public class EditItemActivity extends AppCompatActivity {

    EditText atEditText;
    String clickItem;
    ArrayAdapter<String> aAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_item);
        setTitle("Edit Task");
        atEditText = (EditText) findViewById(R.id.atEditText);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
        clickItem = getIntent().getStringExtra("clItem");
        atEditText.setText(clickItem);
        atEditText.setSelection(clickItem.length());

    }

    public void saveItem(View view) {
        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(atEditText.getWindowToken(), 0);
        Intent data = new Intent();
        String newSt = atEditText.getText().toString();
        data.putExtra("newText", newSt);
        setResult(1,data);
        finish();
    }
}
