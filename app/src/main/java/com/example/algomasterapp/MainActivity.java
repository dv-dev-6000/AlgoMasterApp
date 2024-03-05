package com.example.algomasterapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    DatabaseHelper dbHelper= new DatabaseHelper(MainActivity.this);
    MyApplication myApplication = new MyApplication();
    Button loginButton;
    EditText et_Username;
    EditText et_Password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        et_Username = findViewById(R.id.editText_loginUsername);
        et_Password = findViewById(R.id.editText_LoginPassword);
        loginButton = findViewById(R.id.button_login);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                CSV_Helper csvHelper = new CSV_Helper(getResources().openRawResource(R.raw.users));
                String uName = et_Username.getText().toString();
                String pWord = et_Password.getText().toString();

                // ensure uname and pword fields are filled
                if (!uName.trim().isEmpty() && !pWord.trim().isEmpty()){

                    // call compare function with login button (0=Success, 1=PasswordError, 2=NameError)
                    int result = csvHelper.CheckForUser(uName, pWord);

                    if (result == 0){
                        //Successful Login
                        // log in and set up global vars
                        myApplication.LogIn(uName);
                        myApplication.setUserRank(dbHelper.getUserRank(MyApplication.userID));

                        Toast toast = Toast.makeText(MainActivity.this, "Welcome " + uName, Toast.LENGTH_SHORT);
                        toast.show();

                        Intent intent = new Intent(MainActivity.this, HomeScreen.class);
                        startActivity(intent);
                    }
                    else if (result == 1){
                        // notify user
                        Toast toast = Toast.makeText(MainActivity.this, "Incorrect Password!", Toast.LENGTH_SHORT);
                        toast.show();
                    }
                    else{
                        // notify user
                        Toast toast = Toast.makeText(MainActivity.this, "Username: " + uName + " not found!", Toast.LENGTH_SHORT);
                        toast.show();
                    }
                }
                else {
                    // notify user to enter credentials
                    Toast toast = Toast.makeText(MainActivity.this, "Blank Fields!" + uName, Toast.LENGTH_SHORT);
                    toast.show();
                }
            }
        });
    }

}