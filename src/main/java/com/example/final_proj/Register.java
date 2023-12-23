package com.example.final_proj;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Register extends AppCompatActivity {

    TextView tv_loginButton;
    Button btn_register;
    EditText et_username, et_password;
    DatabaseHelper dbHelper;
    private MyBroadcastReceiver myBroadcastReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);

        et_username = findViewById(R.id.et_username);
        et_password = findViewById(R.id.et_password);

        tv_loginButton = findViewById(R.id.tv_loginButton);
        tv_loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                open_login();
            }
        });

        btn_register = findViewById(R.id.btn_register);
        dbHelper = new DatabaseHelper(this);

        // Register the BroadcastReceiver
        myBroadcastReceiver = new MyBroadcastReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction("com.example.final_proj.LOGIN_SUCCESSFUL");
        filter.addAction("com.example.final_proj.REGISTRATION_SUCCESSFUL");
        // Add more actions as needed
        registerReceiver(myBroadcastReceiver, filter);

        btn_register.setOnClickListener(view -> {
            String newUsername = et_username.getText().toString().trim();
            String newPassword = et_password.getText().toString().trim();

            if (!newUsername.isEmpty() && !newPassword.isEmpty()) {
                if (!dbHelper.isPasswordValid(newPassword)) {
                    Toast.makeText(Register.this, "Password must be at least 8 characters long", Toast.LENGTH_SHORT).show();
                } else if (dbHelper.checkUsernameExists(newUsername)) {
                    Toast.makeText(Register.this, "Username already exists. Please choose a different one.", Toast.LENGTH_SHORT).show();
                } else {
                    dbHelper.addUserCredentials(newUsername, newPassword);
                    Toast.makeText(Register.this, "Sign Up Successful", Toast.LENGTH_SHORT).show();

                    // Send a broadcast to notify about successful registration
                    sendRegistrationBroadcast(newUsername);

                    // Optionally, navigate to the login screen or perform other actions
                    finish(); // Finish this activity after signup
                }
            } else {
                Toast.makeText(Register.this, "Please enter username and password", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void open_login() {
        Intent intent = new Intent(this, Login.class);
        startActivity(intent);
    }

    private void sendRegistrationBroadcast(String username) {
        Intent broadcastIntent = new Intent("com.example.final_proj.REGISTRATION_SUCCESSFUL");
        broadcastIntent.putExtra("username", username); // You can include additional data if needed
        sendBroadcast(broadcastIntent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Unregister the BroadcastReceiver when the activity is destroyed
        unregisterReceiver(myBroadcastReceiver);
    }
}
