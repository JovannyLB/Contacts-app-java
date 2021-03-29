package com.gj.contacts_app_java;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ListActivity extends AppCompatActivity {

    private ListView listView;
    private SharedPreferences accountData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        accountData = getSharedPreferences("accountData", MODE_PRIVATE);

        //Set List View Content
        MountListView();
    }

    public void addItem(View view) {
        Intent intent = new Intent(this, AddItemActivity.class);
        startActivity(intent);
//        startActivity(intent);
    }

    private void MountListView() {
        String fileName = "storage.json";
        String currentUser = accountData.getString("currentUser", "");

        ArrayList<Contact> contactArrayList = new ArrayList<>();

        if (IsFilePresent(getApplicationContext(), fileName)) {
            //Possui arquivo
            String currentJsonString = Read(getApplicationContext(), fileName);

            try {
                JSONObject contactsObject = new JSONObject(currentJsonString);

                if (contactsObject.has(currentUser)) {
                    JSONArray contactsJSONArray = contactsObject.getJSONArray(currentUser);

                    for (int i = 0; i < contactsJSONArray.length(); i++) {
                        JSONObject currentContact = contactsJSONArray.getJSONObject(i);

                        contactArrayList.add(
                                new Contact(
                                        currentContact.get("name").toString(),
                                        currentContact.get("phone").toString(),
                                        currentContact.get("address").toString(),
                                        currentContact.get("type").toString()
                                )
                        );
                    }
                }
            } catch (Exception e) {
                Log.e("ERRO", e.getMessage());
            }
        }

        listView = findViewById(R.id.listView);
        CustomAdapter adapter = new CustomAdapter(this, contactArrayList);
        listView.setAdapter(adapter);
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

    public boolean IsFilePresent(Context context, String fileName) {
        String path = context.getFilesDir().getAbsolutePath() + "/" + fileName;
        File file = new File(path);
        return file.exists();
    }
}