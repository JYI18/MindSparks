package com.example.quizkids.Activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.quizkids.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Settings extends AppCompatActivity {

    private TextView profileEmail, profileLastSignIn;
    private ImageView backButton;
    private Button logoutButton, changePasswordButton;
    private EditText newPasswordInput;
    private Spinner timerSpinner;
    private Button saveTimerButton;

    private FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        // Initialize views
        profileEmail = findViewById(R.id.profileEmail);
        profileLastSignIn = findViewById(R.id.profileLastSignIn);
        backButton = findViewById(R.id.backButton);
        logoutButton = findViewById(R.id.logoutButton);
        newPasswordInput = findViewById(R.id.newPasswordInput);
        changePasswordButton = findViewById(R.id.changePasswordButton);
        timerSpinner = findViewById(R.id.timerSpinner);
        saveTimerButton = findViewById(R.id.saveTimerButton);

        // Get current user
        user = FirebaseAuth.getInstance().getCurrentUser();

        // Set user details
        if (user != null) {
            profileEmail.setText("Email: " + user.getEmail());

            long lastSignInTimestamp = user.getMetadata().getLastSignInTimestamp();
            String lastSignInDate = android.text.format.DateFormat.format("MM/dd/yyyy hh:mm a", new java.util.Date(lastSignInTimestamp)).toString();
            profileLastSignIn.setText("Last Signed In: " + lastSignInDate);
        }

        // Handle logout button click
        logoutButton.setOnClickListener(v -> {
            FirebaseAuth.getInstance().signOut();
            Intent intent = new Intent(Settings.this, Login.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        });

        // Handle Save Timer button click
        saveTimerButton.setOnClickListener(v -> {
            String selectedTimer = timerSpinner.getSelectedItem().toString();
            int timerValue = selectedTimer.equals("60 seconds") ? 60 : selectedTimer.equals("90 seconds") ? 90 : 30;

            SharedPreferences sharedPreferences = getSharedPreferences("QuizSettings", MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putInt("timerValue", timerValue);
            editor.apply();

            Toast.makeText(Settings.this, "Timer saved: " + selectedTimer, Toast.LENGTH_SHORT).show();
        });

        // Handle password change
        changePasswordButton.setOnClickListener(v -> {
            String newPassword = newPasswordInput.getText().toString();
            if (!newPassword.isEmpty()) {
                user.updatePassword(newPassword)
                        .addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                Toast.makeText(Settings.this, "Password updated successfully!", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(Settings.this, "Failed to update password!", Toast.LENGTH_SHORT).show();
                            }
                        });
            } else {
                Toast.makeText(Settings.this, "Please enter a new password", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
