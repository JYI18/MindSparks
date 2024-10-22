package com.example.quizkids;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.quizkids.Activities.Login;
import com.example.quizkids.Activities.MathsCategoryActivity;
import com.example.quizkids.Activities.Settings;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    CardView science, mathematics, rewards, viewScore, settings;

    private long startTime;
    private int screenTimeLimit; // In minutes

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        // Get screen time limit from SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("ParentalControl", MODE_PRIVATE);
        screenTimeLimit = sharedPreferences.getInt("screenTimeLimit", 60); // Default to 60 minutes if not set

        // Start tracking screen time
        startTime = System.currentTimeMillis();

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Initialize CardViews
        science = findViewById(R.id.science);
        mathematics = findViewById(R.id.mathematics);
        rewards = findViewById(R.id.rewards);
        viewScore = findViewById(R.id.ViewScore);
        settings = findViewById(R.id.Settings);

        // Set onClickListeners for each CardView
        mathematics.setOnClickListener(view -> checkScreenTimeLimit(() -> {
            Intent intent = new Intent(MainActivity.this, MathsCategoryActivity.class);
            startActivity(intent);
        }));

        settings.setOnClickListener(view -> checkScreenTimeLimit(() -> {
            Intent intent = new Intent(MainActivity.this, Settings.class);
            startActivity(intent);
        }));
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Restart the screen time tracking when the activity is resumed
        startTime = System.currentTimeMillis();
    }

    @Override
    protected void onPause() {
        super.onPause();
        // Check screen time usage when the activity is paused
        long timeSpent = (System.currentTimeMillis() - startTime) / 1000 / 60; // Time in minutes

        SharedPreferences sharedPreferences = getSharedPreferences("ParentalControl", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        // Save the total time spent in SharedPreferences (optional)
        editor.putLong("timeSpentToday", timeSpent); // Use this if you want to track cumulative time
        editor.apply();
    }

    // Method to check if the screen time limit has been exceeded
    private void checkScreenTimeLimit(Runnable proceed) {
        long currentTimeSpent = (System.currentTimeMillis() - startTime) / 1000 / 60; // Convert to minutes

        if (currentTimeSpent > screenTimeLimit) {
            // If screen time limit is exceeded, show a message and log the user out
            Toast.makeText(MainActivity.this, "Screen time limit exceeded. Please take a break!", Toast.LENGTH_LONG).show();
            FirebaseAuth.getInstance().signOut();
            Intent intent = new Intent(MainActivity.this, Login.class);
            startActivity(intent);
            finish();
        } else {
            // If screen time is within the limit, proceed to the intended action
            proceed.run();
        }
    }
}
