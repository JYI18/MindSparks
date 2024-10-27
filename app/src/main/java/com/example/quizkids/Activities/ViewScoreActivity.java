package com.example.quizkids.Activities;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import com.example.quizkids.R;

public class ViewScoreActivity extends AppCompatActivity {

    private TextView scoreText;
    private Button resetQuizButton;
    private Button backToMenuButton;
    private Button backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_score); // Make sure to use the correct layout file name

        // Initialize views
        scoreText = findViewById(R.id.scoreText);
        TextView messageText = findViewById(R.id.messageText); // New TextView for messages
        resetQuizButton = findViewById(R.id.resetQuizButton);
        backToMenuButton = findViewById(R.id.backToMenuButton);
        Intent intent = getIntent();
        int score = intent.getIntExtra("SCORE", 0); // Default score is 0

        // Display the score
        scoreText.setText("Score: " + score);
        if (score == 10) {
            messageText.setText("Great job! You got a perfect score!");
        } else if (score > 5) {
            messageText.setText("Good job!");
        } else if (score < 4) {
            messageText.setText("Keep trying! You're doing great!");
        } else {
            messageText.setText(""); // No message for score 4 or 5
        }


        // Set up the click listener for Reset Quiz Button
        resetQuizButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetQuiz();
            }
        });

        // Set up the click listener for Back to Menu Button
        backToMenuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToMenu();
            }
        });
    }


    // Method to reset the quiz
    private void resetQuiz() {
        // Set the score to 0 or some default state
        scoreText.setText("Score: 0");

        // Start the quiz activity again
        Intent intent = new Intent(this, ScienceQuizActivity.class); // Assuming QuizActivity is the start of the quiz
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK); // Clear the current task stack and start fresh
        startActivity(intent);
        finish(); // Finish this activity so the user cannot return to the old state by pressing the back button
    }


    // Method to navigate back to the menu
    private void goToMenu() {
        // Implement your navigation logic here
        finish(); // This will close the current activity
        // You can also start another activity if needed
        // Intent intent = new Intent(this, MenuActivity.class);
        // startActivity(intent);
    }
}

