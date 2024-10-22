package com.example.quizkids.Activities;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
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

import java.util.List;

public class MathsQuizActivity extends AppCompatActivity {

    private TextView questionTextView, timerTextView, questionNumberTextView;
    private Button answerButton1, answerButton2, answerButton3, answerButton4, submitAnswerButton;

    private int currentQuestionIndex = 0;
    private int correctAnswer;
    private int score = 0;

    private DatabaseReference quizRef;
    private List<Object> options;
    private CountDownTimer countDownTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maths_quiz);

        // Initialize UI components
        questionTextView = findViewById(R.id.questionTextView);
        timerTextView = findViewById(R.id.timerTextView);
        questionNumberTextView = findViewById(R.id.questionNumberTextView);
        answerButton1 = findViewById(R.id.answerButton1);
        answerButton2 = findViewById(R.id.answerButton2);
        answerButton3 = findViewById(R.id.answerButton3);
        answerButton4 = findViewById(R.id.answerButton4);
        submitAnswerButton = findViewById(R.id.submitAnswerButton);

        // Get the level from intent
        int level = getIntent().getIntExtra("level", 1);

        // Firebase reference to questions for the selected level
        quizRef = FirebaseDatabase.getInstance().getReference("maths/addition/level" + level);


        loadNextQuestion();

        submitAnswerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer();
                loadNextQuestion();
            }
        });
    }

    // Method to start and reset the countdown timer
    private void startTimer() {
        countDownTimer = new CountDownTimer(30000, 1000) { // 30-second countdown
            @Override
            public void onTick(long millisUntilFinished) {
                timerTextView.setText("Time left: " + millisUntilFinished / 1000 + " seconds");
            }

            @Override
            public void onFinish() {
                Toast.makeText(MathsQuizActivity.this, "Time's up!", Toast.LENGTH_SHORT).show();
                loadNextQuestion(); // Automatically load the next question if time runs out
            }
        }.start();
    }

    // Method to load the next question
    private void loadNextQuestion() {
        currentQuestionIndex++;
        questionNumberTextView.setText("Question " + currentQuestionIndex + "/5");

        // Reset the timer when loading a new question
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
        startTimer();

        // Get the question data from Firebase
        quizRef.child("question" + currentQuestionIndex).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    // Get the question text
                    String questionText = dataSnapshot.child("questionText").getValue(String.class);
                    questionTextView.setText(questionText); // Set the question text

                    // Get the correct answer
                    correctAnswer = dataSnapshot.child("correctAnswer").getValue(Integer.class);

                    // Get the options (ensure it's retrieved as a List)
                    options = (List<Object>) dataSnapshot.child("options").getValue();

                    if (options != null && options.size() == 4) {
                        // Set the options to the buttons
                        answerButton1.setText(String.valueOf(options.get(0)));
                        answerButton2.setText(String.valueOf(options.get(1)));
                        answerButton3.setText(String.valueOf(options.get(2)));
                        answerButton4.setText(String.valueOf(options.get(3)));
                    } else {
                        // Handle case where options are missing or invalid
                        Toast.makeText(MathsQuizActivity.this, "Options not available.", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    // No more questions, end the quiz
                    Toast.makeText(MathsQuizActivity.this, "Quiz finished!", Toast.LENGTH_SHORT).show();
                    finish(); // End activity or go to a score screen
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(MathsQuizActivity.this, "Failed to load question.", Toast.LENGTH_SHORT).show();
            }
        });
    }


    // Method to check the selected answer
    private void checkAnswer() {
        int selectedAnswer = -1;
        if (answerButton1.isPressed()) {
            selectedAnswer = Integer.parseInt(answerButton1.getText().toString());
        } else if (answerButton2.isPressed()) {
            selectedAnswer = Integer.parseInt(answerButton2.getText().toString());
        } else if (answerButton3.isPressed()) {
            selectedAnswer = Integer.parseInt(answerButton3.getText().toString());
        } else if (answerButton4.isPressed()) {
            selectedAnswer = Integer.parseInt(answerButton4.getText().toString());
        }

        if (selectedAnswer == correctAnswer) {
            score++;
            Toast.makeText(this, "Correct!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Wrong!", Toast.LENGTH_SHORT).show();
        }
    }
}
