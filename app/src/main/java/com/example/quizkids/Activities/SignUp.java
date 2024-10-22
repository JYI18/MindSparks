package com.example.quizkids.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.quizkids.MainActivity;
import com.example.quizkids.R;
import com.google.firebase.auth.FirebaseAuth;

public class SignUp extends AppCompatActivity {

    private EditText emailEditText, passwordEditText;
    private Button registerButton;
    private ProgressBar progressBar;
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
        registerButton = findViewById(R.id.btnregister);
        progressBar = findViewById(R.id.progressbar);

        // Set onClickListener for the register button
        registerButton.setOnClickListener(v -> {
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

            if (password.length() < 6) {
                passwordEditText.setError("Password must be at least 6 characters");
                return;
            }

            // Show progress bar during registration
            progressBar.setVisibility(View.VISIBLE);

            // Firebase sign-up method
            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(task -> {
                        // Hide progress bar
                        progressBar.setVisibility(View.GONE);

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
}
