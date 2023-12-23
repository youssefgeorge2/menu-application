package com.example.final_proj;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log; // Import Log
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Add extends AppCompatActivity {

    private MenuDatabaseHelper dbHelper;
    private ArrayAdapter<MenuItem> adapter;
    private EditText itemNameEditText, itemPriceEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add);

        dbHelper = new MenuDatabaseHelper(this);

        ListView menuListViewAdmin = findViewById(R.id.menuListViewAdmin);
        Button btnAdd = findViewById(R.id.btnAdd);
        Button back_btn = findViewById(R.id.back);
        Button logout_btn = findViewById(R.id.logout);

        // EditText fields for item name and price
        itemNameEditText = findViewById(R.id.editTextItemName);
        itemPriceEditText = findViewById(R.id.editTextItemPrice);

        // Initialize the adapter with menu items
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, dbHelper.getMenuItems());
        menuListViewAdmin.setAdapter(adapter);

        // Add button click listener
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle add button click
                String itemName = itemNameEditText.getText().toString().trim();
                String priceText = itemPriceEditText.getText().toString().trim();

                if (!itemName.isEmpty() && !priceText.isEmpty()) {
                    double itemPrice = Double.parseDouble(priceText);
                    dbHelper.addMenuItem(itemName, itemPrice);

                    // Log to verify that the sendNotification() method is called
                    Log.d("Add", "New meal added. Sending notification...");

                    // Send a notification when a new meal is added
                    sendNotification();

                    updateAdapter();

                    // Clear the input fields after adding the item
                    itemNameEditText.getText().clear();
                    itemPriceEditText.getText().clear();
                } else {
                    Toast.makeText(Add.this, "Please enter both name and price", Toast.LENGTH_SHORT).show();
                }
            }
        });

        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                back();
            }
        });

        logout_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logout();
            }
        });
    }

    // Update the adapter to reflect changes in menu items
    private void updateAdapter() {
        adapter.clear();
        adapter.addAll(dbHelper.getMenuItems());
        adapter.notifyDataSetChanged();
    }

    private void sendNotification() {
        // Inside the code where you add a new meal
        Intent notificationIntent = new Intent(Add.this, MealNotificationService.class);
        startService(notificationIntent);
    }

    private void back() {
        Intent intent = new Intent(this, Admin.class);
        startActivity(intent);
    }

    private void logout() {
        Intent intent = new Intent(this, Login.class);
        startActivity(intent);
        finish();
    }
}
