package com.example.quizkids.Activities;

import android.content.Intent;
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

public class ScienceChallengeActivity extends AppCompatActivity {

    private TextView questionTextView, questionNumberTextView, timerTextView;
    private Button answerButton1, answerButton2, answerButton3, answerButton4, submitAnswerButton, viewScoreButton;
    private DatabaseReference databaseReference;
    private int currentQuestionIndex = 0;
    private List<ChallengeQuestion> questionsList = new ArrayList<>();
    private String selectedAnswer = "";
    private String correctAnswer;
    private int score = 0;
    private CountDownTimer countDownTimer;
    private static final long TIME_LIMIT = 30000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_science_challenge);

        // Initialize views
        questionTextView = findViewById(R.id.questionTextView);
        questionNumberTextView = findViewById(R.id.questionNumberTextView);
        timerTextView = findViewById(R.id.timerTextView);
        answerButton1 = findViewById(R.id.answerButton1);
        answerButton2 = findViewById(R.id.answerButton2);
        answerButton3 = findViewById(R.id.answerButton3);
        answerButton4 = findViewById(R.id.answerButton4);
        submitAnswerButton = findViewById(R.id.submitAnswerButton);
        viewScoreButton = findViewById(R.id.viewScoreButton);

        // Hide View Score button initially
        viewScoreButton.setVisibility(View.GONE);

        // Firebase reference
        databaseReference = FirebaseDatabase.getInstance("https://mindsparks-b5274-default-rtdb.asia-southeast1.firebasedatabase.app")
                .getReference("science/challenges");

        // Load questions from Firebase
        loadQuestionsFromFirebase();

        // Set click listeners for answer buttons and submit button
        setAnswerButtonListeners();
        submitAnswerButton.setOnClickListener(v -> checkAnswer());
        viewScoreButton.setOnClickListener(v -> openViewScoreActivity());
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
                        ChallengeQuestion question = questionSnapshot.getValue(ChallengeQuestion.class);
                        if (question != null) {
                            questionsList.add(question);
                        }
                    }
                    if (!questionsList.isEmpty()) {
                        displayQuestion();
                    } else {
                        Toast.makeText(ScienceChallengeActivity.this, "No questions found", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(ScienceChallengeActivity.this, "No data found", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(ScienceChallengeActivity.this, "Failed to load questions: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void displayQuestion() {
        if (currentQuestionIndex < questionsList.size()) {
            ChallengeQuestion currentQuestion = questionsList.get(currentQuestionIndex);

            // Display the question and options
            questionTextView.setText(currentQuestion.getQuestion());
            questionNumberTextView.setText("Question " + (currentQuestionIndex + 1) + "/" + questionsList.size());

            List<String> options = currentQuestion.getOptions();
            answerButton1.setText(options.get(0));
            answerButton2.setText(options.get(1));
            answerButton3.setText(options.get(2));
            answerButton4.setText(options.get(3));

            correctAnswer = currentQuestion.getCorrectAnswer();
            selectedAnswer = "";
            resetButtonColors();

            startTimer();
        } else {
            endQuiz();
        }
    }

    private void startTimer() {
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }

        countDownTimer = new CountDownTimer(TIME_LIMIT, 1000) {
            public void onTick(long millisUntilFinished) {
                timerTextView.setText("Time left: " + millisUntilFinished / 1000 + "s");
            }

            public void onFinish() {
                Toast.makeText(ScienceChallengeActivity.this, "Time's up!", Toast.LENGTH_SHORT).show();
                currentQuestionIndex++;
                if (currentQuestionIndex < questionsList.size()) {
                    displayQuestion();
                } else {
                    endQuiz();
                }
            }
        }.start();
    }

    private void setAnswerButtonListeners() {
        int selectedColor = getResources().getColor(R.color.maths_selected_button_color);

        answerButton1.setOnClickListener(v -> selectAnswer(answerButton1, selectedColor));
        answerButton2.setOnClickListener(v -> selectAnswer(answerButton2, selectedColor));
        answerButton3.setOnClickListener(v -> selectAnswer(answerButton3, selectedColor));
        answerButton4.setOnClickListener(v -> selectAnswer(answerButton4, selectedColor));
    }

    private void selectAnswer(Button button, int selectedColor) {
        selectedAnswer = button.getText().toString();
        resetButtonColors();
        button.setBackgroundColor(selectedColor);
    }

    private void resetButtonColors() {
        int defaultColor = getResources().getColor(R.color.maths_default_button_color);
        answerButton1.setBackgroundColor(defaultColor);
        answerButton2.setBackgroundColor(defaultColor);
        answerButton3.setBackgroundColor(defaultColor);
        answerButton4.setBackgroundColor(defaultColor);
    }

    private void checkAnswer() {
        if (selectedAnswer.isEmpty()) {
            Toast.makeText(this, "Please select an answer", Toast.LENGTH_SHORT).show();
            return;
        }

        if (selectedAnswer.equals(correctAnswer)) {
            score++;
        }

        currentQuestionIndex++;
        selectedAnswer = "";
        displayQuestion();
    }

    private void endQuiz() {
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }

        viewScoreButton.setVisibility(View.VISIBLE); // Show View Score button at the end of the quiz
    }

    private void openViewScoreActivity() {
        SharedPreferences prefs = getSharedPreferences("QuizScores", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt("science_challenge_score", score);
        editor.apply();

        Intent intent = new Intent(this, ViewScoreActivity.class);
        intent.putExtra("SCORE", score);
        intent.putExtra("CATEGORY", "Science Challenge");
        startActivity(intent);
        finish();
    }

    public static class ChallengeQuestion {
        private String question;
        private String correctAnswer;
        private List<String> options;

        public ChallengeQuestion() {}

        public String getQuestion() {
            return question;
        }

        public String getCorrectAnswer() {
            return correctAnswer;
        }

        public List<String> getOptions() {
            return options;
        }
    }
}
