package com.gj.contacts_app_java;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class AddItemActivity extends AppCompatActivity {

    private SharedPreferences accountData;

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

        accountData = getSharedPreferences("accountData", MODE_PRIVATE);
    }

    public void cancel(View view) {
        finish();
    }

    public void create(View view) {
        //Generate new Contact Json Object
        EditText nameEditText = findViewById(R.id.nameEditText);
        EditText addressEditText = findViewById(R.id.addressEditText);
        EditText phoneEditText = findViewById(R.id.phoneEditText);
        Spinner contactTypeSpinner = findViewById(R.id.contactTypeSpinner);

        if (nameEditText.getText().toString().length() != 0 || addressEditText.getText().toString().length() != 0 || phoneEditText.getText().toString().length() != 0 || ((String) contactTypeSpinner.getSelectedItem()).length() != 0) {
            Contact newContact = new Contact(
                    nameEditText.getText().toString(),
                    phoneEditText.getText().toString(),
                    addressEditText.getText().toString(),
                    ((String) contactTypeSpinner.getSelectedItem())
            );

            AddNewContact(newContact);
        }

        LoadNextActivity();
    }

    private void LoadNextActivity() {
        Intent intent = new Intent(this, ListActivity.class);
        startActivityForResult(intent, 0);
    }

    private void AddNewContact(Contact contact) {
        String fileName = "storage.json";
        String currentUser = accountData.getString("currentUser", "");

        Log.i("getPackageCodePath", getApplicationContext().getPackageCodePath());
        Log.i("getPackageResourcePath", getApplicationContext().getPackageResourcePath());
        Log.i("toString", getApplicationContext().toString());

        if (IsFilePresent(getApplicationContext(), fileName)) {
            //Possui arquivo
            String currentJsonString = Read(getApplicationContext(), fileName);
            Log.i("FILE JSON STRING", currentJsonString);

            try {
                JSONObject contactsObject = new JSONObject(currentJsonString);
                JSONObject contactObject = new JSONObject();
                JSONArray contactsArray;

                //Mount contact
                contactObject.put("name", contact.getName());
                contactObject.put("address", contact.getAddress());
                contactObject.put("phone", contact.getPhone());
                contactObject.put("type", contact.getType());

                if (contactsObject.has(currentUser)) {
                    //Adicionar objeto contato novo
                    contactsArray = contactsObject.getJSONArray(currentUser);
                    contactsArray.put(contactObject);

                    //Clear last data
                    contactsObject.remove(currentUser);
                } else {
                    contactsArray = new JSONArray();
                    //Mount Array
                    contactsArray.put(contactObject);
                }

                //Criar chave do usuario
                contactsObject.put(currentUser, contactsArray);

                getApplicationContext().deleteFile(fileName);

                Create(getApplicationContext(), fileName, contactsObject.toString());
            } catch (Exception e) {
                Log.e("ERRO 1", e.getMessage());
            }
        } else {
            //Criar arquivo
            JSONObject contactsObject = new JSONObject();
            JSONArray arrayUser = new JSONArray();
            JSONObject contactObject = new JSONObject();

            try {
                //Mount contact
                contactObject.put("name", contact.getName());
                contactObject.put("address", contact.getAddress());
                contactObject.put("phone", contact.getPhone());
                contactObject.put("type", contact.getType());
                //Mount Array
                arrayUser.put(contactObject);
                //Mount contacts
                contactsObject.put(currentUser, arrayUser);

                //Create File
                Create(getApplicationContext(), fileName, contactsObject.toString());
            } catch (Exception e) {
                Log.e("ERRO 2", e.getMessage());
            }
        }
    }

    private String Read(Context context, String fileName) {
        try {
            FileInputStream fis = context.openFileInput(fileName);
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader bufferedReader = new BufferedReader(isr);
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                sb.append(line);
            }
            return sb.toString();
        } catch (FileNotFoundException fileNotFound) {
            return null;
        } catch (IOException ioException) {
            return null;
        }
    }

    private boolean Create(Context context, String fileName, String jsonString) {
        try {
            FileOutputStream fos = context.openFileOutput(fileName, Context.MODE_PRIVATE);
            if (jsonString != null) {
                fos.write(jsonString.getBytes());
            }
            fos.close();
            return true;
        } catch (FileNotFoundException fileNotFound) {
            return false;
        } catch (IOException ioException) {
            return false;
        }
    }

    public boolean IsFilePresent(Context context, String fileName) {
        String path = context.getFilesDir().getAbsolutePath() + "/" + fileName;
        File file = new File(path);
        return file.exists();
    }
}