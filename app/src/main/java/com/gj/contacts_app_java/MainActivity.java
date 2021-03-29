package com.gj.contacts_app_java;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.HashSet;
import java.util.Set;

public class MainActivity extends AppCompatActivity {

    private EditText textUsername, textPassword;

    private SharedPreferences accountData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Gets and sets the inputs to their respective variables
        textUsername = findViewById(R.id.editTextUsername);
        textPassword = findViewById(R.id.editTextPassword);

        // Gets the account list from shared preferences
        accountData = getSharedPreferences("accountData", MODE_PRIVATE);

        // Clears account list
//        accountData.edit().clear().apply();
    }

    // Tries to perform the login of an account, if there is given information
    public void Login(View view) {
        // When the login button is pressed, checks if the fields are empty, if not, proceeds with the login
        if (textUsername.getText().toString().equals("") || textPassword.getText().toString().equals("")) {
            EmptyFieldsAlert();
        } else {
            LoginAction();
        }
    }

    // When the sign up button is pressed, goes to the sign up activity
    public void SignUp(View view) {
        Intent intent = new Intent(this, SignUp.class);
        startActivity(intent);
    }

    // Tries to perform the login of an account, using the given information
    private void LoginAction(){
        // Creates the set that will be used to check if the account, based on the given username, exists or not
        Set<String> noUserSet = new HashSet<>();
        noUserSet.add("Account does not exist");

        // Gets the account data from the account list, based on the given username
        Set<String> dataUsernameSet = accountData.getStringSet(textUsername.getText().toString().toLowerCase() + "_account", noUserSet);

        // Checks if the account, based on the given username, exists or not
        if (dataUsernameSet.equals(noUserSet)) {
            WrongInfoAlert();
            return;
        }

        // Transforms the account data ser into an array
        String[] dataUsernameArray = dataUsernameSet.toArray(new String[dataUsernameSet.size()]);

        // Checks if the info provided is the same as the account info, based on the given username, if it is, logs in
        if (dataUsernameArray[0].equals(textUsername.getText().toString().toLowerCase()) && dataUsernameArray[1].equals(textPassword.getText().toString())) {
            // Alerts the user that the login was successful
            Toast.makeText(MainActivity.this, "Logged in", Toast.LENGTH_SHORT).show();
            // Creates the user singleton and feeds it the required info
            User.GetInstance().FeedInstance(dataUsernameSet);
            //Set currentuser and Load next activity
            accountData.edit().putString("currentUser", textUsername.getText().toString().toLowerCase()).apply();
            LoadNextActivity();
        } else {
            WrongInfoAlert();
        }
    }

    private void LoadNextActivity(){
        Intent intent = new Intent(this, ListActivity.class);
        startActivityForResult(intent, 0);
    }

    // Alerts the user that the fields are empty
    private void EmptyFieldsAlert(){
        new AlertDialog.Builder(this).setTitle("Empty field(s)").setCancelable(true).setPositiveButton("Ok", ((DialogInterface dialog, int which) -> {})).show();
    }

    // Alerts the user that the fields are wrong
    private void WrongInfoAlert(){
        new AlertDialog.Builder(this).setTitle("Wrong username or password").setCancelable(true).setPositiveButton("Ok", ((DialogInterface dialog, int which) -> {})).show();
    }
}
