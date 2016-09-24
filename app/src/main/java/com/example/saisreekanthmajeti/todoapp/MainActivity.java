package com.example.saisreekanthmajeti.todoapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ArrayList<String> todoItems;
    ArrayAdapter<String> aToDoAdapter;
    ListView lvItems;
    EditText etEditText;

    int editingNoteId = -1;


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==1) {
            //Serializable extra = getIntent().getSerializableExtra("editedActivity");
            if(resultCode == Activity.RESULT_OK) {
                String extra = data.getStringExtra("editedActivity");
                todoItems.remove(editingNoteId);
                todoItems.add(editingNoteId, extra.toString());
                aToDoAdapter.notifyDataSetChanged();
            }
            if(resultCode == Activity.RESULT_CANCELED ){
                todoItems.remove(editingNoteId);
                aToDoAdapter.notifyDataSetChanged();
            }
            writeItems();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        populateArrayItems();
        lvItems = (ListView) findViewById(R.id.lvItems);
        lvItems.setAdapter(aToDoAdapter);
        etEditText = (EditText) findViewById(R.id.etEditText);
        /*lvItems.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener(){
            @Override
            public boolean onItemLongClick(AdapterView<?> av, View v, int pos, long id)
            {
                todoItems.remove(pos);
                aToDoAdapter.notifyDataSetChanged();
                return true;
            }
        });*/
        lvItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent editItemIntent = new Intent (view.getContext() ,  EditActivity.class);
                String itemData = todoItems.get(position).toString();
                editItemIntent.putExtra("itemData", itemData);
                editingNoteId = position;
                startActivityForResult(editItemIntent, 1);
            }
        });

    }

    public void populateArrayItems() {
        todoItems = new ArrayList<String>();
        readItems();

        aToDoAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, todoItems);


    }

    public void onAddItem(View view) {
        aToDoAdapter.add(etEditText.getText().toString());
        etEditText.setText("");
        View edit_view = this.getCurrentFocus();
        if (edit_view != null) {
            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(edit_view.getWindowToken(), 0);
        }
        writeItems();
    }

    private void readItems(){
        File filesDir = getFilesDir();
        File file = new File(filesDir,"todo.txt");
        try{
            todoItems = new ArrayList<String> (FileUtils.readLines(file));
        }catch(IOException e){

        }
    }

    private void writeItems(){
        File filesDir = getFilesDir();
        File file = new File(filesDir,"todo.txt");
        try{
            FileUtils.writeLines(file, todoItems);
        }catch(IOException e){

        }
    }

}
