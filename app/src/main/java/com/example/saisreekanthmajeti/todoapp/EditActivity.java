package com.example.saisreekanthmajeti.todoapp;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import java.io.Serializable;

public class EditActivity extends AppCompatActivity {

    EditText etEditItem;
    Button btnSaveItem;

    boolean isInEditMode = true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        etEditItem = (EditText) findViewById(R.id.etEditItem);
        btnSaveItem = (Button) findViewById(R.id.btnSaveItem);

        Serializable extra = getIntent().getSerializableExtra("itemData");
        if(extra!=null){
            etEditItem.setText(extra.toString());

            isInEditMode = false;
            etEditItem.setEnabled(false);
            btnSaveItem.setText("Edit");
        }


    }


    public void onEditClick(View view) {
        if(isInEditMode){

            View edit_view = this.getCurrentFocus();
            if (edit_view != null) {
                InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(edit_view.getWindowToken(), 0);
            }

            Intent returnIntent = new Intent();
            String editedActivityData = etEditItem.getText().toString();
            returnIntent.putExtra("editedActivity", editedActivityData);
            setResult(RESULT_OK, returnIntent);
            finish();
        }
        else{
            System.out.println("changing to edit mode");
            isInEditMode = true;
            btnSaveItem.setText("Save");
            etEditItem.setEnabled(true);
        }
    }

    public void onDeleteClick(View view) {
        Intent returnIntent = new Intent();
        //String editedActivityData = etEditItem.getText().toString();
        //returnIntent.putExtra("editedActivity", editedActivityData);
        setResult(RESULT_CANCELED, returnIntent);
        finish();
    }
}
