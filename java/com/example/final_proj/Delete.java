package com.example.final_proj;

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

public class Delete extends AppCompatActivity {

    private MenuDatabaseHelper dbHelper;
    private ArrayAdapter<MenuItem> adapter;
    private EditText itemIdEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.delete);

        dbHelper = new MenuDatabaseHelper(this);

        itemIdEditText = findViewById(R.id.editTextItemId);

        Button btnDeleteItem = findViewById(R.id.btnDelete);
        Button back_btn = findViewById(R.id.back);
        Button logout_btn = findViewById(R.id.logout);

        ListView menuListViewDelete = findViewById(R.id.menuListViewDelete);
        // Initialize the adapter with menu items
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, dbHelper.getMenuItems());
        menuListViewDelete.setAdapter(adapter);

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

        // Delete button click listener
        btnDeleteItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle delete button click
                String itemIdText = itemIdEditText.getText().toString().trim();

                if (!itemIdText.isEmpty()) {
                    int itemId = Integer.parseInt(itemIdText);
                    dbHelper.deleteMenuItem(itemId);
                    updateAdapter();
                    // Clear the input field after deleting the item
                    clearInputFields();
                    Toast.makeText(Delete.this, "Item deleted", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(Delete.this, "Please enter item ID", Toast.LENGTH_SHORT).show();
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

    // Clear the input field
    private void clearInputFields() {
        itemIdEditText.getText().clear();
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
