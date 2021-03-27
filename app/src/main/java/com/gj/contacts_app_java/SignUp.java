package com.gj.contacts_app_java;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.HashSet;
import java.util.Set;

public class SignUp extends AppCompatActivity {

    private EditText textUsername, textPassword;
    private Button buttonSignUp, buttonCancel;

    private SharedPreferences accountData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        // Gets and sets the inputs to their respective variables
        buttonSignUp = (Button) findViewById(R.id.createAccountButton);
        buttonCancel = (Button) findViewById(R.id.cancelButton);

        // Gets and sets the inputs to their respective variables
        textUsername = (EditText) findViewById(R.id.editTextUsername);
        textPassword = (EditText) findViewById(R.id.editTextPassword);

        // Gets the account list from shared preferences
        accountData = getSharedPreferences("accountData", MODE_PRIVATE);

        // Creates the set that will be used to check if the given username already exists
        Set<String> newUserSet = new HashSet<String>();
        newUserSet.add("New username");

        buttonSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Checks if the given username and password are: empty, already exist or are equal; if not, proceeds with the sign up
                if (textUsername.getText().toString().equals("") || textPassword.getText().toString().equals("")) {
                    EmptyFieldsAlert();
                } else if (!accountData.getStringSet(textUsername.getText().toString().toLowerCase() + "_account", newUserSet).equals(newUserSet)) {
                    ExistingUsernameAlert();
                } else if (textUsername.getText().toString().equals(textPassword.getText().toString()) || textUsername.getText().toString().toLowerCase().equals(textPassword.getText().toString()) || textPassword.getText().toString().equals(textUsername.getText().toString())) {
                    EqualFieldsAlert();
                } else {
                    SignUpAction();
                }
            }
        });

        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Goes back to the login screen
                CancelAction();
            }
        });
    }

    // Alerts the user that the fields are empty
    private void EmptyFieldsAlert(){
        new AlertDialog.Builder(this).setTitle("Empty field(s)").setCancelable(true).setPositiveButton("Ok", ((DialogInterface dialog, int which) -> {})).show();
    }

    // Alerts the user that the username already exists
    private void ExistingUsernameAlert(){
        new AlertDialog.Builder(this).setTitle("Username already exists").setCancelable(true).setPositiveButton("Ok", ((DialogInterface dialog, int which) -> {})).show();
    }

    // Alerts the user that the fields are equal
    private void EqualFieldsAlert(){
        new AlertDialog.Builder(this).setTitle("Username and password cannot be the same").setCancelable(true).setPositiveButton("Ok", ((DialogInterface dialog, int which) -> {})).show();
    }

    // Performs the sign up of a new account
    private void SignUpAction(){
        // Alerts the user that the sign up was successful
        Toast.makeText(SignUp.this, "Signed Up", Toast.LENGTH_SHORT).show();

        // Creates a new account with the given info, the username not being case sensitive
        CreateAccount(textUsername.getText().toString().toLowerCase(), textPassword.getText().toString());

        // Goes back to the login activity
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    // When the cancel button is pressed, goes to the login activity
    private void CancelAction(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    // Performs the internal creation of a new account
    private void CreateAccount(String newUsername, String newPassword){
        // Creates a new set and adds the given info to it
        Set<String> newLoginInfo = new HashSet<String>();
        newLoginInfo.add(newUsername);
        newLoginInfo.add(newPassword);

        // Puts a new account on the account list with the given info, using the given username + _account as the key
        accountData.edit().putStringSet(newUsername + "_account", newLoginInfo).apply();
    }
}