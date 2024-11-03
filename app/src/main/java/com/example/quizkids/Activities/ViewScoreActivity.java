package com.example.quizkids.Activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.example.quizkids.R;



public class ViewScoreActivity extends AppCompatActivity {

    private TextView scoreText, rewardText; // Added rewardText to display rewards
    private Button resetQuizButton, backToMenuButton;
    private String currentCategory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_score);

        // Initialize views
        scoreText = findViewById(R.id.scoreText);
        rewardText = findViewById(R.id.rewardText); // TextView for rewards
        TextView messageText = findViewById(R.id.messageText);
        resetQuizButton = findViewById(R.id.resetQuizButton);
        backToMenuButton = findViewById(R.id.backToMenuButton);
        // Retrieve the score and category from the intent
        Intent intent = getIntent();
        String category = intent.getStringExtra("CATEGORY"); // Get the category
        int score = getSharedPreferences("QuizScores", MODE_PRIVATE).getInt(category + "_score", 0); // Retrieve score

        // Display the score
        scoreText.setText("Score for " + category + ": " + score);


        // Display the score
        scoreText.setText("Score: " + score);


        // Display reward and message based on score
        String reward = getReward(score);
        rewardText.setText("Reward: " + reward);

        // Display message based on score
        if (score == 10) {
            messageText.setText("Great job! You got a perfect score!");
        } else if (score >= 5) {
            messageText.setText("Good job!");
        } else {
            messageText.setText("Keep trying! You're doing great!");
        }

        // Save reward to SharedPreferences (optional)
        saveReward(reward);

        // Reset Quiz Button
        resetQuizButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetQuiz(currentCategory);
            }
        });

        // Back to Menu Button
        backToMenuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToMenu();
            }
        });
    }

    // Method to determine reward based on score
    private String getReward(int score) {
        if (score == 10) {
            return "Gold Star Badge";
        } else if (score >= 5) {
            return "Silver Star Badge";
        } else {
            return "Keep Trying!";
        }
    }

    // Method to save reward in SharedPreferences (optional for persistence)
    private void saveReward(String reward) {
        SharedPreferences prefs = getSharedPreferences("QuizRewards", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("lastReward", reward);
        editor.apply();
    }

    // Method to reset the quiz
    private void resetQuiz(String category) {
        scoreText.setText("Score: 0");

        Intent intent;
        switch (category) {
            case "Animals":
                intent = new Intent(this, AnimalQuizActivity.class);
                break;
            case "Plants":
                intent = new Intent(this, PlantQuizActivity.class);
                break;
            case "Human_Body":
                intent = new Intent(this, HumanBodyActivity.class);
                break;
            case "Planet_Space":
                intent = new Intent(this, PlanetsQuizActivity.class);
                break;
            default:
                intent = new Intent(this, ScienceQuizActivity.class);
                break;
        }

        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }

    // Method to navigate back to the menu
    private void goToMenu() {
        finish();
    }
}




