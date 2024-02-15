package com.example.algomasterapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class Modules extends AppCompatActivity {

    private Button mod1;
    private Button mod2;
    private Button mod3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modules);

        // set toolbar as action bar
        Toolbar toolbar = findViewById(R.id.toolbar_Modules);
        setSupportActionBar(toolbar);

        mod1 = findViewById(R.id.button_m1);
        mod1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // do stuff
                Intent intent = new Intent(Modules.this, Lessons.class);
                intent.putExtra("module", "m1");
                startActivity(intent);
            }
        });

        mod2 = findViewById(R.id.button_m2);
        mod2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // do stuff
                Intent intent = new Intent(Modules.this, Lessons.class);
                intent.putExtra("module", "m2");
                startActivity(intent);
            }
        });

        mod3 = findViewById(R.id.button_m3);
        mod3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // do stuff
                Intent intent = new Intent(Modules.this, Lessons.class);
                intent.putExtra("module", "m3");
                startActivity(intent);
            }
        });
    }

    // bind the menu to the actionbar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_header, menu);
        return true;
    }

    // set button click operations
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.menuItem_Home){
            Intent intent = new Intent(Modules.this, HomeScreen.class);
            startActivity(intent);
        }
        else if (item.getItemId() == R.id.menuItem_Profile){
            Intent intent = new Intent(Modules.this, UserProfile.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }
}