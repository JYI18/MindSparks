package com.example.quizkids.Activities;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.quizkids.R;
import com.google.android.material.textfield.TextInputEditText;
import androidx.appcompat.app.AppCompatActivity;
import java.util.Random;

public class MathsChallengeActivity extends AppCompatActivity {

    private TextView questionTextView, questionNumberTextView;
    private TextInputEditText answerInput;
    private Button submitAnswerButton;
    private int questionCounter = 1;
    private final int numQuestions = 10;
    private Random random;
    private int currentAnswer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maths_challenge);

        // Initialize views
        questionTextView = findViewById(R.id.questionTextView);
        questionNumberTextView = findViewById(R.id.questionNumberTextView);
        answerInput = findViewById(R.id.answerInputEditText);  // Use the correct ID for TextInputEditText
        // Corrected to fetch the TextInputEditText
        submitAnswerButton = findViewById(R.id.submitAnswerButton);

        random = new Random();

        // Start the quiz
        displayNextQuestion();

        // Handle answer submission
        submitAnswerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer();
            }
        });
    }

    // Method to generate and display the next question
    private void displayNextQuestion() {
        if (questionCounter <= numQuestions) {
            int number1 = random.nextInt(11 + 1);
            int number2 = random.nextInt(11 + 1);
            char operator = generateRandomOperator();
            currentAnswer = calculateAnswer(number1, number2, operator);

            // Display question
            questionTextView.setText(number1 + " " + operator + " " + number2 + " = ");
            questionNumberTextView.setText("Question " + questionCounter + "/" + numQuestions);
        } else {
            // End the quiz
            questionTextView.setText("Quiz Complete!");
            submitAnswerButton.setEnabled(false);
        }
    }

    // Method to randomly select an operator
    private char generateRandomOperator() {
        char[] operators = {'+', '-', '*', '÷'};
        return operators[random.nextInt(operators.length)];
    }

    // Method to calculate the answer based on the operator
    private int calculateAnswer(int num1, int num2, char operator) {
        switch (operator) {
            case '+':
                return num1 + num2;
            case '-':
                return num1 - num2;
            case 'x':
                num1 = random.nextInt(13); // Random number between 0 and 12
                num2 = random.nextInt(13); // Random number between 0 and 12
                return num1 * num2;
            case '÷':
                num2 = random.nextInt(12) + 1; // num2 between 1 and 12 (avoid division by 0)
                num1 = num2 * random.nextInt(13); // Ensure num1 is divisible by num2
                return num1 / num2; // Integer division
            default:
                return 0;
        }
    }

    private void checkAnswer() {
        String userAnswer = answerInput.getText().toString();
        if (!userAnswer.isEmpty()) {
            try {
                int userAnswerInt = Integer.parseInt(userAnswer);
                if (userAnswerInt == currentAnswer) {
                    // Correct answer
                    Toast.makeText(MathsChallengeActivity.this, "Correct!", Toast.LENGTH_SHORT).show();
                } else {
                    // Incorrect answer
                    Toast.makeText(MathsChallengeActivity.this, "Incorrect!", Toast.LENGTH_SHORT).show();
                }
                // Move to the next question regardless of correct or incorrect
                questionCounter++;
                displayNextQuestion();
                // Clear the input field
                answerInput.setText("");
            } catch (NumberFormatException e) {
                Toast.makeText(this, "Please enter a valid number.", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "Answer cannot be empty!", Toast.LENGTH_SHORT).show();
        }
    }

}