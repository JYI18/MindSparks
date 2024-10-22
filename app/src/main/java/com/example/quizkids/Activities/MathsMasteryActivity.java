package com.example.quizkids.Activities;

import android.os.Bundle;
import android.util.Log;
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
                    // Loop through each level (level1, level2, etc.)
                    for (DataSnapshot levelSnapshot : categorySnapshot.getChildren()) {
                        // Loop through each question (question1, question2, etc.)
                        for (DataSnapshot questionSnapshot : levelSnapshot.getChildren()) {
                            // Extract question text and correct answer
                            String questionText = questionSnapshot.child("questionText").getValue(String.class);
                            String correctAnswer = String.valueOf(questionSnapshot.child("correctAnswer").getValue());

                            // Create a new TableRow
                            TableRow tableRow = new TableRow(MathsMasteryActivity.this);

                            // Create TextViews for question and answer
                            TextView questionView = new TextView(MathsMasteryActivity.this);
                            questionView.setText(questionText);

                            TextView answerView = new TextView(MathsMasteryActivity.this);
                            answerView.setText(correctAnswer);

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






//package com.example.quizkids.Activities;
//
//import android.content.Intent;
//import android.os.Bundle;
//import android.view.View;
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.cardview.widget.CardView;
//import android.widget.ImageView;
//import android.widget.TextView;
//import com.example.quizkids.R;
//
//public class MathsMasteryActivity extends AppCompatActivity {
//
//    private CardView additionMastery, subtractionMastery, multiplicationMastery, divisionMastery;
//    private ImageView backButton;
//    private TextView titleText;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_maths_mastery);
//
//        // Initialize views
//        additionMastery = findViewById(R.id.additionicon);
//        subtractionMastery = findViewById(R.id.subtractionicon);
//        multiplicationMastery = findViewById(R.id.multiplicationicon);
//        divisionMastery = findViewById(R.id.divisionicon);
//        backButton = findViewById(R.id.imageView2);
//        titleText = findViewById(R.id.textView2);
//
//        // Set onClickListeners for each card view
//        additionMastery.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                // Start Addition Quiz Activity
//                Intent intent = new Intent(MathsMasteryActivity.this, AdditionMastery.class);
//                startActivity(intent);
//            }
//        });
//
//        subtractionMastery.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                // Start Subtraction Quiz Activity
//                Intent intent = new Intent(MathsMasteryActivity.this, SubtractionMastery.class);
//                startActivity(intent);
//            }
//        });
//
//        multiplicationMastery.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                // Start Multiplication Quiz Activity
//                Intent intent = new Intent(MathsMasteryActivity.this, MultiplicationMastery.class);
//                startActivity(intent);
//            }
//        });
//
//        divisionMastery.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                // Start Division Quiz Activity
//                Intent intent = new Intent(MathsMasteryActivity.this, DivisionMastery.class);
//                startActivity(intent);
//            }
//        });
//
//        // Set up back button functionality
//        backButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                finish(); // Close the current activity and return to the previous one
//            }
//        });
//    }
//}
