package com.gj.contacts_app_java;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    public void login(View view) {
        String editTextTextPersonName = ((EditText) findViewById(R.id.editTextTextPersonName)).getText().toString();
        String editTextTextPassword = ((EditText) findViewById(R.id.editTextTextPassword)).getText().toString();

        if(editTextTextPersonName.trim().length() > 0 && !editTextTextPersonName.isEmpty() &&
                editTextTextPassword.trim().length() > 0 && !editTextTextPassword.isEmpty()){
            if(editTextTextPersonName.equals("Gabriel") && editTextTextPassword.equals("secret")){
                Intent intent = new Intent(this, ListActivity.class);
                startActivity(intent);
            } else {
//                showWrongCredentialsDialog();
            }
        } else {
//            showEmptyFieldsDialog();
        }
    }

    public void signIn(View view) {
//        Log.i("text", "SIGN IN");

        String editTextTextPersonName = ((EditText) findViewById(R.id.editTextTextPersonName)).getText().toString();
        String editTextTextPassword = ((EditText) findViewById(R.id.editTextTextPassword)).getText().toString();

        if(editTextTextPersonName.trim().length() > 0 && !editTextTextPersonName.isEmpty() &&
                editTextTextPassword.trim().length() > 0 && !editTextTextPassword.isEmpty()){
//            Intent intent = new Intent(this, Gallery.class);
//            intent.putExtra("EXTRA_USERNAME", editTextTextPersonName);
//            startActivity(intent);
        } else {
//            showEmptyFieldsDialog();
        }
    }
}