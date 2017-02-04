package com.riddhikakadia.codepathtodoapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    public static final String ITEM_VALUE = "com.rk.codepathtodoapp.ITEM_VALUE";
    public static final String ITEM_POSITION = "com.rk.codepathtodoapp.ITEM_POSITION";
    public static final int EDIT_ITEM_REQUEST_CODE = 100;


    ArrayList<String> listItems;
    ArrayAdapter<String> arrayAdapter;

    ListView itemsListView;
    EditText itemEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        itemsListView = (ListView) findViewById(R.id.items_listview);
        itemEditText = (EditText) findViewById(R.id.hint_item_edittext);

        //listItems = new ArrayList<>();
        readItems();

        arrayAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_expandable_list_item_1,
                listItems);
        itemsListView.setAdapter(arrayAdapter);

        setupListViewListener();
    }

    public void addItem(View view) {
        String item = itemEditText.getText().toString();
        arrayAdapter.add(item);
        itemEditText.setText("");
        writeItems();
    }

    private void setupListViewListener() {
        itemsListView.setOnItemLongClickListener
                (new AdapterView.OnItemLongClickListener() {
                     @Override
                     public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                         listItems.remove(position);
                         arrayAdapter.notifyDataSetChanged();
                         writeItems();
                         return true;
                     }
                 }
                );

        itemsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), EditItemActivity.class);
                intent.putExtra(ITEM_VALUE, listItems.get(position));
                intent.putExtra(ITEM_POSITION, position);
                startActivityForResult(intent, EDIT_ITEM_REQUEST_CODE);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == EDIT_ITEM_REQUEST_CODE && resultCode == RESULT_OK) {

            if (data.getExtras() != null) {
                String updatedItem = data.getStringExtra(ITEM_VALUE);
                int pos = data.getIntExtra(ITEM_POSITION, 0);
                listItems.set(pos, updatedItem);
                arrayAdapter.notifyDataSetChanged();
                writeItems();
            }
        }
    }

    private void readItems() {
        File filesDir = getFilesDir();
        File todoFile = new File(filesDir, "todo.txt");
        try {
            listItems = new ArrayList<String>(FileUtils.readLines(todoFile));
        } catch (IOException ioe) {
            listItems = new ArrayList<String>();
        }
    }

    private void writeItems() {
        File filesDir = getFilesDir();
        File todoFile = new File(filesDir, "todo.txt");
        try {
            FileUtils.writeLines(todoFile, listItems);
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }
}
