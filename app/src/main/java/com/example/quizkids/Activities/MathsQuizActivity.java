package com.example.quizkids.Activities;

import android.content.SharedPreferences;
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
    private CountDownTimer countDownTimer;
    private long TIME_LIMIT = 30000;  // Default 30 seconds

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

        // Retrieve the level and category passed from MathsAdditionLevelActivity
        String category = getIntent().getStringExtra("category");
        int level = getIntent().getIntExtra("level", 1);  // Default to level 1 if no level is passed

        Log.d("QuizActivity", "Category: " + category + ", Level: " + level);

        // Construct the Firebase reference dynamically based on the selected category and level
        String firebasePath = "maths/" + category + "/level" + level;
        Log.d("QuizActivity", "Firebase path: " + firebasePath);

        databaseReference = FirebaseDatabase.getInstance("https://mindsparks-b5274-default-rtdb.asia-southeast1.firebasedatabase.app")
                .getReference(firebasePath);

        // Load questions from Firebase
        loadQuestionsFromFirebase();

        // Set click listeners for answer buttons
        setAnswerButtonListeners();

        // Submit button logic
        submitAnswerButton.setOnClickListener(v -> checkAnswer());

        // Retrieve the selected timer value from SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("QuizSettings", MODE_PRIVATE);
        int timerValue = sharedPreferences.getInt("timerValue", 30);  // Default to 30 seconds if not set
        TIME_LIMIT = timerValue * 1000;  // Convert to milliseconds

        Log.d("QuizActivity", "Timer value: " + timerValue + " seconds");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
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
                        Log.d("FirebaseData", "Question snapshot: " + questionSnapshot.toString());

                        Question question = questionSnapshot.getValue(Question.class);
                        if (question != null) {
                            questionsList.add(question);
                        } else {
                            Log.e("FirebaseError", "Question is null");
                        }
                    }
                    if (!questionsList.isEmpty()) {
                        displayQuestion();
                    } else {
                        Toast.makeText(MathsQuizActivity.this, "No questions found", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(MathsQuizActivity.this, "No data found", Toast.LENGTH_SHORT).show();
                    Log.e("FirebaseError", "No data found at path");
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e("FirebaseError", "Error: " + databaseError.getMessage());
                Toast.makeText(MathsQuizActivity.this, "Failed to load questions: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void displayQuestion() {
        if (currentQuestionIndex < questionsList.size()) {
            Question currentQuestion = questionsList.get(currentQuestionIndex);

            questionTextView.setText(currentQuestion.getQuestionText());
            questionNumberTextView.setText("Question " + (currentQuestionIndex + 1) + "/" + questionsList.size());

            List<Integer> options = currentQuestion.getOptions();
            answerButton1.setText(String.valueOf(options.get(0)));
            answerButton2.setText(String.valueOf(options.get(1)));
            answerButton3.setText(String.valueOf(options.get(2)));
            answerButton4.setText(String.valueOf(options.get(3)));

            correctAnswer = currentQuestion.getCorrectAnswer();

            selectedAnswer = -1;  // Reset selected answer
            resetButtonColors();  // Reset button colors

            startTimer();  // Start the countdown timer
        } else {
            Toast.makeText(this, "Quiz Completed! Your score: " + score, Toast.LENGTH_LONG).show();
        }
    }

    private void startTimer() {
        if (countDownTimer != null) {
            countDownTimer.cancel();  // Cancel previous timer
        }

        countDownTimer = new CountDownTimer(TIME_LIMIT, 1000) {

            public void onTick(long millisUntilFinished) {
                timerTextView.setText("Time left: " + millisUntilFinished / 1000 + "s");
            }

            public void onFinish() {
                Toast.makeText(MathsQuizActivity.this, "Time's up!", Toast.LENGTH_SHORT).show();
                currentQuestionIndex++;
                displayQuestion();
            }
        }.start();
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

        currentQuestionIndex++;
        selectedAnswer = -1;  // Reset selected answer
        displayQuestion();
    }

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
