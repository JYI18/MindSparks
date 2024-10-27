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
    private double currentAnswer;

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
        }
    }

    // Method to randomly select an operator
    private char generateRandomOperator() {
        char[] operators = {'+', '-', 'x', '÷'};
        return operators[random.nextInt(operators.length)];
    }

    // Method to calculate the answer based on the operator
    private double calculateAnswer(int num1, int num2, char operator) {
        switch (operator) {
            case '+':
                return num1 + num2;
            case '-':
                if (num2 > num1) {
                    // Swap num1 and num2 to ensure num1 > num2
                    int temp = num2;
                    num2 = num1;
                    num1 = temp;
                }
                return num1 - num2;

            case 'x':
                num1 = random.nextInt(13); // Random number between 0 and 12
                num2 = random.nextInt(13); // Random number between 0 and 12
                return num1 * num2;

            case '÷':
                num1 = random.nextInt(13);
                num2 = random.nextInt(13) + 1;
                double answer = num1/num2;
                return (double) num1 / num2; // Cast num1 to double before division

            default:
                return 0;
        }
    }

    private void checkAnswer() {
        String userAnswer = answerInput.getText().toString();
        if (!userAnswer.isEmpty()) {
            try {
                double userAnswerDouble = Double.parseDouble(userAnswer); // Use Double here
                if (Math.abs(userAnswerDouble - currentAnswer) < 0.01) { // Allow a small tolerance for decimals
                    // Correct answer
                    Toast.makeText(MathsChallengeActivity.this, "Correct!", Toast.LENGTH_SHORT).show();
                } else {
                    // Incorrect answer
                    Toast.makeText(MathsChallengeActivity.this, "Incorrect!", Toast.LENGTH_SHORT).show();
                }
                // Move to the next question
                questionCounter++;
                displayNextQuestion();
                answerInput.setText("");
            } catch (NumberFormatException e) {
                Toast.makeText(this, "Please enter a valid number.", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "Answer cannot be empty!", Toast.LENGTH_SHORT).show();
        }
    }


}