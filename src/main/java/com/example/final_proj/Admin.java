package com.example.final_proj;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;


public class Admin extends AppCompatActivity {

    private MenuDatabaseHelper dbHelper;
    private ArrayAdapter<MenuItem> adapter;
    private EditText itemNameEditText, itemPriceEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin);

        dbHelper = new MenuDatabaseHelper(this);

        ListView menuListViewAdmin = findViewById(R.id.menuListViewAdmin);
        Button btnAdd = findViewById(R.id.btnAdd);
        Button btnEdit = findViewById(R.id.btnEdit);
        Button btnDelete = findViewById(R.id.btnDelete);
        Button logout = findViewById(R.id.logout);


        // EditText fields for item name and price
        itemNameEditText = findViewById(R.id.editTextItemName);
        itemPriceEditText = findViewById(R.id.editTextItemPrice);

        // Initialize the adapter with menu items
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, dbHelper.getMenuItems());
        menuListViewAdmin.setAdapter(adapter);


        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logout();
            }
        });
        // Add button click listener
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle add button click
                open_add();
            }
        });

        // Edit button click listener
        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle add button click
                open_edit();
            }
        });

        // Delete button click listener
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle delete button click (assuming you delete the first item)
                open_delete();
            }
        });
    }

    private void logout() {
        Intent intent = new Intent(this, Login.class);
        startActivity(intent);
        finish();
    }
    private void open_add() {
        Intent intent = new Intent(this, Add.class);
        startActivity(intent);
    }
    private void open_edit() {
        Intent intent = new Intent(this, Edit.class);
        startActivity(intent);
    }
    private void open_delete() {
        Intent intent = new Intent(this, Delete.class);
        startActivity(intent);
    }
}
