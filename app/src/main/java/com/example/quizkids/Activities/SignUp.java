package com.example.quizkids.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.quizkids.MainActivity;
import com.example.quizkids.R;
import com.google.firebase.auth.FirebaseAuth;

public class SignUp extends AppCompatActivity {

    private EditText emailEditText, passwordEditText;
    private Button signupButton;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        // Link the UI components
        emailEditText = findViewById(R.id.email);
        passwordEditText = findViewById(R.id.passwd);
        signupButton = findViewById(R.id.signupbutton);

        // Set onClickListener for the register button
        signupButton.setOnClickListener(v -> {
            String email = emailEditText.getText().toString().trim();
            String password = passwordEditText.getText().toString().trim();

            // Input validation
            if (TextUtils.isEmpty(email)) {
                emailEditText.setError("Email is required");
                return;
            }

            if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                emailEditText.setError("Please enter a valid email");
                return;
            }

            if (TextUtils.isEmpty(password)) {
                passwordEditText.setError("Password is required");
                return;
            }

            if (!isPasswordValid(password)) {
                passwordEditText.setError("Password must be at least 6 characters, contain at least one uppercase letter, one lowercase letter, one number, and one special character");
                return;
            }

            // Firebase sign-up method
            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(task -> {

                        if (task.isSuccessful()) {
                            // Sign-up successful, log user in and navigate to MainActivity
                            Toast.makeText(SignUp.this, "Registration successful! Logging in...", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(), MainActivity.class));
                            finish(); // Close the sign-up activity
                        } else {
                            // If sign-up fails, display a message to the user
                            Toast.makeText(SignUp.this, "Registration failed: " + task.getException().getMessage(), Toast.LENGTH_LONG).show();
                        }
                    });
        });
    }

    // Function to check password requirements
    private boolean isPasswordValid(String password) {
        return password.length() >= 6 &&
                password.matches(".*[A-Z].*") &&  // At least one uppercase letter
                password.matches(".*[a-z].*") &&  // At least one lowercase letter
                password.matches(".*\\d.*") &&    // At least one digit
                password.matches(".*[@#$%^&+=!?].*");  // At least one special character
    }
}
