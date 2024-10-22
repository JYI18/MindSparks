package com.example.quizkids.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import com.example.quizkids.R;

public class MathsCategoryActivity extends AppCompatActivity {

    CardView addition, subtraction, multiplication, division, challenge;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maths_category);

        // Initialize CardViews
        addition = findViewById(R.id.addition);
        subtraction = findViewById(R.id.subtraction);
        multiplication = findViewById(R.id.multiplication);
        division = findViewById(R.id.division);
        challenge = findViewById(R.id.challenge);

        // Set OnClickListeners for each category card
        addition.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to Addition Quiz Activity
                Intent intent = new Intent(MathsCategoryActivity.this, MathsAdditionLevelActivity.class);
                startActivity(intent);
            }
        });

        subtraction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to Subtraction Quiz Activity
                Intent intent = new Intent(MathsCategoryActivity.this, MathsSubtractionLevelActivity.class);
                startActivity(intent);
            }
        });

        multiplication.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to Multiplication Quiz Activity
                Intent intent = new Intent(MathsCategoryActivity.this, MathsMultiplicationLevelActivity.class);
                startActivity(intent);
            }
        });

        division.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to Division Quiz Activity
                Intent intent = new Intent(MathsCategoryActivity.this, MathsDivisionLevelActivity.class);
                startActivity(intent);
            }
        });

        challenge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to Challenge Quiz Activity
                Intent intent = new Intent(MathsCategoryActivity.this, ChallengeActivity.class);
                startActivity(intent);
            }
        });
    }
}

