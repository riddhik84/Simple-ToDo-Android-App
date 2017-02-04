package com.riddhikakadia.codepathtodoapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class EditItemActivity extends AppCompatActivity {

    EditText itemEditText;
    Button editButton;

    int position = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_item);
        itemEditText = (EditText) findViewById(R.id.edit_item_text);
        editButton = (Button) findViewById(R.id.edit_item_button);

        Intent intent = getIntent();
        if (intent.getExtras() != null) {
            String itemValue = intent.getExtras().getString(MainActivity.ITEM_VALUE);
            position = intent.getExtras().getInt(MainActivity.ITEM_POSITION);

            itemEditText.setText(itemValue);
            itemEditText.setSelection(itemValue.length());
        }

        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newItem = itemEditText.getText().toString();
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.putExtra(MainActivity.ITEM_VALUE, newItem);
                intent.putExtra(MainActivity.ITEM_POSITION, position);
                setResult(MainActivity.RESULT_OK, intent);
                finish();
            }
        });
    }
}
