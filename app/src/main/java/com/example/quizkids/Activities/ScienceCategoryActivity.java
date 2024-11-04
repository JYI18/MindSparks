package com.example.quizkids.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import com.example.quizkids.R;

public class ScienceCategoryActivity extends AppCompatActivity {

    // Declare CardViews for the different science categories
    CardView animal, plants, human_body, planet_space, challenge;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_science_category);

        // Initialize CardViews
        animal = findViewById(R.id.animal);
        plants = findViewById(R.id.plants);
        human_body = findViewById(R.id.human_body);
        planet_space = findViewById(R.id.planet_space);
        challenge = findViewById(R.id.challenge);

        // Set OnClickListeners for each category card
        animal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to Animal Quiz Activity
                Intent intent = new Intent(ScienceCategoryActivity.this, AnimalQuizActivity.class);
                startActivity(intent);
            }
        });

        plants.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to Plants Quiz Activity
                Intent intent = new Intent(ScienceCategoryActivity.this, PlantQuizActivity.class);
                startActivity(intent);
            }
        });

        human_body.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to Human Body Quiz Activity
                Intent intent = new Intent(ScienceCategoryActivity.this, HumanBodyActivity.class);
                startActivity(intent);
            }
        });

        planet_space.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to Planets Quiz Activity
                Intent intent = new Intent(ScienceCategoryActivity.this, PlanetsQuizActivity.class);
                startActivity(intent);
            }
        });

        challenge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to Science Challenge Activity
                Intent intent = new Intent(ScienceCategoryActivity.this, ScienceChallengeActivity.class);
                startActivity(intent);
            }
        });
    }
}
