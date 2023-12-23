package com.example.final_proj;

// MainActivity.java

// MainActivity.java

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

// MainActivity.java

// ... (Other imports)

public class User extends AppCompatActivity {

    private MenuDatabaseHelper dbHelper;
    private ArrayAdapter<MenuItem> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user);

        dbHelper = new MenuDatabaseHelper(this);

        ListView menuListView = findViewById(R.id.menuListView);
        Button logout = findViewById(R.id.logout);
        Button btnSendEmail = findViewById(R.id.opengoogle);



        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, dbHelper.getMenuItems());
        menuListView.setAdapter(adapter);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logout();
            }
        });
        btnSendEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGoogle();
            }
        });

    }
    private void openGoogle() {
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.google.com"));

        // Verify that the intent will resolve to an activity
        if (browserIntent.resolveActivity(getPackageManager()) != null) {
            startActivity(browserIntent);
        } else {
            // Handle the case where there is no web browser available
            // For example, show a Toast message
            Toast.makeText(this, "No web browser available", Toast.LENGTH_SHORT).show();
        }
    }

    private void logout() {
        Intent intent = new Intent(this, Login.class);
        startActivity(intent);
        finish();
    }
}
