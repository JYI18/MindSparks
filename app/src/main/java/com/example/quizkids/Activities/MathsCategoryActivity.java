package com.example.quizkids.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import com.example.quizkids.R;

public class MathsCategoryActivity extends AppCompatActivity {

    CardView addition, subtraction, multiplication, division, challenge;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maths_category);

        ImageView backButton = findViewById(R.id.imageView2);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish(); // Close the current activity and go back
            }
        });


        // Initialize CardViews
        addition = findViewById(R.id.addition);
        subtraction = findViewById(R.id.subtraction);
        multiplication = findViewById(R.id.multiplication);
        division = findViewById(R.id.division);
        challenge = findViewById(R.id.challenge);

        addition.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MathsCategoryActivity.this, MathsLevelActivity.class);
                intent.putExtra("category", "addition"); // Pass the category
                startActivity(intent);
            }
        });

        subtraction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MathsCategoryActivity.this, MathsLevelActivity.class);
                intent.putExtra("category", "subtraction"); // Pass the category
                startActivity(intent);
            }
        });

        multiplication.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MathsCategoryActivity.this, MathsLevelActivity.class);
                intent.putExtra("category", "multiplication"); // Pass the category
                startActivity(intent);
            }
        });

        division.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MathsCategoryActivity.this, MathsLevelActivity.class);
                intent.putExtra("category", "division"); // Pass the category
                startActivity(intent);
            }
        });

        challenge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MathsCategoryActivity.this, MathsChallengeActivity.class);
                startActivity(intent);
            }
        });

        Button masteryButton = findViewById(R.id.button);
        masteryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MathsCategoryActivity.this, MathsMasteryActivity.class);
                startActivity(intent);
            }
        });

    }
}

