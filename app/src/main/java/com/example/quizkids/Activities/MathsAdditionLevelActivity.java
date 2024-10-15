package com.example.quizkids.Activities;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import com.example.quizkids.R;
import androidx.appcompat.app.AppCompatActivity;

public class MathsAdditionLevelActivity extends AppCompatActivity{

    private Button level1Button, level2Button, level3Button, level4Button, level5Button, boosterButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maths_addition);

        // Initializing buttons
        level1Button = findViewById(R.id.level1Button);
        level2Button = findViewById(R.id.level2Button);
        level3Button = findViewById(R.id.level3Button);
        level4Button = findViewById(R.id.level4Button);
        level5Button = findViewById(R.id.level5Button);
        boosterButton = findViewById(R.id.boosterButton);

        // Set click listeners for each level
        level1Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startQuiz(1); // Starts quiz for level 1
            }
        });

        level2Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startQuiz(2); // Starts quiz for level 2
            }
        });

        level3Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startQuiz(3); // Starts quiz for level 3
            }
        });

        level4Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startQuiz(4); // Starts quiz for level 4
            }
        });

        level5Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startQuiz(5); // Starts quiz for level 5
            }
        });

        // Booster level with random questions
//        boosterButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startBoosterQuiz();
//            }
//        });
    }

    // Method to start quiz for selected level
    private void startQuiz(int level) {
        Intent intent = new Intent(MathsAdditionLevelActivity.this, MathsQuizActivity.class);
        intent.putExtra("level", level);
        startActivity(intent);
    }

    // Method to start the booster quiz (random questions)
//    private void startBoosterQuiz() {
//        Intent intent = new Intent(MathsAdditionLevelActivity.this, BoosterQuizActivity.class);
//        startActivity(intent);
//    }
















//    private Button level1Button;
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_maths_addition);
//
//        // Set click listeners for each level
//        Button level1Button = findViewById(R.id.level1Button);
//        level1Button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startQuiz(1);  // Start quiz for Level 1
//            }
//        });
//
//        // Similarly, for Levels 2-10, use startQuiz(levelNumber);
//
//        // Random level button
//        Button masteryButton = findViewById(R.id.masteryButton);
//        masteryButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startQuiz(11);  // Start quiz for Random level
//            }
//        });
//    }
//
//    // Start the quiz for the selected level
//    private void startQuiz(int level) {
//        Intent intent = new Intent(MathsAdditionLevelActivity.this, MathsQuizActivity.class);
//        intent.putExtra("LEVEL", level);
//        startActivity(intent);
//    }
}
