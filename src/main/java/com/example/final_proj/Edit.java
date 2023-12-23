package com.example.final_proj;

// EditMenuItemActivity.java

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

public class Edit extends AppCompatActivity {

    private MenuDatabaseHelper dbHelper;
    private ArrayAdapter<MenuItem> adapter;
    private EditText itemIdEditText, newItemNameEditText, newItemPriceEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit);

        dbHelper = new MenuDatabaseHelper(this);

        itemIdEditText = findViewById(R.id.editTextItemId);
        newItemNameEditText = findViewById(R.id.editTextNewItemName);
        newItemPriceEditText = findViewById(R.id.editTextNewItemPrice);

        Button btnEditItem = findViewById(R.id.btnEditItem);
        Button back_btn = findViewById(R.id.back);
        Button logout_btn = findViewById(R.id.logout);

        ListView menuListViewEdit = findViewById(R.id.menuListViewEdit);
        // Initialize the adapter with menu items
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, dbHelper.getMenuItems());
        menuListViewEdit.setAdapter(adapter);

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
                finish();
            }
        });

        // Edit button click listener
        btnEditItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle edit button click
                String itemIdText = itemIdEditText.getText().toString().trim();
                String newName = newItemNameEditText.getText().toString().trim();
                String newPriceText = newItemPriceEditText.getText().toString().trim();

                if (!itemIdText.isEmpty() && !newName.isEmpty() && !newPriceText.isEmpty()) {
                    int itemId = Integer.parseInt(itemIdText);
                    double newPrice = Double.parseDouble(newPriceText);
                    dbHelper.updateMenuItem(itemId, newName, newPrice);
                    updateAdapter();
                    // Clear the input fields after editing the item
                    clearInputFields();
                    Toast.makeText(Edit.this, "Item edited", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(Edit.this, "Please enter item ID, new name, and new price", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    // Update the adapter to reflect changes in menu items
    private void updateAdapter() {
        adapter.clear();
        adapter.addAll(dbHelper.getMenuItems());
        adapter.notifyDataSetChanged();
    }

    // Clear the input fields
    private void clearInputFields() {
        itemIdEditText.getText().clear();
        newItemNameEditText.getText().clear();
        newItemPriceEditText.getText().clear();
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
