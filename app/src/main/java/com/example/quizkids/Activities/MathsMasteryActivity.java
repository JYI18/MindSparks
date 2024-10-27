package com.example.quizkids.Activities;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.quizkids.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MathsMasteryActivity extends AppCompatActivity {

    private TableLayout tableLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maths_mastery);

        ImageView backButton = findViewById(R.id.imageView2);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish(); // Close the current activity and go back
            }
        });
        
        // Initialize the TableLayout
        tableLayout = findViewById(R.id.tableLayout);

        // Firebase Database reference
        DatabaseReference databaseRef = FirebaseDatabase.getInstance("https://mindsparks-b5274-default-rtdb.asia-southeast1.firebasedatabase.app")
                .getReference().child("maths");

        // Load data from Firebase
        loadQuestionsFromFirebase(databaseRef);
    }

    // Method to load questions from Firebase
    private void loadQuestionsFromFirebase(DatabaseReference databaseReference) {
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // Loop through each category (addition, subtraction, etc.)
                for (DataSnapshot categorySnapshot : dataSnapshot.getChildren()) {
                    // Get the category name (addition, subtraction, etc.)
                    String categoryName = categorySnapshot.getKey();

                    // Add a header row for each category
                    TableRow categoryRow = new TableRow(MathsMasteryActivity.this);
                    TextView categoryView = new TextView(MathsMasteryActivity.this);
                    categoryView.setText(categoryName.toUpperCase());
                    categoryView.setTextSize(18);
                    categoryView.setTextColor(Color.WHITE);
                    categoryView.setBackgroundColor(Color.DKGRAY);
                    categoryView.setPadding(16, 16, 16, 16);
                    categoryRow.addView(categoryView);

                    // Add the category row to the table
                    tableLayout.addView(categoryRow);

                    // Loop through each level (level1, level2, etc.)
                    for (DataSnapshot levelSnapshot : categorySnapshot.getChildren()) {
                        // Loop through each question (question1, question2, etc.)
                        for (DataSnapshot questionSnapshot : levelSnapshot.getChildren()) {
                            // Extract question text and correct answer
                            String questionText = questionSnapshot.child("questionText").getValue(String.class);
                            String correctAnswer = String.valueOf(questionSnapshot.child("correctAnswer").getValue());

                            // Create a new TableRow for each question
                            TableRow tableRow = new TableRow(MathsMasteryActivity.this);

                            // Create TextViews for question and answer
                            TextView questionView = new TextView(MathsMasteryActivity.this);
                            questionView.setText(questionText);
                            questionView.setPadding(16, 16, 16, 16);
                            questionView.setBackgroundResource(R.drawable.table_column_border);  // Apply cell border

                            TextView answerView = new TextView(MathsMasteryActivity.this);
                            answerView.setText(correctAnswer);
                            answerView.setPadding(16, 16, 16, 16);
                            answerView.setBackgroundResource(R.drawable.table_column_border);  // Apply cell border

                            // Add TextViews to the TableRow
                            tableRow.addView(questionView);
                            tableRow.addView(answerView);

                            // Add TableRow to the TableLayout
                            tableLayout.addView(tableRow);
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle potential errors here
                Log.e("FirebaseError", "Error fetching data: " + databaseError.getMessage());
                Toast.makeText(MathsMasteryActivity.this, "Failed to load data.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
