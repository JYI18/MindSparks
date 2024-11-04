package com.example.quizkids.Activities;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import com.example.quizkids.R;

public class ViewScoreActivity extends AppCompatActivity {

    private TextView scoreText, rewardText;
    private Button resetQuizButton, backToMenuButton;
    private ImageView rewardBadgeImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_score); // Ensure the correct layout file name

        // Initialize views
        scoreText = findViewById(R.id.scoreText);
        rewardText = findViewById(R.id.rewardText); // Reward TextView for displaying the reward
        TextView messageText = findViewById(R.id.messageText); // New TextView for messages
        resetQuizButton = findViewById(R.id.resetQuizButton);
        rewardBadgeImage = findViewById(R.id.rewardBadgeImage);
        backToMenuButton = findViewById(R.id.backToMenuButton);

        Intent intent = getIntent();
        int score = intent.getIntExtra("SCORE", 0); // Default score is 0

        // Display the score
        scoreText.setText("Score: " + score);

        // Determine message and reward based on the score
        if (score >= 5) {
            messageText.setText("Great job!!!!");
            rewardText.setText("Reward: Gold Badge"); // Set gold badge for score >= 5
            rewardBadgeImage.setImageResource(R.drawable.gold_badge); // Set gold badge image
        } else if (score >= 3) {
            messageText.setText("Good job!!!");
            rewardText.setText("Reward: Silver Badge"); // Set silver badge for score 3 or 4
            rewardBadgeImage.setImageResource(R.drawable.silver_badge);
        } else {
            messageText.setText("Keep trying! !");
            rewardText.setText("Reward: Better luck next time!"); // Encouragement for score 1 or 2
            rewardBadgeImage.setImageResource(R.drawable.betterluck);
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
        rewardText.setText("Reward: None"); // Reset reward

        // Start the quiz activity again
        Intent intent = new Intent(this, ScienceQuizActivity.class); // Assuming QuizActivity is the start of the quiz
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK); // Clear the current task stack
        startActivity(intent);
        finish(); // Finish this activity
    }

    // Method to navigate back to the menu
    private void goToMenu() {
        finish(); // This will close the current activity
        // You can also start another activity if needed
    }
}
