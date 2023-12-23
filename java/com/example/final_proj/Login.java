package com.example.final_proj;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Login extends AppCompatActivity {

    TextView tv_registerBtn;
    Button btn_login;
    EditText et_username, et_password;
    DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        et_username = findViewById(R.id.et_username);
        et_password = findViewById(R.id.et_password);

        tv_registerBtn = findViewById(R.id.tv_registerButton);
        tv_registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                open_register();
            }
        });

        dbHelper = new DatabaseHelper(this);
        btn_login = findViewById(R.id.btn_login);

        btn_login.setOnClickListener(view -> {
            String username = et_username.getText().toString().trim();
            String password = et_password.getText().toString().trim();

            if (dbHelper.checkUserCredentials(username, password)) {
                // Authentication successful, send broadcast
                sendLoginBroadcast(username);

                // Navigate to next screen or perform actions
                Toast.makeText(Login.this, "Login Successful", Toast.LENGTH_SHORT).show();
                if (username.equals("admin")) {
                    open_admin();
                } else {
                    open_user();
                }

                // Add your logic here (e.g., navigating to another activity)
            } else {
                // Authentication failed, show an error message or handle accordingly
                Toast.makeText(Login.this, "Invalid credentials", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void open_register() {
        Intent intent = new Intent(this, Register.class);
        startActivity(intent);
    }

    private void open_admin() {
        Intent intent = new Intent(this, Admin.class);
        startActivity(intent);
    }

    private void open_user() {
        Intent intent = new Intent(this, User.class);
        startActivity(intent);
    }

    private void setLocale(String language) {
        Log.d("Locale", "Changing language to: " + language);
        Context updatedContext = LocaleHelper.setLocale(this, language);

        // Log the updated locale for debugging
        Log.d("Locale", "Updated locale: " + updatedContext.getResources().getConfiguration().locale);

        recreate(); // Restart the activity to apply the new language
    }

    private void sendLoginBroadcast(String username) {
        Intent broadcastIntent = new Intent("com.example.final_proj.LOGIN_SUCCESSFUL");
        broadcastIntent.putExtra("username", username); // You can include additional data if needed
        sendBroadcast(broadcastIntent);
    }
}
