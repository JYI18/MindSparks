package com.example.quizkids.Activities;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.quizkids.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MathsQuizActivity extends AppCompatActivity {

    private TextView questionTextView, questionNumberTextView, timerTextView;
    private Button answerButton1, answerButton2, answerButton3, answerButton4, submitAnswerButton;
    private DatabaseReference databaseReference;
    private int currentQuestionIndex = 0;
    private List<Question> questionsList = new ArrayList<>();
    private int selectedAnswer = -1;
    private int correctAnswer;
    private int score = 0;
    private CountDownTimer countDownTimer;  // Timer for each question
    private static final long TIME_LIMIT = 30000; // 30 seconds per question

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maths_quiz);

        // Initialize views
        questionTextView = findViewById(R.id.questionTextView);
        questionNumberTextView = findViewById(R.id.questionNumberTextView);
        timerTextView = findViewById(R.id.timerTextView);
        answerButton1 = findViewById(R.id.answerButton1);
        answerButton2 = findViewById(R.id.answerButton2);
        answerButton3 = findViewById(R.id.answerButton3);
        answerButton4 = findViewById(R.id.answerButton4);
        submitAnswerButton = findViewById(R.id.submitAnswerButton);

        // Retrieve the level passed from MathsAdditionLevelActivity
        String category = getIntent().getStringExtra("category");
        int level = getIntent().getIntExtra("level", 1);  // Default to level 1 if no level is passed

        // Log the category and level for debugging
        Log.d("QuizActivity", "Category: " + category + ", Level: " + level);

        // Construct the Firebase reference dynamically based on the selected category and level
        String firebasePath = "maths/" + category + "/level" + level;
        Log.d("QuizActivity", "Firebase path: " + firebasePath);

        // Firebase reference
        databaseReference = FirebaseDatabase.getInstance("https://mindsparks-b5274-default-rtdb.asia-southeast1.firebasedatabase.app")
                .getReference(firebasePath);

        // Load questions from Firebase
        loadQuestionsFromFirebase();

        // Set click listeners for answer buttons
        setAnswerButtonListeners();

        // Submit button logic
        submitAnswerButton.setOnClickListener(v -> checkAnswer());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Cancel the timer to prevent memory leaks
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
    }

    private void loadQuestionsFromFirebase() {
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot questionSnapshot : dataSnapshot.getChildren()) {
                        // Log the data for debugging
                        Log.d("FirebaseData", "Question snapshot: " + questionSnapshot.toString());

                        // Parse the question object
                        Question question = questionSnapshot.getValue(Question.class);
                        if (question != null) {
                            questionsList.add(question);
                        } else {
                            Log.e("FirebaseError", "Question is null");
                        }
                    }
                    // If questions are loaded, display the first question
                    if (!questionsList.isEmpty()) {
                        displayQuestion();
                    } else {
                        Toast.makeText(MathsQuizActivity.this, "No questions found", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    // No data exists in the path
                    Toast.makeText(MathsQuizActivity.this, "No data found", Toast.LENGTH_SHORT).show();
                    Log.e("FirebaseError", "No data found at path");
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Handle database errors
                Log.e("FirebaseError", "Error: " + databaseError.getMessage());
                Toast.makeText(MathsQuizActivity.this, "Failed to load questions: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void displayQuestion() {
        if (currentQuestionIndex < questionsList.size()) {
            Question currentQuestion = questionsList.get(currentQuestionIndex);

            // Display the question and options
            questionTextView.setText(currentQuestion.getQuestionText());
            questionNumberTextView.setText("Question " + (currentQuestionIndex + 1) + "/" + questionsList.size());

            // Set answer options
            List<Integer> options = currentQuestion.getOptions();
            answerButton1.setText(String.valueOf(options.get(0)));
            answerButton2.setText(String.valueOf(options.get(1)));
            answerButton3.setText(String.valueOf(options.get(2)));
            answerButton4.setText(String.valueOf(options.get(3)));

            correctAnswer = currentQuestion.getCorrectAnswer();

            // Reset selected answer and button colors when a new question is displayed
            selectedAnswer = -1; // Reset the selected answer
            resetButtonColors(); // Reset the button colors to the default color

            // Start the timer for this question
            startTimer();
        } else {
            // End of quiz
            Toast.makeText(this, "Quiz Completed! Your score: " + score, Toast.LENGTH_LONG).show();
        }
    }

    // Start the countdown timer
    private void startTimer() {
        if (countDownTimer != null) {
            countDownTimer.cancel();  // Cancel the previous timer if it exists
        }

        countDownTimer = new CountDownTimer(TIME_LIMIT, 1000) {  // 30 seconds with 1-second intervals

            public void onTick(long millisUntilFinished) {
                // Update the timer text view every second
                timerTextView.setText("Time left: " + millisUntilFinished / 1000 + "s");
            }

            public void onFinish() {
                // Time is up! Move to the next question or handle no answer.
                Toast.makeText(MathsQuizActivity.this, "Time's up!", Toast.LENGTH_SHORT).show();
                currentQuestionIndex++;
                displayQuestion();
            }
        }.start();  // Start the timer
    }

    private void setAnswerButtonListeners() {
        int selectedColor = getResources().getColor(R.color.maths_selected_button_color);

        answerButton1.setOnClickListener(v -> {
            selectedAnswer = Integer.parseInt(answerButton1.getText().toString());
            resetButtonColors();
            answerButton1.setBackgroundColor(selectedColor);
        });

        answerButton2.setOnClickListener(v -> {
            selectedAnswer = Integer.parseInt(answerButton2.getText().toString());
            resetButtonColors();
            answerButton2.setBackgroundColor(selectedColor);
        });

        answerButton3.setOnClickListener(v -> {
            selectedAnswer = Integer.parseInt(answerButton3.getText().toString());
            resetButtonColors();
            answerButton3.setBackgroundColor(selectedColor);
        });

        answerButton4.setOnClickListener(v -> {
            selectedAnswer = Integer.parseInt(answerButton4.getText().toString());
            resetButtonColors();
            answerButton4.setBackgroundColor(selectedColor);
        });
    }

    private void resetButtonColors() {
        int defaultColor = getResources().getColor(R.color.maths_default_button_color);
        answerButton1.setBackgroundColor(defaultColor);
        answerButton2.setBackgroundColor(defaultColor);
        answerButton3.setBackgroundColor(defaultColor);
        answerButton4.setBackgroundColor(defaultColor);
    }

    private void checkAnswer() {
        if (selectedAnswer == -1) {
            Toast.makeText(this, "Please select an answer", Toast.LENGTH_SHORT).show();
            return;
        }

        if (selectedAnswer == correctAnswer) {
            score++;
        }

        // Move to next question
        currentQuestionIndex++;
        selectedAnswer = -1; // Reset selected answer
        displayQuestion();
    }

    // Question class to model data from Firebase
    public static class Question {
        private String questionText;
        private List<Integer> options;
        private int correctAnswer;

        public Question() {
            // Default constructor required for Firebase
        }

        public String getQuestionText() {
            return questionText;
        }

        public List<Integer> getOptions() {
            return options;
        }

        public int getCorrectAnswer() {
            return correctAnswer;
        }
    }
}
