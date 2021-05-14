package com.example.task61;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.task61.data.user_DatabaseHelper;
import com.example.task61.model.User;

public class SignUp extends AppCompatActivity {
    user_DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        EditText confirmPasswordEditText = findViewById(R.id.confirmPasswordEditText);
        EditText passwordEditText = findViewById(R.id.passwordEditText);
        EditText phoneEditText = findViewById(R.id.phoneEditText);
        EditText emailEditText = findViewById(R.id.emailEditText);
        EditText addressEditText = findViewById(R.id.addressEditText);
        EditText fullNameEditText = findViewById(R.id.fullNameEditText);

        Button saveButton = findViewById(R.id.saveButton);
        db = new user_DatabaseHelper(this);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String confirmPassword = confirmPasswordEditText.getText().toString();
                String password = passwordEditText.getText().toString();
                String phone = phoneEditText.getText().toString();
                String email = emailEditText.getText().toString();
                String address = addressEditText.getText().toString();
                String fullName = fullNameEditText.getText().toString();

                if (password.equals(confirmPassword))
                {
                    long result = db.insertUser(new User(fullName, password, email, phone, address));
                    if (result > 0)
                    {
                        Toast.makeText(com.example.task61.SignUp.this, "Registered successfully!", Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        Toast.makeText(com.example.task61.SignUp.this, "Registration error!", Toast.LENGTH_SHORT).show();
                    }

                }
                else
                {
                    Toast.makeText(com.example.task61.SignUp.this, "Two passwords do not match!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}