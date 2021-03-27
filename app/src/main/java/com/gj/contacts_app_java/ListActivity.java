package com.gj.contacts_app_java;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class ListActivity extends AppCompatActivity {

    private ListView listView;
    ArrayList<Contact> contactArrayList ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        //Set List View Content
        listView = findViewById(R.id.listView);

        contactArrayList = new ArrayList<>();
        dataSaving();
        CustomAdapter adapter = new CustomAdapter(this,contactArrayList);
        listView.setAdapter(adapter);

//        List<String> your_array_list = new ArrayList<String>();
//        your_array_list.add("foo");
//        your_array_list.add("bar");
//        your_array_list.add("bbr");
//        your_array_list.add("bcr");
//        your_array_list.add("bdr");
//        your_array_list.add("ber");
//        your_array_list.add("bfr");
//        your_array_list.add("bgr");
//        your_array_list.add("bhr");
//        your_array_list.add("bir");
//        your_array_list.add("bjr");
//        your_array_list.add("bkr");
//        your_array_list.add("blr");
//        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
//                this,
//                android.R.layout.simple_list_item_1,
//                your_array_list );
//
//        listView.setAdapter(arrayAdapter);
    }

    public void addItem(View view) {
        Intent intent = new Intent(this, AddItemActivity.class);
        startActivityForResult(intent, 0);
//        startActivity(intent);
    }

    private void dataSaving() {

        Contact student = new Contact();
        student.setName("Jorge");
        student.setPhone("876543210");
        student.setAddress("Hyderabad");
        student.setType("Outro");
        contactArrayList.add(student);

        student = new Contact();
        student.setName("Pedro");
        student.setPhone("120067890");
        student.setAddress("Bangadlore");
        student.setType("Casa");
        contactArrayList.add(student);

        student = new Contact();
        student.setName("Brian");
        student.setPhone("18887890");
        student.setAddress("Banddglore");
        student.setType("Casa");
        contactArrayList.add(student);

        student = new Contact();
        student.setName("Carlos");
        student.setPhone("1234567890");
        student.setAddress("Banglore");
        student.setType("Trabalho");
        contactArrayList.add(student);
    }
}