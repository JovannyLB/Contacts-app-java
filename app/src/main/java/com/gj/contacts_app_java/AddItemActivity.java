package com.gj.contacts_app_java;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class AddItemActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);

        //Set Spinner Options
        Spinner spinner = findViewById(R.id.contactTypeSpinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
    this,
            R.array.contact_type,
            android.R.layout.simple_spinner_item
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }

    public void cancel(View view) {
//        finish();
        finishActivity(0);
    }

    public void create(View view) {
//        finish();
        finishActivity(0);
    }
}