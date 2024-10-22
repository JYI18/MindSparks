package com.example.quizkids.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import com.example.quizkids.R;
import androidx.appcompat.app.AppCompatActivity;

public class MathsLevelActivity extends AppCompatActivity {

    private Button level1Button, level2Button, level3Button, level4Button, level5Button, boosterButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maths_level);
        //Get the category from the intent
        String category = getIntent().getStringExtra("category");

        // Initializing buttons
        level1Button = findViewById(R.id.level1Button);
        level2Button = findViewById(R.id.level2Button);
        level3Button = findViewById(R.id.level3Button);
        level4Button = findViewById(R.id.level4Button);
        level5Button = findViewById(R.id.level5Button);
        //boosterButton = findViewById(R.id.boosterButton);



        // Set click listeners for each level
//        level1Button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startQuiz(category, 1); // Starts quiz for level 1
//            }
//        });

        level1Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get the category from the intent
                String category = getIntent().getStringExtra("category");

                // Pass the category and level to MathsQuizActivity
                Intent intent = new Intent(MathsLevelActivity.this, MathsQuizActivity.class);
                intent.putExtra("category", category);  // Pass the selected category
                intent.putExtra("level", 1);  // Pass the selected level
                startActivity(intent);
            }
        });

        level2Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get the category from the intent
                String category = getIntent().getStringExtra("category");

                // Pass the category and level to MathsQuizActivity
                Intent intent = new Intent(MathsLevelActivity.this, MathsQuizActivity.class);
                intent.putExtra("category", category);  // Pass the selected category
                intent.putExtra("level", 2);  // Pass the selected level
                startActivity(intent);
            }
        });

        level3Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get the category from the intent
                String category = getIntent().getStringExtra("category");

                // Pass the category and level to MathsQuizActivity
                Intent intent = new Intent(MathsLevelActivity.this, MathsQuizActivity.class);
                intent.putExtra("category", category);  // Pass the selected category
                intent.putExtra("level", 3);  // Pass the selected level
                startActivity(intent);
            }
        });

        level4Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get the category from the intent
                String category = getIntent().getStringExtra("category");

                // Pass the category and level to MathsQuizActivity
                Intent intent = new Intent(MathsLevelActivity.this, MathsQuizActivity.class);
                intent.putExtra("category", category);  // Pass the selected category
                intent.putExtra("level", 4);  // Pass the selected level
                startActivity(intent);
            }
        });

        level5Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get the category from the intent
                String category = getIntent().getStringExtra("category");

                // Pass the category and level to MathsQuizActivity
                Intent intent = new Intent(MathsLevelActivity.this, MathsQuizActivity.class);
                intent.putExtra("category", category);  // Pass the selected category
                intent.putExtra("level", 5);  // Pass the selected level
                startActivity(intent);
            }
        });

        // Booster level with random questions
//        boosterButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                // Get the category from the intent
//                String category = getIntent().getStringExtra("category");
//
//                // Pass the category and level to MathsQuizActivity
//                Intent intent = new Intent(MathsLevelActivity.this, MathsQuizActivity.class);
//                intent.putExtra("category", category);  // Pass the selected category
//                intent.putExtra("level", 6);  // Pass the selected level
//                startActivity(intent);
//            }
//        });
    }

    // Method to start quiz for the selected level
    private void startQuiz(String category, int level) {
        Intent intent = new Intent(MathsLevelActivity.this, MathsQuizActivity.class);
        intent.putExtra("category", category); // Pass the category
        intent.putExtra("level", level); // Pass the selected level to the quiz activity
        startActivity(intent);
    }
}
