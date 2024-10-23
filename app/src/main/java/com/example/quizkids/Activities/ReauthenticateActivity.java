package com.example.quizkids.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.quizkids.R;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ReauthenticateActivity extends AppCompatActivity {

    private FirebaseUser user;
    private EditText passwordInput;
    private Button reauthenticateButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reauthenticate);

        // Initialize Firebase user
        user = FirebaseAuth.getInstance().getCurrentUser();

        // Initialize views
        passwordInput = findViewById(R.id.passwordInput);
        reauthenticateButton = findViewById(R.id.reauthenticateButton);

        // Handle re-authentication
        reauthenticateButton.setOnClickListener(v -> {
            String password = passwordInput.getText().toString();

            if (user != null && !password.isEmpty()) {
                // Re-authenticate the user
                AuthCredential credential = EmailAuthProvider.getCredential(user.getEmail(), password);
                user.reauthenticate(credential)
                        .addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                // Re-authentication successful, proceed to settings
                                Intent intent = new Intent(ReauthenticateActivity.this, Settings.class);
                                startActivity(intent);
                                finish();  // Close re-authentication activity
                            } else {
                                // Re-authentication failed
                                Toast.makeText(ReauthenticateActivity.this, "Authentication failed! Check your password.", Toast.LENGTH_SHORT).show();
                            }
                        });
            } else {
                Toast.makeText(ReauthenticateActivity.this, "Please enter your password", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
