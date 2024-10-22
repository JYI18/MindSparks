package com.example.quizkids.Activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.quizkids.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Settings extends AppCompatActivity {

    private TextView profileEmail, profileLastSignIn;
    private ImageView backButton;
    private Button logoutButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        // Initialize views
        profileEmail = findViewById(R.id.profileEmail);
        profileLastSignIn = findViewById(R.id.profileLastSignIn);
        backButton = findViewById(R.id.backButton);
        logoutButton = findViewById(R.id.logoutButton);

        // Get current user
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        if (user != null) {
            // Set user email
            profileEmail.setText("Email: " + user.getEmail());

            // Get last sign-in time (in milliseconds) and format it
            long lastSignInTimestamp = user.getMetadata().getLastSignInTimestamp();
            String lastSignInDate = android.text.format.DateFormat.format("MM/dd/yyyy hh:mm a", new java.util.Date(lastSignInTimestamp)).toString();
            profileLastSignIn.setText("Last Signed In: " + lastSignInDate);
        } else {
            profileEmail.setText("No user signed in");
            profileLastSignIn.setText("");
        }

        // Handle back button click
        backButton.setOnClickListener(v -> onBackPressed());

        // Handle logout button click
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Sign out the user
                FirebaseAuth.getInstance().signOut();
                // Redirect to login screen or SplashScreenActivity
                Intent intent = new Intent(Settings.this, Login.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish(); // Close the current activity
            }
        });

        // Initialize views
        EditText screenTimeInput = findViewById(R.id.screenTimeInput);
        Button saveScreenTimeBtn = findViewById(R.id.saveScreenTimeBtn);

// Handle Save button click
        saveScreenTimeBtn.setOnClickListener(v -> {
            String screenTime = screenTimeInput.getText().toString();

            if (!screenTime.isEmpty()) {
                // Convert to int (in minutes)
                int screenTimeLimit = Integer.parseInt(screenTime);

                // Save to Firebase or SharedPreferences
                SharedPreferences sharedPreferences = getSharedPreferences("ParentalControl", MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putInt("screenTimeLimit", screenTimeLimit); // Save time in minutes
                editor.apply();

                Toast.makeText(Settings.this, "Screen time limit saved", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(Settings.this, "Please enter a valid time", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
