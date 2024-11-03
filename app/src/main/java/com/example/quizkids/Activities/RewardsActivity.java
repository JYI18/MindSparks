package com.example.quizkids.Activities;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.example.quizkids.R;

public class RewardsActivity extends AppCompatActivity {

    private TextView rewardText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reward);

        rewardText = findViewById(R.id.rewardText);

        // Retrieve the reward based on the number of quizzes completed
        String reward = getProgressReward();
        rewardText.setText("Reward: " + reward);
    }

    // Method to determine reward based on quizzes completed
    private String getProgressReward() {
        // Access SharedPreferences to get the total quizzes completed
        SharedPreferences prefs = getSharedPreferences("QuizProgress", MODE_PRIVATE);
        int completedQuizzes = prefs.getInt("completedQuizzes", 0);

        // Determine reward based on the number of quizzes completed
        if (completedQuizzes >= 10) {
            return "Intermediate Badge";
        } else if (completedQuizzes >= 5) {
            return "Beginner Badge";
        } else {
            return "Keep Going!";
        }
    }
}
